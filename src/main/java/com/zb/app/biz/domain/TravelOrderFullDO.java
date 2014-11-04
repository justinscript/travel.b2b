/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author ZhouZhong 2014-7-9 下午3:23:26
 */
public class TravelOrderFullDO extends TravelOrderDO {

    private static final long serialVersionUID = 3579103073017982030L;

    private String            lTile;                                  // 线路标题
    private Integer            lViews;                                  // 线路浏览量
    private String            mTel;
    private String            cName;
    private String            mName;
    private String            accName;
    
    public String getAccName() {
        return accName;
    }
    
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getlTile() {
        return lTile;
    }

    public void setlTile(String lTile) {
        this.lTile = lTile;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmTel() {
        return mTel;
    }

    public void setmTel(String mTel) {
        this.mTel = mTel;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getlViews() {
		return lViews;
	}

	public void setlViews(Integer lViews) {
		this.lViews = lViews;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
