/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.query.TravelRouteQuery;

/**
 * @author zxc Jun 25, 2014 9:57:03 AM
 */
public interface TravelRouteMapper extends BaseMapper<TravelRouteDO> {

    /**
     * find 查询
     * 
     * @param query
     * @return
     */
    public TravelRouteDO find(TravelRouteQuery query);

    /**
     * update 更新
     * 
     * @param query
     * @return
     */
    public Integer update(TravelRouteDO travelRoute);

    /**
     * count 操作
     * 
     * @param query
     * @return
     */
    public Integer count(TravelRouteQuery query);

    /**
     * 批量删除行程
     * 
     * @param id
     * @return
     */
    public boolean deleteByLineId(Long id);
}
