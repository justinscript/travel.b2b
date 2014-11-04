/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.query;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.external.lucene.search.utils.ZuobianSolrQueryConvert;
import com.zb.app.external.lucene.solr.query.SearchQuery;

/**
 * @author zxc Sep 2, 2014 5:02:07 PM
 */
public class WordSearchQuery implements SearchQuery {

    private int          rows        = 2000;
    private int          start;
    private List<String> words;
    private boolean      expectMatch = true; // 精确匹配
    private Long         cid;
    private Long[]       cids;

    public WordSearchQuery(List<String> words) {
        this.words = words;
    }

    public WordSearchQuery(List<String> words, Long cid) {
        this.words = words;
        this.setCid(cid);
    }

    public WordSearchQuery setWord(List<String> words) {
        this.words = words;
        return this;
    }

    public WordSearchQuery setCid(Long cid) {
        this.cid = cid;
        return this;
    }

    public WordSearchQuery() {
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String getSortFiled() {
        return "pv";
    }

    @Override
    public ORDER getOrderBy() {
        return ORDER.asc;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public Long getCid() {
        return cid;
    }

    public boolean isExpectMatch() {
        return expectMatch;
    }

    public void setExpectMatch(boolean expectMatch) {
        this.expectMatch = expectMatch;
    }

    @Override
    public SolrQuery toSolrQuery() {
        return ZuobianSolrQueryConvert.to(this);
    }

    public Long[] getCids() {
        return cids;
    }

    public void setCids(Long... cids) {
        this.cids = ArrayUtils.removeNullElement(cids);
    }
}
