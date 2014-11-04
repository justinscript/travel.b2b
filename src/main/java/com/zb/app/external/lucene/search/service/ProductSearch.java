/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import com.zb.app.external.lucene.search.cons.SearchTypeEnum;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;
import com.zb.app.external.lucene.solr.query.SearchQuery;

/**
 * @author zxc Sep 3, 2014 12:19:44 PM
 */
@Service
public class ProductSearch<Q extends SearchQuery> extends AppBaseSearch<ProductSearchField, Q> {

    @Override
    public String getCoreName(Integer version) {
        return "zuobian";
    }

    public SearchTypeEnum getType() {
        return SearchTypeEnum.PRODUCT;
    }

    @Override
    public SolrQuery convert(Q query) {
        return query.toSolrQuery();
    }
}
