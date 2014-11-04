/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.service;

import com.zb.app.external.lucene.search.cons.VersionType;
import com.zb.app.external.lucene.solr.pojo.SearchField;
import com.zb.app.external.lucene.solr.query.SearchQuery;
import com.zb.app.external.lucene.solr.service.BaseSearch;

/**
 * @author zxc Sep 2, 2014 1:58:45 PM
 */
public abstract class AppBaseSearch<T extends SearchField, Q extends SearchQuery> extends BaseSearch<T, Q> {

    public Integer getVersion(VersionType versionType) {
        if (versionType == null) {
            return null;
        }
        Integer maxVersion = 0;
        return maxVersion;
    }
}
