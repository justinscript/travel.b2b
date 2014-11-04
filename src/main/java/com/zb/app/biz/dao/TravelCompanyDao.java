/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.mapper.TravelCompanyMapper;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author ZhouZhong 2014-6-25 下午2:09:06
 */
@SuppressWarnings("unchecked")
@Repository
public class TravelCompanyDao extends BaseDao<TravelCompanyDO, TravelCompanyMapper, TravelCompanyQuery> {

    /**
     * 根据名称查询
     * 
     * @param query
     * @return
     */
    public TravelCompanyDO getByName(TravelCompanyQuery query) {
        Assert.assertNotNull(query);
        return m.getByName(query);
    }

    @Override
    public String getNameSpace() {
        return TravelCompanyMapper.class.getName();
    }

    public PaginationList<TravelCompanyDO> showCompanyPagination(TravelCompanyQuery query, IPageUrl... ipPages) {
        Assert.assertNotNull(query);
        return paginationList("count", "showCompanyPagination", query, ipPages);
    }

	public Integer countByAccount(TravelCompanyQuery companyQuery) {
		return m.countByAccount(companyQuery);
	}
}
