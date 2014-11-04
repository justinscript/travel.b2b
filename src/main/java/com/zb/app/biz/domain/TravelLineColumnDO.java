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
 * 专线与公司关联表，公司所属专线
 * 
 * @author Administrator 2014-6-27 下午2:18:14
 */
public class TravelLineColumnDO implements Serializable {

    private static final long serialVersionUID = -465461648764161L;

    private Long              lcId;                                // 自动编号
    private Date              gmtCreate;                           // 创建时间
    private Date              gmtModified;                         // 修改时间

    private Long              zId;                                 // 专线ID
    private Long              cId;                                 // 公司ID

    public TravelLineColumnDO() {

    }

    public TravelLineColumnDO(Long zId, Long cId) {
        setzId(zId);
        setcId(cId);
    }

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

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
