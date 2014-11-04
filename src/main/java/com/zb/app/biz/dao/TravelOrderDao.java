/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelOrderContactDO;
import com.zb.app.biz.domain.TravelOrderCountByStateDO;
import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.mapper.TravelOrderMapper;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.util.ObjectUtils;

/**
 * @author zxc Jul 18, 2014 12:31:46 AM
 */
@SuppressWarnings("unchecked")
@Repository
public class TravelOrderDao extends BaseDao<TravelOrderDO, TravelOrderMapper, TravelOrderQuery> {

    public List<TravelOrderCountDO> getOrderCount(TravelOrderQuery query) {
        Assert.assertNotNull(query);
        return m.countOrder(query);
    }

    public List<TravelOrderFullDO> showOrderQuery(TravelOrderQuery query) {
        Assert.assertNotNull(query);
        return m.showOrderQuery(query);
    }

    public List<TravelOrderFullDO> companyOrderQuery(TravelOrderQuery query) {
        Assert.assertNotNull(query);
        return m.companyOrderQuery(query);
    }

    @Override
    public String getNameSpace() {
        return TravelOrderMapper.class.getName();
    }

    @Override
    public PaginationList<TravelOrderDO> paginationList(TravelOrderQuery query) {
        Assert.assertNotNull(query);
        return super.paginationList("listPagination", query);
    }

    public List<TravelOrderContactDO> getTourCompany(TravelOrderQuery query) {
        ObjectUtils.trim(query);
        Assert.assertNotNull(query);
        return m.getTourCompany(query);
    }

    /**
     * count 操作
     * 
     * @param query
     * @return
     */
    @Override
    public Integer count(TravelOrderQuery query) {
        ObjectUtils.trim(query);
        return m.count(query);
    }

    public PaginationList<TravelOrderFullDO> showOrderPagination(TravelOrderQuery query, IPageUrl... ipPages) {
        Assert.assertNotNull(query);
        return super.paginationList("count", "showOrderPagination", query, ipPages);
    }

    /**
     * find 查询
     * 
     * @param query
     * @return
     */
    @Override
    public TravelOrderFullDO find(TravelOrderQuery query) {
        ObjectUtils.trim(query);
        return m.find(query);
    }

    public Boolean cancelOrder(TravelOrderDO orderDO) {
        Assert.assertNotNull(orderDO);
        Integer count = 0;
        count = m.cancelOrder(orderDO);
        return count == 0 ? false : true;
    }

	public List<TravelOrderCountByStateDO> countByOrState(TravelOrderQuery travelOrderQuery) {
		return m.countByOrState(travelOrderQuery);
	}
}
