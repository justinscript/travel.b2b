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
 * @author Administrator 2014-8-4 下午1:56:17
 */
public class TravelFinanceDetailDO implements Serializable {

    private static final long serialVersionUID = 2336257006169699486L;

    private Long              fdId;                                   // NUMBER(20) NOT NULL, --自动编号
    private Date              gmtCreate;                              // TIMESTAMP NOT NULL, --记录创建时间
    private Date              gmtModified;                            // TIMESTAMP NOT NULL, --记录最后修改时间

    private Long              fId;                                    // NUMBER(20), --财务ID
    private Long              mId;                                    // NUMBER(20), --操作人ID
    private Date              fdSaveTime;                             // TIMESTAMP, --收款时间
    private Integer           fdType;                                 // NUMBER(9), --(0=收款，1=退款)
    private Float             fdPrice;                                // FLOAT, --价格
    private Integer           fdPay;                                  // NUMBER(9), --支付方式(0=支票,1=现金,2=电汇,3=其它)
    private String            fdPayBank;                              // VARCHAR2(50), --银行
    private String            fdRemark;                               // VARCHAR2(200) --备注

    public Long getFdId() {
        return fdId;
    }

    public void setFdId(Long fdId) {
        this.fdId = fdId;
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

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Date getFdSaveTime() {
        return fdSaveTime;
    }

    public void setFdSaveTime(Date fdSaveTime) {
        this.fdSaveTime = fdSaveTime;
    }

    public Integer getFdType() {
        return fdType;
    }

    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }

    public Float getFdPrice() {
        return fdPrice;
    }

    public void setFdPrice(Float fdPrice) {
        this.fdPrice = fdPrice;
    }

    public Integer getFdPay() {
        return fdPay;
    }

    public void setFdPay(Integer fdPay) {
        this.fdPay = fdPay;
    }

    public String getFdPayBank() {
        return fdPayBank;
    }

    public void setFdPayBank(String fdPayBank) {
        this.fdPayBank = fdPayBank;
    }

    public String getFdRemark() {
        return fdRemark;
    }

    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
