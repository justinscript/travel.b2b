/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 侧边栏枚举类
 * 
 * @author ZhouZhong
 */
public enum TheSidebarEnum {

    ABOUT_ZUOBIAN(1, "关于左边"),

    CONSULTAT_CENTER(2, "咨询中心"),

    BUSINE_ALL(3, "商家大全"),

    HELP_CENTER(4, "帮助中心"),

    INTEGRAL_MALL(5, "积分商城");

    private int    value;

    private String name;

    private TheSidebarEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static TheSidebarEnum getAction(int value) {
        for (TheSidebarEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
