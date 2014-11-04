/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

/**
 * @author zxc Sep 2, 2014 1:45:51 PM
 */
public interface SearchQuery {

    int DEFAULT_ROWS = 200;

    int getRows();

    int getStart();

    String getSortFiled();

    ORDER getOrderBy();

    // 转换为Solr查询格式
    SolrQuery toSolrQuery();
}
