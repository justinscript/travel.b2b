/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

/**
 * <pre>
 *     <xml>
 *          <ToUserName><![CDATA[toUser]]></ToUserName>
 *          <FromUserName><![CDATA[FromUser]]></FromUserName>
 *          <CreateTime>123456789</CreateTime>
 *          <MsgType><![CDATA[event]]></MsgType>
 *          <Event><![CDATA[SCAN]]></Event>
 *          <EventKey><![CDATA[SCENE_VALUE]]></EventKey>
 *          <Ticket><![CDATA[TICKET]]></Ticket>
 *      </xml>
 * </pre>
 * 
 * <pre>
 *      EventKey  事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
 *      Ticket   二维码的ticket，可用来换取二维码图片
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:14:31 PM
 */
public class WeixinEventScanRequest extends WeixinEventRequest {

    private String eventKey; // scene_id
    private String ticket;  //

    public WeixinEventScanRequest(Map<String, String> datas) {
        super(datas);
        eventKey = datas.get("EventKey");
        ticket = datas.get("Ticket");
    }

    public String getEventKey() {
        return eventKey;
    }

    public String getTicket() {
        return ticket;
    }
}
