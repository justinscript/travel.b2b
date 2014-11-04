/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.mapper.TravelRouteMapper;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.common.core.lang.Assert;

/**
 * @author zxc Jun 25, 2014 10:04:09 AM
 */
@Repository
public class TravelRouteDao extends BaseDao<TravelRouteDO, TravelRouteMapper, TravelRouteQuery> {

    /**
     * 插入,return 实体的ID属性值
     * 
     * @param travelRoute
     * @return
     */
    @Override
    public Integer insert(TravelRouteDO travelRoute) {
        m.insert(travelRoute);
        return travelRoute.getrId().intValue();
    }

    /**
     * find 查询
     * 
     * @param query
     * @return
     */
    public TravelRouteDO find(TravelRouteQuery query) {
        Assert.assertNotNull(query);
        return m.find(query);
    }

    /**
     * update 更新
     * 
     * @param travelRoute
     * @return
     */
    public Integer update(TravelRouteDO travelRoute) {
        if (travelRoute == null) {
            return 0;
        }
        return m.update(travelRoute);
    }

    /**
     * count 操作
     * 
     * @param query
     * @return
     */
    public Integer count(TravelRouteQuery query) {
        if (query == null) {
            return 0;
        }
        return m.count(query);
    }

    /***
     * 批量删除行程
     * 
     * @param id
     * @return
     */
    public boolean deleteByLineId(Long id) {
        Assert.assertNotNull(id);
        return m.deleteByLineId(id);
    }

    @Override
    public String getNameSpace() {
        return TravelRouteMapper.class.getName();
    }
}
