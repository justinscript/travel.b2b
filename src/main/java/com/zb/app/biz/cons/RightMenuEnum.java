/*
 * Copyright 2011-2016 YueJi.com All right reserved. This software is the confidential and proprietary information of
 * YueJi.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with YueJi.com.
 */
package com.zb.app.biz.cons;

import com.zb.app.common.authority.Right;

/**
 * @author zxc Aug 11, 2014 3:20:58 PM
 */
public enum RightMenuEnum {

    ORDER_MANAGE(1, "订单管理", Right.SHOW_ORDER_MENU, Right.VIEW_ORDER, Right.CANCEL_ORDER, Right.MODIFY_ORDER),

    PRODUCT_MANAGE(2, "产品中心", Right.SHOW_PRODUCT_MENU, Right.CREATE_PRODUCT, Right.MODIFY_PRODUCT,
                   Right.REMOVE_PRODUCT, Right.SAVE_PRODUCT, Right.CREATE_LINE_TEMPLATE, Right.MODIFY_LINE_TEMPLATE,
                   Right.COPY_LINE_TEMPLATE, Right.DELETE_LINE_TEMPLATE, Right.CREATE_TRAFFIC_TEMPLATE,
                   Right.MODIFY_TRAFFIC_TEMPLATE, Right.DELETE_TRAFFIC_TEMPLATE, Right.UPLOAD_PHOTOS,
                   Right.MODIFY_PHOTOS, Right.DELETE_PHOTOS),

    SYSTEM_MANAGE(3, "系统管理", Right.SHOW_COMPANY_MENU, Right.COMPANY_NEWS, Right.COMPANY_INFO, Right.USER_MANAGE,
                  Right.ONLINE_SERVICE),

    ORDER_TOUR(1, "订单管理", Right.SHOW_ORDER_MENU, Right.VIEW_ORDER, Right.CANCEL_ORDER, Right.MODIFY_ORDER),

    PRODUCT_TOUR(2, "产品中心", Right.SHOW_PRODUCT_MENU),

    SYSTEM_TOUR(3, "系统管理", Right.SHOW_COMPANY_MENU, Right.COMPANY_NEWS, Right.COMPANY_INFO, Right.USER_MANAGE,
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
