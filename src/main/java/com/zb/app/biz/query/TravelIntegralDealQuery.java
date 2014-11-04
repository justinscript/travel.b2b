/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * @author Administrator 2014-7-23 下午3:11:05
 */
public class TravelIntegralDealQuery extends Pagination {

    private Long    idId;       // 自动编号
    private Date    gmtCreate;  // 记录创建时间
    private Date    gmtModified; // 记录最后修改时间
    private Long    cId;        // 公司id
    private Long    mId;        // 用户id
    private String  idType;     // 积分类型(0=可用积分,1=冻结积分)
    private Integer idIntegral; // 消耗积分
    private Long    goId;       // 积分订单id
    private Long    gId;        // 积分产品id
    private Long    lId;        // 消费产品ID
    private String  idRemark;   // 备注

    public TravelIntegralDealQuery() {

    }

    public TravelIntegralDealQuery(Long cId, Long mId) {
        setcId(cId);
        setmId(mId);
    }

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

    public String getIdRemark() {
        return idRemark;
    }

    public void setIdRemark(String idRemark) {
        this.idRemark = idRemark;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long getGoId() {
        return goId;
    }

    public void setGoId(Long goId) {
        this.goId = goId;
    }

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }
}
