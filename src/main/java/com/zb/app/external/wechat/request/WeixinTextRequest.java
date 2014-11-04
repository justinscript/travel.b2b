/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

/**
 * <pre>
 * <xml>
 *  <ToUserName><![[toUser]]></ToUserName>
 *  <FromUserName><![CDATA[fromUser]]></FromUserName> 
 *  <CreateTime>1348831860</CreateTime>
 *  <MsgType><![CDATA[text]]></MsgType>
 *  <Content><![CDATA[this is a test]]></Content>
 *  <MsgId>1234567890123456</MsgId>
 * </xml>
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:18:09 PM
 */
public class WeixinTextRequest extends WeixinRequest {

    private String content;

    public WeixinTextRequest(Map<String, String> datas) {
        super(datas);
        content = datas.get("Content");
    }

    public String getContent() {
        return content;
    }
}
