/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

import org.apache.commons.lang.StringUtils;

/**
 * 公司类型（1=组团社，2=地接社）
 * 
 * @author zxc Jul 30, 2014 11:31:03 PM
 */
public enum CompanyTypeEnum {

    /**
     * 组团社
     */
    TOUR(1, "tour", "组团社", "/index.htm", "/tour/line/longLine.htm", "/tour/**", "/*.htm", "/account/login.htm",
         "/zbmanlogin/login.htm", "/order/**"),

    /**
     * 地接社
     */
    ACCOUNT(2, "account", "地接社", "/account/login.htm", "/account/home.htm", "/account/**", "/*.htm",
            "/zbmanlogin/login.htm", "/order/**"),

    /**
     * 管理后台
     */
    MANAGE(3, "manage", "管理后台", "/zbmanlogin/login.htm", "/zbmanlogin/m/manage.htm", "/zbmanlogin/**", "/*.htm",
           "/account/login.htm", "/order/**"),

    /**
     * 直客会员
     */
    PERSON(4, "person", "直客会员", "/zbmanlogin/login.htm", "/zbmanlogin/company/person.htm", "/zbmanlogin/**", "/*.htm",
           "/account/login.htm");

    private int      value;
    private String   name;
    private String   desc;
    private String   loginUrl;
    private String   indexUrl;
    private String[] authUrl;

    private CompanyTypeEnum(int value, String name, String desc, String loginUrl, String indexUrl, String... authUrl) {
        this.value = value;
        this.name = name;
        this.desc = desc;
        this.loginUrl = loginUrl;
        this.indexUrl = indexUrl;
        this.authUrl = authUrl;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public String getDesc() {
        return desc;
    }

    public String[] getAuthUrl() {
        return authUrl;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public static boolean isAccount(CompanyTypeEnum type) {
        return CompanyTypeEnum.ACCOUNT.value == type.value;
    }

    public static boolean isManage(CompanyTypeEnum type) {
        return CompanyTypeEnum.MANAGE.value == type.value;
    }

    public static boolean isTour(CompanyTypeEnum type) {
        return CompanyTypeEnum.TOUR.value == type.value;
    }

    /**
     * 根据name获取类型
     */
    public static CompanyTypeEnum getEnum(String name) {
        for (CompanyTypeEnum current : values()) {
            if (StringUtils.equals(current.name, name)) {
                return current;
            }
        }
        return null;
    }

    /**
     * 根据value获取类型
     */
    public static CompanyTypeEnum getEnum(int value) {
        for (CompanyTypeEnum current : values()) {
            if (current.value == value) {
                return current;
            }
        }
        return null;
    }
}
