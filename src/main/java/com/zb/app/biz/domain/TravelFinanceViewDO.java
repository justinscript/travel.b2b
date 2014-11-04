/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.util.Date;

/**
 * @author Administrator 2014-8-12 下午4:03:41
 */
public class TravelFinanceViewDO extends TravelFinanceDO {

    private static final long serialVersionUID = 2740153052978544224L;

    private String            lTitle;                                 // 线路名称
    private Date              goGroupTime;                            // 出团日期
    private Long              zId;                                    // 专线ID
    private String            tourName;                               // 组团社名称
    private String            accountName;                            // 地接社名称
    private Long              lId;                                    // 线路ID
    private String            orName;                                 // 预定人名称
    private String            cCustomName;                            // 公司联系人
    private String            lGroupNumber;                           // 产品编号
    private Date              orCreateTime;                           // 订单生成时间
    private String            orderNumber;                            // 订单编号
    private String            orderPeopleCount;                       // 订单人数
    private Long              mId;                                    // 订单预定人
    // 用于导出Excel
    private String            goGroupTimeString;                      // 出团日期
    private String            orCreateTimeString;                     // 订单创建时间
    private String            gmtModifiedString;                      // 财务修改时间

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getOrderPeopleCount() {
        return orderPeopleCount;
    }

    public void setOrderPeopleCount(String orderPeopleCount) {
        this.orderPeopleCount = orderPeopleCount;
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

    public Date getOrCreateTime() {
        return orCreateTime;
    }

    public void setOrCreateTime(Date orCreateTime) {
        this.orCreateTime = orCreateTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public String getlTitle() {
        return lTitle;
    }

    public void setlTitle(String lTitle) {
        this.lTitle = lTitle;
    }

    public Date getGoGroupTime() {
        return goGroupTime;
    }

    public void setGoGroupTime(Date goGroupTime) {
        this.goGroupTime = goGroupTime;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
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

}
