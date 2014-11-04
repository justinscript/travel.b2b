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

import com.zb.app.biz.dao.TravelFinanceDao;
import com.zb.app.biz.dao.TravelFinanceDetailDao;
import com.zb.app.biz.domain.TravelFinanceDO;
import com.zb.app.biz.domain.TravelFinanceDetailDO;
import com.zb.app.biz.domain.TravelFinanceViewDO;
import com.zb.app.biz.query.TravelFinanceDetailQuery;
import com.zb.app.biz.query.TravelFinanceQuery;
import com.zb.app.biz.service.interfaces.FinanceService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author zxc Aug 5, 2014 1:32:14 PM
 */
@Service
public class TravelFinanceServiceImpl implements FinanceService {

    @Autowired
    private TravelFinanceDao       travelFinanceDao;

    @Autowired
    private TravelFinanceDetailDao travelFinanceDetailDao;

    // /////////////////////////////////////////////////////////////////////
    // TravelFinanceDO 财务表
    // /////////////////////////////////////////////////////////////////////

    @Override
    public TravelFinanceDO find(TravelFinanceQuery query) {
        if (query == null) {
            return null;
        }
        return travelFinanceDao.find(query);
    }

    @Override
    public List<TravelFinanceViewDO> listQuery(TravelFinanceQuery query) {
        if (query == null) {
            return Collections.<TravelFinanceViewDO> emptyList();
        }
        return travelFinanceDao.listQuery(query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginationList<TravelFinanceViewDO> listPagination(TravelFinanceQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelFinanceViewDO>) Collections.<TravelFinanceViewDO> emptyList();
        }
        return (PaginationList<TravelFinanceViewDO>) travelFinanceDao.paginationList(query, iPages);
    }

    @Override
    public TravelFinanceDO getTravelFinanceById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelFinanceDao.getById(id);
    }

    @Override
    public Integer addTravelFinance(TravelFinanceDO... travels) {
        if (Argument.isEmptyArray(travels)) {
            return null;
        }
        return travelFinanceDao.insert(travels);
    }

    @Override
    public boolean updateTravelFinance(TravelFinanceDO travel) {
        if (travel == null) {
            return false;
        }
        return travelFinanceDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelFinance(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelFinanceDao.deleteById(id);
    }

    // /////////////////////////////////////////////////////////////////////
    // TravelFinanceDetailDO 财务详情表表
    // /////////////////////////////////////////////////////////////////////

    @Override
    public TravelFinanceDetailDO find(TravelFinanceDetailQuery query) {
        if (query == null) {
            return null;
        }
        return travelFinanceDetailDao.find(query);
    }

    @Override
    public List<TravelFinanceDetailDO> list(TravelFinanceDetailQuery query) {
        if (query == null) {
            return Collections.<TravelFinanceDetailDO> emptyList();
        }
        return travelFinanceDetailDao.list(query);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public PaginationList<TravelFinanceDetailDO> listPagination(TravelFinanceDetailQuery query) {
        if (query == null) {
            return (PaginationList<TravelFinanceDetailDO>) Collections.<TravelFinanceDetailDO> emptyList();
        }
        return (PaginationList<TravelFinanceDetailDO>) travelFinanceDetailDao.paginationList(query);
    }

    @Override
    public TravelFinanceDetailDO getTravelFinanceDetailById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelFinanceDetailDao.getById(id);
    }

    @Override
    public Integer addTravelFinanceDetail(TravelFinanceDetailDO... travels) {
        if (Argument.isEmptyArray(travels)) {
            return null;
        }
        return travelFinanceDetailDao.insert(travels);
    }

    @Override
    public boolean updateTravelFinanceDetail(TravelFinanceDetailDO travel) {
        if (travel == null) {
            return false;
        }
        return travelFinanceDetailDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelFinanceDetail(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelFinanceDetailDao.deleteById(id);
    }
}
