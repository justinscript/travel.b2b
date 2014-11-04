/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * 积分表
 * 
 * @author Administrator 2014-7-24 上午10:46:35
 */
public class TravelIntegralQuery extends Pagination {

    private Long    iId;         // number(20) not null, --自动编号
    private Date    gmtCreate;   // timestamp not null, --记录创建时间
    private Date    gmtModified; // timestamp not null, --记录最后修改时间

    private Long    cId;         // number(20), --公司id
    private Long    mId;         // number(20), --用户id
    private Integer iSource;     // number(10), --积分来源(0=消费产品,1=返还积分,2=转入)
    private Long	iFrozen;								 // float, --冻结积分
	private Long	iAltogether;							 // float, --总积分
    private Long  	iBalance;    // float, --积分余额
    private Long  	iAddintegral; // float --本次新增积分
    private Long    lId;         // 消费产品ID
    private String  iRemark;     // 备注

    public TravelIntegralQuery() {

    }

    public TravelIntegralQuery(Long cId, Long mId) {
        setcId(cId);
        setmId(mId);
    }

    public Long getiId() {
        return iId;
    }

    public void setiId(Long iId) {
        this.iId = iId;
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

    public Integer getiSource() {
        return iSource;
    }

    public void setiSource(Integer iSource) {
        this.iSource = iSource;
    }

    public Long getiBalance() {
        return iBalance;
    }

    public void setiBalance(Long iBalance) {
        this.iBalance = iBalance;
    }

    public Long getiAddintegral() {
        return iAddintegral;
    }

    public void setiAddintegral(Long iAddintegral) {
        this.iAddintegral = iAddintegral;
    }

    public String getiRemark() {
        return iRemark;
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

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public void setiRemark(String iRemark) {
        this.iRemark = iRemark;
    }

	public Long getiFrozen() {
		return iFrozen;
	}

	public void setiFrozen(Long iFrozen) {
		this.iFrozen = iFrozen;
	}

	public Long getiAltogether() {
		return iAltogether;
	}

	public void setiAltogether(Long iAltogether) {
		this.iAltogether = iAltogether;
	}
}
