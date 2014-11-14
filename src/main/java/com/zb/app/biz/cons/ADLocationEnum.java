/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 广告位置(首页横幅,首页右侧,左边服务-横幅,商家大全-横幅,关于我们-横幅,新闻资讯-横幅,常见问题-横幅,商城横幅,推荐商家,页底广告)
 * 
 * @author zxc Aug 20, 2014 12:12:11 PM
 */
public enum ADLocationEnum {

    INDEX_BANNERS(1, "首页横幅"),

    INDEX_RIGHT(2, "首页右侧"),

    INDEX_FOOTER(3, "首页横幅底侧"),

    SERVICES_BANNERS(4, "左边服务-横幅"),

    ACCOUT_BANNERS(5, "商家大全-横幅"),

    ABOUT_BANNERS(6, "关于我们-横幅"),

    NEWS_BANNERS(7, "新闻资讯-横幅"),

    QUESTION_BANNERS(8, "常见问题-横幅"),

    MALL_BANNERS(9, "商城横幅"),

    RECOMMENDED_BANNERS(10, "推荐商家"),

    FOOTER_BANNERS(11, "页底广告");

    private int    value;

    private String name;

    private ADLocationEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ADLocationEnum getAction(int value) {
        for (ADLocationEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
