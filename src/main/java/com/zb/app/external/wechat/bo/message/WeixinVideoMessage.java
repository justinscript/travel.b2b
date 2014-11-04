/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.bo.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxc Oct 22, 2014 4:48:45 PM
 */
public class WeixinVideoMessage extends WeixinMessage {

    private Map<String, String> video;

    public WeixinVideoMessage(String touser) {
        this(touser, null, null, null);
    }

    public WeixinVideoMessage(String touser, String mediaId, String title, String description) {
        super(touser, "video");
        video = new HashMap<String, String>();
        setMediaId(mediaId);
        setTitle(title);
        setDescription(description);
    }

    public void setMediaId(String mediaId) {
        video.put("media_id", mediaId);
    }

    public void setTitle(String title) {
        video.put("title", title);
    }

    public void setDescription(String description) {
        video.put("description", description);
    }

    public Map<String, String> getVideo() {
        return video;
    }
}
