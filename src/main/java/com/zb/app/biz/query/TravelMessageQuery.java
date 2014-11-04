/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.biz.cons.MessageReadStateEnum;
import com.zb.app.biz.cons.MessageTypeEnum;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Aug 1, 2014 2:46:53 PM
 */
public class TravelMessageQuery extends Pagination {

    private TravelMessageDO message = new TravelMessageDO();

    // 按时间查询,创建时间的起始查询时间,截止查询时间
    private String          startGmtCreate;
    private String          endGmtCreate;

    // 按时间查询,更新时间的起始查询时间,截止查询时间
    private String          startGmtModified;
    private String          endGmtModified;

    public TravelMessageQuery() {

    }

    public TravelMessageQuery(Long toCid) {
        message.setToCid(toCid);
        message.setMessageState(MessageReadStateEnum.UN_READ.value);
    }

    public TravelMessageQuery(String title, MessageReadStateEnum messageState, MessageTypeEnum messageType) {
        this(null, null, messageState, messageType, title);
    }

    public TravelMessageQuery(Long toCid, MessageTypeEnum messageType) {
        message.setToCid(toCid);
        message.setMessageState(MessageReadStateEnum.UN_READ.value);
        message.setMessageType(messageType.getValue());
    }

    public TravelMessageQuery(MessageTypeEnum messageType, Long fromCid) {
        message.setFromCid(fromCid);
        message.setMessageState(MessageReadStateEnum.UN_READ.value);
        message.setMessageType(messageType.getValue());
    }

    public TravelMessageQuery(Long toCid, Long toMid, MessageReadStateEnum messageState, MessageTypeEnum messageType) {
        message.setToCid(toCid);
        message.setToMid(toMid);
        message.setMessageState(messageState.value);
        message.setMessageType(messageType.getValue());
    }

    public TravelMessageQuery(Long fromCid, Long fromMid, MessageReadStateEnum messageState,
                              MessageTypeEnum messageType, String title) {
        message.setFromCid(fromCid);
        message.setFromMid(fromMid);
        message.setTitle(title);
        message.setMessageState(messageState.value);
        message.setMessageType(messageType.getValue());
    }

    public TravelMessageDO getMessage() {
        return message;
    }

    public void setMessage(TravelMessageDO message) {
        this.message = message;
    }

    public String getStartGmtCreate() {
        return startGmtCreate;
    }

    public void setStartGmtCreate(String startGmtCreate) {
        this.startGmtCreate = startGmtCreate;
    }

    public String getEndGmtCreate() {
        return endGmtCreate;
    }

    public void setEndGmtCreate(String endGmtCreate) {
        this.endGmtCreate = endGmtCreate;
    }

    public String getStartGmtModified() {
        return startGmtModified;
    }

    public void setStartGmtModified(String startGmtModified) {
        this.startGmtModified = startGmtModified;
    }

    public String getEndGmtModified() {
        return endGmtModified;
    }

    public void setEndGmtModified(String endGmtModified) {
        this.endGmtModified = endGmtModified;
    }
}
