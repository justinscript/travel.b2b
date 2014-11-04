/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.cons;

import org.apache.commons.lang.StringUtils;

/**
 * 通信状态
 * 
 * @author zxc Sep 16, 2014 1:08:31 PM
 */
public enum ActionStatusEnum {

    CONNECTION_SUCCESS("connectionSuccess", 1, "连接成功"),

    CONNECTION_ERROR("connectionError", 2, "连接失败"),

    RECEIVE_SUCCESS("receiveSuccess", 3, "接收成功"),

    RECEIVE_ERROR("receiveError", 4, "接收失败");

    public int    value;

    public String name;

    public String desc;

    private ActionStatusEnum(String name, int value, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static ActionStatusEnum getAction(String name) {
        for (ActionStatusEnum action : values()) {
            if (StringUtils.equalsIgnoreCase(name, action.getName())) return action;
        }
        return null;
    }

    public static ActionStatusEnum getAction(int value) {
        for (ActionStatusEnum action : values()) {
            if (value == action.getValue()) return action;
        }
        return null;
    }
}
