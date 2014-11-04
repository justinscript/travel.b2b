/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

import org.apache.commons.lang.StringUtils;

/**
 * @author ZhouZhong 游客年龄类型
 */
public enum GuestAgeTypeEnum {

    ADULT(0, "adult", "成人"),

    CHILDREN(1, "children", "儿童"),

    BABY(2, "baby", "婴儿");

    public int     value;
    private String name;
    private String desc;

    private GuestAgeTypeEnum(int value, String name, String desc) {
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

    public void setValue(int value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static GuestAgeTypeEnum getAction(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (GuestAgeTypeEnum type : values()) {
            if (StringUtils.equals(name, type.name)) return type;
        }
        return null;
    }

    public static GuestAgeTypeEnum getAction(int value) {
        for (GuestAgeTypeEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
