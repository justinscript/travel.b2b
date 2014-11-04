/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

/**
 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，请注意，点击菜单弹出子菜单，不会产生上报
 * 
 * <pre>
 *      <xml>
 *          <ToUserName><![CDATA[toUser]]></ToUserName>
 *          <FromUserName><![CDATA[FromUser]]></FromUserName>
 *          <CreateTime>123456789</CreateTime>
 *          <MsgType><![CDATA[event]]></MsgType>
 *          <Event><![CDATA[CLICK]]></Event>
 *          <EventKey><![CDATA[EVENTKEY]]></EventKey>
 *      </xml>
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:10:09 PM
 */
public class WeixinEventClickRequest extends WeixinEventRequest {

    private String eventKey;

    public WeixinEventClickRequest(Map<String, String> datas) {
        super(datas);
        eventKey = datas.get("EventKey");
    }

    public String getEventKey() {
        return eventKey;
    }
}
