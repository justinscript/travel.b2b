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
import com.zb.app.common.util.SerialNumGenerator;

/**
 * @author Administrator 2014-6-24 上午10:55:53
 */
public class TravelOrderDO implements Serializable {

    private static final long serialVersionUID = 7143695394472873609L;

    private Long              orId;                                   // 自动编号
    private Date              gmtCreate;                              // 创建时间
    private Date              gmtModified;                            // 修改时间

    private Long              lId;                                    // 线路id
    private Long              customId;                               // 组团社用户id
    private Long              customCompanyId;                        // 组团社公司id
    private Long              cId;                                    // 地接社公司id
    private Long              mId;                                    // 预留人
    private String            orOrderId;                              // 订单号
    private String            orName;                                 // 预订人姓名
    private String            orMobile;                               // 预订人手机
    private String            orTel;                                  // 预订人电话
    private String            orFax;                                  // 预订人传真
    private Integer           orAdultCount;                           // 成人数
    private Integer           orChildCount;                           // 儿童数
    private Integer           orBabyCount;                            // 婴儿数
    private Double            orPirceCount;                           // 订单总金额
    private Double            orYouHuiPrice;                          // 优惠价
    private Double            orDangFangPrice;                        // 单房差总价
    private String            orMode;                                 // 预定备注
    private Date              orGoGroupTime;                          // 出团日期
    private String            orClearMode;                            // 取消原因
    private String            orNMode;                                // 内部备注
    private Long              orJs;                                   // 接送id
    private Double            orJsPrice;                              // 最初价格接送
    private String            orPostSite;                             // 来源
    private Double            orFirstCrPrice;                         // 初始成人门市价
    private Double            orFirstJcrPrice;                        // 初始成人结算价
    private Double            orFirstXhPrice;                         // 初始儿童门市价
    private Double            orFirstJxhPrice;                        // 初始儿童结算价
    private Double            orFirstyPrice;                          // 初始婴儿门市价
    private Double            orFirstJyPrice;                         // 婴儿结算价
    private Double            orFirstFangPrice;                       // 初始单房差
    private Integer           orState;                                // 状态
    private Integer           orAdultIntegral;                        // 成人积分
    private Integer           orChildrenIntegral;                     // 儿童积分

    public TravelOrderDO() {

    }

    public TravelOrderDO(Long orId) {
        setOrId(orId);
    }

    public TravelOrderDO(Long orId, Long orOrderId) {
        setOrId(orId);
        setOrOrderId(SerialNumGenerator.createTradeNo(orOrderId));
    }

    public Long getOrId() {
        return orId;
    }

    public void setOrId(Long orId) {
        this.orId = orId;
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

    public Integer getOrAdultCount() {
        return parseIntegerNull(orAdultCount);
    }

    public void setOrAdultCount(Integer orAdultCount) {
        this.orAdultCount = orAdultCount;
    }

    public Integer getOrChildCount() {
        return parseIntegerNull(orChildCount);
    }

    public void setOrChildCount(Integer orChildCount) {
        this.orChildCount = orChildCount;
    }

    public Integer getOrBabyCount() {
        return parseIntegerNull(orBabyCount);
    }

    public void setOrBabyCount(Integer orBabyCount) {
        this.orBabyCount = orBabyCount;
    }

    public Double getOrPirceCount() {
        return parseDoubleNull(orPirceCount);
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

    public Integer getOrState() {
        return orState;
    }

    public void setOrState(Integer orState) {
        this.orState = orState;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }

    @SuppressWarnings("unchecked")
    protected static <T extends Number> T parseNull(T n) {
        return (T) (n == null ? 0 : n);
    }

    protected static <T extends Number> Integer parseIntegerNull(T n) {
        return parseNull(n).intValue();
    }

    protected static <T extends Number> Long parseLongNull(T n) {
        return parseNull(n).longValue();
    }

    protected static <T extends Number> Double parseDoubleNull(T n) {
        return parseNull(n).doubleValue();
    }
}
