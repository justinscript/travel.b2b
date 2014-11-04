/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.cons;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc Oct 21, 2014 5:08:47 PM
 */
public enum WeixinEventType {

    // 订阅
    subscribe,
    // 取消订阅
    unsubscribe,
    // 地理位置
    LOCATION,

    CLICK,

    VIEW;

    public static WeixinEventType getEventType(String event) {
        if (StringUtils.isEmpty(event)) {
            return null;
        }
        for (WeixinEventType eventType : values()) {
            if (eventType.name().equals(event)) {
                return eventType;
            }
        }
        return null;
    }
}
