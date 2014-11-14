/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 栏目类别(关于左边,帮助中心)
 * 
 * @author zxc Aug 20, 2014 11:20:49 AM
 */
public enum ArticlesSourceEnum {

    ABOUT_ZUOBIAN(1, "关于左边"),

    HELP_CENTER(2, "帮助中心"),

    ORDER_GUIDE(3, "订购指南"),

    ACCOUNT_ISSUE(4, "批发商问题"),

    TOUR_ISSUE(5, "组团社问题");

    private int    value;

    private String name;

    private ArticlesSourceEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ArticlesSourceEnum getAction(int value) {
        for (ArticlesSourceEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
