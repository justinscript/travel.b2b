/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 消息状态 (0=已读，1=未读,-1,已删除)
 * 
 * @author zxc Aug 1, 2014 4:13:50 PM
 */
public enum MessageReadStateEnum {

    // 已读
    READ(0, "read"),
    // 未读
    UN_READ(1, "unRead"),
    // 已删除
    DELETE(-1, "delete");

    public int    value;

    public String name;

    private MessageReadStateEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static MessageReadStateEnum getAction(int value) {
        for (MessageReadStateEnum state : values()) {
            if (value == state.getValue()) return state;
        }
        return null;
    }
}
