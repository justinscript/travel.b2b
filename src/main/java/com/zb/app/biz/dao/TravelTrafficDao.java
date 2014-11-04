/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelTrafficDO;
import com.zb.app.biz.mapper.TravelTrafficMapper;
import com.zb.app.biz.query.TravelTrafficQuery;

/**
 * @author Administrator 2014-7-9 下午3:45:54
 */
@Repository
public class TravelTrafficDao extends BaseDao<TravelTrafficDO, TravelTrafficMapper, TravelTrafficQuery> {

    @Override
    public String getNameSpace() {
        return TravelTrafficMapper.class.getName();
    }
}
