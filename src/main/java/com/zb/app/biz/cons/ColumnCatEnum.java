/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

import org.apache.commons.lang.StringUtils;

/**
 * 专线分类(周边短线,国内长线,国际线路)
 * 
 * @author zxc Aug 13, 2014 11:05:26 AM
 */
public enum ColumnCatEnum {

    SHORT_LINE(0, "shortLine", "周边短线"),

    LONG_LINE(1, "longLine", "国内长线"),

    INTERNATIONAL_LINE(2, "internationalLine", "国际线路");

    public int     value;
    private String name;
    private String desc;

    private ColumnCatEnum(int value, String name, String desc) {
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

    public static ColumnCatEnum getAction(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (ColumnCatEnum type : values()) {
            if (StringUtils.equals(name, type.name)) return type;
        }
        return null;
    }

    public static ColumnCatEnum getAction(int value) {
        for (ColumnCatEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
