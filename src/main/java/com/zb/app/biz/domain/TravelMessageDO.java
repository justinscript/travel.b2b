/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import com.zb.app.biz.cons.MessageReadStateEnum;
import com.zb.app.biz.cons.MessageTypeEnum;

/**
 * @author zxc Aug 1, 2014 2:44:37 PM
 */
public class TravelMessageDO implements Serializable {

    private static final long serialVersionUID = -6192755146057040458L;

    private Long              meId;                                    // 自动编号
    private Date              gmtCreate;                               // 创建时间
    private Date              gmtModified;                             // 修改时间

    private Long              fromCid;                                 // 创建者公司ID
    private Long              fromMid;                                 // 创建者用户ID
    private Integer           fromType;                                // 创建者公司类型
    private Long              toCid;                                   // 接收者公司ID
    private Long              toMid;                                   // 接收者用户ID
    private Integer           toType;                                  // 接收者公司类型

    private String            title;                                   // 消息标题
    private String            content;                                 // 消息内容
    private Integer           messageState;                            // 消息状态(0=已读，1=未读,-1,已删除)
    private Integer           messageType;                             // 消息类型(0=订单未读，1=订单修改，2=订单取消，3=订单恢复)

    public TravelMessageDO() {

    }

    public TravelMessageDO(Long fromCid, Long toCid, String title, String content, MessageTypeEnum messageType) {
        this(fromCid, toCid, null, null, title, content, messageType);
    }

    public TravelMessageDO(Long fromCid, Long toCid, Long toMid, String title, String content,
                           MessageTypeEnum messageType) {
        this(fromCid, toCid, toMid, null, title, content, messageType);
    }

    public TravelMessageDO(Long fromCid, Long toCid, Long toMid, Integer toType, String title, String content,
                           MessageTypeEnum messageType) {
        setFromCid(fromCid);
        setToCid(toCid);
        setToMid(toMid);
        setToType(toType);
        setTitle(title);
        setContent(content);
        setMessageType(messageType.getValue());
    }

    public TravelMessageDO(Long meId, Integer messageState) {
        setMeId(meId);
        setMessageState(messageState);
    }

    public TravelMessageDO(Long meId, MessageReadStateEnum messageState) {
        setMeId(meId);
        setMessageState(messageState.value);
    }

    public Long getMeId() {
        return meId;
    }

    public void setMeId(Long meId) {
        this.meId = meId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getFromCid() {
        return fromCid;
    }

    public void setFromCid(Long fromCid) {
        this.fromCid = fromCid;
    }

    public Long getFromMid() {
        return fromMid;
    }

    public void setFromMid(Long fromMid) {
        this.fromMid = fromMid;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Long getToCid() {
        return toCid;
    }

    public void setToCid(Long toCid) {
        this.toCid = toCid;
    }

    public Long getToMid() {
        return toMid;
    }

    public void setToMid(Long toMid) {
        this.toMid = toMid;
    }

    public Integer getToType() {
        return toType;
    }

    public void setToType(Integer toType) {
        this.toType = toType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMessageState() {
        return messageState;
    }

    public void setMessageState(Integer messageState) {
        this.messageState = messageState;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
}
