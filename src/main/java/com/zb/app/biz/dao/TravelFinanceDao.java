/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelFinanceDO;
import com.zb.app.biz.domain.TravelFinanceViewDO;
import com.zb.app.biz.mapper.TravelFinanceMapper;
import com.zb.app.biz.query.TravelFinanceQuery;

/**
 * @author zxc Aug 5, 2014 1:31:04 PM
 */
@Repository
public class TravelFinanceDao extends BaseDao<TravelFinanceDO, TravelFinanceMapper, TravelFinanceQuery> {

    public List<TravelFinanceViewDO> listQuery(TravelFinanceQuery query) {
        return m.listQuery(query);
    }
}
