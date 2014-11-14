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
        String q = StringUtils.join(query.getProducts().toArray(new String[0]), " ");
        List<String> params = new ArrayList<String>();
        if (StringUtils.isNotBlank(q)) {
            params.add(q.length()==1?"lTile:*"+q+"*":filterQuery(q));
        }
        // 过滤查询
        List<String> fiter = new ArrayList<String>();
        // 产品编号不为空
        if (query.getlGroupNumber() != null && query.getlGroupNumber() != "") {
            fiter.add("lGroupNumber:" + query.getlGroupNumber());
        }
        // 线路类型不为空
        if (query.getlType() != null && query.getlGroupNumber() != "") {
            fiter.add("lType:" + query.getlType());
        }
        // 旅行天数不为空
        if (query.getlDay() != null && query.getlDay() != 0) {
            fiter.add("lDay:" + query.getlDay());
        }
        // 专线类别不为空
        if (query.getzIds() != null) {
            String paramszid = "";
            for (Long zid : query.getzIds()) {
                paramszid += "zId:" + zid + " OR ";
            }
            fiter.add("(" + paramszid.substring(0, paramszid.length() - 4) + ")");
        }
        // 抵达城市不为空
        if (query.getlArrivalCity() != null && query.getlArrivalCity() != "") {
            fiter.add("lArrivalCity:" + query.getlArrivalCity());
        }
        SolrQuery solrQuery = createSearchQuery(params, fiter, query);
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
        SolrQuery solrQuery = createSearchQuery(params, null, query);
        return solrQuery;
    }
}
