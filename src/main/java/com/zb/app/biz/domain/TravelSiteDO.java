/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * 站点管理表
 * 
 * @author zxc Jul 30, 2014 11:24:28 PM
 */
public class TravelSiteDO implements Serializable {

    private static final long serialVersionUID = -65413468469461321L;

    private Long              sId;                                   // 自动编号
    private Date              gmtCreate;                             // 创建时间
    private Date              gmtModified;                           // 修改时间

    private String            sName;                                 // 站点名称
    @NotEmpty(message = "站点省不能为空")
    private String            sProvince;                             // 站点省
    @NotEmpty(message = "站点市不能为空")
    private String            sCity;                                 // 站点市
    private Long              sToid;                                 // 站点上级ID,0 出港地
    private Integer           sState;                                // 状态:0=正常，1=停止
    private Integer           sSort;                                 // 排序

    public TravelSiteDO() {

    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
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

    public String getsProvince() {
        return sProvince;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
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

    public Long getsToid() {
        return sToid;
    }

    public void setsToid(Long sToid) {
        this.sToid = sToid;
    }

    public Integer getsState() {
        return sState;
    }

    public void setsState(Integer sState) {
        this.sState = sState;
    }

    public Integer getsSort() {
        return sSort;
    }

    public void setsSort(Integer sSort) {
        this.sSort = sSort;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
