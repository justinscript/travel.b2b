/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 消息表记录枚举类型(0=订单未读，1=订单修改，2=订单取消，3=订单恢复,4=积分兑换成功)
 * 
 * @author zxc Aug 1, 2014 3:10:19 PM
 */
public enum MessageTypeEnum {

    NEW_ORDER_UNREAD(0, "newOrder", "新订单", ""),

    ORDER_MODIFY(1, "orderModify", "订单修改", ""),

    ORDER_CANCEL(2, "orderCancel", "订单取消", ""),

    ORDER_RESTORE(3, "orderRestore", "订单恢复", ""),

    NEW_INTEGRAL(4, "newIntegral", "新积分", ""),

    INTEGRAL_CONVERT(5, "integralConvert", "积分兑换成功", "");

    private int    value;

    private String name;

    private String desc;

    private String time;

    private MessageTypeEnum(int value, String name, String desc, String time) {
        this.value = value;
        this.name = name;
        this.desc = desc;
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }

    public static MessageTypeEnum getAction(int value) {
        for (MessageTypeEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
