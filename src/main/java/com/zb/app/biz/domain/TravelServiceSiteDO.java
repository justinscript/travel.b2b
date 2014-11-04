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
 * 客服站点类
 * 
 * @author ZhouZhong 2014-9-1 上午11:33:33
 */
public class TravelServiceSiteDO implements Serializable {

    private static final long serialVersionUID = -4837508995753991431L;
    private Long              szId;                                    // number(20) not null; --主键编号
    private Date              gmtCreate;                              // timestamp not null; --创建时间
    private Date              gmtModified;                            // timestamp not null; --修改时间
    private Integer           sId;
    private Integer           zId;
    
    
    public Long getSzId() {
        return szId;
    }

    
    public void setSzId(Long szId) {
        this.szId = szId;
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

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getzId() {
        return zId;
    }

    public void setzId(Integer zId) {
        this.zId = zId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
