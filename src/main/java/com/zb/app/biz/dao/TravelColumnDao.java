/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.mapper.TravelColumnMapper;
import com.zb.app.biz.query.TravelColumnQuery;

/**
 * @author ZhouZhong 2014-6-27 下午2:07:14
 */
@Repository
public class TravelColumnDao extends BaseDao<TravelColumnDO, TravelColumnMapper, TravelColumnQuery> {

    @Override
    public String getNameSpace() {
        return TravelColumnMapper.class.getName();
    }
}
