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
 * 旅游产品
 * 
 * @author zxc Jun 24, 2014 2:33:47 PM
 */
public class TravelLineDO extends TravelLineThinDO implements Serializable {

    private static final long serialVersionUID = 4507549465328573993L;

    private String            lJhd;                                   // 集合地
    private String            lJhdTime;                               // 集合时间
    private String            lIco;                                   // 送团标志
    private String            lDipei;                                 // 地陪
    private String            lGroupTel;                              // 送团电话
    private String            lMode;                                  // 线路备注
    private String            lNMode;                                 // 线路内部备注
    private String            lYesItem;                               // 包含项目
    private String            lNoItem;                                // 不包含项目
    private String            lChildren;                              // 儿童安排
    private String            lShop;                                  // 购物安排
    private String            lExpenseItem;                           // 自费项目
    private String            lPreseItem;                             // 赠送项目
    private String            lAttention;                             // 注意事项
    private String            lOther;                                 // 其他事项
    private String            lReminder;                              // 温馨提示
    private String            lTourContent;                           // 法律信息

    public TravelLineDO() {

    }

    public TravelLineDO(Long lId) {
        setlId(lId);
    }

    public TravelLineDO(Long lId, String lGroupNumber) {
        setlId(lId);
        setlGroupNumber(lGroupNumber);
    }

    public TravelLineDO(Long lId, Integer lCrCount, Integer lXhCount, Integer lYCount) {
        setlId(lId);
        setlCrCount(lCrCount);
        setlXhCount(lXhCount);
        setlYCount(lYCount);
    }

    public String getlTourContent() {
        return lTourContent;
    }

    public void setlTourContent(String lTourContent) {
        this.lTourContent = lTourContent;
    }

    public String getlJhd() {
        return lJhd;
    }

    public void setlJhd(String lJhd) {
        this.lJhd = lJhd;
    }

    public String getlIco() {
        return lIco;
    }

    public void setlIco(String lIco) {
        this.lIco = lIco;
    }

    public String getlDipei() {
        return lDipei;
    }

    public void setlDipei(String lDipei) {
        this.lDipei = lDipei;
    }

    public String getlGroupTel() {
        return lGroupTel;
    }

    public void setlGroupTel(String lGroupTel) {
        this.lGroupTel = lGroupTel;
    }

    public String getlMode() {
        return lMode;
    }

    public void setlMode(String lMode) {
        this.lMode = lMode;
    }

    public String getlNMode() {
        return lNMode;
    }

    public void setlNMode(String lNMode) {
        this.lNMode = lNMode;
    }

    public String getlYesItem() {
        return lYesItem;
    }

    public void setlYesItem(String lYesItem) {
        this.lYesItem = lYesItem;
    }

    public String getlNoItem() {
        return lNoItem;
    }

    public void setlNoItem(String lNoItem) {
        this.lNoItem = lNoItem;
    }

    public String getlChildren() {
        return lChildren;
    }

    public void setlChildren(String lChildren) {
        this.lChildren = lChildren;
    }

    public String getlShop() {
        return lShop;
    }

    public void setlShop(String lShop) {
        this.lShop = lShop;
    }

    public String getlExpenseItem() {
        return lExpenseItem;
    }

    public void setlExpenseItem(String lExpenseItem) {
        this.lExpenseItem = lExpenseItem;
    }

    public String getlPreseItem() {
        return lPreseItem;
    }

    public void setlPreseItem(String lPreseItem) {
        this.lPreseItem = lPreseItem;
    }

    public String getlAttention() {
        return lAttention;
    }

    public void setlAttention(String lAttention) {
        this.lAttention = lAttention;
    }

    public String getlOther() {
        return lOther;
    }

    public void setlOther(String lOther) {
        this.lOther = lOther;
    }

    public String getlReminder() {
        return lReminder;
    }

    public void setlReminder(String lReminder) {
        this.lReminder = lReminder;
    }

    public String getlJhdTime() {
        return lJhdTime;
    }

    public void setlJhdTime(String lJhdTime) {
        this.lJhdTime = lJhdTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
