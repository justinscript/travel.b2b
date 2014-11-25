/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * 专线下所有公司
 * 
 * @author zxc Aug 17, 2014 8:51:08 PM
 */
public class CompanyColumnDO implements Serializable {

    private static final long serialVersionUID = -2152139130085035694L;

    private Long              cId;                                     // 公司ID
    private Long              zId;                                     // 专线ID

    private Integer           cType;                                   // 公司类型
    private String            cName;                                   // 公司名称
    private String            cLogo;                                   // 公司Logo
    private String            cAboutus;                                // 公司简介
    private String            cContact;                                // 公司联系方式
    private String            cBank;                                   // 公司银行账户

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
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

    public String getcLogo() {
        return cLogo;
    }

    public void setcLogo(String cLogo) {
        this.cLogo = cLogo;
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

    public String getcBank() {
        return cBank;
    }

    public void setcBank(String cBank) {
        this.cBank = cBank;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
