/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.build.fetcher;

import org.springframework.stereotype.Service;

import com.zb.app.common.pagination.PaginationList;
import com.zb.app.external.lucene.search.build.base.BaseFetcher;
import com.zb.app.external.lucene.search.build.base.DataFetcher;
import com.zb.app.external.lucene.search.build.base.Param;
import com.zb.app.external.lucene.search.pojo.WordSearchField;
import com.zb.app.external.lucene.search.query.WordQuery;

/**
 * @author zxc Sep 2, 2014 2:32:20 PM
 */
@Service
public class WordFetcher extends BaseFetcher<WordQuery> {

    private static final WordFetcher instance = new WordFetcher();

    @Override
    protected WordQuery createQuery() {
        WordQuery query = new WordQuery();
        return query;
    }

    public static DataFetcher<?> create(Param param) {
        return param == null ? instance : new WordFetcher().setParam(param);
    }

    @Override
    public PaginationList<WordSearchField> doFetch(WordQuery p) {
        PaginationList<WordSearchField> result = null;
        return result;
    }
}
