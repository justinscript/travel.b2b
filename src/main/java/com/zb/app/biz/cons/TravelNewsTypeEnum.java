/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

import org.apache.commons.lang.StringUtils;

/**
 * 旅游资讯(1),用户资讯(2),网站公告(3)
 * 
 * @author zxc Aug 5, 2014 3:43:42 PM
 */
public enum TravelNewsTypeEnum {

    TRAVEL_news(1, "news"),

    USER_news(2, "notice"),

    WEB_news(3, "userinfo");

    public int     value;
    private String name;

    private TravelNewsTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public static TravelNewsTypeEnum getAction(String name) {
        for (TravelNewsTypeEnum type : values()) {
            if (StringUtils.equals(name, type.name)) return type;
        }
        return null;
    }

    public static TravelNewsTypeEnum getAction(int value) {
        for (TravelNewsTypeEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
