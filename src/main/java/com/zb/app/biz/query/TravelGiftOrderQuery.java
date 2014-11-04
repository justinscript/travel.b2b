/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * @author ZhouZhong 2014-7-24 下午5:54:16
 */
public class TravelGiftOrderQuery extends Pagination {

    private Long    goId;           // 自动编号
    private Date    gmtCreate;      // 记录创建时间
    private Date    gmtModified;    // 记录最后修改时间

    private Long    cId;            // 公司ID
    private Long    mId;            // 用户ID
    private Long    gId;            // 积分产品ID
    private String  goName;         // 订单名称
    private String  goProvince;     // 省
    private String  goCity;         // 市
    private String  goCounty;       // 区/县
    private String  goAddress;      // 详细地址
    private String  goMobile;       // 手机
    private String  goTel;          // 电话
    private String  goEmail;        // 邮箱
    private Integer goState;        // 状态(0=正常,1=停止)
    private Integer goCount;        // 兑换数量
    private Integer goIntegralCount; // 所需积分
    private String  gTitle;
    private String  mName;
    private String  mUserName;
    private String  cName;

    public TravelGiftOrderQuery() {

    }

    public TravelGiftOrderQuery(Long cId, Long mId) {
        setcId(cId);
        setmId(mId);
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

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public Long getGoId() {
        return goId;
    }

    public void setGoId(Long goId) {
        this.goId = goId;
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

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
    }

    public String getGoName() {
        return goName;
    }

    public void setGoName(String goName) {
        this.goName = goName;
    }

    public String getGoProvince() {
        return goProvince;
    }

    public void setGoProvince(String goProvince) {
        this.goProvince = goProvince;
    }

    public String getGoCity() {
        return goCity;
    }

    public void setGoCity(String goCity) {
        this.goCity = goCity;
    }

    public String getGoCounty() {
        return goCounty;
    }

    public void setGoCounty(String goCounty) {
        this.goCounty = goCounty;
    }

    public String getGoAddress() {
        return goAddress;
    }

    public void setGoAddress(String goAddress) {
        this.goAddress = goAddress;
    }

    public String getGoMobile() {
        return goMobile;
    }

    public void setGoMobile(String goMobile) {
        this.goMobile = goMobile;
    }

    public String getGoTel() {
        return goTel;
    }

    public void setGoTel(String goTel) {
        this.goTel = goTel;
    }

    public String getGoEmail() {
        return goEmail;
    }

    public void setGoEmail(String goEmail) {
        this.goEmail = goEmail;
    }

    public Integer getGoState() {
        return goState;
    }

    public void setGoState(Integer goState) {
        this.goState = goState;
    }

    public Integer getGoCount() {
        return goCount;
    }

    public void setGoCount(Integer goCount) {
        this.goCount = goCount;
    }

    public Integer getGoIntegralCount() {
        return goIntegralCount;
    }

    public void setGoIntegralCount(Integer goIntegralCount) {
        this.goIntegralCount = goIntegralCount;
    }

    public String getgTitle() {
        return gTitle;
    }

    public void setgTitle(String gTitle) {
        this.gTitle = gTitle;
    }
}
