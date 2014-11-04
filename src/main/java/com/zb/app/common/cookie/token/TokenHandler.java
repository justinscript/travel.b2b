/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.common.cookie.CookieNameEnum;
import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.cookie.manager.CookieManagerLocator;
import com.zb.app.common.security.EncryptBuilder;

/**
 * @author zxc Jul 3, 2014 12:03:59 AM
 */
public class TokenHandler {

    public static Logger       logger        = LoggerFactory.getLogger(TokenHandler.class);
    public static final String SESSION_TOKEN = "_session_token";
    public static final String ATTRIBUTE_KEY = "_$_old_session_token_$_";

    /**
     * @param request
     * @param response
     * @return
     */
    public static String addToken(HttpServletRequest request, HttpServletResponse response) {
        String addToken = addToken(request, response, createTokenValue());
        logger.info("添加token-cookies: " + addToken);
        return addToken;
    }

    /**
     * @param request
     * @param response
     * @param value
     * @return
     */
    private static String addToken(HttpServletRequest request, HttpServletResponse response, String value) {
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        CookieNameEnum cookieNameEnum = getDomainCookieType();
        cookieManager.set(cookieNameEnum, value);
        return value;
    }

    /**
     * @param request
     * @param response
     */
    public static void resetToken(HttpServletRequest request, HttpServletResponse response) {
        String value = (String) request.getAttribute(ATTRIBUTE_KEY);
        addToken(request, response, value);
        request.removeAttribute(ATTRIBUTE_KEY);
        logger.info("重新添加token-cookies: " + value);
    }

    /**
     * @param request
     * @param response
     * @return
     */
    public static boolean checkToken(HttpServletRequest request, HttpServletResponse response) {
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        String checkValue = request.getParameter(SESSION_TOKEN);
        if (checkValue == null || checkValue.trim().length() == 0) {
            logger.warn("tokne校验, 没有发现表单隐藏token值域,请检查");
            return false;
        }
        CookieNameEnum cookieNameEnum = getDomainCookieType();
        String cookieString = cookieManager.get(cookieNameEnum);
        if (cookieString == null || cookieString.trim().length() == 0) {
            logger.warn("tokne校验, 没有发现cookie中的校验值,请检查");
            return false;
        }
        // 检查成功
        if (checkValue.equals(cookieString)) {
            logger.info("token校验成功");
            request.setAttribute(ATTRIBUTE_KEY, cookieString);
            logger.info("清除token-cookie");
            cookieManager.clear(cookieNameEnum);
            return true;
        }
        return false;
    }

    public static CookieNameEnum getDomainCookieType() {
        // logger.error("未知应用,请检查,添加token失败");
        return CookieNameEnum.zuobian_cookie_tooken;
    }

    private static String createTokenValue() {
        return EncryptBuilder.getInstance().encrypt(Long.toString(System.nanoTime()));
    }
}
