/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import java.util.List;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.query.TravelMemberQuery;

/**
 * @author ZhouZhong 2014-6-16 下午4:25:12
 */
public interface TravelMemberMapper extends BaseMapper<TravelMemberDO> {

    /**
     * 根据名称查询
     * 
     * @param name
     * @return
     */
    public TravelMemberDO getByName(TravelMemberQuery query);

	public List<TravelMemberDO> listQuery(TravelMemberQuery query);
}
