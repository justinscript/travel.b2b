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
 * @author ZhouZhong 标签类目表
 */
public class TravelLabelCategoryDO implements Serializable {

    private static final long serialVersionUID = 2255751643198088573L;

    private Long              lcId;                                   // 主键编号
    private Date              gmtCreate;                              // 创建时间
    private Date              gmtModified;                            // 修改时间

    private Long              parentId;                               // 父类ID
    private String            lcName;                                 // 标签名称
    private Integer           lcType;                                 // 热门类型
    private Long              sId;                                    // 站点ID
    private String            province;                               // 省名
    private String            city;                                   // 城市名
    private Integer           lineType;                               // 线路类型
    private Integer           lcSort;                                 // 线路类型

    public Long getLcId() {
        return lcId;
    }

    public void setLcId(Long lcId) {
        this.lcId = lcId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLcName() {
        return lcName;
    }

    public void setLcName(String lcName) {
        this.lcName = lcName;
    }

    public Integer getLcType() {
        return lcType;
    }

    public void setLcType(Integer lcType) {
        this.lcType = lcType;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public Integer getLcSort() {
        return lcSort;
    }

    public void setLcSort(Integer lcSort) {
        this.lcSort = lcSort;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
