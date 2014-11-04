/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.common.cookie.CookieKeyEnum;
import com.zb.app.common.cookie.CookieNameEnum;
import com.zb.app.common.cookie.parser.CookieNameConfig;
import com.zb.app.common.cookie.parser.CookieNameHelper;
import com.zb.app.common.cookie.parser.CookieNamePolicyParser;
import com.zb.app.common.cookie.parser.CookieParser;
import com.zb.app.common.cookie.parser.CookieUtils;

/**
 * DefaultCookieManager是每一个请求一个的。
 * 
 * <pre>
 *      DefaultCookieManager首先CookieParser.loadCookie将所有的Cookie出来。
 * </pre>
 * 
 * @author zxc Jul 1, 2014 4:58:11 PM
 */
public class DefaultCookieManager implements CookieManager {

    private static final Logger                     logger             = LoggerFactory.getLogger(DefaultCookieManager.class);
    /**
     * 所有的CookieName及其值，如果本次请求没有的话，将返回null
     */
    protected Map<CookieNameEnum, CookieNameHelper> currentCookieValue = new ConcurrentHashMap<CookieNameEnum, CookieNameHelper>();

    private HttpServletRequest                      request;
    private HttpServletResponse                     repsonse;

    public DefaultCookieManager(HttpServletRequest request, HttpServletResponse response) {
        this.repsonse = response;
        this.request = request;
        init();
    }

    protected void init() {
        // 取得当前所有的Cookie值
        Map<CookieNameEnum, CookieNameHelper> newValues = CookieParser.loadCookie(request.getCookies());
        for (CookieNameEnum cookieNameEnum : newValues.keySet()) {
            currentCookieValue.put(cookieNameEnum, newValues.get(cookieNameEnum));
        }
    }

    /**
     * 判断某个cookieName是否存在于本次请求
     */
    public boolean containsCookieName(CookieNameEnum cookieName) {
        return currentCookieValue.containsKey(cookieName);
    }

    public String get(CookieKeyEnum cookieKeyEnum) {
        if (cookieKeyEnum == null) {
            return null;
        }
        // 首先找到该CookieKey所在的CookieName
        CookieNameEnum cookienameEnum = CookieNamePolicyParser.getCookieName(cookieKeyEnum);
        assertNotSimple(cookienameEnum);
        // 然后取出该CookieName下cookieKey的值
        CookieNameHelper cookieNameHelper = currentCookieValue.get(cookienameEnum);
        if (cookieNameHelper == null) {
            return null;
        } else {
            return CookieUtils.filterNullValue(cookieNameHelper.getValue(cookieKeyEnum));
        }
    }

    private void assertNotSimple(CookieNameEnum cookienameEnum) {
        CookieNameConfig cookieNameConfig = CookieNamePolicyParser.getCookieNamePolicyMap().get(cookienameEnum);
        if (cookieNameConfig.isSimpleValue()) {
            throw new RuntimeException("这是一个简单CookieName，不能使用这个方法");
        }
    }

    public String get(CookieNameEnum cookieNameEnum) {
        assertSimple(cookieNameEnum);
        CookieNameHelper cookieNameHelper = currentCookieValue.get(cookieNameEnum);
        if (cookieNameHelper == null) {
            return null;
        } else {
            return CookieUtils.filterNullValue(cookieNameHelper.getValue());
        }
    }

    private void assertSimple(CookieNameEnum cookieNameEnum) {
        CookieNameConfig cookieNameConfig = CookieNamePolicyParser.getCookieNamePolicyMap().get(cookieNameEnum);
        if (!cookieNameConfig.isSimpleValue()) {
            throw new RuntimeException("不是简单CookieName，不能使用这个方法");
        }
    }

    public void set(CookieNameEnum cookieNameEnum, String value) {
        if (cookieNameEnum == null) return;
        assertSimple(cookieNameEnum);
        CookieNameHelper cookieNameHelper = currentCookieValue.get(cookieNameEnum);
        boolean isNewCookieKey = cookieNameHelper == null;
        if (isNewCookieKey) {
            cookieNameHelper = createNewCookie(cookieNameEnum, null, value);
            currentCookieValue.put(cookieNameEnum, cookieNameHelper);
        } else {
            cookieNameHelper.updateSimpleValue(value);
        }

    }

    public void set(CookieKeyEnum cookieKey, String value) {
        if (cookieKey == null) return;

        // 首先找到该CookieKey所在的CookieName
        CookieNameEnum cookieName = CookieNamePolicyParser.getCookieName(cookieKey);
        assertNotSimple(cookieName);
        CookieNameHelper cookieNameHelper = currentCookieValue.get(cookieName);
        // 如果当前值没有取到，需要创建一个新的CookieKey
        boolean isNewCookieKey = cookieNameHelper == null;
        if (isNewCookieKey) {
            cookieNameHelper = createNewCookie(cookieName, cookieKey, value);
            currentCookieValue.put(cookieName, cookieNameHelper);
        } else {
            cookieNameHelper.update(cookieKey, value);
        }
    }

    /**
     * 创建一项Cookie
     */
    private CookieNameHelper createNewCookie(CookieNameEnum cookieName, CookieKeyEnum cookieKey, String value) {
        // 首先取得CookieName的配置
        CookieNameConfig cookieNameConfig = CookieNamePolicyParser.getCookieNamePolicyMap().get(cookieName);
        CookieNameHelper cookieNameHelper = new CookieNameHelper(cookieName.getCookieName(), cookieNameConfig);
        if (cookieNameConfig.isSimpleValue()) {
            cookieNameHelper.updateSimpleValue(value);
        } else {
            cookieNameHelper.update(cookieKey, value);
        }
        return cookieNameHelper;
    }

    public void clear(CookieNameEnum cookieName) {
        CookieNameHelper cookieNameHelper = currentCookieValue.get(cookieName);
        if (cookieNameHelper == null) {
            logger.error("试图更新一个Cookie的值，但是本次请求[" + cookieName + "]不存在！是不是因为域设置有问题。");
            return;
        }
        cookieNameHelper.clear();
    }

    public void save() {
        for (CookieNameHelper cookieNameHelper : currentCookieValue.values()) {
            cookieNameHelper.saveIfModified(repsonse);
        }
    }

    @Override
    public void clearAll() {
        for (CookieNameHelper cookieNameHelper : currentCookieValue.values()) {
            cookieNameHelper.clear();
        }
    }
}
