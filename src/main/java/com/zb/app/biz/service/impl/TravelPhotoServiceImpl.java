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

import com.zb.app.biz.dao.TravelPhotoDao;
import com.zb.app.biz.domain.TravelPhotoDO;
import com.zb.app.biz.query.TravelPhotoQuery;
import com.zb.app.biz.service.interfaces.PhotoService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author zxc Sep 17, 2014 10:45:16 PM
 */
@Service
public class TravelPhotoServiceImpl implements PhotoService {

    @Autowired
    private TravelPhotoDao photoDao;

    @Override
    public TravelPhotoDO find(TravelPhotoQuery query) {
        if (query == null) {
            return null;
        }
        return photoDao.find(query);
    }

    @Override
    public List<TravelPhotoDO> list(TravelPhotoQuery query) {
        if (query == null) {
            return Collections.<TravelPhotoDO> emptyList();
        }
        return photoDao.list(query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginationList<TravelPhotoDO> listPagination(TravelPhotoQuery query, IPageUrl... iPageUrls) {
        if (query == null) {
            return (PaginationList<TravelPhotoDO>) Collections.<TravelPhotoDO> emptyList();
        }
        return (PaginationList<TravelPhotoDO>) photoDao.paginationList(query, iPageUrls);
    }

    @Override
    public TravelPhotoDO getTravelPhotoById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return photoDao.getById(id);
    }

    @Override
    public Integer addTravelPhoto(TravelPhotoDO... travels) {
        if (Argument.isEmptyArray(travels)) {
            return null;
        }
        return photoDao.insert(travels);
    }

    @Override
    public boolean updateTravelPhoto(TravelPhotoDO travel) {
        if (travel == null) {
            return false;
        }
        return photoDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelPhoto(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return photoDao.deleteById(id);
    }
}
