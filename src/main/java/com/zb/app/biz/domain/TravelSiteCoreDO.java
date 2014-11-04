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
 * @author zxc Jul 18, 2014 1:52:38 PM
 */
public class TravelSiteCoreDO implements Serializable {

    private static final long serialVersionUID = 1582167852612265519L;

    // 站点信息
    private Long              sId;                                    // 站点ID
    private String            sName;                                  // 站点名称
    private String            sProvince;                              // 站点省
    private String            sCity;                                  // 站点市
    private Integer           sSort;                                  // 排序

    // 出港点信息
    private Long              cId;                                    // 出港点ID
    private String            cName;                                  // 站点名称
    private Integer           cSort;                                  // 排序

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsProvince() {
        return sProvince;
    }

    public void setsProvince(String sProvince) {
        this.sProvince = sProvince;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public Integer getsSort() {
        return sSort;
    }

    public void setsSort(Integer sSort) {
        this.sSort = sSort;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcSort() {
        return cSort;
    }

    public void setcSort(Integer cSort) {
        this.cSort = cSort;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
