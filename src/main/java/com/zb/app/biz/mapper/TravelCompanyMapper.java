/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.query.TravelCompanyQuery;

/**
 * @author ZhouZhong 2014-6-24 下午4:30:21
 */
public interface TravelCompanyMapper extends BaseMapper<TravelCompanyDO> {

    /**
     * 根据名称查询
     * 
     * @param query
     * @return
     */
    public TravelCompanyDO getByName(TravelCompanyQuery query);

	public Integer countByAccount(TravelCompanyQuery companyQuery);
}
