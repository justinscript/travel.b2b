/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelIntegralFullDO;
import com.zb.app.biz.mapper.TravelIntegralMapper;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author Administrator 2014-7-24 上午10:58:01
 */
@Repository
@SuppressWarnings("unchecked")
public class TravelIntegralDao extends BaseDao<TravelIntegralDO, TravelIntegralMapper, TravelIntegralQuery> {

    @Override
    public String getNameSpace() {
        return TravelIntegralMapper.class.getName();
    }

    public TravelIntegralDO queryBala(TravelIntegralQuery integralQuery) {
        Assert.assertNotNull(integralQuery);
        return super.find(integralQuery, "queryBala");
    }

    public PaginationList<TravelIntegralFullDO> fullListPagination(TravelIntegralQuery query, IPageUrl... iPages) {
        Assert.assertNotNull(query);
        return super.paginationList("fullCount", "fullListPagination", query, iPages);
    }
}
