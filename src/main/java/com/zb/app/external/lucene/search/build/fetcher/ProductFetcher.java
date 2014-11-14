/*
     * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.build.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.app.biz.cons.LineStateEnum;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.service.interfaces.LineService;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.external.lucene.search.build.base.BaseFetcher;
import com.zb.app.external.lucene.search.build.base.DataFetcher;
import com.zb.app.external.lucene.search.build.base.Param;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;
import com.zb.app.external.lucene.solr.pojo.SearchField;

/**
 * @author zxc Sep 3, 2014 12:19:07 PM
 */
@Service
public class ProductFetcher extends BaseFetcher<TravelLineQuery> {

    @Autowired
    private LineService                 lineService;

    private static final ProductFetcher instance = new ProductFetcher();

    @Override
    protected TravelLineQuery createQuery() {
        TravelLineQuery query = new TravelLineQuery();
        query.setlTemplateState(LineTemplateEnum.Line.getValue());
        query.setlState(LineStateEnum.NORMAL.getValue());
        return query;
    }

    @Override
    protected PaginationList<? extends SearchField> doFetch(TravelLineQuery q) {
        lineService = (LineService) SpringContextAware.getBean("travelLineServiceImpl");
        PaginationList<ProductSearchField> fieldpagilist = lineService.listProductSearch(q);
        return fieldpagilist;
    }

    public static DataFetcher<?> create(Param param) {
        return param == null ? instance : new ProductFetcher().setParam(param);
    }
}
