/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.bo.message;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {
 *     "touser":"OPENID",
 *     "msgtype":"voice",
 *     "voice":
 *     {
 *       "media_id":"MEDIA_ID"
 *     }
 * }
 * </pre>
 * 
 * @author zxc Oct 22, 2014 4:49:16 PM
 */
public class WexinVoiceMessage extends WeixinMessage {

    private Map<String, String> voice;

    public WexinVoiceMessage(String touser) {
        this(touser, null);
    }

    public WexinVoiceMessage(String touser, String mediaId) {
        super(touser, "voice");
        voice = new HashMap<String, String>();
        setVoiceMediaId(mediaId);
    }

    public void setVoiceMediaId(String mediaId) {
        voice.put("media_id", mediaId);
    }

    public Map<String, String> getVoice() {
        return voice;
    }
}
