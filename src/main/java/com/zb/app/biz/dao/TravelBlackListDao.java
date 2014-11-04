/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelBlackListDO;
import com.zb.app.biz.domain.TravelBlackListThinDO;
import com.zb.app.biz.mapper.TravelBlackListMapper;
import com.zb.app.biz.query.TravelBlackListQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 黑名单的Dao
 * 
 * @author ZhouZhong 2014-8-15 下午5:29:05
 */
@Repository
@SuppressWarnings("unchecked")
public class TravelBlackListDao extends BaseDao<TravelBlackListDO, TravelBlackListMapper, TravelBlackListQuery> {

    @Override
    public String getNameSpace() {
        return TravelBlackListMapper.class.getName();
    }

    public Integer getBlackCount(TravelBlackListQuery travelBlackListQuery) {
        Assert.assertNotNull(travelBlackListQuery);
        return count(travelBlackListQuery, "getBlackCount");
    }

	public PaginationList<TravelBlackListThinDO> queryAllCompanyBlack(
			TravelBlackListQuery query, IPageUrl... iPages) {
		Assert.assertNotNull(query);
        return super.paginationList("thinCount", "queryAllCompanyBlack", query, iPages);
	}
}
