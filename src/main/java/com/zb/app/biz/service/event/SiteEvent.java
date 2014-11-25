/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.event;

import java.util.Date;

import com.zb.app.common.notify.event.BaseEvent;
import com.zb.app.common.notify.event.EventType;

/**
 * 站点专线发生变化,缓存异步通知事件
 * 
 * @author zxc Aug 6, 2014 2:44:29 PM
 */
public class SiteEvent extends BaseEvent {

    private static final long serialVersionUID = 6227223106770227380L;

    private EventType         eventType;

    private long              happen;

    // 公司id
    private Long              cId;
    // 站点id
    private Long              sId;
    // 专线id
    private Long              zId;

    public SiteEvent(EventType eventType, Long sId) {
        this(eventType, sId, null, null);
    }

    public SiteEvent(EventType eventType, Long cId, Long zId) {
        this(eventType, null, zId, cId);
    }

    public SiteEvent(EventType eventType, Long sId, Long zId, Long cId) {
        this.eventType = eventType;
        this.sId = sId;
        this.zId = zId;
        this.cId = cId;
        this.happen = System.currentTimeMillis();
    }

    @Override
    public EventType getEventType() {
        return eventType == null ? EventType.siteAdd : eventType;
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

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Long getzId() {
        return zId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
