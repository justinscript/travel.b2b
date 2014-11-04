/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.io.Serializable;

/**
 * @author Administrator 2014-8-18 下午3:21:34
 */
public class FinanceExcelVO implements Serializable {

    private static final long serialVersionUID = 6451770228968194800L;

    private String            fSerialNumber;                          // VARCHAR2(50), --流水号
    private String            orderNumber;                            // 订单编号
    private String            lGroupNumber;                           // 产品编号
    private String            lTitle;                                 // 线路名称
    private String            goGroupTimeString;                      // 出团日期
    private String            tourName;                               // 组团社名称
    private String            orName;                                 // 预定人名称
    private String            cCustomName;                            // 公司联系人
    private String            accountName;                            // 地接社名称
    private Float             fCountPrice;                            // 总金额
    private Float             fReceivable;                            // FLOAT, --应收款
    private Float             fReceipt;                               // FLOAT, --已收款
    private String            orCreateTimeString;                     // 订单生成时间
    private String            gmtModifiedString;                      // TIMESTAMP NOT NULL, --记录最后修改时间

    public String getfSerialNumber() {
        return fSerialNumber;
    }

    public void setfSerialNumber(String fSerialNumber) {
        this.fSerialNumber = fSerialNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
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

    public String getOrName() {
        return orName;
    }

    public void setOrName(String orName) {
        this.orName = orName;
    }

    public String getcCustomName() {
        return cCustomName;
    }

    public void setcCustomName(String cCustomName) {
        this.cCustomName = cCustomName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Float getfCountPrice() {
        return fCountPrice;
    }

    public void setfCountPrice(Float fCountPrice) {
        this.fCountPrice = fCountPrice;
    }

    public Float getfReceivable() {
        return fReceivable;
    }

    public String getGoGroupTimeString() {
        return goGroupTimeString;
    }

    public void setGoGroupTimeString(String goGroupTimeString) {
        this.goGroupTimeString = goGroupTimeString;
    }

    public String getOrCreateTimeString() {
        return orCreateTimeString;
    }

    public void setOrCreateTimeString(String orCreateTimeString) {
        this.orCreateTimeString = orCreateTimeString;
    }

    public String getGmtModifiedString() {
        return gmtModifiedString;
    }

    public void setGmtModifiedString(String gmtModifiedString) {
        this.gmtModifiedString = gmtModifiedString;
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

    public void init() {
        this.fCountPrice = this.fReceipt + this.fReceivable;
    }
}
