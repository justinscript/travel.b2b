/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

import org.apache.commons.lang.StringUtils;

/**
 * 专线类型(普通专线,热门专线,推荐专线)
 * 
 * @author zxc Aug 13, 2014 11:12:21 AM
 */
public enum ColumnHotEnum {

    COMMON_LINE(0, "commonLine", "普通专线"),

    HOT_LINE(1, "hotLine", "热门专线"),

    RECOMMENDED_LINE(2, "recommendedLine", "推荐专线");

    public int     value;
    private String name;
    private String desc;

    private ColumnHotEnum(int value, String name, String desc) {
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

    public static ColumnHotEnum getAction(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (ColumnHotEnum type : values()) {
            if (StringUtils.equals(name, type.name)) return type;
        }
        return null;
    }

    public static ColumnHotEnum getAction(int value) {
        for (ColumnHotEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
