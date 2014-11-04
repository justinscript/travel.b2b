/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.event;

import java.util.Date;

import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.common.notify.event.BaseEvent;
import com.zb.app.common.notify.event.EventType;

/**
 * @author zxc Aug 7, 2014 5:44:06 PM
 */
public class CompanyEvent extends BaseEvent {

    private static final long serialVersionUID = -8162460158235421458L;

    private EventType         eventType;

    private long              happen;

    private TravelCompanyDO   travelCompany;

    // 公司id
    private Long              cId;

    public CompanyEvent(EventType eventType, TravelCompanyDO travelCompany) {
        this(eventType, null, travelCompany);
    }

    public CompanyEvent(EventType eventType, Long cId, TravelCompanyDO travelCompany) {
        setcId(cId);
        setEventType(eventType);
        setTravelCompany(travelCompany);
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TravelCompanyDO getData() {
        return travelCompany;
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

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public TravelCompanyDO getTravelCompany() {
        return travelCompany;
    }

    public void setTravelCompany(TravelCompanyDO travelCompany) {
        this.travelCompany = travelCompany;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
