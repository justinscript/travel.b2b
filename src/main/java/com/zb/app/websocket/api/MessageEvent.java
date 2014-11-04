/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.api;

import java.util.Date;

import com.zb.app.common.notify.event.BaseEvent;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.web.webuser.ZuobianWebUser;

/**
 * @author zxc Jul 29, 2014 4:07:12 PM
 */
public class MessageEvent extends BaseEvent {

    private static final long   serialVersionUID = 8180547546767390969L;

    private EventType           eventType;

    private ZuobianWebUser      webUser;

    private MessageMapper<?, ?> messageMapper;

    private long                sendTime;

    public MessageEvent(Long cId, Long mId, EventType eventType, MessageMapper<?, ?> messageMapper) {
        this(new ZuobianWebUser(cId, mId), null, messageMapper);
    }

    public MessageEvent(ZuobianWebUser webUser, MessageMapper<?, ?> messageMapper) {
        this(webUser, null, messageMapper);
    }

    public MessageEvent(ZuobianWebUser webUser, EventType eventType, MessageMapper<?, ?> messageMapper) {
        this.webUser = webUser;
        this.messageMapper = messageMapper;
        this.eventType = eventType;
        this.sendTime = System.currentTimeMillis();
    }

    @Override
    public Date time() {
        return new Date();
    }

    @Override
    public EventType getEventType() {
        return eventType == null ? EventType.orderAdd : eventType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MessageMapper<?, ?> getData() {
        return messageMapper;
    }

    @Override
    public String summary() {
        return "new message!currentTimeMillis=" + getSendTime();
    }

    public MessageMapper<?, ?> getMessageMapper() {
        return messageMapper;
    }

    public void setMessageMapper(MessageMapper<?, ?> messageMapper) {
        this.messageMapper = messageMapper;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public ZuobianWebUser getWebUser() {
        return webUser;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public void setWebUser(ZuobianWebUser webUser) {
        this.webUser = webUser;
    }
}
