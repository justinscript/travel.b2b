/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelGiftDO;
import com.zb.app.biz.mapper.TravelGiftMapper;
import com.zb.app.biz.query.TravelGiftQuery;

/**
 * @author Administrator 2014-7-22 上午11:50:28
 */
@Repository
public class TravelGiftDao extends BaseDao<TravelGiftDO, TravelGiftMapper, TravelGiftQuery> {

    @Override
    public String getNameSpace() {
        return TravelGiftMapper.class.getName();
    }
}
