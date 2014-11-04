/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

import org.apache.commons.lang.StringUtils;

/**
 * 图片类型
 * 
 * @author zxc Aug 20, 2014 3:40:51 PM
 */
public enum PhotoTypeEnum {

    SCENIC(1, "news", "景点"),

    HOTELS(2, "hotels", "酒店"),

    REPAST(3, "repast", "餐饮"),

    ADVERTISEMENT(4, "advertisement", "广告"),

    MEMBER(5, "member", "用户"),

    COMPANY(6, "company", "公司"),

    GIFT_PRODUCT(7, "gift_product", "积分产品"),

    SITE_COLUMN(7, "site_column", "站点专线");

    public int     value;
    private String name;
    private String desc;

    private PhotoTypeEnum(int value, String name, String desc) {
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

    public static PhotoTypeEnum getAction(String name) {
        for (PhotoTypeEnum type : values()) {
            if (StringUtils.equals(name, type.name)) return type;
        }
        return null;
    }

    public static PhotoTypeEnum getAction(int value) {
        for (PhotoTypeEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
