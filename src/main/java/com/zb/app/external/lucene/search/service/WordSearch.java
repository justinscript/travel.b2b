/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import com.zb.app.external.lucene.search.cons.SearchTypeEnum;
import com.zb.app.external.lucene.search.cons.VersionType;
import com.zb.app.external.lucene.search.pojo.WordSearchField;
import com.zb.app.external.lucene.solr.query.SearchQuery;

/**
 * @author zxc Sep 2, 2014 5:00:16 PM
 */
@Service
public class WordSearch<Q extends SearchQuery> extends AppBaseSearch<WordSearchField, Q> {

    private static String key_prefix = "word.search." + SearchTypeEnum.WORD.getValue() + "_";

    public void init() {

    }

    @Override
    public SolrQuery convert(Q query) {
        return query.toSolrQuery();
    }

    public SearchTypeEnum getType() {
        return SearchTypeEnum.WORD;
    }

    @Override
    public String getCoreName(Integer version) {
        return key_prefix + (version == null ? getVersion(VersionType.product) : version);
    }
}
