/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.common.pagination.Pagination;

/**
 * @author ZhouZhong 2014-6-18 下午1:41:56
 */
public class TravelMemberQuery extends Pagination {

    private Long    mId;
    private Long    cId;
    private Integer cType;
    private String  mUserName;
    private String  mPassword;
    public TravelMemberQuery() {

    }

    public TravelMemberQuery(Long cId) {
        setcId(cId);
    }

    public TravelMemberQuery(Long mId, Long cId) {
        setmId(mId);
        setcId(cId);
    }

    public TravelMemberQuery(String mUserName, Integer type) {
        setmUserName(mUserName);
        setcType(type);
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

	public String getmPassword() {
		return mPassword;
	}

	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}
}
