/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author zxc Jul 23, 2014 3:00:31 PM
 */
public class TravelLineThinDO implements Serializable {

    private static final long serialVersionUID = 4528858008546815963L;

    private Long              lId;                                    // 线路编号
    private Date              gmtCreate;                              // 记录创建时间
    private Date              gmtModified;                            // 记录最后修改时间

    private Long              zId;                                    // 专线ID
    private Long              mId;                                    // 用户ID（发布人）
    private Long              cId;                                    // 公司ID（发布人公司）
    private String            lTile;                                  // 标题
    private Integer           lType;                                  // 类型
    private String            lGroupNumber;                           // 团号
    private Date              lGoGroupTime;                           // 出团日期
    private Date              lEndTime;                               // 截止日期
    private String            lProvince;                              // 省
    private String            lCity;                                  // 市
    private String            lArea;                                  // 区/县
    private Integer           lIsJs;                                  // 是否带接送（0=不带，1=带）
    private String            lJsRud;                                 // 接送区域
    private String            lArrivalProvince;                       // 抵达省
    private String            lArrivalCity;                           // 抵达市
    private String            lArrivalArea;                           // 抵达区县
    private Integer           lRenCount;                              // 总人数
    private Integer           lCrCount;                               // 已订成人数
    private Integer           lXhCount;                               // 已订儿童数
    private Integer           lYCount;                                // 已订婴儿数
    private Float             lCrPrice;                               // 成人门市价
    private Float             lJCrPrice;                              // 成人结算价
    private Float             lXhPrice;                               // 儿童门市价
    private Float             lJXhPrice;                              // 儿童结算价
    private Float             lYPrice;                                // 婴儿门市价
    private Float             lJYPrice;                               // 婴儿结算价
    private Float             lFangPrice;                             // 单房差
    private Integer           lAdultIntegral;                         // 成人积分
    private Integer           lChildrenIntegral;                      // 儿童积分
    private Integer           lDay;                                   // 旅游天数
    private Integer           lTrafficyType;                          // 出发交通类型（0=飞机,1=汽车,2=火车,3=游船,4=待定）
    private String            lGoTraffic;                             // 往，交通介绍
    private Integer           lTrafficBackType;                       // 返回交通类型（0=飞机,1=汽车,2=火车,3=游船,4=待定）
    private String            lBackTraffice;                          // 返，交通介绍
    private Integer           lTemplateState;                         // 0=线路,1=模板
    private Integer           lEditUserId;                            // 最后修改人ID
    private Integer           lState;                                 // 状态（0=正常,1=停止,2=客满,3=过期,）
    private Integer           lDelState;                              // 0=正常,1=删除
    private Integer           lDisplay;                               // 显示状态（0=显,1=隐）
    private String            lProduct;                               // 所属分组
    private Integer           lIsIntegral;                            // 是否积分(1=是 0=否)
    private Integer           lIsVouchers;                            // 是否可用抵用券
    private Integer           lSurplusCount;                          // 剩余人数(数据库没有，自动计算)
    private String            lPhotoCover;                            // varchar2(500) 封面地址
    private Integer           lViews;                                 // 浏览量

    public Integer getlViews() {
        return lViews;
    }

    public void setlViews(Integer lViews) {
        this.lViews = lViews;
    }

    public String getlPhotoCover() {
        return lPhotoCover;
    }

    public void setlPhotoCover(String lPhotoCover) {
        this.lPhotoCover = lPhotoCover;
    }

    public Integer getlIsIntegral() {
        return lIsIntegral;
    }

    public Integer getlSurplusCount() {
        return lSurplusCount;
    }

    public void setlSurplusCount(Integer lSurplusCount) {
        this.lSurplusCount = lSurplusCount;
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

    public String getlProduct() {
        return lProduct;
    }

    public void setlProduct(String lProduct) {
        this.lProduct = lProduct;
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
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

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    @NotNull(message = "标题不能为空!")
    public String getlTile() {
        return lTile;
    }

    public void setlTile(String lTile) {
        this.lTile = lTile;
    }

    public String getlProvince() {
        return lProvince;
    }

    public void setlProvince(String lProvince) {
        this.lProvince = lProvince;
    }

    public String getlCity() {
        return lCity;
    }

    public void setlCity(String lCity) {
        this.lCity = lCity;
    }

    public String getlArea() {
        return lArea;
    }

    public void setlArea(String lArea) {
        this.lArea = lArea;
    }

    public Integer getlIsJs() {
        return lIsJs;
    }

    public void setlIsJs(Integer lIsJs) {
        this.lIsJs = lIsJs;
    }

    public String getlJsRud() {
        return lJsRud;
    }

    public void setlJsRud(String lJsRud) {
        this.lJsRud = lJsRud;
    }

    public String getlArrivalProvince() {
        return lArrivalProvince;
    }

    public void setlArrivalProvince(String lArrivalProvince) {
        this.lArrivalProvince = lArrivalProvince;
    }

    public String getlArrivalCity() {
        return lArrivalCity;
    }

    public void setlArrivalCity(String lArrivalCity) {
        this.lArrivalCity = lArrivalCity;
    }

    public String getlArrivalArea() {
        return lArrivalArea;
    }

    public void setlArrivalArea(String lArrivalArea) {
        this.lArrivalArea = lArrivalArea;
    }

    public Integer getlCrCount() {
        return lCrCount;
    }

    public void setlCrCount(Integer lCrCount) {
        this.lCrCount = lCrCount;
    }

    public Integer getlXhCount() {
        return lXhCount;
    }

    public void setlXhCount(Integer lXhCount) {
        this.lXhCount = lXhCount;
    }

    public Integer getlYCount() {
        return lYCount;
    }

    public void setlYCount(Integer lYCount) {
        this.lYCount = lYCount;
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

    public Integer getlDay() {
        return lDay;
    }

    public void setlDay(Integer lDay) {
        this.lDay = lDay;
    }

    public Integer getlTrafficyType() {
        return lTrafficyType;
    }

    public void setlTrafficyType(Integer lTrafficyType) {
        this.lTrafficyType = lTrafficyType;
    }

    public String getlGoTraffic() {
        return lGoTraffic;
    }

    public void setlGoTraffic(String lGoTraffic) {
        this.lGoTraffic = lGoTraffic;
    }

    public Integer getlTrafficBackType() {
        return lTrafficBackType;
    }

    public void setlTrafficBackType(Integer lTrafficBackType) {
        this.lTrafficBackType = lTrafficBackType;
    }

    public String getlBackTraffice() {
        return lBackTraffice;
    }

    public void setlBackTraffice(String lBackTraffice) {
        this.lBackTraffice = lBackTraffice;
    }

    public Integer getlTemplateState() {
        return lTemplateState;
    }

    public void setlTemplateState(Integer lTemplateState) {
        this.lTemplateState = lTemplateState;
    }

    public Integer getlEditUserId() {
        return lEditUserId;
    }

    public void setlEditUserId(Integer lEditUserId) {
        this.lEditUserId = lEditUserId;
    }

    public Integer getlState() {
        return lState;
    }

    public void setlState(Integer lState) {
        this.lState = lState;
    }

    public Integer getlDelState() {
        return lDelState;
    }

    public void setlDelState(Integer lDelState) {
        this.lDelState = lDelState;
    }

    public Integer getlDisplay() {
        return lDisplay;
    }

    public void setlDisplay(Integer lDisplay) {
        this.lDisplay = lDisplay;
    }

    public Integer getlType() {
        return lType;
    }

    public void setlType(Integer lType) {
        this.lType = lType;
    }

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
    }

    @Future(message = "必须大于系统时间")
    public Date getlGoGroupTime() {
        return lGoGroupTime;
    }

    public void setlGoGroupTime(Date lGoGroupTime) {
        this.lGoGroupTime = lGoGroupTime;
    }

    public Date getlEndTime() {
        return lEndTime;
    }

    public void setlEndTime(Date lEndTime) {
        this.lEndTime = lEndTime;
    }

    public Integer getlRenCount() {
        return lRenCount;
    }

    public void setlRenCount(Integer lRenCount) {
        this.lRenCount = lRenCount;
    }

    public Float getlFangPrice() {
        return lFangPrice;
    }

    public void setlFangPrice(Float lFangPrice) {
        this.lFangPrice = lFangPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
