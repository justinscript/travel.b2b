/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author Administrator 2014-9-2 下午3:57:35
 */
public class TravelRecruitingDO implements Serializable {

    private static final long serialVersionUID = -7167623139115042586L;

    private String            cName;                                   // 地接社名称
    private String            zName;                                   // 专线名称
    private String            adultCount;                              // 成人人数
    private String            childCount;                              // 小孩人数
    private String            babyCount;                               // 婴儿人数

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getzName() {
        return zName;
    }

    public void setzName(String zName) {
        this.zName = zName;
    }

    public String getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(String adultCount) {
        this.adultCount = adultCount;
    }

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }

    public String getBabyCount() {
        return babyCount;
    }

    public void setBabyCount(String babyCount) {
        this.babyCount = babyCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
