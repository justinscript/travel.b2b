/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.app.biz.dao.TravelMemberDao;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.biz.service.interfaces.MemberService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author zxc Jun 18, 2014 2:10:04 PM
 */
@Service
public class TravelMemberServiceImpl implements MemberService {

    @Autowired
    private TravelMemberDao travelMemberDao;

    @Override
    public List<Long> getMidListByCid(Long cId) {
        List<TravelMemberDO> list = list(new TravelMemberQuery(cId));
        if (list == null || list.isEmpty()) {
            return Collections.<Long> emptyList();
        }
        return (List<Long>) CollectionUtils.getLongValues(list, "mId");
    }

    /**
     * 添加用户
     * 
     * @param travelMemberDO
     * @return
     */
    @Override
    public Integer insert(TravelMemberDO travelMemberDO) {
        if (travelMemberDO == null) {
            return null;
        }
        return travelMemberDao.insert(travelMemberDO);
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelMemberDao.deleteById(id);
    }

    /**
     * 分页查询
     */
    @Override
    public List<TravelMemberDO> list(TravelMemberQuery query) {
        if (query == null) {
            return Collections.<TravelMemberDO> emptyList();
        }
        return travelMemberDao.list(query);
    }

    @Override
    public List<TravelMemberDO> list() {
        return travelMemberDao.list();
    }

    @Override
    public boolean update(TravelMemberDO travelMemberDO) {
        return travelMemberDao.updateById(travelMemberDO);
    }

    @Override
    public TravelMemberDO getByName(TravelMemberQuery query) {
        return travelMemberDao.getByName(query);
    }

    @Override
    public TravelMemberDO getById(Long mId) {
        if (Argument.isNotPositive(mId)) {
            return null;
        }
        return travelMemberDao.getById(mId);
    }

    @Override
    public PaginationList<TravelMemberDO> showMemberPagination(TravelMemberQuery query, IPageUrl... ipPages) {
        if (query == null) {
            return null;
        }
        return (PaginationList<TravelMemberDO>) travelMemberDao.showMemberPagination(query, ipPages);
    }

	@Override
	public List<TravelMemberDO> listQuery(TravelMemberQuery query) {
		// TODO Auto-generated method stub
		return travelMemberDao.listQuery(query);
	}
}
