/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * @author ZhouZhong 日志表类型
 */
public enum LogTableEnum {

    ORDERLOG("travel_order", "orderLog", "订单表"),

    LINELOG("travel_line", "lineLog", "线路表");

    public String  value;
    private String name;
    private String desc;

    private LogTableEnum(String value, String name, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static LogTableEnum getAction(String value) {
        for (LogTableEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
