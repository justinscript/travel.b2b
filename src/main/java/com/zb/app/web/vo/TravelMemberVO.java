/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.zb.app.web.validation.annotation.Matches;

/**
 * @author ZhouZhong 2014-6-16 下午2:09:15
 */
@Matches(field = "mPassword", verifyField = "mAffirmPassword", message = "两次密码不一致")
public class TravelMemberVO {

    /**
     * 用户ID
     */
    private Long    mId;
    /**
     * 记录创建时间
     */
    private Date    gmtCreate;
    /**
     * 记录最后修改时间
     */
    private Date    gmtModified;
    /**
     * 公司ID
     */
    private Long    cId;
    /**
     * 头像
     */
    private String  mPic;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String  mUserName;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String  mPassword;
    /**
     * 确认密码
     */
    @NotNull(message = "确认密码不能不能为空")
    private String  mAffirmPassword;

    /**
     * 真实姓名
     */
    @NotNull(message = "真实不能为空")
    private String  mName;
    /**
     * 性别
     */
    private Integer mSex;           // 0:男，1：女
    /**
     * 手机号码
     */
    @NotNull(message = "手机号码不能为空")
    private String  mMobile;
    /**
     * 电话号码
     */
    @NotNull(message = "电话号码不能为空")
    private String  mTel;
    /**
     * 电子邮件
     */
    @NotNull(message = "电子邮箱不能为空")
    private String  mEmail;
    /**
     * 传真
     */
    private String  mFax;
    /**
     * QQ号码
     */
    private String  mQQ;
    /**
     * 类型
     */
    private Integer mType;
    /**
     * 权限
     */
    private String  mRole;
    /**
     * 状态
     */
    private Integer mState;         // 0:正常，1:停止
    /**
     * last login date最后登录时间
     */
    private Date    mLastLoginTime;
    /**
     * 拼音
     */
    private String  cSpell;

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
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

    public String getmPic() {
        return mPic;
    }

    public void setmPic(String mPic) {
        this.mPic = mPic;
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

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Integer getmSex() {
        return mSex;
    }

    public void setmSex(Integer mSex) {
        this.mSex = mSex;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getmTel() {
        return mTel;
    }

    public void setmTel(String mTel) {
        this.mTel = mTel;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmFax() {
        return mFax;
    }

    public void setmFax(String mFax) {
        this.mFax = mFax;
    }

    public String getmQQ() {
        return mQQ;
    }

    public void setmQQ(String mQQ) {
        this.mQQ = mQQ;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }

    public Integer getmState() {
        return mState;
    }

    public void setmState(Integer mState) {
        this.mState = mState;
    }

    public String getmAffirmPassword() {
        return mAffirmPassword;
    }

    public void setmAffirmPassword(String mAffirmPassword) {
        this.mAffirmPassword = mAffirmPassword;
    }

    public Date getmLastLoginTime() {
        return mLastLoginTime;
    }

    public void setmLastLoginTime(Date mLastLoginTime) {
        this.mLastLoginTime = mLastLoginTime;
    }

    public String getcSpell() {
        return cSpell;
    }

    public void setcSpell(String cSpell) {
        this.cSpell = cSpell;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
