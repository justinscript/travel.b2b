/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelLineColumnDO;
import com.zb.app.biz.mapper.TravelLineColumnMapper;
import com.zb.app.biz.query.TravelLineColumnQuery;

/**
 * @author zxc Jul 25, 2014 10:06:55 AM
 */
@Repository
public class TravelLineColumnDao extends BaseDao<TravelLineColumnDO, TravelLineColumnMapper, TravelLineColumnQuery> {

    @Override
    public String getNameSpace() {
        return TravelLineColumnMapper.class.getName();
    }

    public List<TravelLineColumnDO> getByCid(Long cId) {
        if (cId == null || cId <= 0) {
            return null;
        }
        return m.getByCid(cId);
    }

    public List<TravelLineColumnDO> getByCidAndZid(Long cId, Long zId) {
        if ((cId == null || cId <= 0) && (zId == null || zId <= 0)) {
            return null;
        }
        return super.list(new TravelLineColumnQuery(zId, cId));
    }
}
