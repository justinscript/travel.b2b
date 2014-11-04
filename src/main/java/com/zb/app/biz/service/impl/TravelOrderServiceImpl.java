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
import com.zb.app.biz.dao.TravelOrderDao;
import com.zb.app.biz.dao.TravelOrderGuestDao;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.biz.domain.TravelOrderContactDO;
import com.zb.app.biz.domain.TravelOrderCountByStateDO;
import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.domain.TravelOrderGuestFullDO;
import com.zb.app.biz.query.TravelOrderGuestQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.biz.service.interfaces.MessageService;
import com.zb.app.biz.service.interfaces.OrderService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.core.lang.CollectionUtils.Grep;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author zxc Aug 4, 2014 3:43:22 PM
 */
@Service
public class TravelOrderServiceImpl implements OrderService {

    @Autowired
    private TravelOrderDao      travelOrderDao;
    @Autowired
    private TravelOrderGuestDao travelGuestDao;

    @Autowired
    private MessageService      messageService;

    private void createOrderMsg(TravelOrderDO order, MessageTypeEnum type) {
        if (order == null || order.getcId() == null || order.getOrId() == null || type == null) {
            return;
        }
        messageService.addTravelMessage(new TravelMessageDO(order.getCustomCompanyId(), order.getcId(),
                                                            order.getCustomId(), type.getDesc(),
                                                            "/order/orderdetails.htm?id=" + order.getOrId(), type));
    }

    @Override
    public List<TravelOrderCountDO> getOrderCount(TravelOrderQuery query) {
        if (query == null) {
            return Collections.<TravelOrderCountDO> emptyList();
        }
        return travelOrderDao.getOrderCount(query);
    }

    @Override
    public List<Long> getTourCompany(TravelOrderQuery query) {
        if (query == null) {
            return Collections.<Long> emptyList();
        }
        List<TravelOrderContactDO> tourCompanyList = travelOrderDao.getTourCompany(query);
        if (tourCompanyList == null || tourCompanyList.size() == 0) {
            return Collections.<Long> emptyList();
        }
        CollectionUtils.remove(tourCompanyList, new Grep<TravelOrderContactDO>() {

            @Override
            public boolean grep(TravelOrderContactDO contact) {
                return contact == null || Argument.isNotPositive(contact.getcId());
            }
        });
        return CollectionUtils.getLongValues(tourCompanyList, "cId");
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<TravelOrderFullDO> showOrderQuery(TravelOrderQuery query) {
        if (query == null) {
            return Collections.<TravelOrderFullDO> emptyList();
        }
        return travelOrderDao.showOrderQuery(query);
    }

    @Override
    public List<TravelOrderFullDO> companyOrderQuery(TravelOrderQuery query) {
        if (query == null) {
            return Collections.<TravelOrderFullDO> emptyList();
        }
        return travelOrderDao.companyOrderQuery(query);
    }

    @Override
    public List<TravelOrderDO> list(TravelOrderQuery query) {
        if (query == null) {
            return Collections.<TravelOrderDO> emptyList();
        }
        return travelOrderDao.list(query);
    }

    @Override
    public List<TravelOrderDO> listTravelOrder() {
        return travelOrderDao.list();
    }

    @Override
    public TravelOrderDO getTravelOrderById(Long oid) {
        if (Argument.isNotPositive(oid)) {
            return null;
        }
        return travelOrderDao.getById(oid);
    }

    @Override
    public Integer addTravelOrder(TravelOrderDO... tr) {
        if (tr == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(tr);
        if (Argument.isEmptyArray(tr)) {
            return 0;
        }
        Integer count = travelOrderDao.insert(tr);
        if (tr.length == 1) {
            TravelOrderDO order = tr[0];
            createOrderMsg(order, MessageTypeEnum.NEW_ORDER_UNREAD);

            return tr[0].getOrId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public boolean updateTravelOrder(TravelOrderDO tr) {
        if (tr == null) {
            return false;
        }
        boolean isSuccess = travelOrderDao.updateById(tr);
        if (isSuccess) {
            createOrderMsg(tr, MessageTypeEnum.ORDER_MODIFY);
        }
        return isSuccess;
    }

    @Override
    public boolean deleteTravelOrder(Long id) {
        TravelOrderDO tr = travelOrderDao.getById(id);
        if (tr == null) {
            return false;
        }
        boolean isSuccess = travelOrderDao.deleteById(id);
        if (isSuccess) {
            createOrderMsg(tr, MessageTypeEnum.ORDER_MODIFY);
        }
        return isSuccess;
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelOrderGuestDO find(TravelOrderGuestQuery query) {
        if (query == null) {
            return null;
        }
        return travelGuestDao.find(query);
    }

    @Override
    public List<TravelOrderGuestDO> list(TravelOrderGuestQuery query) {
        if (query == null) {
            return Collections.<TravelOrderGuestDO> emptyList();
        }
        return travelGuestDao.list(query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginationList<TravelOrderGuestDO> listPagination(TravelOrderGuestQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelOrderGuestDO>) Collections.<TravelOrderGuestDO> emptyList();
        }
        return (PaginationList<TravelOrderGuestDO>) travelGuestDao.paginationList(query, iPages);
    }

    @Override
    public List<TravelOrderGuestDO> listTravelOrderGuest() {
        return travelGuestDao.list();
    }

    @Override
    public TravelOrderGuestDO getTravelOrderGuestById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelGuestDao.getById(id);
    }

    @Override
    public Integer addTravelOrderGuest(TravelOrderGuestDO... tg) {
        if (tg == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(tg);
        if (Argument.isEmptyArray(tg)) {
            return 0;
        }
        Integer count = travelGuestDao.insert(tg);
        if (tg.length == 1) {
            return tg[0].getOrId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public boolean updateTravelOrderGuest(TravelOrderGuestDO... tg) {
        ArrayUtils.removeNullElement(tg);
        if (Argument.isEmptyArray(tg)) {
            return false;
        }
        return travelGuestDao.updateById(tg);
    }

    @Override
    public boolean deleteTravelOrderGuest(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelGuestDao.deleteById(id);
    }

    @Override
    public PaginationList<TravelOrderDO> listPagination(TravelOrderQuery query) {
        if (query == null) {
            return (PaginationList<TravelOrderDO>) Collections.<TravelOrderDO> emptyList();
        }
        return (PaginationList<TravelOrderDO>) travelOrderDao.paginationList(query);
    }

    @Override
    public PaginationList<TravelOrderFullDO> showOrderPagination(TravelOrderQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelOrderFullDO>) Collections.<TravelOrderFullDO> emptyList();
        }
        return (PaginationList<TravelOrderFullDO>) travelOrderDao.showOrderPagination(query, iPages);
    }

    @Override
    public TravelOrderFullDO find(TravelOrderQuery query) {
        if (query == null) {
            return null;
        }
        return travelOrderDao.find(query);
    }

    @Override
    public List<TravelOrderGuestDO> getByOrId(Long orId) {
        if (Argument.isNotPositive(orId)) {
            return Collections.<TravelOrderGuestDO> emptyList();
        }
        return travelGuestDao.getByOrId(orId);
    }

    @Override
    public Boolean cancelOrder(TravelOrderDO orderDO) {
        if (orderDO == null) {
            return false;
        }
        boolean isSuccess = travelOrderDao.cancelOrder(orderDO);
        if (isSuccess) {
            createOrderMsg(orderDO, MessageTypeEnum.ORDER_MODIFY);
        }
        return isSuccess;
    }

    @Override
    public TravelOrderDO getById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelOrderDao.getById(id);
    }

    @Override
    public List<TravelOrderGuestDO> getByLId(Long id) {
        return travelGuestDao.getByLId(id);
    }

    @Override
    public List<TravelOrderGuestFullDO> getByLIdAndPrice(Long id) {
        return travelGuestDao.getByLIdAndPrice(id);
    }

	@Override
	public List<TravelOrderCountByStateDO> countByOrState(TravelOrderQuery travelOrderQuery) {
		return travelOrderDao.countByOrState(travelOrderQuery);
	}

	@Override
	public Integer countByOrderGuest() {
		return travelGuestDao.countByOrderGuest();
	}
}
