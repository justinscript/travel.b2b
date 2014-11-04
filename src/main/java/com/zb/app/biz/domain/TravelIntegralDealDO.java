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
 * @author Administrator 2014-7-23 下午12:56:07
 */
public class TravelIntegralDealDO implements Serializable {

    private static final long serialVersionUID = -7655645775790371528L;

    private Long              idId;                                    // 自动编号
    private Date              gmtCreate;                               // 记录创建时间
    private Date              gmtModified;                             // 记录最后修改时间

    private Integer           cId;                                     // 公司id
    private Integer           mId;                                     // 用户id
    private String            idType;                                  // 积分类型(0=可用积分,1=冻结积分)
    private Integer           idIntegral;                              // 消耗积分
    private Integer           goId;                                    // 积分订单id
    private Integer           gId;                                     // 积分产品id
    private Integer           lId;
    private String            idRemark;

    public Long getIdId() {
        return idId;
    }

    public void setIdId(Long idId) {
        this.idId = idId;
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

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Integer getIdIntegral() {
        return idIntegral;
    }

    public void setIdIntegral(Integer idIntegral) {
        this.idIntegral = idIntegral;
    }

    public Integer getGoId() {
        return goId;
    }

    public void setGoId(Integer goId) {
        this.goId = goId;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
    }
    
    public Integer getlId() {
        return lId;
    }
    
    public void setlId(Integer lId) {
        this.lId = lId;
    }
    
    public String getIdRemark() {
        return idRemark;
    }
    
    public void setIdRemark(String idRemark) {
        this.idRemark = idRemark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
