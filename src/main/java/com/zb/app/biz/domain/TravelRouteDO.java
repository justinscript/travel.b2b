/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * Travel Route
 * 
 * @author zxc Jun 24, 2014 3:27:54 PM
 */
public class TravelRouteDO implements Serializable {

    private static final long serialVersionUID = -8286056625494448791L;

    private Long              rId;

    /**
     * 记录创建时间
     */
    private Date              gmtCreate;
    /**
     * 记录最后修改时间
     */
    private Date              gmtModified;

    /**
     * 线路ID
     */
    private Long              lId;

    /**
     * 内容
     */
    private String            rContent         = "";
    /**
     * 住宿
     */
    private String            rZhu             = "";
    /**
     * 餐（0=早，1=中，2=晚）
     */
    private String            rCan;
    /**
     * 图片地址（每个路径用英文逗号隔开）
     */
    private String            rPicpath;
    /**
     * 交通计划
     */
    private String            rCar             = "";
    /**
     * 资源ID（每个 ID用英文逗号隔开）
     */
    private String            rResource;

    public TravelRouteDO() {

    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
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

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public String getrZhu() {
        return rZhu;
    }

    public void setrZhu(String rZhu) {
        this.rZhu = rZhu;
    }

    public String getrCan() {
        return rCan;
    }

    public void setrCan(String rCan) {
        this.rCan = rCan;
    }

    public String getrPicpath() {
        return rPicpath;
    }

    public void setrPicpath(String rPicpath) {
        this.rPicpath = rPicpath;
    }

    public String getrCar() {
        return rCar;
    }

    public void setrCar(String rCar) {
        this.rCar = rCar;
    }

    public String getrResource() {
        return rResource;
    }

    public void setrResource(String rResource) {
        this.rResource = rResource;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
