/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.parser;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zb.app.common.cookie.CookieKeyEnum;
import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.security.UUIDGenerator;

/**
 * 用来生成CookieId
 * 
 * @author zxc Jul 1, 2014 5:12:35 PM
 */
public class CookieIdBuilder {

    private static final Lock lock = new ReentrantLock();

    /**
     * 获取或者生成新的CookieId
     * 
     * <pre>
     *      如果当前Cookie中CookieID存在，直接返回
     *      如果没有则创建一个，同时保存到Cookie当中去
     * </pre>
     * 
     * @return 返回当前或者最新的CookieId
     */
    public static String createCookieId(CookieManager cookieManager) {
        try {
            lock.lock();
            String cookieId = getCookieId(cookieManager);
            if (cookieId == null) {
                cookieId = UUIDGenerator.createUUID();
                cookieManager.set(CookieKeyEnum.cookie_id, cookieId);
            }
            return cookieId;
        } finally {
            lock.unlock();
        }
    }

    public static String getCookieId(CookieManager cookieManager) {
        return cookieManager.get(CookieKeyEnum.cookie_id);
    }
}
