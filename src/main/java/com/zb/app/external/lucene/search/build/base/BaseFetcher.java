/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.build.base;

import java.util.List;

import com.zb.app.common.pagination.Pagination;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.external.lucene.search.cons.VersionType;
import com.zb.app.external.lucene.solr.pojo.SearchField;

/**
 * @author zxc Sep 2, 2014 1:55:05 PM
 */
public abstract class BaseFetcher<Q extends Pagination> implements DataFetcher<Q> {

    public static final int default_page_size = 200;

    protected Param         param;

    @Override
    public PaginationList<?> fetch(Q p) {
        if (p != null) {
            if (!p.toNextPage()) {
                return null;
            }
        } else {
            p = createQuery();
        }
        return doFetch(p);
    }

    protected abstract Q createQuery();

    protected abstract PaginationList<? extends SearchField> doFetch(Q p);

    protected static void appendItemCount(List<?> data, String keyName, String valueName) {

    }

    protected static Integer getVersion(VersionType versionType, Param param) {
        return 0;
    }

    protected DataFetcher<Q> setParam(Param param) {
        this.param = param;
        return this;
    }
}
