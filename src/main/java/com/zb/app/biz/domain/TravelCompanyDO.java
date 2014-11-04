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
 * @author ZhouZhong 2014-6-24 下午3:03:05
 */
public class TravelCompanyDO implements Serializable {

    private static final long serialVersionUID = -6469971385860279982L;

    private Long              cId;                                     // 自动编号,主键
    private Date              gmtCreate;                               // 记录创建时间
    private Date              gmtModified;                             // 记录最后修改时间

    private Integer           cType;                                   // 公司类型
    private String            cName;                                   // 公司名称
    private String            cProvince;                               // 省
    private String            cCity;                                   // 市
    private String            cCounty;                                 // 区/县
    private String            cCustomname;                             // 公司联系人
    private String            cLogo;                                   // 公司Logo
    private String            cQQ;                                     // 公司qq
    private String            cEmail;                                  // 电子邮件
    private String            cTel;                                    // 电话
    private String            cFax;                                    // 传真
    private String            cMobile;                                 // 手机
    private String            cAddress;                                // 公司地址
    private String            cAboutus;                                // 公司简介
    private String            cContact;                                // 公司联系方式
    private String            cDefaultCity;                            // 组团社默认登录城市（格式：浙江|杭州）
    private String            cCityTop;                                // 默认站点（当前IP站点如果不在站点列表里，默认一个站点）
    private String            cCityList;                               // 组团社可访问城站（浙江|杭州,上海|上海）
    private String            cBank;                                   // 公司银行账户
    private String            cCorporation;                            // 公司法人
    private String            cRecommend;                              // 推荐
    private Date              cLoginTime;                              // 登录日期
    private Integer           cState;                                  // 状态（0=未审核，1=正常，2=停止）
    private String            cSpell;                                  // 拼音

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
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

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcProvince() {
        return cProvince;
    }

    public void setcProvince(String cProvince) {
        this.cProvince = cProvince;
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public String getcCounty() {
        return cCounty;
    }

    public void setcCounty(String cCounty) {
        this.cCounty = cCounty;
    }

    public String getcCustomname() {
        return cCustomname;
    }

    public void setcCustomname(String cCustomname) {
        this.cCustomname = cCustomname;
    }

    public String getcLogo() {
        return cLogo;
    }

    public void setcLogo(String cLogo) {
        this.cLogo = cLogo;
    }

    public String getcQQ() {
        return cQQ;
    }

    public void setcQQ(String cQQ) {
        this.cQQ = cQQ;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getcTel() {
        return cTel;
    }

    public void setcTel(String cTel) {
        this.cTel = cTel;
    }

    public String getcFax() {
        return cFax;
    }

    public void setcFax(String cFax) {
        this.cFax = cFax;
    }

    public String getcMobile() {
        return cMobile;
    }

    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String getcAboutus() {
        return cAboutus;
    }

    public void setcAboutus(String cAboutus) {
        this.cAboutus = cAboutus;
    }

    public String getcContact() {
        return cContact;
    }

    public void setcContact(String cContact) {
        this.cContact = cContact;
    }

    public String getcDefaultCity() {
        return cDefaultCity;
    }

    public void setcDefaultCity(String cDefaultCity) {
        this.cDefaultCity = cDefaultCity;
    }

    public String getcCityTop() {
        return cCityTop;
    }

    public void setcCityTop(String cCityTop) {
        this.cCityTop = cCityTop;
    }

    public String getcCityList() {
        return cCityList;
    }

    public void setcCityList(String cCityList) {
        this.cCityList = cCityList;
    }

    public String getcBank() {
        return cBank;
    }

    public void setcBank(String cBank) {
        this.cBank = cBank;
    }

    public String getcCorporation() {
        return cCorporation;
    }

    public void setcCorporation(String cCorporation) {
        this.cCorporation = cCorporation;
    }

    public Date getcLoginTime() {
        return cLoginTime;
    }

    public void setcLoginTime(Date cLoginTime) {
        this.cLoginTime = cLoginTime;
    }

    public Integer getcState() {
        return cState;
    }

    public void setcState(Integer cState) {
        this.cState = cState;
    }

    public String getcRecommend() {
        return cRecommend;
    }

    public void setcRecommend(String cRecommend) {
        this.cRecommend = cRecommend;
    }

    public String getcSpell() {
        return cSpell;
    }

    public void setcSpell(String cSpell) {
        this.cSpell = cSpell;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
