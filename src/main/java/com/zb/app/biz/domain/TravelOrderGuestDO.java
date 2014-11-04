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
 * @author Administrator 2014-6-24 上午11:46:18
 */
public class TravelOrderGuestDO implements Serializable {

    private static final long serialVersionUID = -3915897565972667276L;

    private Long              gId;                                     // 自动编号
    private Date              gmtCreate;                               // 创建时间
    private Date              gmtModified;                             // 修改时间

    private Long              orId;                                    // 订单id
    private Long              lId;                                     // 线路id
    private String            gName;                                   // 姓名
    private Integer           gSex;                                    // 性别
    private Integer           gType;                                   // 年龄类型
    private String            gCard;                                   // 证件号
    private String            gMobile;                                 // 旅客手机
    private Integer           gDangFang;                               // 单房差
    private String            gMode;                                   // 备注
    private Double            gCostPrice;                              // 现价
    private Double            gDangFangPrice;                          // 单房差价
    private Double            gYouHui;                                 // 优惠
    private Double            gJsPrice;                                // 接送单价

    public TravelOrderGuestDO() {

    }

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
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

    public Long getOrId() {
        return orId;
    }

    public void setOrId(Long orId) {
        this.orId = orId;
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public Integer getgSex() {
        return gSex;
    }

    public void setgSex(Integer gSex) {
        this.gSex = gSex;
    }

    public Integer getgType() {
        return gType;
    }

    public void setgType(Integer gType) {
        this.gType = gType;
    }

    public String getgCard() {
        return gCard;
    }

    public void setgCard(String gCard) {
        this.gCard = gCard;
    }

    public String getgMobile() {
        return gMobile;
    }

    public void setgMobile(String gMobile) {
        this.gMobile = gMobile;
    }

    public Integer getgDangFang() {
        return gDangFang;
    }

    public void setgDangFang(Integer gDangFang) {
        this.gDangFang = gDangFang;
    }

    public String getgMode() {
        return gMode;
    }

    public void setgMode(String gMode) {
        this.gMode = gMode;
    }

    public Double getgCostPrice() {
        return gCostPrice;
    }

    public void setgCostPrice(Double gCostPrice) {
        this.gCostPrice = gCostPrice;
    }

    public Double getgDangFangPrice() {
        return gDangFangPrice;
    }

    public void setgDangFangPrice(Double gDangFangPrice) {
        this.gDangFangPrice = gDangFangPrice;
    }

    public Double getgYouHui() {
        return gYouHui;
    }

    public void setgYouHui(Double gYouHui) {
        this.gYouHui = gYouHui;
    }

    public Double getgJsPrice() {
        return gJsPrice;
    }

    public void setgJsPrice(Double gJsPrice) {
        this.gJsPrice = gJsPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
