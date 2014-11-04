/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.parser;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.common.cookie.CookieKeyEnum;
import com.zb.app.common.cookie.CookieNameEnum;
import com.zb.app.common.cookie.annotation.CookieKeyPolicy;
import com.zb.app.common.cookie.annotation.CookieNamePolicy;

/**
 * @author zxc Jul 3, 2014 4:43:08 PM
 */
public class CookieNamePolicyParser {

    private static final Logger                          logger                      = LoggerFactory.getLogger(CookieNamePolicyParser.class);

    /**
     * 所有的CookieName到CookieNameConfig的映射
     */
    private static Map<CookieNameEnum, CookieNameConfig> COOKIE_NAME_CONFIG_MAP      = new HashMap<CookieNameEnum, CookieNameConfig>();

    /**
     * 所有CookieKey到CookieName的映射。以便判断一个CookieKey是属于那个CookieName下的
     */
    private static Map<CookieKeyEnum, CookieNameEnum>    COOKIEKEY_TO_COOKIENAME_MAP = new HashMap<CookieKeyEnum, CookieNameEnum>();

    /**
     * 当该类被加载时，将CookiePoliy和CookieGroupPolicy初始化
     */
    static {
        if (logger.isDebugEnabled()) {
            logger.debug("开始初始化所有的Cookie配置");
        }

        Field[] allCookieGroups = CookieNameEnum.class.getFields();
        for (Field field : allCookieGroups) {
            CookieNamePolicy cookieNamePolicy = field.getAnnotation(CookieNamePolicy.class);
            if (cookieNamePolicy == null) {
                throw new IllegalArgumentException("有一个CookieName:" + field.getName() + "没有配置CookieNamePolicy");
            }
            // 初始化当前的CookieNameConfig
            CookieNameEnum cookieNameEnum = CookieNameEnum.valueOf(field.getName());
            CookieNameConfig CookieNameConfig = new CookieNameConfig(cookieNameEnum, cookieNamePolicy);
            COOKIE_NAME_CONFIG_MAP.put(cookieNameEnum, CookieNameConfig);
        }

        Field[] allCookieKeys = CookieKeyEnum.class.getFields();
        for (Field field : allCookieKeys) {
            CookieKeyPolicy cookiePolicy = field.getAnnotation(CookieKeyPolicy.class);
            if (cookiePolicy == null) {
                throw new IllegalArgumentException("CookieKey:[" + field.getName() + "]没有配置CookiePolicy");
            }
            CookieKeyEnum cookieKey = CookieKeyEnum.valueOf(field.getName());
            // 当前CookieKey所在的CookieName
            CookieNameEnum cookieName = cookiePolicy.withinCookieName();
            // 保存下CookieKey到cookieName的映射
            COOKIEKEY_TO_COOKIENAME_MAP.put(cookieKey, cookieName);
            // 同时将该CookieKey保存到它所在的Cookiename下
            CookieNameConfig cookieNameConfig = COOKIE_NAME_CONFIG_MAP.get(cookieName);
            cookieNameConfig.appendKey(cookieKey);

        }
        // unmodified
        COOKIE_NAME_CONFIG_MAP = Collections.unmodifiableMap(COOKIE_NAME_CONFIG_MAP);
        if (logger.isDebugEnabled()) {
            logger.debug("所有Cookie的配置初始化完成 [" + COOKIE_NAME_CONFIG_MAP + "]");
        }
    }

    /**
     * 获取所有的CookieName到CookieNameConfig(cookie name 的配置）的Map
     */
    public static Map<CookieNameEnum, CookieNameConfig> getCookieNamePolicyMap() {
        return COOKIE_NAME_CONFIG_MAP;
    }

    /**
     * 从CookieKey获取他所在的Cookiename
     */
    public static CookieNameEnum getCookieName(CookieKeyEnum cookieKeyEnum) {
        return COOKIEKEY_TO_COOKIENAME_MAP.get(cookieKeyEnum);
    }
}
