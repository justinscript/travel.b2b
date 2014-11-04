/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.io.Serializable;

import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Aug 5, 2014 1:31:32 PM
 */
public class TravelFinanceQuery extends Pagination implements Serializable {

    private static final long serialVersionUID = -2073536732540580283L;
    private Long              fId;                                     // NUMBER(20) NOT NULL, --自动编号
    private Long              orId;                                    // NUMBER(20), --订单ID
    private Integer           fType;                                   // NUMBER(10), --财务类型
    private String            fSerialNumber;                           // VARCHAR2(50), --流水号
    private Long              accountCid;                              // NUMBER(20), --地接社公司ID
    private Long              tourCid;                                 // NUMBER(20), --组团社公司ID
    private Float             fReceivable;                             // FLOAT, --应收款
    private Float             fReceipt;                                // FLOAT, --已收款
    private Long              appId;                                   // NUMBER(20) --最后修改操作人ID
    private Long              zId;                                     // 专线ID
    private String            lTitle;                                  // 线路名称
    private String            tourName;                                // 组团社名称
    private String            accountName;                             // 地接社名称
    private Long              mId;                                     // 预定用户
    //出团日期查询
    private String            goGroupTime;                             
    private String            goGroupEndTime;                          
    //订单日期查询
    private String            orderTime;
    private String            orderEndTime;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getGoGroupTime() {
        return goGroupTime;
    }

    public void setGoGroupTime(String goGroupTime) {
        this.goGroupTime = goGroupTime;
    }

    public String getGoGroupEndTime() {
        return goGroupEndTime;
    }

    public void setGoGroupEndTime(String goGroupEndTime) {
        this.goGroupEndTime = goGroupEndTime;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public String getlTitle() {
        return lTitle;
    }

    public void setlTitle(String lTitle) {
        this.lTitle = lTitle;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
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
        this.fType = fType == 1 || fType == 2 ? fType : null;
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
}
