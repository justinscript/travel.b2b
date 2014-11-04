/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.domain.TravelOrderGuestFullDO;
import com.zb.app.biz.mapper.TravelOrderGuestMapper;
import com.zb.app.biz.query.TravelOrderGuestQuery;
import com.zb.app.common.core.lang.Assert;

/**
 * @author Administrator 2014-6-25 上午10:12:00
 */
@Repository
public class TravelOrderGuestDao extends BaseDao<TravelOrderGuestDO, TravelOrderGuestMapper, TravelOrderGuestQuery> {

    @Override
    public String getNameSpace() {
        return TravelOrderGuestMapper.class.getName();
    }

    public List<TravelOrderGuestDO> getByOrId(Long orId) {
        Assert.assertNotNull(orId);
        return m.getByOrId(orId);
    }

    public List<TravelOrderGuestDO> getByLId(Long id) {
        // TODO Auto-generated method stub
        return m.getByLId(id);
    }

    public List<TravelOrderGuestFullDO> getByLIdAndPrice(Long id) {
        // TODO Auto-generated method stub
        return m.getByLIdAndPrice(id);
    }

	public Integer countByOrderGuest() {
		// TODO Auto-generated method stub
		return m.countByOrderGuest();
	}
}
