/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Jun 26, 2014 1:51:43 PM
 */
public class TravelOrderGuestQuery extends Pagination {

    private Long    orId;          // 订单id
    private Long    lId;           // 线路id
    private String  gName;         // 姓名
    private Integer gSex;          // 性别
    private Integer gType;         // 年龄类型
    private String  gCard;         // 证件号
    private String  gMobile;       // 旅客手机
    private Integer gDangFang;     // 单房差
    private String  gMode;         // 备注
    private Double  gCostPrice;    // 现价
    private Double  gDangFangPrice; // 单房差价
    private Double  gYouHui;       // 优惠
    private Double  gJsPrice;      // 接送单价

    public TravelOrderGuestQuery() {

    }

    public TravelOrderGuestQuery(Long orId) {
        setOrId(orId);
    }

    public TravelOrderGuestQuery(Long orId, Long lId) {
        setOrId(orId);
        setlId(lId);
    }

    public TravelOrderGuestQuery(String gName) {
        setgName(gName);
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
}
