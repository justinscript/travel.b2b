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

import com.zb.app.biz.cons.MessageReadStateEnum;
import com.zb.app.biz.cons.MessageTypeEnum;
import com.zb.app.biz.dao.TravelMessageDao;
import com.zb.app.biz.domain.MessageData;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.biz.query.TravelMessageQuery;
import com.zb.app.biz.service.interfaces.MessageService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.notify.NotifyService;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.websocket.api.MessageEvent;
import com.zb.app.websocket.api.MessageMapper;

/**
 * @author zxc Aug 1, 2014 2:49:48 PM
 */
@Service
public class TravelMessageServiceImpl implements MessageService {

    @Autowired
    private TravelMessageDao messageDao;

    @Autowired
    private NotifyService    notifyService;

    @Override
    public MessageData getMessageData(Long toCid) {
        List<TravelMessageDO> list = list(new TravelMessageQuery(WebUserTools.getCid()));
        if (list == null || list.size() == 0) {
            return null;
        }
        return new MessageData(list.toArray(new TravelMessageDO[list.size()]));
    }

    private void notifyMsgEvent(TravelMessageDO message, EventType eventType, MessageMapper<?, ?> mm) {
        Assert.assertNotNull(message);
        Assert.assertPositive(message.getToCid());
        //Assert.assertPositive(message.getToMid());
        notifyService.notify(new MessageEvent(message.getToCid(), message.getToMid(), eventType, mm));
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelMessageDO find(TravelMessageQuery query) {
        if (query == null) {
            return null;
        }
        return messageDao.find(query);
    }

    @Override
    public List<TravelMessageDO> list(TravelMessageQuery query) {
        if (query == null) {
            return Collections.<TravelMessageDO> emptyList();
        }
        return messageDao.list(query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginationList<TravelMessageDO> listPagination(TravelMessageQuery query, IPageUrl... iPageUrl) {
        if (query == null) {
            return (PaginationList<TravelMessageDO>) Collections.<TravelMessageDO> emptyList();
        }
        return (PaginationList<TravelMessageDO>) messageDao.paginationList(query, iPageUrl);
    }

    @Override
    public TravelMessageDO getTravelMessageById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return messageDao.getById(id);
    }

    @Override
    public Integer addTravelMessage(TravelMessageDO... messages) {
        if (messages == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(messages);
        if (Argument.isEmptyArray(messages)) {
            return 0;
        }
        Integer count = messageDao.insert(messages);
        if (messages.length == 1) {
            TravelMessageDO message = messages[0];
            switchMessageNotify(message);
            return message.getMeId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public boolean updateTravelMessage(TravelMessageDO message) {
        if (message == null) {
            return false;
        }
        if (Argument.isNotPositive(message.getMeId())) {
            return false;
        }
        boolean isSuccess = messageDao.updateById(message);
        if (isSuccess) {
            TravelMessageDO _message = messageDao.getById(message.getMeId());
            switchMessageNotify(_message);
        }
        return isSuccess;
    }

    @Override
    public boolean deleteTravelMessage(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return updateTravelMessage(new TravelMessageDO(id, MessageReadStateEnum.DELETE));
    }

    @Override
    public boolean realDeleteTravelMessage(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return messageDao.deleteById(id);
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void switchMessageNotify(TravelMessageDO message) {
        MessageTypeEnum messageType = MessageTypeEnum.getAction(message.getMessageType());
        switch (messageType) {
            case NEW_ORDER_UNREAD:
                notifyMsgEvent(message, EventType.orderAdd, null);
                break;

            case NEW_INTEGRAL:
                notifyMsgEvent(message, EventType.orderAdd, null);
                break;

            default:
                break;
        }
    }
}
