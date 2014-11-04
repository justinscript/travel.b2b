/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.TravelOrderCountByStateDO;
import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.domain.TravelOrderGuestFullDO;
import com.zb.app.biz.query.TravelOrderGuestQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 订单管理 service层 接口
 * 
 * @author zxc Jun 18, 2014 2:20:33 PM
 */
public interface OrderService extends BaseService {

    List<TravelOrderCountDO> getOrderCount(TravelOrderQuery query);

    List<TravelOrderFullDO> companyOrderQuery(TravelOrderQuery query);

    List<Long> getTourCompany(TravelOrderQuery query);

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////订单管理
    // /////////////////////////////////////////////////////////////////////////////////////

    List<TravelOrderDO> listTravelOrder();

    List<TravelOrderFullDO> showOrderQuery(TravelOrderQuery query);

    List<TravelOrderDO> list(TravelOrderQuery query);

    TravelOrderDO getTravelOrderById(Long oid);

    Integer addTravelOrder(TravelOrderDO... tr);

    boolean updateTravelOrder(TravelOrderDO tr);

    boolean deleteTravelOrder(Long id);

    TravelOrderFullDO find(TravelOrderQuery query);

    PaginationList<TravelOrderDO> listPagination(TravelOrderQuery query);

    PaginationList<TravelOrderFullDO> showOrderPagination(TravelOrderQuery query, IPageUrl... iPages);

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////订单游客管理
    // /////////////////////////////////////////////////////////////////////////////////////

    TravelOrderGuestDO find(TravelOrderGuestQuery query);

    List<TravelOrderGuestDO> list(TravelOrderGuestQuery query);

    PaginationList<TravelOrderGuestDO> listPagination(TravelOrderGuestQuery query, IPageUrl... iPages);

    List<TravelOrderGuestDO> listTravelOrderGuest();

    TravelOrderGuestDO getTravelOrderGuestById(Long id);

    Integer addTravelOrderGuest(TravelOrderGuestDO... tg);

    boolean updateTravelOrderGuest(TravelOrderGuestDO... tg);

    boolean deleteTravelOrderGuest(Long id);

    List<TravelOrderGuestDO> getByOrId(Long id);

    Boolean cancelOrder(TravelOrderDO orderDO);

    TravelOrderDO getById(Long id);

    List<TravelOrderGuestDO> getByLId(Long id);

    List<TravelOrderGuestFullDO> getByLIdAndPrice(Long id);

	List<TravelOrderCountByStateDO> countByOrState(TravelOrderQuery travelOrderQuery);

	Integer countByOrderGuest();
}
