/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelIntegralDealDO;
import com.zb.app.biz.mapper.TravelIntegralDealMapper;
import com.zb.app.biz.query.TravelIntegralDealQuery;

/**
 * @author Administrator 2014-7-23 下午3:13:16
 */
@Repository
public class TravelIntegralDealDao extends BaseDao<TravelIntegralDealDO, TravelIntegralDealMapper, TravelIntegralDealQuery> {

    @Override
    public String getNameSpace() {
        return TravelIntegralDealMapper.class.getName();
    }
}
