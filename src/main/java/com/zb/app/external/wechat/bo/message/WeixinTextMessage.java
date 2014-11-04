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
 *     "msgtype":"text",
 *     "text":
 *     {
 *          "content":"Hello World"
 *     }
 * }
 * </pre>
 * 
 * @author zxc Oct 22, 2014 4:48:03 PM
 */
public class WeixinTextMessage extends WeixinMessage {

    private Map<String, String> text;

    public WeixinTextMessage(String touser) {
        this(touser, null);
    }

    public WeixinTextMessage(String touser, String content) {
        super(touser, "text");
        text = new HashMap<String, String>();
        setContent(content);
    }

    public void setContent(String content) {
        text.put("content", content);
    }

    /**
     * @return the text
     */
    public Map<String, String> getText() {
        return text;
    }
}
