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
    //
    FIT_ORDER("fitOrder", "散客订单", 4),
    //
    COUNT_ORDER("countOrder", "订单统计", 5),

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
    SHOW_SYSTEM_MENU("showSystemMenu", "显示菜单", 30),
    //
    COMPANY_NEWS("companyNews", "公司新闻", 31),
    //
    COMPANY_INFO("companyInfo", "公司信息", 32),
    //
    USER_MANAGE("userMange", "用户管理", 33),
    //
    ONLINE_SERVICE("onlineService", "在线客服", 34),
    //
    SITE_MANAGE_MENU("siteManageMenu", "站点管理", 35),
    //
    SITE_TAG_MENU("siteTagMenu", "站点标签", 36),
    //
    AD_MANAGE_MENU("adManageMenu", "广告管理", 37),
    //
    WEB_MANAGE_MENU("webManageMenu", "网站栏目", 38),

    // *******************公司管理***************//
    //
    SHOW_COMPANY_MENU("showCompanyMenu", "显示菜单", 40),
    //
    MANAGE_COMPANY_MENU("manageCompanyMenu", "总管理", 41),
    //
    ACCOUNT_COMPANY_MENU("accountCompanyMenu", "批发商", 42),
    //
    TOUR_COMPANY_MENU("tourCompanyMenu", "组团社", 43),
    //
    BLACKLIST_MENU("blacklistMenu", "黑名单", 44),

    // *******************新闻管理***************//
    //
    SHOW_NEWS_MENU("showNewsMenu", "显示菜单", 50),
    //
    TRAVEL_NEWS("travelNews", "旅游资讯", 51),
    //
    USER_NEWS("userNews", "用户资讯", 52),
    //
    WEB_NEWS("webNews", "网站公告", 53),

    // *******************礼品管理***************//
    //
    SHOW_GIFT_MENU("showGiftMenu", "显示菜单", 60),
    //
    GIFT_LIST("giftList", "礼品列表", 61),
    //
    GIFT_ORDER_MANAGE("giftOrderManage", "礼品订单管理", 62),
    //
    GIFT_CLASS("giftClass", "礼品类别", 63),

    // *******************积分管理***************//
    //
    SHOW_INTEGRAL_MENU("showGiftmenu", "显示菜单", 70),
    //
    INTEGRAL_LIST("giftList", "积分记录", 71),
    //
    INTEGRAL_ADD("giftOrderManage", "积分充值", 72),

    // *******************统计分析***************//
    //
    SHOW_COUNT_MENU("showCountMenu", "显示菜单", 80),
    //
    ACCOUNT_COUNT_MANAGE("accountCountManage", "批发商收客", 81),
    //
    LOGIN_COUNT("loginCount", "登录统计", 82),
    //
    REGISTER_COUNT("registerCount", "注册统计", 83),

    // *******************线路管理***************//
    //
    SHOW_LINE_MENU("showLineMenu", "显示菜单", 90),
    //
    SHORT_LINE("shortLine", "周边短线", 91),
    //
    LONG_LINE("longLine", "国内长线", 92),
    //
    INTERNATIONAL_LINE("internationalLine", "国际线路", 93),
    //
    LINE_TEMPLATE("lineTemplate", "线路模板", 94)

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
