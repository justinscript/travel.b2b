/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zb.app.biz.cons.CompanyStateEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.pagination.PagesPagination;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.util.DateViewTools;
import com.zb.app.external.lucene.analyzer.JcsegWordAnalyzer;
import com.zb.app.external.lucene.analyzer.cons.SegMode;
import com.zb.app.external.lucene.search.build.SearchBuilder;
import com.zb.app.external.lucene.search.build.SearchBuilder.Counter;
import com.zb.app.external.lucene.search.build.base.Param;
import com.zb.app.external.lucene.search.cons.SearchTypeEnum;
import com.zb.app.external.lucene.search.cons.VersionType;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;
import com.zb.app.external.lucene.search.query.ProductSearchQuery;
import com.zb.app.external.lucene.search.query.WordSearchQuery;
import com.zb.app.external.lucene.search.service.AppBaseSearch;
import com.zb.app.external.lucene.search.service.ProductSearch;
import com.zb.app.external.lucene.search.service.WordSearch;
import com.zb.app.external.lucene.solr.query.SuggestQuery;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.SiteCacheTools;
import com.zb.app.web.tools.SystemInfos;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.ChufaFullVO;
import com.zb.app.web.vo.ColumnThinVO;
import com.zb.app.web.vo.SolrVersionVO;

/**
 * @author zxc Sep 2, 2014 4:32:48 PM
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController extends BaseController {

    @Autowired
    private JcsegWordAnalyzer                 analyzer;
    @Autowired
    private WordSearch<WordSearchQuery>       wordSearch;
    @Autowired
    private ProductSearch<ProductSearchQuery> productSearch;
    @Autowired
    private ProductSearch<SuggestQuery>       productComplete;

    private static String                     useage = "缺少Key";

    @RequestMapping(value = "/index.htm")
    public ModelAndView search(ModelAndView mav) {
        List<SearchTypeEnum> searchs = new ArrayList<SearchTypeEnum>();
        searchs.add(SearchTypeEnum.WORD);
        searchs.add(SearchTypeEnum.PRODUCT);
        Map<SearchTypeEnum, List<SolrVersionVO>> versionMap = new HashMap<SearchTypeEnum, List<SolrVersionVO>>();
        for (SearchTypeEnum searchType : searchs) {
            if (StringUtils.isNotBlank(searchType.getVersionType())) {
                versionMap.put(searchType, getVersions(searchType.getVersionType()));
            }
        }
        mav.addObject("searchs", searchs);
        mav.addObject("versionMap", versionMap);
        mav.setViewName("search/search");
        return mav;
    }

    @RequestMapping(value = "/getAllVersion.htm", produces = "application/json")
    @ResponseBody
    public JsonResult getAllVersion(String versionType) {
        if (versionType == null) {
            return JsonResultUtils.error("缺少versionType");
        }
        List<SolrVersionVO> result = getVersions(versionType);
        return JsonResultUtils.success(result);
    }

    private List<SolrVersionVO> getVersions(String versionType) {
        List<SolrVersionVO> result = new ArrayList<SolrVersionVO>();
        result.add(new SolrVersionVO(DateViewTools.formatFullDate(new Date()), 1, 1));
        return result;
    }

    @RequestMapping(value = "/home.htm")
    public ModelAndView home() {
        return new ModelAndView("search/fullIndex");
    }

    @RequestMapping(value = "/find.htm")
    public ModelAndView find() {
        return new ModelAndView("search/find");
    }

    @RequestMapping(value = "/lineSearch.htm")
    public ModelAndView linesearch(ModelAndView model) {
        model.setViewName("search/searchLine");
        return model;
    }

    @RequestMapping(value = "/productSearch.htm")
    public ModelAndView linesearch(ModelAndView model, final String type, final String keyword, boolean expectMatch,
                                   final ProductSearchQuery query, Integer page) {
        // 类型设置
        SearchTypeEnum searchType = SearchTypeEnum.getByValue(StringUtils.isEmpty(type) ? "zuobian" : type);
        if (searchType == null) {
            searchType = SearchTypeEnum.PRODUCT;
        }
        // 版本设置
        Integer version = getVersion(VersionType.product);
        // 专线设置
        if (query.getzId() == null) {
            SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
            ChufaFullVO chugang = siteCacheTools.getChugangByChugangId(WebUserTools.getChugangId());
            Map<Integer, List<ColumnThinVO>> column = chugang.getColumnMap();
            List<ColumnThinVO> columnlist = new ArrayList<ColumnThinVO>();
            for (List<ColumnThinVO> list : column.values()) {
                columnlist.addAll(list);
            }
            query.setzIds(CollectionUtils.getLongValueArrays(columnlist, "zId"));
        } else {
            query.setzIds(query.getzId());
        }

        // 分词
        List<String> _segWords = StringUtils.isEmpty(keyword) ? Collections.<String> emptyList() : analyzer.segWords(keyword);
        query.setProducts(_segWords);
        query.setExpectMatch(expectMatch);

        // 分页
        page = Argument.isNotPositive(page) ? 0 : page - 1;
        query.setNowPageIndex(page);
        query.setStart(query.getNowPageIndex() * query.getRows());

        // 查询到的集合
        List<ProductSearchField> list = productSearch.search(version, query);
        for (ProductSearchField productSearchField : list) {
            for (String regex : _segWords) {
                handleSearchResult(productSearchField, regex);
            }
        }

        PagesPagination pagination = PaginationParser.getPaginationList(page, query.getRows(), query.getAllRecordNum(),
                                                                        new IPageUrl() {

                                                                            @Override
                                                                            public String parsePageUrl(Object... objs) {
                                                                                String str = "/search/productSearch.htm?page="
                                                                                             + (Integer) objs[1];
                                                                                if (type != null) {
                                                                                    str += "&type=" + type;
                                                                                }
                                                                                if (keyword != null) {
                                                                                    str += "&keyword=" + keyword;
                                                                                }
                                                                                if (query.getlArrivalCity() != null) {
                                                                                    str += "&lArrivalCity="
                                                                                           + query.getlArrivalCity();
                                                                                }
                                                                                if (query.getlDay() != null) {
                                                                                    str += "&lDay=" + query.getlDay();
                                                                                }
                                                                                if (query.getlType() != null) {
                                                                                    str += "&lType=" + query.getlType();
                                                                                }
                                                                                if (query.getzId() != null) {
                                                                                    str += "&zId=" + query.getzId();
                                                                                }
                                                                                return str;
                                                                            }
                                                                        });

        model.addObject("linelist", list);
        model.addObject("keyword", keyword);
        model.addObject("lDay", query.getlDay());
        model.addObject("lType", query.getlType());
        model.addObject("city", query.getlArrivalCity());
        model.addObject("zId", query.getzId());
        model.addObject("searchcount", query.getAllRecordNum());
        model.addObject("pagination", pagination);
        model.setViewName("search/searchLine");

        // 获取所有抵达城市
        model.addObject("citylists", lineService.getCityByCid(WebUserTools.getChugangId()));
        
        // 商家推荐
        TravelCompanyQuery companyQuery = new TravelCompanyQuery();
        companyQuery.setNowPageIndex(0);
        companyQuery.setPageSize(6);
        companyQuery.setcType(CompanyTypeEnum.ACCOUNT.getValue());
        companyQuery.setcState(CompanyStateEnum.NORMAL.getValue());
        PaginationList<TravelCompanyDO> companyDOs = companyService.showCompanyPagination(companyQuery,
                                                                                          new DefaultIpageUrl());
        model.addObject("companyDOs", companyDOs);
        return model;
    }

    @RequestMapping(value = "/fullIndex.htm", produces = "application/json")
    @ResponseBody
    public JsonResult fullIndex(final String _force, final String key, final String param, final Integer version) {
        final SearchTypeEnum searchType = SearchTypeEnum.getByValue(key);
        if (searchType == null) {
            return JsonResultUtils.error();
        }
        new Thread() {

            public void run() {
                logger.debug("***********************************fullIndex.htm*********************************");
                Param fromJson = null;
                if (param != null) {
                    try {
                        Gson gson = new GsonBuilder().create();
                        fromJson = gson.fromJson(param, Param.class);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                boolean force = "y".equalsIgnoreCase(_force);
                String info = SystemInfos.getHostname() + "@" + SystemInfos.getIpaddress();
                SearchBuilder.build(searchType, version, force, info, searchType.getDateFetcher(fromJson));
            }
        }.start();
        return JsonResultUtils.success(null, "开始build...");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/search.htm", produces = "application/json")
    @ResponseBody
    public JsonResult search(String type, @RequestParam("keyword") String keyword, boolean expectMatch) {
        long starttime = System.currentTimeMillis();
        SearchTypeEnum searchType = SearchTypeEnum.getByValue(type);
        if (searchType == null) {
            searchType = SearchTypeEnum.PRODUCT;
        }
        Object data = null;
        Integer version = null;
        switch (searchType) {
            case WORD:
                version = getVersion(VersionType.word);
                List<String> segWords = analyzer.segWords(keyword);
                data = wordSearch.search(version, new WordSearchQuery(segWords));
                break;

            case PRODUCT:
                version = getVersion(VersionType.product);
                List<String> _segWords = analyzer.segWords(keyword);

                ProductSearchQuery query = new ProductSearchQuery(_segWords);

                query.setExpectMatch(expectMatch);
                data = productSearch.search(version, query);
                List<ProductSearchField> list = (List<ProductSearchField>) data;
                for (ProductSearchField productSearchField : list) {
                    for (String regex : _segWords) {
                        handleSearchResult(productSearchField, regex);
                    }
                }
                data = list;
        }
        logger.error("search Product user time : 【{}】", System.currentTimeMillis() - starttime);
        return JsonResultUtils.success(data);
    }

    private static String highLight(String text, String search) {
        if (StringUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }
        String replacement = "<font style='color:red;'>" + search + "</font>";
        return StringUtils.replace(text, search, replacement);
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/autoComplete.htm", produces = "application/json")
    @ResponseBody
    public JsonResult autoComplete(String type, String q, Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        SearchTypeEnum searchType = SearchTypeEnum.getByValue(type);
        if (searchType == null) {
            searchType = SearchTypeEnum.PRODUCT;
        }
        Integer version = null;
        switch (searchType) {
            case WORD:
                version = getVersion(VersionType.word);
                List<String> segWords = analyzer.segWords(q);
                Object data = wordSearch.search(version, new WordSearchQuery(segWords));
                break;

            case PRODUCT:
                version = getVersion(VersionType.product);
                SuggestQuery query = new SuggestQuery("lTitle", q == null ? "" : q);
                List<ProductSearchField> list = productComplete.search(version, query);
                Set<String> set = new HashSet<String>(CollectionUtils.getProperty(list, "lTitle"));
                return JsonResultUtils.success(set);
        }
        return JsonResultUtils.success("暂无数据!");
    }

    @RequestMapping(value = "/manage.htm", produces = "application/json")
    @ResponseBody
    public JsonResult manage(String option, final String _force, final String key, final String param,
                             final Integer version) {
        if ("index".equalsIgnoreCase(option)) {
            return fullIndex(_force, key, param, version);
        } else if ("peek".equalsIgnoreCase(option)) {
            return peek(key, version);
        } else if ("useNewVersion".equalsIgnoreCase(option)) {
            return useNewVersion(key, version, false);
        } else if ("createNewVersion".equalsIgnoreCase(option)) {
            return useNewVersion(key, version, true);
        } else if ("clear".equalsIgnoreCase(option)) {
            return clear(key, version);
        }
        return JsonResultUtils.error("不支持的操作");
    }

    @RequestMapping(value = "/analy.htm", produces = "application/json")
    @ResponseBody
    public JsonResult analy(Map<String, Object> model, String keyword, String mode) {
        SegMode segMode = SegMode.getByValue(mode);
        String segWords = analyzer.segWords(keyword, " | ", segMode);
        return JsonResultUtils.success(segWords, false);
    }

    @RequestMapping(value = "/reloadDic.htm", produces = "application/json")
    @ResponseBody
    public JsonResult reloadDic(Map<String, Object> model) {
        boolean reload = analyzer.reload();
        return JsonResultUtils.success(null, reload ? "成功" : "失败");
    }

    // ///////////////////////////////////////////////////////////////
    // ////
    // //// private method
    // ////
    // ///////////////////////////////////////////////////////////////
    private void handleSearchResult(ProductSearchField productSearchField, String regex) {
        productSearchField.setlTile(highLight(productSearchField.getlTile(), regex));
        productSearchField.setlMode(highLight(productSearchField.getlMode(), regex));
        productSearchField.setlYesItem(highLight(productSearchField.getlYesItem(), regex));
        productSearchField.setlNoItem(highLight(productSearchField.getlNoItem(), regex));
        productSearchField.setlChildren(highLight(productSearchField.getlChildren(), regex));
        productSearchField.setlShop(highLight(productSearchField.getlShop(), regex));
        productSearchField.setlExpenseItem(highLight(productSearchField.getlExpenseItem(), regex));
        productSearchField.setlPreseItem(highLight(productSearchField.getlPreseItem(), regex));
        productSearchField.setrContent(highLight(productSearchField.getrContent(), regex));
        productSearchField.setrCar(highLight(productSearchField.getrCar(), regex));
    }

    private JsonResult clear(String key, Integer version) {
        SearchTypeEnum searchType = SearchTypeEnum.getByValue(key);
        if (searchType == null) {
            return JsonResultUtils.error(useage);
        }
        if (searchType.isWord()) {
            wordSearch.delAll(version);
        }
        return JsonResultUtils.success();
    }

    private JsonResult peek(String key, Integer version) {
        SearchTypeEnum searchType = SearchTypeEnum.getByValue(key);
        if (searchType == null) {
            return JsonResultUtils.error(useage);
        }
        @SuppressWarnings("rawtypes")
        String corename = ((AppBaseSearch) SearchBuilder.getSearch(searchType)).getCoreName(version);
        Counter current = Counter.getCurrent(corename);
        if (current != null) {
            StringBuilder info = new StringBuilder();
            info.append("<p style=\"color:green\">").append(current.sumarry()).append("</p>");
            info.append("<p style=\"color:red\">");
            for (Counter history : current.history) {
                info.append(history.sumarry()).append("<br>");
            }
            info.append("</p>");
            return current.end != null ? JsonResultUtils.error(null, info.toString(), false) : JsonResultUtils.success(null,
                                                                                                                       info.toString(),
                                                                                                                       false);
        } else {
            return JsonResultUtils.error(String.format("Get Nothing For 【%s】", key));
        }
    }

    private JsonResult useNewVersion(String key, Integer version, boolean isCreate) {
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getByValue(key);
        if (searchTypeEnum == null || searchTypeEnum.getVersionType() == null || (!isCreate && version == null)) {
            return JsonResultUtils.error(String.format("【%s】searchTypeEnum【%s】,version【%s】", key, searchTypeEnum,
                                                       version));
        }
        if (isCreate) {

        } else {

        }
        return JsonResultUtils.success();
    }

    private Integer getVersion(VersionType versionType) {
        return 0;
    }
}
