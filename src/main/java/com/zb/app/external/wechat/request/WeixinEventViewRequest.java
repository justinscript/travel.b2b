/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

/**
 * 点击菜单跳转链接时的事件推送
 * 
 * <pre>
 * <xml>
 * <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[FromUser]]></FromUserName>
 * <CreateTime>123456789</CreateTime>
 * <MsgType><![CDATA[event]]></MsgType>
 * <Event><![CDATA[VIEW]]></Event>
 * <EventKey><![CDATA[www.qq.com]]></EventKey>
 * </xml>
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:16:31 PM
 */
public class WeixinEventViewRequest extends WeixinEventRequest {

    private String eventKey;

    public WeixinEventViewRequest(Map<String, String> datas) {
        super(datas);
        eventKey = datas.get("EventKey");
    }

    public String getEventKey() {
        return eventKey;
    }
}
