/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author zxc Jul 18, 2014 2:08:48 PM
 */
public class ChufaCoreVO {

    // 出港点信息
    private Long    cId;  // 出港点ID
    private String  cName; // 站点名称
    private Integer cSort; // 排序

    public ChufaCoreVO() {

    }

    public ChufaCoreVO(Long cId, String cName, Integer cSort) {
        setcId(cId);
        setcName(cName);
        setcSort(cSort);
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcSort() {
        return cSort;
    }

    public void setcSort(Integer cSort) {
        this.cSort = cSort;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
