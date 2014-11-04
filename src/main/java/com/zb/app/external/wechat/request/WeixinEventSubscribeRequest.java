/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

/**
 * 关注、取消关注
 * 
 * <pre>
 *  <xml>
 *  <ToUserName><![CDATA[toUser]]></ToUserName>
 *  <FromUserName><![CDATA[FromUser]]></FromUserName>
 *  <CreateTime>123456789</CreateTime>
 *  <MsgType><![CDATA[event]]></MsgType>
 *  <Event><![CDATA[subscribe]]></Event>
 *  </xml>
 * </pre>
 * 
 * 用户扫描带场景值二维码时，未关注公众号
 * 
 * <pre>
 * <xml><ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[FromUser]]></FromUserName>
 * <CreateTime>123456789</CreateTime>
 * <MsgType><![CDATA[event]]></MsgType>
 * <Event><![CDATA[subscribe]]></Event>
 * <EventKey><![CDATA[qrscene_123123]]></EventKey>
 * <Ticket><![CDATA[TICKET]]></Ticket>
 * </xml>
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:15:03 PM
 */
public class WeixinEventSubscribeRequest extends WeixinEventRequest {

    private static final String QRSCENE        = "qrscene_";
    private static final int    QRSCENE_LENGTH = QRSCENE.length();
    private String              eventKey;
    private String              ticket;

    public WeixinEventSubscribeRequest(Map<String, String> datas) {
        super(datas);
        eventKey = datas.get("EventKey");
        ticket = datas.get("Ticket");
    }

    public boolean isSubscribe() {
        return "subscribe".equals(getEvent());
    }

    public boolean isUnSubscribe() {
        return "unsubscribe".equals(getEvent());
    }

    /**
     * 是否是从扫描二维码过来的
     * 
     * @return the fromQrscene
     */
    public boolean isFromQrscene() {
        return eventKey != null && eventKey.length() != 0;
    }

    public String getQRValue() {
        if (isFromQrscene()) {
            if (eventKey.startsWith(QRSCENE)) {
                return eventKey.substring(QRSCENE_LENGTH);
            }
        }
        return null;
    }

    public String getTicket() {
        return ticket;
    }

    public String getEventKey() {
        return eventKey;
    }
}
