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

import com.zb.app.biz.cons.MessageTypeEnum;
import com.zb.app.biz.dao.TravelGiftClassDao;
import com.zb.app.biz.dao.TravelGiftDao;
import com.zb.app.biz.dao.TravelGiftOrderDao;
import com.zb.app.biz.dao.TravelIntegralDao;
import com.zb.app.biz.dao.TravelIntegralDealDao;
import com.zb.app.biz.domain.TravelGiftClassDO;
import com.zb.app.biz.domain.TravelGiftDO;
import com.zb.app.biz.domain.TravelGiftOrderDO;
import com.zb.app.biz.domain.TravelGiftOrderFullDO;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelIntegralDealDO;
import com.zb.app.biz.domain.TravelIntegralFullDO;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.biz.query.TravelGiftClassQuery;
import com.zb.app.biz.query.TravelGiftOrderQuery;
import com.zb.app.biz.query.TravelGiftQuery;
import com.zb.app.biz.query.TravelIntegralDealQuery;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.biz.service.interfaces.IntegralService;
import com.zb.app.biz.service.interfaces.MessageService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 积分实现
 * 
 * @author zxc Jul 14, 2014 10:43:28 AM
 */
@SuppressWarnings("unchecked")
@Service
public class TravelIntegralServiceImpl implements IntegralService {

    @Autowired
    private TravelGiftDao         travelGiftDao;
    @Autowired
    private TravelGiftClassDao    travelGiftClassDao;
    @Autowired
    private TravelGiftOrderDao    travelGiftOrderDao;
    @Autowired
    private TravelIntegralDao     travelIntegralDao;
    @Autowired
    private TravelIntegralDealDao travelIntegralDealDao;

    @Autowired
    private MessageService        messageService;

    private void createIntegralMsg(TravelIntegralDO integral, MessageTypeEnum type) {
        if (integral == null || integral.getcId() == null || integral.getiId() == null || type == null) {
            return;
        }
        messageService.addTravelMessage(new TravelMessageDO(integral.getcId(), integral.getcId(), integral.getmId(),
                                                            type.getDesc(), "/tour/integral.htm", type));
    }

    private void createIntegralMsg(TravelGiftOrderDO giftOrder, MessageTypeEnum type) {
        if (giftOrder == null || giftOrder.getcId() == null || giftOrder.getgId() == null || type == null) {
            return;
        }
        messageService.addTravelMessage(new TravelMessageDO(giftOrder.getcId(), giftOrder.getcId(), giftOrder.getmId(),
                                                            type.getDesc(), "/tour/integral/cart.htm", type));
    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分产品表 TravelGiftDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelGiftDO find(TravelGiftQuery query) {
        if (query == null) {
            return null;
        }
        return travelGiftDao.find(query);
    }

    @Override
    public List<TravelGiftDO> list(TravelGiftQuery query) {
        if (query == null) {
            return Collections.<TravelGiftDO> emptyList();
        }
        return travelGiftDao.list(query);
    }

    @Override
    public PaginationList<TravelGiftDO> listPagination(TravelGiftQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelGiftDO>) Collections.<TravelGiftDO> emptyList();
        }
        return (PaginationList<TravelGiftDO>) travelGiftDao.paginationList(query, iPages);
    }

    @Override
    public TravelGiftDO getTravelGiftById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelGiftDao.getById(id);
    }

    @Override
    public Integer addTravelGift(TravelGiftDO... gift) {
        if (Argument.isEmptyArray(gift)) {
            return 0;
        }
        return travelGiftDao.insert(gift);
    }

    @Override
    public boolean updateTravelGift(TravelGiftDO travel) {
        if (travel == null) {
            return false;
        }
        return travelGiftDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelGift(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelGiftDao.deleteById(id);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分产品类型表 TravelGiftClassDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelGiftClassDO find(TravelGiftClassQuery query) {
        if (query == null) {
            return null;
        }
        return travelGiftClassDao.find(query);
    }

    @Override
    public List<TravelGiftClassDO> list(TravelGiftClassQuery query) {
        if (query == null) {
            return Collections.<TravelGiftClassDO> emptyList();
        }
        return travelGiftClassDao.list(query);
    }

    @Override
    public PaginationList<TravelGiftClassDO> listPagination(TravelGiftClassQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelGiftClassDO>) Collections.<TravelGiftClassDO> emptyList();
        }
        return (PaginationList<TravelGiftClassDO>) travelGiftClassDao.paginationList(query, iPages);
    }

    @Override
    public TravelGiftClassDO getTravelGiftClassById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelGiftClassDao.getById(id);
    }

    @Override
    public Integer addTravelGiftClass(TravelGiftClassDO... giftClass) {
        if (Argument.isEmptyArray(giftClass)) {
            return 0;
        }
        return travelGiftClassDao.insert(giftClass);
    }

    @Override
    public boolean updateTravelGiftClass(TravelGiftClassDO travel) {
        if (travel == null) {
            return false;
        }
        return travelGiftClassDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelGiftClass(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelGiftClassDao.deleteById(id);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分产品订单表 TravelGiftOrderDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelGiftOrderDO find(TravelGiftOrderQuery query) {
        if (query == null) {
            return null;
        }
        return travelGiftOrderDao.find(query);
    }

    @Override
    public List<TravelGiftOrderDO> list(TravelGiftOrderQuery query) {
        if (query == null) {
            return Collections.<TravelGiftOrderDO> emptyList();
        }
        return travelGiftOrderDao.list(query);
    }

    @Override
    public PaginationList<TravelGiftOrderDO> listPagination(TravelGiftOrderQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelGiftOrderDO>) Collections.<TravelGiftOrderDO> emptyList();
        }
        return (PaginationList<TravelGiftOrderDO>) travelGiftOrderDao.paginationList(query, iPages);
    }

    @Override
    public TravelGiftOrderDO getTravelGiftOrderById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelGiftOrderDao.getById(id);
    }

    @Override
    public Integer addTravelGiftOrder(TravelGiftOrderDO... giftOrders) {
        if (giftOrders == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(giftOrders);
        if (Argument.isEmptyArray(giftOrders)) {
            return 0;
        }
        Integer count = travelGiftOrderDao.insert(giftOrders);
        if (giftOrders.length == 1) {
            TravelGiftOrderDO giftOrder = giftOrders[0];
            createIntegralMsg(giftOrder, MessageTypeEnum.INTEGRAL_CONVERT);
            return giftOrders[0].getgId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public boolean updateTravelGiftOrder(TravelGiftOrderDO travel) {
        if (travel == null) {
            return false;
        }
        return travelGiftOrderDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelGiftOrder(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelGiftOrderDao.deleteById(id);
    }

    @Override
    public PaginationList<TravelGiftOrderFullDO> fullListPagination(TravelGiftOrderQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelGiftOrderFullDO>) Collections.<TravelGiftOrderFullDO> emptyList();
        }
        return (PaginationList<TravelGiftOrderFullDO>) travelGiftOrderDao.fullListPagination(query, iPages);
    }

    @Override
    public TravelGiftOrderFullDO getTravelGiftOrderFullById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelGiftOrderDao.fullGetById(id);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分表 TravelIntegralDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelIntegralDO find(TravelIntegralQuery query) {
        if (query == null) {
            return null;
        }
        return travelIntegralDao.find(query);
    }

    @Override
    public List<TravelIntegralDO> list(TravelIntegralQuery query) {
        if (query == null) {
            return Collections.<TravelIntegralDO> emptyList();
        }
        return travelIntegralDao.list(query);
    }

    @Override
    public PaginationList<TravelIntegralDO> listPagination(TravelIntegralQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelIntegralDO>) Collections.<TravelIntegralDO> emptyList();
        }
        return (PaginationList<TravelIntegralDO>) travelIntegralDao.paginationList(query, iPages);
    }

    @Override
    public TravelIntegralDO getTravelIntegralById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelIntegralDao.getById(id);
    }

    @Override
    public Integer addTravelIntegral(TravelIntegralDO... integrals) {
        if (integrals == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(integrals);
        if (Argument.isEmptyArray(integrals)) {
            return 0;
        }
        Integer count = travelIntegralDao.insert(integrals);
        if (integrals.length == 1) {
            TravelIntegralDO integral = integrals[0];
            createIntegralMsg(integral, MessageTypeEnum.NEW_INTEGRAL);
            return integrals[0].getiId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public boolean updateTravelIntegral(TravelIntegralDO travel) {
        if (travel == null) {
            return false;
        }
        return travelIntegralDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelIntegral(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelIntegralDao.deleteById(id);
    }

    @Override
    public TravelIntegralDO queryBala(TravelIntegralQuery integralQuery) {
        if (integralQuery == null) {
            return null;
        }
        return travelIntegralDao.queryBala(integralQuery);
    }

    @Override
    public PaginationList<TravelIntegralFullDO> fullListPagination(TravelIntegralQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelIntegralFullDO>) Collections.<TravelIntegralFullDO> emptyList();
        }
        return (PaginationList<TravelIntegralFullDO>) travelIntegralDao.fullListPagination(query, iPages);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    // 积分历史交易表 TravelIntegralDealDO
    // ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelIntegralDealDO find(TravelIntegralDealQuery query) {
        if (query == null) {
            return null;
        }
        return travelIntegralDealDao.find(query);
    }

    @Override
    public List<TravelIntegralDealDO> list(TravelIntegralDealQuery query) {
        if (query == null) {
            return Collections.<TravelIntegralDealDO> emptyList();
        }
        return travelIntegralDealDao.list(query);
    }

    @Override
    public PaginationList<TravelIntegralDealDO> listPagination(TravelIntegralDealQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelIntegralDealDO>) Collections.<TravelIntegralDealDO> emptyList();
        }
        return (PaginationList<TravelIntegralDealDO>) travelIntegralDealDao.paginationList(query, iPages);
    }

    @Override
    public TravelIntegralDealDO getTravelIntegralDealById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelIntegralDealDao.getById(id);
    }

    @Override
    public Integer addTravelIntegralDeal(TravelIntegralDealDO... travels) {
        if (travels == null) {
            return 0;
        }
        return travelIntegralDealDao.insert(travels);
    }

    @Override
    public boolean updateTravelIntegralDeal(TravelIntegralDealDO travel) {
        if (travel == null) {
            return false;
        }
        return travelIntegralDealDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelIntegralDeal(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelIntegralDealDao.deleteById(id);
    }
}
