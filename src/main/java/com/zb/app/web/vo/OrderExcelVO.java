/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

/**
 * @author ZhouZhong 2014-8-27 下午3:30:46
 */
public class OrderExcelVO {

    private String gmtCreate;
    private String orState;
    private String orOrderId;
    private String orGoGroupTime;
    private String lTile;
    private String cName;
    private String mName;
    private String mTel;
    private Double orPirceCount;
    private String gmtModified;

    public OrderExcelVO() {

    }

    public OrderExcelVO(String gmtCreateString, String orStateString, String orOrderId, String orGoGroupTimeString,
                        String lTile, String cName, String mName, String mTel, Double orPirceCount,
                        String gmtModifiedString) {
        this.gmtCreate = gmtCreateString;
        this.orState = orStateString;
        this.orOrderId = orOrderId;
        this.orGoGroupTime = orGoGroupTimeString;
        this.lTile = lTile;
        this.cName = cName;
        this.mName = mName;
        this.mTel = mTel;
        this.orPirceCount = orPirceCount;
        this.gmtModified = gmtModifiedString;
    }

    public String getlTile() {
        return lTile;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getOrState() {
        return orState;
    }

    public void setOrState(String orState) {
        this.orState = orState;
    }

    public String getOrOrderId() {
        return orOrderId;
    }

    public void setOrOrderId(String orOrderId) {
        this.orOrderId = orOrderId;
    }

    public String getOrGoGroupTime() {
        return orGoGroupTime;
    }

    public void setOrGoGroupTime(String orGoGroupTime) {
        this.orGoGroupTime = orGoGroupTime;
    }

    public void setlTile(String lTile) {
        this.lTile = lTile;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
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

    public Double getOrPirceCount() {
        return orPirceCount;
    }

    public void setOrPirceCount(Double orPirceCount) {
        this.orPirceCount = orPirceCount;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
