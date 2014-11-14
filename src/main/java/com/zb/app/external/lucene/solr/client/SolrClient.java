/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.solr.client;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.BinaryResponseParser;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest.Create;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.result.Result;
import com.zb.app.external.lucene.solr.callback.SolrCallback;
import com.zb.app.external.lucene.solr.exception.SolrServerUnAvailableException;
import com.zb.app.external.lucene.solr.utils.BaseSolrQueryConvert;
import com.zb.app.external.lucene.solr.utils.CounterMonitor;

/**
 * Solr Client interacts with the class
 * 
 * @author zxc Sep 2, 2014 1:40:14 PM
 */
@Component
public class SolrClient {

    private Logger                                             logger            = LoggerFactory.getLogger(SolrClient.class);

    // RFC规范是255个字节，但多数浏览器不要超过2000个，IE8+ 支持2083,搜索引擎对超过2048个字符以上就不索引了。
    private static final int                                   ERROR_STATUS      = 0;
    private static final int                                   MAX_URL_LENGTH    = 200;
    private CounterMonitor                                     counterMonitor    = null;
    private volatile ConcurrentHashMap<String, HttpSolrServer> httpSolrServerMap = new ConcurrentHashMap<String, HttpSolrServer>();

    @Value("${root.solr.server.url}")
    protected String                                           rootSolrServerUrl;
    @Value("${root.index.dir}")
    protected String                                           rootIndexDir;
    @Value("${is.load.solr}")
    protected boolean                                          isLoadSolr;

    SolrServer                                                 solrServer;

    /**
     * client的初始化
     * 
     * <pre>
     * 1.从properits中获取SolrServer地址,索引文件根地址
     * 2.创建一个错误计数器（以便服务端出错后，及时丢弃池化的连接）
     * </pre>
     */
    @PostConstruct
    public void init() {
        if (isLoadSolr) {
            if (StringUtils.isEmpty(rootSolrServerUrl) || StringUtils.isEmpty(rootIndexDir)) {
                throw new RuntimeException("rootSolrServerUrl and rootIndexDir should not empty");
            }
            counterMonitor = new CounterMonitor(0, TimeUnit.SECONDS.toMillis(15));
            solrServer = getRootSolrServer();
            logger.debug("create or connection root solr server,successful! solr server url={}", rootSolrServerUrl);
        }
    }

    /**
     * solr的查询方法
     * 
     * @param corename 命名空间，用户区分不同的搜索类型
     * @param returnType 返回值的Class类型
     * @param solrQuery 使用{@link SolrQueryConvert}进行转化
     * @return
     */
    public <T> List<T> query(String corename, final Class<T> returnType, final SolrQuery solrQuery,
                             final SolrCallback... callbacks) {
        final HttpSolrServer server = getOrCreateSolrServer(corename);
        final List<T> queryResult = new ArrayList<T>();
        exec(new Executor() {

            public Result exec() throws SolrServerException, IOException {
                QueryResponse query = null;
                if (solrQuery.toString().length() > MAX_URL_LENGTH) {
                    query = server.query(solrQuery, SolrRequest.METHOD.POST);
                } else {
                    query = server.query(solrQuery, SolrRequest.METHOD.GET);
                }
                List<T> beans = query.getBeans(returnType);
                if (beans != null) {
                    queryResult.addAll(beans);
                }
                if (Argument.isNotEmptyArray(callbacks)) {
                    for (SolrCallback callback : callbacks) {
                        callback.handleSolrResult(query);
                    }
                }
                return Result.success();
            }
        });
        return queryResult;
    }

    /**
     * solr的高亮查询方法
     * 
     * @param corename 命名空间，用户区分不同的搜索类型
     * @param returnType 返回值的Class类型
     * @param solrQuery 使用{@link SolrQueryConvert}进行转化
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> querys(String corename, final Class<T> returnType, final SolrQuery solrQuery) {
        // 设置高亮
        solrQuery.setParam("hl", "true"); // highlighting
        solrQuery.setParam("hl.fl", "lTile", "lMode", "lYesItem", "lNoItem", "lChildren", "lShop", "lExpenseItem",
                           "lPreseItem", "rContent", "rCar");
        solrQuery.setHighlightSimplePre("<font color=\"red\">");
        solrQuery.setHighlightSimplePost("</font>");
        final SolrDocumentList list = new SolrDocumentList();
        final HttpSolrServer server = getOrCreateSolrServer(corename);
        exec(new Executor() {

            public Result exec() throws SolrServerException, IOException {
                QueryResponse query = null;
                if (solrQuery.toString().length() > MAX_URL_LENGTH) {
                    query = server.query(solrQuery, SolrRequest.METHOD.POST);
                } else {
                    query = server.query(solrQuery, SolrRequest.METHOD.GET);
                }
                SolrDocumentList documents = query.getResults();
                Map<String, Map<String, List<String>>> map = query.getHighlighting();
                for (SolrDocument document : documents) {
                    document.setField("lTile", map.get(document.getFieldValue("id")).get("lTile"));
                    list.add(document);
                }
                return Result.success();
            }
        });
        return (List<T>) toBeanList(list, returnType);
    }

    /**
     * solr的autoComplete方法
     * 
     * @param corename
     * @param solrQuery
     * @return
     */
    public List<Term> suggest(String corename, final SolrQuery solrQuery) {
        final HttpSolrServer server = getOrCreateSolrServer(corename);
        // final List<FacetField> queryResult = new ArrayList<FacetField>();
        final List<Term> queryResult = new ArrayList<Term>();
        exec(new Executor() {

            public Result exec() throws SolrServerException, IOException {
                QueryResponse query = null;
                if (solrQuery.toString().length() > MAX_URL_LENGTH) {
                    query = server.query(solrQuery, SolrRequest.METHOD.POST);
                } else {
                    query = server.query(solrQuery, SolrRequest.METHOD.GET);
                }
                // List<FacetField> list = query.getFacetFields();
                // if (Argument.isNotEmpty(list)) {
                // queryResult.addAll(list);
                // }
                TermsResponse resp = query.getTermsResponse();
                queryResult.addAll(resp.getTerms(solrQuery.getTermsRegexFlags()[0]));
                return Result.success();
            }
        });
        return queryResult;
    }

    public <T> boolean addBean(String corename, final T bean) {
        ArrayList<T> beans = new ArrayList<T>();
        beans.add(bean);
        return addBeans(corename, beans);
    }

    /**
     * 索引方法
     */
    public boolean addBeans(String corename, final List<?> beans) {
        final HttpSolrServer server = getOrCreateSolrServer(corename);
        Result result = exec(new Executor() {

            @Override
            public Result exec() throws SolrServerException, IOException {
                UpdateResponse addBeans = server.addBeans(beans);
                server.commit();
                return new Result().setSuccess(addBeans.getStatus() == ERROR_STATUS);
            }
        });
        return result.isSuccess();
    }

    /**
     * 清空所有
     * 
     * @param corename
     * @return
     */
    public boolean delAll(String corename) {
        final HttpSolrServer server = getOrCreateSolrServer(corename);
        Result result = exec(new Executor() {

            @Override
            public Result exec() throws SolrServerException, IOException {
                String query = BaseSolrQueryConvert.toAll().getQuery();
                UpdateResponse deleteByQuery = server.deleteByQuery(query);
                server.commit();
                return new Result().setSuccess(deleteByQuery.getStatus() == ERROR_STATUS);
            }
        });
        return result.isSuccess();

    }

    /**
     * 删除
     */
    public boolean del(String corename, final SolrQuery solrQuery) {
        final HttpSolrServer server = getOrCreateSolrServer(corename);
        final String query = solrQuery.getQuery();
        Result result = exec(new Executor() {

            @Override
            public Result exec() throws SolrServerException, IOException {
                UpdateResponse deleteByQuery = server.deleteByQuery(query);
                server.commit();
                return new Result().setSuccess(deleteByQuery.getStatus() == ERROR_STATUS);
            }
        });
        return result.isSuccess();
    }

    public boolean commit() {
        Result result = exec(new Executor() {

            @Override
            public Result exec() throws SolrServerException, IOException {
                for (HttpSolrServer solrServ : httpSolrServerMap.values()) {
                    UpdateResponse updateResponse = solrServ.commit();
                    if (updateResponse.getStatus() != 0) {
                        return Result.failed();
                    }
                }
                return Result.success();
            }
        });
        return result.isSuccess();
    }

    protected void commitAndNewServer() {
        try {
            this.commit();
        } catch (Throwable t) {
            // pass away
        }
        httpSolrServerMap = new ConcurrentHashMap<String, HttpSolrServer>();

    }

    /**
     * 获取或者创建一个corename的server
     */
    public HttpSolrServer getOrCreateSolrServer(String corename) {
        if (httpSolrServerMap.get(corename) != null) {
            return httpSolrServerMap.get(corename);
        }
        // create
        synchronized (httpSolrServerMap) {
            if (!httpSolrServerMap.contains(corename)) {
                HttpSolrServer solrServer = createSolrServer(corename);
                httpSolrServerMap.putIfAbsent(corename, solrServer);
            }
        }
        return httpSolrServerMap.get(corename);
    }

    private HttpSolrServer createSolrServer(String corename) {
        createDateDirIfNotExisted(corename);
        String solrServerUrl = this.rootSolrServerUrl + corename + "/";
        HttpSolrServer solrServer = newSolrServer(solrServerUrl);
        return solrServer;

    }

    private HttpSolrServer newSolrServer(String solrServerUrl) {
        HttpSolrServer solrServer = new HttpSolrServer(solrServerUrl);
        solrServer.setSoTimeout(60000);
        solrServer.setConnectionTimeout(5000);
        solrServer.setDefaultMaxConnectionsPerHost(50);
        solrServer.setMaxTotalConnections(100);
        solrServer.setFollowRedirects(false);
        solrServer.setAllowCompression(false);
        solrServer.setMaxRetries(1);
        solrServer.setParser(new BinaryResponseParser());
        solrServer.setRequestWriter(new BinaryRequestWriter());
        commit();
        return solrServer;
    }

    private void createDateDirIfNotExisted(String corename) {
        boolean solrCoreExist = isDataDirExsited(corename);
        if (!solrCoreExist) {
            Create create = new Create();
            create.setCoreName(corename);
            create.setDataDir(rootIndexDir + File.separator + corename);
            create.setInstanceDir(rootIndexDir + File.separator + corename);
            try {
                solrServer.request(create);
            } catch (SolrServerException e) {
                logger.error("createSolrCore", e);
                throw new RuntimeException("createSolrCore", e);
            } catch (IOException e) {
                logger.error("createSolrCore", e);
                throw new RuntimeException("createSolrCore", e);
            }
            this.commit();
        }
    }

    private SolrServer getRootSolrServer() {
        if (solrServer == null) {
            synchronized (SolrClient.class) {
                if (solrServer == null) {
                    solrServer = newSolrServer(rootSolrServerUrl);
                }
            }
        }
        return solrServer;
    }

    private boolean isDataDirExsited(String namespace) {
        CoreAdminResponse status;
        try {
            status = CoreAdminRequest.getStatus(namespace, solrServer);
        } catch (SolrServerException e) {
            logger.error("isSolrCoreExist", e);
            throw new SolrServerUnAvailableException("isSolrCoreExist", e);
        } catch (IOException e) {
            logger.error("isSolrCoreExist", e);
            throw new SolrServerUnAvailableException("isSolrCoreExist", e);
        }
        return status != null && status.getCoreStatus(namespace).get("instanceDir") != null;
    }

    protected Result exec(Executor executor) {
        try {
            return executor.exec();
        } catch (SolrServerException e) {
            logger.error(e.getMessage(), e);
            counterMonitor.pulse();
            if (counterMonitor.isExceedWithInForOnce()) {
                commitAndNewServer();
            }
            return Result.failed();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return Result.failed();
        }

    }

    static interface Executor {

        Result exec() throws SolrServerException, IOException;
    }

    public static Object toBean(SolrDocument record, Class<?> clazz) {

        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = record.get(field.getName());
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            try {
                BeanUtils.setProperty(o, field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    public static Object toBeanList(SolrDocumentList records, Class<?> clazz) {
        List<Object> list = new ArrayList<Object>();
        for (SolrDocument record : records) {
            list.add(toBean(record, clazz));
        }
        return list;
    }
}
