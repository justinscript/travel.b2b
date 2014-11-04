/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.io.Serializable;
import java.util.Date;

import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.web.tools.WebUserTools;

/**
 * @author Administrator 2014-8-1 下午3:02:45
 */
public class TravelLineSimpleVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long              lId;                  // 线路ID
    private Date              lGoGroupTime;         // 出团日期
    private Float             lCrPrice;             // 成人门市价
    private Float             lJCrPrice;            // 成人结算价
    private Float             lXhPrice;             // 小孩门市价
    private Float             lJXhPrice;            // 小孩结算价
    private Float             lYPrice;              // 婴儿门市价
    private Float             lJYPrice;             // 婴儿结算价
    private Float             lFangPrice;           // 单房差
    private Integer           lAdultIntegral;       // 成人积分
    private Integer           lChildrenIntegral;    // 儿童积分
    private Integer           lIsIntegral;          // 是否积分
    private Integer           lIsVouchers;          // 是否抵用券
    private Integer           lSurplusCount;        // 剩余人数
    private Date              lEndTime;             // 结束日期
    private String            lGroupNumber;         // 团号

    public TravelLineSimpleVO() {

    }

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
    }

    public Date getlEndTime() {
        return lEndTime;
    }

    public void setlEndTime(Date lEndTime) {
        this.lEndTime = lEndTime;
    }

    public Integer getlSurplusCount() {
        return lSurplusCount;
    }

    public void setlSurplusCount(Integer lSurplusCount) {
        this.lSurplusCount = lSurplusCount;
    }

    public Integer getlIsIntegral() {
        return lIsIntegral;
    }

    public void setlIsIntegral(Integer lIsIntegral) {
        this.lIsIntegral = lIsIntegral;
    }

    public Integer getlIsVouchers() {
        return lIsVouchers;
    }

    public void setlIsVouchers(Integer lIsVouchers) {
        this.lIsVouchers = lIsVouchers;
    }

    public TravelLineSimpleVO(TravelLineDO lineDO) {
        BeanUtils.copyProperties(TravelLineSimpleVO.class, lineDO);
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public Date getlGoGroupTime() {
        return lGoGroupTime;
    }

    public void setlGoGroupTime(Date lGoGroupTime) {
        this.lGoGroupTime = lGoGroupTime;
    }

    public Float getlCrPrice() {
        return lCrPrice;
    }

    public void setlCrPrice(Float lCrPrice) {
        this.lCrPrice = lCrPrice;
    }

    public Float getlJCrPrice() {
        return lJCrPrice;
    }

    public void setlJCrPrice(Float lJCrPrice) {
        this.lJCrPrice = lJCrPrice;
    }

    public Float getlXhPrice() {
        return lXhPrice;
    }

    public void setlXhPrice(Float lXhPrice) {
        this.lXhPrice = lXhPrice;
    }

    public Float getlJXhPrice() {
        return lJXhPrice;
    }

    public void setlJXhPrice(Float lJXhPrice) {
        this.lJXhPrice = lJXhPrice;
    }

    public Float getlYPrice() {
        return lYPrice;
    }

    public void setlYPrice(Float lYPrice) {
        this.lYPrice = lYPrice;
    }

    public Float getlJYPrice() {
        return lJYPrice;
    }

    public void setlJYPrice(Float lJYPrice) {
        this.lJYPrice = lJYPrice;
    }

    public Float getlFangPrice() {
        return lFangPrice;
    }

    public void setlFangPrice(Float lFangPrice) {
        this.lFangPrice = lFangPrice;
    }

    public Integer getlAdultIntegral() {
        return lAdultIntegral;
    }

    public void setlAdultIntegral(Integer lAdultIntegral) {
        this.lAdultIntegral = lAdultIntegral;
    }

    public Integer getlChildrenIntegral() {
        return lChildrenIntegral;
    }

    public void setlChildrenIntegral(Integer lChildrenIntegral) {
        this.lChildrenIntegral = lChildrenIntegral;
    }

    // 转换方法
    public void init() {
        // 如果登陆，把结算价set进门市价
        if (WebUserTools.getCid() != 0) {
            this.lCrPrice = this.lJCrPrice;
            this.lXhPrice = this.lJXhPrice;
            this.lYPrice = this.lJYPrice;
        }
    }
}
