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

import com.zb.app.biz.dao.TravelOperationLogDao;
import com.zb.app.biz.domain.TravelOperationLogDO;
import com.zb.app.biz.domain.TravelOperationLogFullDO;
import com.zb.app.biz.query.TravelOperationLogQuery;
import com.zb.app.biz.service.interfaces.OperationLogService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author ZhouZhong
 */
@SuppressWarnings("unchecked")
@Service
public class TravelOperationLogServiceImpl implements OperationLogService {

    @Autowired
    private TravelOperationLogDao travelOperationLogDao;

    @Override
    public List<TravelOperationLogDO> list(TravelOperationLogQuery query) {
        if (query == null) {
            return Collections.<TravelOperationLogDO> emptyList();
        }
        return travelOperationLogDao.list(query);
    }

    @Override
    public PaginationList<TravelOperationLogFullDO> listPagination(TravelOperationLogQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelOperationLogFullDO>) Collections.<TravelOperationLogFullDO> emptyList();
        }
        return (PaginationList<TravelOperationLogFullDO>) travelOperationLogDao.paginationList(query, iPages);
    }

    @Override
    public List<TravelOperationLogDO> listTravelOperationLog() {
        List<TravelOperationLogDO> list = travelOperationLogDao.list();
        return list == null ? Collections.<TravelOperationLogDO> emptyList() : list;
    }

    @Override
    public TravelOperationLogDO getTravelOperationLogById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelOperationLogDao.getById(id);
    }

    @Override
    public Integer insertTravelOperationLog(TravelOperationLogDO ts) {
        Integer count = travelOperationLogDao.insert(ts);
        if (count == null || count == 0) {
            return 0;
        }
        return ts.getOlId().intValue();
    }

    @Override
    public boolean updateTravelOperationLog(TravelOperationLogDO ts) {
        if (ts == null) {
            return false;
        }
        return travelOperationLogDao.updateById(ts);
    }

    @Override
    public boolean deleteTravelOperationLog(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelOperationLogDao.deleteById(id);
    }
}
