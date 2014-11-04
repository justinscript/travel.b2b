/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.mapper.TravelMemberMapper;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author ZhouZhong 2014-6-18 下午12:16:47
 */
@SuppressWarnings("unchecked")
@Repository
public class TravelMemberDao extends BaseDao<TravelMemberDO, TravelMemberMapper, TravelMemberQuery> {

    @Override
    public String getNameSpace() {
        return TravelMemberMapper.class.getName();
    }

    /**
     * 根据名称查询
     * 
     * @param name
     * @return
     */
    public TravelMemberDO getByName(TravelMemberQuery query) {
        Assert.assertNotNull(query);
        return m.getByName(query);
    }

    public PaginationList<TravelMemberDO> showMemberPagination(TravelMemberQuery query, IPageUrl... ipPages) {
        Assert.assertNotNull(query);
        return super.paginationList("count", "showMemberPagination", query, ipPages);
    }

	public List<TravelMemberDO> listQuery(TravelMemberQuery query) {
		// TODO Auto-generated method stub
		Assert.assertNotNull(query);
		return m.listQuery(query);
	}
}
