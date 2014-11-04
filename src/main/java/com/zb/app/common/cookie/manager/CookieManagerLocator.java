/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.manager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zxc Jul 3, 2014 4:08:52 PM
 */
public class CookieManagerLocator {

    private static final String key  = "_cookiemanager_";
    private static final Lock   LOCK = new ReentrantLock();

    public static CookieManager get(HttpServletRequest request, HttpServletResponse response) {
        // 大多数情况
        CookieManager cookieManager = (CookieManager) request.getAttribute(key);
        if (cookieManager != null) {
            return cookieManager;
        }
        // 第一次需要创建
        try {
            LOCK.lock();
            if (cookieManager == null) {
                cookieManager = new DefaultCookieManager(request, response);
                request.setAttribute(key, cookieManager);
            }
            return cookieManager;
        } finally {
            LOCK.unlock();
        }
    }
}
