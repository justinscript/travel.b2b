/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.solr.query;

import java.io.Serializable;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.zb.app.external.lucene.search.utils.ZuobianSolrQueryConvert;

/**
 * @author zxc Sep 18, 2014 3:48:58 PM
 */
public class SuggestQuery implements SearchQuery, Serializable {

    private static final long serialVersionUID = -4093861352777358600L;

    private String            field;

    private String            prefix;

    private String            sortFiled;

    private int               limitCount;

    private int               minCount;

    public SuggestQuery(String field, String prefix) {
        setField(field);
        setPrefix(prefix);
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    @Override
    public int getRows() {
        return 0;
    }

    @Override
    public int getStart() {
        return 0;
    }

    @Override
    public String getSortFiled() {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    @Override
    public ORDER getOrderBy() {
        return null;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    @Override
    public SolrQuery toSolrQuery() {
        return ZuobianSolrQueryConvert.createSuggestQuery(this);
    }
}
