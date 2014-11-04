/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.domain.TravelPhotoDO;
import com.zb.app.biz.query.TravelPhotoQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author zxc Aug 11, 2014 11:06:31 PM
 */
public interface PhotoService {

    TravelPhotoDO find(TravelPhotoQuery query);

    List<TravelPhotoDO> list(TravelPhotoQuery query);

    PaginationList<TravelPhotoDO> listPagination(TravelPhotoQuery query, IPageUrl... iPageUrls);

    TravelPhotoDO getTravelPhotoById(Integer id);

    Integer addTravelPhoto(TravelPhotoDO... travels);

    boolean updateTravelPhoto(TravelPhotoDO travel);

    boolean deleteTravelPhoto(Integer id);
}
