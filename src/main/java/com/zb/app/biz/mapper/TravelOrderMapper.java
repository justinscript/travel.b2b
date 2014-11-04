/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import java.util.List;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelOrderContactDO;
import com.zb.app.biz.domain.TravelOrderCountByStateDO;
import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.query.TravelOrderQuery;

/**
 * @author zxc Aug 7, 2014 1:32:19 PM
 */
public interface TravelOrderMapper extends BaseMapper<TravelOrderDO> {

    public Integer count(TravelOrderQuery query);

    public TravelOrderFullDO find(TravelOrderQuery query);

    public Integer cancelOrder(TravelOrderDO orderDO);

    public List<TravelOrderCountDO> countOrder(TravelOrderQuery query);

    public List<TravelOrderFullDO> showOrderQuery(TravelOrderQuery query);

    public List<TravelOrderFullDO> companyOrderQuery(TravelOrderQuery query);

    public List<TravelOrderContactDO> getTourCompany(TravelOrderQuery query);

	public List<TravelOrderCountByStateDO> countByOrState(TravelOrderQuery query);
}
