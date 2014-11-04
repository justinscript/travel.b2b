/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.TravelGiftClassDO;
import com.zb.app.biz.domain.TravelGiftDO;
import com.zb.app.biz.domain.TravelGiftOrderDO;
import com.zb.app.biz.domain.TravelGiftOrderFullDO;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelIntegralDealDO;
import com.zb.app.biz.domain.TravelIntegralFullDO;
import com.zb.app.biz.query.TravelGiftClassQuery;
import com.zb.app.biz.query.TravelGiftOrderQuery;
import com.zb.app.biz.query.TravelGiftQuery;
import com.zb.app.biz.query.TravelIntegralDealQuery;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 积分管理，礼品管理 service层 接口
 * 
 * @author zxc Jun 18, 2014 2:27:01 PM
 */
public interface IntegralService extends BaseService {

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分产品表 TravelGiftDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    TravelGiftDO find(TravelGiftQuery query);

    List<TravelGiftDO> list(TravelGiftQuery query);

    PaginationList<TravelGiftDO> listPagination(TravelGiftQuery query, IPageUrl... iPages);

    TravelGiftDO getTravelGiftById(Integer id);

    Integer addTravelGift(TravelGiftDO... travels);

    boolean updateTravelGift(TravelGiftDO travel);

    boolean deleteTravelGift(Integer id);

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分产品类型表 TravelGiftClassDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    TravelGiftClassDO find(TravelGiftClassQuery query);

    List<TravelGiftClassDO> list(TravelGiftClassQuery query);

    PaginationList<TravelGiftClassDO> listPagination(TravelGiftClassQuery query, IPageUrl... iPages);

    TravelGiftClassDO getTravelGiftClassById(Integer id);

    Integer addTravelGiftClass(TravelGiftClassDO... travels);

    boolean updateTravelGiftClass(TravelGiftClassDO travel);

    boolean deleteTravelGiftClass(Integer id);

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分产品订单表 TravelGiftOrderDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    TravelGiftOrderDO find(TravelGiftOrderQuery query);

    List<TravelGiftOrderDO> list(TravelGiftOrderQuery query);

    PaginationList<TravelGiftOrderDO> listPagination(TravelGiftOrderQuery query, IPageUrl... iPages);

    TravelGiftOrderDO getTravelGiftOrderById(Integer id);

    Integer addTravelGiftOrder(TravelGiftOrderDO... travels);

    boolean updateTravelGiftOrder(TravelGiftOrderDO travel);

    boolean deleteTravelGiftOrder(Integer id);

    PaginationList<TravelGiftOrderFullDO> fullListPagination(TravelGiftOrderQuery query, IPageUrl... iPages);

    TravelGiftOrderFullDO getTravelGiftOrderFullById(Integer id);

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分表 TravelIntegralDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    TravelIntegralDO find(TravelIntegralQuery query);

    List<TravelIntegralDO> list(TravelIntegralQuery query);

    PaginationList<TravelIntegralDO> listPagination(TravelIntegralQuery query, IPageUrl... iPages);

    TravelIntegralDO getTravelIntegralById(Integer id);

    Integer addTravelIntegral(TravelIntegralDO... travels);

    boolean updateTravelIntegral(TravelIntegralDO travel);

    boolean deleteTravelIntegral(Integer id);

    TravelIntegralDO queryBala(TravelIntegralQuery integralQuery);
    
    PaginationList<TravelIntegralFullDO> fullListPagination(TravelIntegralQuery query, IPageUrl... iPages);

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分历史交易表 TravelIntegralDealDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    TravelIntegralDealDO find(TravelIntegralDealQuery query);

    List<TravelIntegralDealDO> list(TravelIntegralDealQuery query);

    PaginationList<TravelIntegralDealDO> listPagination(TravelIntegralDealQuery query, IPageUrl... iPages);

    TravelIntegralDealDO getTravelIntegralDealById(Integer id);

    Integer addTravelIntegralDeal(TravelIntegralDealDO... travels);

    boolean updateTravelIntegralDeal(TravelIntegralDealDO travel);

    boolean deleteTravelIntegralDeal(Integer id);
}
