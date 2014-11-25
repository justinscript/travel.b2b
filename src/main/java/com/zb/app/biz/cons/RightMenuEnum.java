/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

import com.zb.app.common.authority.Right;

/**
 * @author zxc Aug 11, 2014 3:20:58 PM
 */
public enum RightMenuEnum {

    /**
     * manage right
     */
    COMPANY_MANAGE(1, "公司管理", Right.SHOW_COMPANY_MENU, Right.MANAGE_COMPANY_MENU, Right.ACCOUNT_COMPANY_MENU,
                   Right.TOUR_COMPANY_MENU, Right.BLACKLIST_MENU),

    LINE_MANAGE(2, "线路管理", Right.SHOW_LINE_MENU, Right.SHORT_LINE, Right.LONG_LINE, Right.INTERNATIONAL_LINE,
                Right.LINE_TEMPLATE),

    ORDER_MANAGE(3, "订单管理", Right.SHOW_ORDER_MENU, Right.FIT_ORDER, Right.COUNT_ORDER),

    NEWS_MANAGE(4, "新闻管理", Right.SHOW_NEWS_MENU, Right.TRAVEL_NEWS, Right.USER_NEWS, Right.WEB_NEWS),

    GIFT_MANAGE(5, "礼品管理", Right.SHOW_GIFT_MENU, Right.GIFT_LIST, Right.GIFT_ORDER_MANAGE, Right.GIFT_CLASS),

    SYSTEM_MANAGE(6, "系统管理", Right.SHOW_COMPANY_MENU, Right.SITE_MANAGE_MENU, Right.SITE_TAG_MENU,
                  Right.AD_MANAGE_MENU, Right.WEB_MANAGE_MENU),

    INTEGRAL_MANAGE(7, "积分管理", Right.SHOW_INTEGRAL_MENU, Right.INTEGRAL_LIST, Right.INTEGRAL_ADD),

    COUNT_MANAGE(8, "统计分析", Right.SHOW_COUNT_MENU, Right.ACCOUNT_COUNT_MANAGE, Right.LOGIN_COUNT, Right.REGISTER_COUNT),

    /**
     * account right
     */
    ORDER_ACCOUNT(1, "订单管理", Right.SHOW_ORDER_MENU, Right.VIEW_ORDER, Right.CANCEL_ORDER, Right.MODIFY_ORDER),

    PRODUCT_ACCOUNT(2, "产品中心", Right.SHOW_PRODUCT_MENU, Right.CREATE_PRODUCT, Right.MODIFY_PRODUCT,
                    Right.REMOVE_PRODUCT, Right.SAVE_PRODUCT, Right.CREATE_LINE_TEMPLATE, Right.MODIFY_LINE_TEMPLATE,
                    Right.COPY_LINE_TEMPLATE, Right.DELETE_LINE_TEMPLATE, Right.CREATE_TRAFFIC_TEMPLATE,
                    Right.MODIFY_TRAFFIC_TEMPLATE, Right.DELETE_TRAFFIC_TEMPLATE, Right.UPLOAD_PHOTOS,
                    Right.MODIFY_PHOTOS, Right.DELETE_PHOTOS),

    SYSTEM_ACCOUNT(3, "系统管理", Right.SHOW_SYSTEM_MENU, Right.COMPANY_NEWS, Right.COMPANY_INFO, Right.USER_MANAGE,
                   Right.ONLINE_SERVICE),

    /**
     * tour right
     */
    ORDER_TOUR(1, "订单管理", Right.SHOW_ORDER_MENU, Right.VIEW_ORDER, Right.CANCEL_ORDER, Right.MODIFY_ORDER),

    PRODUCT_TOUR(2, "产品中心", Right.SHOW_PRODUCT_MENU),

    SYSTEM_TOUR(3, "系统管理", Right.SHOW_SYSTEM_MENU, Right.COMPANY_NEWS, Right.COMPANY_INFO, Right.USER_MANAGE,
                Right.ONLINE_SERVICE);

    private int     value;

    private String  name;

    private Right[] rights;

    private RightMenuEnum(int value, String name, Right... rights) {
        this.value = value;
        this.name = name;
        this.rights = rights;
    }

    public static RightMenuEnum getAction(int value) {
        for (RightMenuEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Right[] getRights() {
        return rights;
    }

    public void setRights(Right[] rights) {
        this.rights = rights;
    }
}
