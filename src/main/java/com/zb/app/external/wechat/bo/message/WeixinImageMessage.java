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
 *     "msgtype":"image",
 *     "image":
 *     {
 *       "media_id":"MEDIA_ID"
 *     }
 * }
 * </pre>
 * 
 * @author zxc Oct 22, 2014 4:45:29 PM
 */
public class WeixinImageMessage extends WeixinMessage {

    private Map<String, String> image;

    public WeixinImageMessage(String touser) {
        this(touser, null);
    }

    public WeixinImageMessage(String touser, String mediaId) {
        super(touser, "image");
        image = new HashMap<String, String>();
        setImageMediaId(mediaId);
    }

    public void setImageMediaId(String mediaId) {
        image.put("media_id", mediaId);
    }

    /**
     * @return the image
     */
    public Map<String, String> getImage() {
        return image;
    }
}
