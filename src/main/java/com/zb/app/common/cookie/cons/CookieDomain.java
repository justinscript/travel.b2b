/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.cons;

/**
 * @author zxc Jul 3, 2014 4:43:08 PM
 */
public enum CookieDomain {

    DOT_ZUOBIAN_COM(".zuobian.com"), WWW_ZUOBIAN_COM("www.zuobian.com");

    private String domain;

    private CookieDomain(String cookieDomain) {
        this.domain = cookieDomain;
    }

    public String getDomain() {
        return domain;
    }

    public static CookieDomain getEnum(String domain) {
        for (CookieDomain cookieDomain : values()) {
            if (cookieDomain.getDomain().equals(domain)) {
                return cookieDomain;
            }
        }
        return null;
    }
}
