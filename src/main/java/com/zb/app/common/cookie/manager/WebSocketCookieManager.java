/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.manager;

import static com.zb.app.common.cookie.cons.CookieConstant.COOKIE_KEY_VALUE_SEPARATOR_CHAR;
import static com.zb.app.common.cookie.cons.CookieConstant.COOKIE_SPLIT_SEPARATOR_CHAR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.zb.app.common.cookie.CookieNameEnum;
import com.zb.app.common.cookie.parser.CookieNameHelper;
import com.zb.app.common.cookie.parser.CookieParser;
import com.zb.app.common.util.ObjectUtils;

/**
 * @author zxc Jul 29, 2014 2:02:53 PM
 */
public class WebSocketCookieManager extends DefaultCookieManager implements CookieManager {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketCookieManager.class);

    private HttpHeaders         httpHeaders;

    private Cookie[]            cookieArray;

    public WebSocketCookieManager(HttpHeaders httpHeaders) {
        this(httpHeaders, null, null);
    }

    public WebSocketCookieManager(HttpHeaders httpHeaders, HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.httpHeaders = httpHeaders;
        doInit();
    }

    @Override
    protected void init() {

    }

    public Cookie[] getCookies() {
        return cookieArray;
    }

    protected void doInit() {
        logger.debug("WebSocketCookieManager init():init httpHeaders cookies!");
        List<String> list = httpHeaders.get("cookie");
        String cookieStr = StringUtils.EMPTY;
        if (list != null && list.size() > 0) {
            cookieStr = list.get(0);
        }
        if (StringUtils.isNotEmpty(cookieStr)) {
            cookieArray = str2Cookie(cookieStr);
            Map<CookieNameEnum, CookieNameHelper> newValues = CookieParser.loadCookie(cookieArray);
            for (CookieNameEnum cookieNameEnum : newValues.keySet()) {
                super.currentCookieValue.put(cookieNameEnum, newValues.get(cookieNameEnum));
            }
        }
    }

    private Cookie[] str2Cookie(String cookies) {
        Map<String, String> cookieMap = str2Map(cookies);
        ObjectUtils.trim(cookieMap);
        List<Cookie> cookieList = new ArrayList<Cookie>();
        for (Entry<String, String> entry : cookieMap.entrySet()) {
            cookieList.add(new Cookie(entry.getKey(), entry.getValue()));
        }
        Cookie[] array = cookieList.toArray(new Cookie[cookieList.size()]);
        return array;
    }

    private Map<String, String> str2Map(String cookies) {
        String[] kvs = StringUtils.split(cookies, COOKIE_SPLIT_SEPARATOR_CHAR);
        if (kvs == null || kvs.length == 0) {
            return Collections.<String, String> emptyMap();
        }
        Map<String, String> kvMap = new HashMap<String, String>();
        for (String kv : kvs) {
            int offset = kv.indexOf(COOKIE_KEY_VALUE_SEPARATOR_CHAR);
            if (offset > 0 && offset < kv.length()) {
                String key = kv.substring(0, offset);
                if (key != null) {
                    kvMap.put(key, kv.substring(offset + 1, kv.length()));
                }
            }
        }
        return kvMap;
    }
}
