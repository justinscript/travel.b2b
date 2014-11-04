/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.event;

import java.util.Date;

import com.zb.app.common.notify.event.BaseEvent;
import com.zb.app.common.notify.event.EventType;

/**
 * @author zxc Aug 21, 2014 2:30:14 PM
 */
public class LineEvent extends BaseEvent {

    private static final long serialVersionUID = 519281369774129458L;

    private EventType         eventType;

    private long              happen;

    private Long              lId;

    public LineEvent(EventType eventType, Long lId) {
        setlId(lId);
        setEventType(eventType);
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public <T> T getData() {
        return null;
    }

    @Override
    public String summary() {
        return null;
    }

    @Override
    public Date time() {
        return null;
    }

    public long getHappen() {
        return happen;
    }

    public void setHappen(long happen) {
        this.happen = happen;
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
