/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.cons;

import org.apache.commons.lang.StringUtils;

/**
 * 接收微信的消息类型
 * 
 * @author zxc Oct 21, 2014 5:09:14 PM
 */
public enum WeixinMsgType {

    /**
     * 文本消息
     */
    text, voice, event, location;

    public boolean isText() {
        return this == text;
    }

    public boolean isVoice() {
        return this == voice;
    }

    public boolean isEvent() {
        return this == event;
    }

    public static WeixinMsgType getMsgType(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (WeixinMsgType type : values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
