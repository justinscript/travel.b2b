/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import com.zb.app.common.core.lang.Argument;
import com.zb.app.external.lucene.search.query.ProductSearchQuery;
import com.zb.app.external.lucene.search.query.WordSearchQuery;
import com.zb.app.external.lucene.solr.utils.BaseSolrQueryConvert;

/**
 * 产品查询
 * 
 * @author zxc Sep 2, 2014 1:57:47 PM
 */
public class ZuobianSolrQueryConvert extends BaseSolrQueryConvert {

    public static SolrQuery to(ProductSearchQuery query) {
        List<String> params = new ArrayList<String>();
        String product = null;
        String[] fields = new String[] { "lTile", "lMode", "lYesItem", "lArrivalCity", "lNoItem", "lChildren", "lShop",
                "lExpenseItem", "lPreseItem", "rContent", "rCar" };
        if (query.getProducts() != null && query.getProducts().size() != 0) {
            // 精确搜索
            if (query.isExpectMatch()) {
                int i = 0;
                for (String s : query.getProducts()) {
                    s = filterQuery(s);
                    if (StringUtils.isNotBlank(s)) {
                        product = i > 0 ? product + String.format(" AND \\regex:%s", s) : String.format("(\\regex:%s",
                                                                                                        s);
                        i++;
                    }
                }
                product += ")";
            }
            // 模糊搜索
            else {
                product = StringUtils.join(query.getProducts().toArray(new String[0]), " ");
                product = product.length() == 1 ? "*" + product + "*" : filterQuery(product);
                if (StringUtils.isNotBlank(product)) {
                    product = String.format("\\regex:%s", product);
                }
            }
            // 生成全文检索字符串
            String param = "";
            for (String string : fields) {
                param += product.replaceAll("\\\\regex", string);
                if (!string.equals("rCar")) {
                    param += " OR ";
                }
            }
            params.add("(" + param + ")");
        }
        // 产品编号不为空
        if (query.getlGroupNumber() != null && query.getlGroupNumber() != "") {
            params.add("lGroupNumber:" + query.getlGroupNumber());
        }
        // 线路类型不为空
        if (query.getlType() != null && query.getlGroupNumber() != "") {
            params.add("lType:" + query.getlType());
        }
        // 旅行天数不为空
        if (query.getlDay() != null && query.getlDay() != 0) {
            params.add("lDay:" + query.getlDay());
        }
        // 专线类别不为空
        if (query.getzIds() != null) {
            String paramszid = "";
            for (Long zid : query.getzIds()) {
                paramszid += "zId:" + zid + " OR ";
            }
            params.add("(" + paramszid.substring(0, paramszid.length() - 4) + ")");
        }
        // 抵达城市不为空
        if (query.getlArrivalCity() != null && query.getlArrivalCity() != "") {
            params.add("lArrivalCity:" + query.getlArrivalCity());
        }
        SolrQuery solrQuery = createSearchQuery(params, query);
        logger.debug("ZuobianSolrQueryConvert solr query: [{}]", solrQuery.toString());
        return solrQuery;
    }

    public static SolrQuery to(WordSearchQuery query) {
        List<String> params = new ArrayList<String>();
        String word = null;
        if (query.isExpectMatch()) {
            int i = 0;
            for (String s : query.getWords()) {
                s = filterQuery(s);
                if (StringUtils.isNotBlank(s)) {
                    if (i > 0) {
                        word += String.format("AND word:(%s)", s);
                    } else {
                        word = String.format("word:(%s) ", s);
                    }
                    i++;
                }
            }
        } else {
            word = StringUtils.join(query.getWords().toArray(new String[0]), " ");
            word = filterQuery(word);
            if (StringUtils.isNotBlank(word)) {
                word = String.format("word:(%s)", word);
            }
        }
        params.add(word);
        if (query.getCid() != null) {
            params.add(String.format("cid:(%s)", query.getCid()));
        }

        if (Argument.isNotEmptyArray(query.getCids())) {
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0, j = query.getCids().length; i < j; i++) {
                sb.append(String.format("cid:(%s)", query.getCids()[i]));
                if (i != (j - 1)) {
                    sb.append(" OR ");
                }
            }
            sb.append(")");
            params.add(sb.toString());
        }
        SolrQuery solrQuery = createSearchQuery(params, query);
        return solrQuery;
    }
}
