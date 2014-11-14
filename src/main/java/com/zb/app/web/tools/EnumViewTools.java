/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zb.app.biz.cons.ADLocationEnum;
import com.zb.app.biz.cons.ColumnCatEnum;
import com.zb.app.biz.cons.LineDayEnum;
import com.zb.app.biz.cons.LineTypeEnum;
import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.biz.cons.RightMenuEnum;
import com.zb.app.biz.cons.TheSidebarEnum;

/**
 * @author zxc Aug 7, 2014 1:09:55 PM
 */
public class EnumViewTools {

    private static List<OrderStateEnum> orderStateEnumList; // 订单状态枚举
    private static List<LineTypeEnum>   lineTypeEnumList;  // 线路类型枚举
    private static List<ADLocationEnum> adLocationEnumList; // 广告展示位置枚举
    private static List<LineDayEnum>    lineDayEnumList;   // 线路天数枚举类
    private static List<TheSidebarEnum> theSidebarEnumList; // 侧边栏枚举类

    public static List<TheSidebarEnum> getallTheSidebar() {
        if (theSidebarEnumList == null) {
            theSidebarEnumList = new ArrayList<TheSidebarEnum>();
            for (TheSidebarEnum _enum : TheSidebarEnum.values()) {
                theSidebarEnumList.add(_enum);
            }
            theSidebarEnumList = Collections.unmodifiableList(theSidebarEnumList);
        }
        return theSidebarEnumList;
    }

    public static List<OrderStateEnum> getAllOrderState() {
        if (orderStateEnumList == null) {
            orderStateEnumList = new ArrayList<OrderStateEnum>();
            for (OrderStateEnum _enum : OrderStateEnum.values()) {
                orderStateEnumList.add(_enum);
            }
            orderStateEnumList = Collections.unmodifiableList(orderStateEnumList);
        }
        return orderStateEnumList;
    }

    public static List<LineDayEnum> getAllLineDay() {
        if (lineDayEnumList == null) {
            lineDayEnumList = new ArrayList<LineDayEnum>();
            for (LineDayEnum _enum : LineDayEnum.values()) {
                lineDayEnumList.add(_enum);
            }
            lineDayEnumList = Collections.unmodifiableList(lineDayEnumList);
        }
        return lineDayEnumList;
    }

    public static List<LineTypeEnum> getAllLineType() {
        if (lineTypeEnumList == null) {
            lineTypeEnumList = new ArrayList<LineTypeEnum>();
            for (LineTypeEnum _enum : LineTypeEnum.values()) {
                lineTypeEnumList.add(_enum);
            }
            lineTypeEnumList = Collections.unmodifiableList(lineTypeEnumList);
        }
        return lineTypeEnumList;
    }

    public static List<RightMenuEnum> getManageRightMenu() {
        List<RightMenuEnum> rightMenuEnumList = Arrays.asList(new RightMenuEnum[] { RightMenuEnum.COMPANY_MANAGE,
                RightMenuEnum.LINE_MANAGE, RightMenuEnum.ORDER_MANAGE, RightMenuEnum.NEWS_MANAGE,
                RightMenuEnum.GIFT_MANAGE, RightMenuEnum.SYSTEM_MANAGE, RightMenuEnum.INTEGRAL_MANAGE,
                RightMenuEnum.COUNT_MANAGE });
        return Collections.unmodifiableList(rightMenuEnumList);
    }

    public static List<RightMenuEnum> getAccountRightMenu() {
        List<RightMenuEnum> rightMenuEnumList = Arrays.asList(new RightMenuEnum[] { RightMenuEnum.ORDER_ACCOUNT,
                RightMenuEnum.PRODUCT_ACCOUNT, RightMenuEnum.SYSTEM_ACCOUNT });
        return Collections.unmodifiableList(rightMenuEnumList);
    }

    public static List<RightMenuEnum> getTourRightMenu() {
        List<RightMenuEnum> rightMenuEnumList = Arrays.asList(new RightMenuEnum[] { RightMenuEnum.ORDER_TOUR,
                RightMenuEnum.PRODUCT_TOUR, RightMenuEnum.SYSTEM_TOUR });
        return Collections.unmodifiableList(rightMenuEnumList);
    }

    public static List<ADLocationEnum> getAlladLocation() {
        if (adLocationEnumList == null) {
            adLocationEnumList = new ArrayList<ADLocationEnum>();
            for (ADLocationEnum _enum : ADLocationEnum.values()) {
                adLocationEnumList.add(_enum);
            }
            adLocationEnumList = Collections.unmodifiableList(adLocationEnumList);
        }
        return adLocationEnumList;
    }

    public static String orderStateEnumName(Integer v) {
        if (v == null) {
            return StringUtils.EMPTY;
        }
        return OrderStateEnum.getAction(v).getName();
    }

    public static String lineTypeEnumName(Integer v) {
        if (v == null) {
            return StringUtils.EMPTY;
        }
        return LineTypeEnum.getAction(v).getName();
    }

    public static String rightMenuEnumName(Integer v) {
        if (v == null) {
            return StringUtils.EMPTY;
        }
        return RightMenuEnum.getAction(v).getName();
    }

    public static Integer getColumnCatValue(String value) {
        if (value == null) {
            return null;
        }
        return ColumnCatEnum.valueOf(value).getValue();
    }

    public static String getADLocationName(Integer v) {
        if (v == null) {
            return StringUtils.EMPTY;
        }
        return ADLocationEnum.getAction(v).getName();
    }

    public static Integer getADLocationValue(String value) {
        if (value == null) {
            return null;
        }
        return ADLocationEnum.valueOf(value).getValue();
    }
}
