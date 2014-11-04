/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.authority;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc Jul 14, 2014 10:49:12 PM
 */
public enum Right {

    // *******************订单管理***************//
    //
    SHOW_ORDER_MENU("showOrderMenu", "显示菜单", 0),
    //
    VIEW_ORDER("viewOrder", "查看订单", 1),
    //
    CANCEL_ORDER("cancelOrder", "取消订单", 2),
    //
    MODIFY_ORDER("modifyOrder", "修改订单", 3),

    // *******************产品管理***************//
    //
    SHOW_PRODUCT_MENU("showProductMenu", "显示菜单", 10),
    //
    CREATE_PRODUCT("createProduct", "创建产品", 11),
    //
    MODIFY_PRODUCT("modifyProduct", "修改产品", 12),
    //
    REMOVE_PRODUCT("removeProduct", "删除产品", 13),
    //
    SAVE_PRODUCT("saveProduct", "另存产品", 14),
    //
    CREATE_LINE_TEMPLATE("createLineTemplate", "创建线路模版", 15),
    //
    MODIFY_LINE_TEMPLATE("modifyLineTemplate", "修改线路模版", 16),
    //
    COPY_LINE_TEMPLATE("copyLineTemplate", "复制线路模版", 17),
    //
    DELETE_LINE_TEMPLATE("deleteLineTemplate", "删除线路模版", 18),
    //
    CREATE_TRAFFIC_TEMPLATE("createTrafficTemplate", "创建交通模版", 19),
    //
    MODIFY_TRAFFIC_TEMPLATE("modiftTrafficTemplate", "修改交通模版", 20),
    //
    DELETE_TRAFFIC_TEMPLATE("deleteTrafficTemplate", "删除交通模版", 21),
    //
    UPLOAD_PHOTOS("uploadPhotos", "上传图片", 22),
    //
    MODIFY_PHOTOS("modiftPhotos", "修改图片", 23),
    //
    DELETE_PHOTOS("deletePhotos", "删除图片", 24),

    // *******************系统管理***************//
    //
    SHOW_COMPANY_MENU("showCompanyMenu", "显示菜单", 30),
    //
    COMPANY_NEWS("companyNews", "公司新闻", 31),
    //
    COMPANY_INFO("companyInfo", "公司信息", 32),
    //
    USER_MANAGE("userMange", "用户管理", 33),
    //
    ONLINE_SERVICE("onlineService", "在线客服", 34)

    ;

    private String desc;
    private String name;
    private int    index;

    private Right(String desc, String name, int index) {
        this.desc = desc;
        this.name = name;
        this.index = index;
    }

    public static Right getAction(int index) {
        for (Right type : values()) {
            if (index == type.getIndex()) return type;
        }
        return null;
    }

    public static Right getAction(String desc) {
        if (StringUtils.isEmpty(desc)) {
            return null;
        }
        for (Right type : values()) {
            if (StringUtils.equals(desc, type.getDesc())) return type;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
