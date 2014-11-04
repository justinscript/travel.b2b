/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * OrderState(0=未确认,1=已确认,2=取消,3=名单不全)
 * 
 * @author zxc Aug 6, 2014 5:39:35 PM
 */
public enum OrderStateEnum {

    UN_CONFIRM(0, "未确认"),

    CONFIRM(1, "已确认"),

    CANCEL(2, "取消"),

    INCOMPLETE(3, "名单不全");

    private int    value;

    private String name;

    private OrderStateEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static OrderStateEnum getAction(int value) {
        for (OrderStateEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
