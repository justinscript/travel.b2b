/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author Administrator 2014-8-4 下午1:49:00
 */
public class TravelFinanceDO implements Serializable {

    private static final long serialVersionUID = -6920785353385179606L;

    private Long              fId;                                     // NUMBER(20) NOT NULL, --自动编号
    private Date              gmtCreate;                               // TIMESTAMP NOT NULL, --记录创建时间
    private Date              gmtModified;                             // TIMESTAMP NOT NULL, --记录最后修改时间

    private Long              orId;                                    // NUMBER(20), --订单ID
    private Integer           fType;                                   // NUMBER(10), --财务类型
    private String            fSerialNumber;                           // VARCHAR2(50), --流水号
    private Long              accountCid;                              // NUMBER(20), --地接社公司ID
    private Long              tourCid;                                 // NUMBER(20), --组团社公司ID
    private Float             fReceivable;                             // FLOAT, --应收款
    private Float             fReceipt;                                // FLOAT, --已收款
    private Long              appId;                                   // NUMBER(20) --最后修改操作人ID

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
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

    public Long getOrId() {
        return orId;
    }

    public void setOrId(Long orId) {
        this.orId = orId;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public String getfSerialNumber() {
        return fSerialNumber;
    }

    public void setfSerialNumber(String fSerialNumber) {
        this.fSerialNumber = fSerialNumber;
    }

    public Long getAccountCid() {
        return accountCid;
    }

    public void setAccountCid(Long accountCid) {
        this.accountCid = accountCid;
    }

    public Long getTourCid() {
        return tourCid;
    }

    public void setTourCid(Long tourCid) {
        this.tourCid = tourCid;
    }

    public Float getfReceivable() {
        return fReceivable;
    }

    public void setfReceivable(Float fReceivable) {
        this.fReceivable = fReceivable;
    }

    public Float getfReceipt() {
        return fReceipt;
    }

    public void setfReceipt(Float fReceipt) {
        this.fReceipt = fReceipt;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
