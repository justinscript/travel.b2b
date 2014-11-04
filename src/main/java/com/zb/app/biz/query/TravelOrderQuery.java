/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Jun 26, 2014 1:50:45 PM
 */
public class TravelOrderQuery extends Pagination {

    private Long    orId;              // 自动编号'
    private String  gmtCreate;         // 创建时间
    private Date    gmtModified;       // 修改时间
    private Long    lId;               // 线路id
    private Long    customId;          // 组团社用户id
    private Long    customCompanyId;   // 组团社公司id
    private Long    cId;               // 地接社公司id
    private Long    mId;               // 预留人
    private String  orOrderId;         // 订单号
    private String  orName;            // 预订人姓名
    private String  orMobile;          // 预订人手机
    private String  orTel;             // 预订人电话
    private String  orFax;             // 预订人传真
    private Long    orAdultCount;      // 成人数
    private Long    orChildCount;      // 儿童数
    private Long    orBabyCount;       // 婴儿数
    private Double  orPirceCount;      // 订单总金额
    private Double  orYouHuiPrice;     // 优惠价
    private Double  orDangFangPrice;   // 单房差总价
    private String  orMode;            // 预定备注
    private Date    orGoGroupTime;     // 出团日期
    private String  orClearMode;       // 取消原因
    private String  orNMode;           // 内部备注
    private Long    orJs;              // 接送id
    private Double  orJsPrice;         // 最初价格接送
    private String  orPostSite;        //
    private Double  orFirstCrPrice;    // 初始成人门市价
    private Double  orFirstJcrPrice;   // 初始成人结算价
    private Double  orFirstXhPrice;    //
    private Double  orFirstJxhPrice;   //
    private Double  orFirstyPrice;     //
    private Double  orFirstJyPrice;    // 婴儿结算价
    private Double  orFirstFangPrice;  // 初始单房差
    private Integer orState;           //
    private Integer orAdultIntegral;   // 成人积分
    private Integer orChildrenIntegral; // 儿童积分
    private String  lTile;             // 线路标题
    private String  startDate;
    private String  endDate;
    private Integer zId; // 专线ID
    private String zType; // 专线类型
    
    public Integer getzId() {
        return zId;
    }

    
    public void setzId(Integer zId) {
        this.zId = zId;
    }

    
    public String getzType() {
        return zType;
    }

    
    public void setzType(String zType) {
        this.zType = zType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public TravelOrderQuery() {

    }

    public TravelOrderQuery(Integer orState, Long cId) {
        setcId(cId);
        setOrState(orState);
    }

    public TravelOrderQuery(Long cId, OrderStateEnum orState) {
        setcId(cId);
        setOrState(orState.getValue());
    }

    public TravelOrderQuery(Long orId) {
        setOrId(orId);
    }

    public String getlTile() {
        return lTile;
    }

    public void setlTile(String lTile) {
        this.lTile = lTile;
    }

    public Long getOrId() {
        return orId;
    }

    public void setOrId(Long orId) {
        this.orId = orId;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
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

    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public Long getCustomCompanyId() {
        return customCompanyId;
    }

    public void setCustomCompanyId(Long customCompanyId) {
        this.customCompanyId = customCompanyId;
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

    public String getOrOrderId() {
        return orOrderId;
    }

    public void setOrOrderId(String orOrderId) {
        this.orOrderId = orOrderId;
    }

    public String getOrName() {
        return orName;
    }

    public void setOrName(String orName) {
        this.orName = orName;
    }

    public String getOrMobile() {
        return orMobile;
    }

    public void setOrMobile(String orMobile) {
        this.orMobile = orMobile;
    }

    public String getOrTel() {
        return orTel;
    }

    public void setOrTel(String orTel) {
        this.orTel = orTel;
    }

    public String getOrFax() {
        return orFax;
    }

    public void setOrFax(String orFax) {
        this.orFax = orFax;
    }

    public Long getOrAdultCount() {
        return orAdultCount;
    }

    public void setOrAdultCount(Long orAdultCount) {
        this.orAdultCount = orAdultCount;
    }

    public Long getOrChildCount() {
        return orChildCount;
    }

    public void setOrChildCount(Long orChildCount) {
        this.orChildCount = orChildCount;
    }

    public Long getOrBabyCount() {
        return orBabyCount;
    }

    public void setOrBabyCount(Long orBabyCount) {
        this.orBabyCount = orBabyCount;
    }

    public Double getOrPirceCount() {
        return orPirceCount;
    }

    public void setOrPirceCount(Double orPirceCount) {
        this.orPirceCount = orPirceCount;
    }

    public Double getOrYouHuiPrice() {
        return orYouHuiPrice;
    }

    public void setOrYouHuiPrice(Double orYouHuiPrice) {
        this.orYouHuiPrice = orYouHuiPrice;
    }

    public Double getOrDangFangPrice() {
        return orDangFangPrice;
    }

    public void setOrDangFangPrice(Double orDangFangPrice) {
        this.orDangFangPrice = orDangFangPrice;
    }

    public String getOrMode() {
        return orMode;
    }

    public void setOrMode(String orMode) {
        this.orMode = orMode;
    }

    public Date getOrGoGroupTime() {
        return orGoGroupTime;
    }

    public void setOrGoGroupTime(Date orGoGroupTime) {
        this.orGoGroupTime = orGoGroupTime;
    }

    public String getOrClearMode() {
        return orClearMode;
    }

    public void setOrClearMode(String orClearMode) {
        this.orClearMode = orClearMode;
    }

    public String getOrNMode() {
        return orNMode;
    }

    public void setOrNMode(String orNMode) {
        this.orNMode = orNMode;
    }

    public Long getOrJs() {
        return orJs;
    }

    public void setOrJs(Long orJs) {
        this.orJs = orJs;
    }

    public Double getOrJsPrice() {
        return orJsPrice;
    }

    public void setOrJsPrice(Double orJsPrice) {
        this.orJsPrice = orJsPrice;
    }

    public String getOrPostSite() {
        return orPostSite;
    }

    public void setOrPostSite(String orPostSite) {
        this.orPostSite = orPostSite;
    }

    public Double getOrFirstCrPrice() {
        return orFirstCrPrice;
    }

    public void setOrFirstCrPrice(Double orFirstCrPrice) {
        this.orFirstCrPrice = orFirstCrPrice;
    }

    public Double getOrFirstJcrPrice() {
        return orFirstJcrPrice;
    }

    public void setOrFirstJcrPrice(Double orFirstJcrPrice) {
        this.orFirstJcrPrice = orFirstJcrPrice;
    }

    public Double getOrFirstXhPrice() {
        return orFirstXhPrice;
    }

    public void setOrFirstXhPrice(Double orFirstXhPrice) {
        this.orFirstXhPrice = orFirstXhPrice;
    }

    public Double getOrFirstJxhPrice() {
        return orFirstJxhPrice;
    }

    public void setOrFirstJxhPrice(Double orFirstJxhPrice) {
        this.orFirstJxhPrice = orFirstJxhPrice;
    }

    public Double getOrFirstyPrice() {
        return orFirstyPrice;
    }

    public void setOrFirstyPrice(Double orFirstyPrice) {
        this.orFirstyPrice = orFirstyPrice;
    }

    public Double getOrFirstJyPrice() {
        return orFirstJyPrice;
    }

    public void setOrFirstJyPrice(Double orFirstJyPrice) {
        this.orFirstJyPrice = orFirstJyPrice;
    }

    public Double getOrFirstFangPrice() {
        return orFirstFangPrice;
    }

    public void setOrFirstFangPrice(Double orFirstFangPrice) {
        this.orFirstFangPrice = orFirstFangPrice;
    }

    public Integer getOrAdultIntegral() {
        return orAdultIntegral;
    }

    public void setOrAdultIntegral(Integer orAdultIntegral) {
        this.orAdultIntegral = orAdultIntegral;
    }

    public Integer getOrChildrenIntegral() {
        return orChildrenIntegral;
    }

    public void setOrChildrenIntegral(Integer orChildrenIntegral) {
        this.orChildrenIntegral = orChildrenIntegral;
    }

    public Integer getOrState() {
        return orState;
    }

    public void setOrState(Integer orState) {
        this.orState = orState;
    }
}
