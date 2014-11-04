/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zb.app.biz.cons.LineStateEnum;
import com.zb.app.biz.cons.LineTypeEnum;
import com.zb.app.biz.cons.TrafficBackTypeEnum;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.util.DateViewTools;

/**
 * @author Administrator 2014-7-1 上午10:35:26
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TravelLineVO extends TravelLineDO {

    private static final long   serialVersionUID = 6546132134684L;

    private String              lGoGroupTimeString;               // 出团日期
    private String              lEndTimeString;                   // 截止日期显示字符串
    private String              lStateString;                     // 状态
    private String              lTrafficyTypeString;              // 出发交通类型
    private String              lTrafficBackTypeString;           // 返回交通类型
    private int                 lEndTimeInteger;                  // 截止日期添加INT
    private String[]            lGoTrafficStrings;                // 往交通介绍
    private String[]            lBackTrafficeStrings;             // 返交通介绍
    private String              lTypeString;                      // 类型
    private String              lBackTimeString;                  // 返程日期
    private Integer             lSurplusCount;                    // 剩余人数
    private Integer[]           lPeople;                          // 人数

    private List<TravelRouteDO> routelist;                        // 行程安排

    public TravelLineVO() {

    }

    public TravelLineVO(TravelLineDO travelLineDO) {
        if (travelLineDO == null) {
            return;
        }
        BeanUtils.copyProperties(this, travelLineDO);
    }

    public Integer[] getlPeople() {
        return lPeople;
    }

    public void setlPeople(Integer[] lPeople) {
        this.lPeople = lPeople;
    }

    public Integer getlSurplusCount() {
        return lSurplusCount;
    }

    public void setlSurplusCount(Integer lSurplusCount) {
        this.lSurplusCount = lSurplusCount;
    }

    @NotNull(message = "截止日期不能为空!")
    public int getlEndTimeInteger() {
        return lEndTimeInteger;
    }

    public void setlEndTimeInteger(int lEndTimeInteger) {
        this.lEndTimeInteger = lEndTimeInteger;
    }

    public List<TravelRouteDO> getRoutelist() {
        return routelist;
    }

    public void setRoutelist(List<TravelRouteDO> routelist) {
        this.routelist = routelist;
    }

    public String getlEndTimeString() {
        return lEndTimeString;
    }

    public void setlEndTimeString(String lEndTimeString) {
        this.lEndTimeString = lEndTimeString;
    }

    @NotNull(message = "出团日期不能为空!")
    public String getlGoGroupTimeString() {
        return lGoGroupTimeString;
    }

    public void setlGoGroupTimeString(String lGoGroupTimeString) {
        this.lGoGroupTimeString = lGoGroupTimeString;
    }

    public String getlStateString() {
        return lStateString;
    }

    public void setlStateString(String lStateString) {
        this.lStateString = lStateString;
    }

    public String getlTrafficyTypeString() {
        return lTrafficyTypeString;
    }

    public void setlTrafficyTypeString(String lTrafficyTypeString) {
        this.lTrafficyTypeString = lTrafficyTypeString;
    }

    public String getlTrafficBackTypeString() {
        return lTrafficBackTypeString;
    }

    public void setlTrafficBackTypeString(String lTrafficBackTypeString) {
        this.lTrafficBackTypeString = lTrafficBackTypeString;
    }

    public String[] getlGoTrafficStrings() {
        return lGoTrafficStrings;
    }

    public void setlGoTrafficStrings(String[] lGoTrafficStrings) {
        this.lGoTrafficStrings = lGoTrafficStrings;
    }

    public String[] getlBackTrafficeStrings() {
        return lBackTrafficeStrings;
    }

    public void setlBackTrafficeStrings(String[] lBackTrafficeStrings) {
        this.lBackTrafficeStrings = lBackTrafficeStrings;
    }

    public String getlTypeString() {
        return lTypeString;
    }

    public void setlTypeString(String lTypeString) {
        this.lTypeString = lTypeString;
    }

    public String getlBackTimeString() {
        return lBackTimeString;
    }

    public void setlBackTimeString(String lBackTimeString) {
        this.lBackTimeString = lBackTimeString;
    }

    /**
     * DO属性转VO属性
     */
    public void init() {
        // 线路类型转换
        this.lTypeString = LineTypeEnum.getAction(this.getlType()).getName();

        if (super.getlGoGroupTime() == null || super.getlTemplateState() == 1) {
            return;
        }
        // 出团日期转换
        String date = DateViewTools.format(super.getlGoGroupTime(), "yyyy-MM-dd EEEE");
        this.lGoGroupTimeString = date.replaceAll("星期", "");

        // 截止日期转换
        String date1 = DateViewTools.format(super.getlEndTime(), "yyyy-MM-dd EEEE");
        this.lEndTimeString = date1.replace("星期", "");

        // 截止日期INT
        this.lEndTimeInteger = DateViewTools.getDifferDay(this.getlGoGroupTime(), this.getlEndTime());

        // 返程日期计算
        Calendar cale = Calendar.getInstance();
        cale.setTime(this.getlGoGroupTime());
        cale.add(Calendar.DAY_OF_MONTH, this.getlDay());
        this.setlBackTimeString(DateViewTools.format(cale.getTime(), "yyyy-MM-dd EEEE").replace("星期", ""));

        // 交通工具转换
        this.lTrafficyTypeString = TrafficBackTypeEnum.getEnum(this.getlTrafficyType()).getDesc();
        this.lTrafficBackTypeString = TrafficBackTypeEnum.getEnum(this.getlTrafficBackType()).getDesc();

        // 把备注转换为数组（考虑有可能是飞机）
        if (this.getlTrafficBackType() == 0) {
            this.lBackTrafficeStrings = this.getlBackTraffice().replace("无", "").split(",");
            this.lBackTrafficeStrings = this.lBackTrafficeStrings.length < 8 ? null : this.lBackTrafficeStrings;
        }
        if (this.getlTrafficyType() == 0) {
            this.lGoTrafficStrings = this.getlGoTraffic().replace("无", "").split(",");
            this.lGoTrafficStrings = this.lGoTrafficStrings.length < 8 ? null : this.lGoTrafficStrings;
        }

        // 原备注转换 方便不是飞机的使用
        this.setlGoTraffic(this.getlGoTraffic().replace("无", "").replaceAll(",", ""));
        this.setlBackTraffice(this.getlBackTraffice().replace("无", "").replaceAll(",", ""));

        // 剩余人数
        this.lSurplusCount = this.getlRenCount() - this.getlCrCount() - this.getlXhCount();

        // 线路状态转换
        // 正常（人数>0&&状态=0&&出团日期>=当前时间）
        if (this.lSurplusCount > 0 && this.getlState() == 0 && !DateViewTools.isExpirationTime(this.getlGoGroupTime())) {
            this.lStateString = LineStateEnum.NORMAL.getName();
        }
        // 停止（状态=1）
        else if (this.getlState() == 1) {
            this.lStateString = LineStateEnum.STOP.getName();
        }
        // 客满（【人数=0||状态=2】&&出团日期>=当前时间）
        else if ((this.lSurplusCount == 0 || this.getlState() == 2)
                 && !DateViewTools.isExpirationTime(this.getlGoGroupTime())) {
            this.lStateString = LineStateEnum.FULL.getName();
        }
        // 过期（出团日期<当前时间）
        else if (DateViewTools.isExpirationTime(this.getlGoGroupTime())) {
            this.lStateString = LineStateEnum.EXPIRE.getName();
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
