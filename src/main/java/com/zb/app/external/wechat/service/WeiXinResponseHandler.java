/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.zb.app.external.wechat.cons.WeixinEventType;
import com.zb.app.external.wechat.cons.WeixinMsgType;
import com.zb.app.external.wechat.request.WeixinEventClickRequest;
import com.zb.app.external.wechat.request.WeixinEventLocationRequest;
import com.zb.app.external.wechat.request.WeixinEventSubscribeRequest;
import com.zb.app.external.wechat.request.WeixinEventViewRequest;
import com.zb.app.external.wechat.request.WeixinLocationRequest;
import com.zb.app.external.wechat.request.WeixinTextRequest;
import com.zb.app.external.wechat.request.WeixinVoiceRequest;
import com.zb.app.external.wechat.response.WeixinResponse;

/**
 * @author zxc Oct 22, 2014 1:02:31 PM
 */
public class WeiXinResponseHandler {

    private WeixinResponseService weixinResponseService;
    private WeixinConfig          weixinConfig;

    public WeiXinResponseHandler() {

    }

    public WeiXinResponseHandler(WeixinResponseService weixinResponseService, WeixinConfig weixinConfig) {
        this.weixinResponseService = weixinResponseService;
        this.weixinConfig = weixinConfig;
    }

    /**
     * @param weixinResponseService the weixinResponseService to set
     */
    public void setWeixinResponseService(WeixinResponseService weixinResponseService) {
        this.weixinResponseService = weixinResponseService;
    }

    /**
     * @param weixinConfig the weixinConfig to set
     */
    public void setWeixinConfig(WeixinConfig weixinConfig) {
        this.weixinConfig = weixinConfig;
    }

    /**
     * 验证请求是否是从微信发送过来的 几个参数都是从请求参数中获取的
     * 
     * @param signature 微信加密签名
     * @param timestamp 时间轴
     * @param nonce 随机数
     * @return
     */
    public boolean validateSignature(String signature, String timestamp, String nonce) {
        String[] authStrs = new String[] { weixinConfig.getToken(), timestamp, nonce };
        Arrays.sort(authStrs);
        String validateSignature = DigestUtils.shaHex(StringUtils.join(authStrs, ""));
        return StringUtils.equals(signature, validateSignature);
    }

    public String service(String body) throws JDOMException, IOException {
        if (StringUtils.isEmpty(body)) {
            return null;
        }
        Map<String, String> datas = paserXML(body);
        String msgType = datas.get("MsgType");
        WeixinMsgType type = WeixinMsgType.getMsgType(msgType);
        if (type == null) {
            return null;
        }
        WeixinResponse response = null;
        switch (type) {
            case text:
                response = weixinResponseService.textRequest(new WeixinTextRequest(datas));
                break;
            case voice:
                response = weixinResponseService.voiceRequest(new WeixinVoiceRequest(datas));
                break;
            case event:
                response = eventResponse(datas);
                break;
            case location:
                response = weixinResponseService.locationRequest(new WeixinLocationRequest(datas));
                break;
            default:
                break;
        }
        if (response != null) {
            return response.toXML();
        }
        return null;
    }

    private WeixinResponse eventResponse(Map<String, String> datas) {
        String string = datas.get("Event");
        WeixinEventType eventType = WeixinEventType.getEventType(string);
        if (eventType == null) {
            return null;
        }
        WeixinResponse response = null;
        switch (eventType) {
            case subscribe:
                response = weixinResponseService.eventSubscribeRequest(new WeixinEventSubscribeRequest(datas));
                break;
            case unsubscribe:
                response = weixinResponseService.eventSubscribeRequest(new WeixinEventSubscribeRequest(datas));
                break;
            case LOCATION:
                response = weixinResponseService.eventLocationRequest(new WeixinEventLocationRequest(datas));
                break;
            case CLICK:
                response = weixinResponseService.eventClickRequest(new WeixinEventClickRequest(datas));
                break;
            case VIEW:
                response = weixinResponseService.eventViewRequest(new WeixinEventViewRequest(datas));
                break;
            default:
                break;
        }
        return response;
    }

    @SuppressWarnings("rawtypes")
    private Map<String, String> paserXML(String body) {
        Map<String, String> result = new HashMap<String, String>();
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        try {
            document = builder.build(new StringReader(body));
        } catch (JDOMException e) {
            return Collections.emptyMap();
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        Element rootNode = document.getRootElement();
        List children = rootNode.getChildren();
        for (Object object : children) {
            if (object instanceof Element) {
                String text = ((Element) object).getText();
                String name = ((Element) object).getName();
                result.put(name, text);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        WeiXinResponseHandler handler = new WeiXinResponseHandler();
        String body = "<xml>" + "<ToUserName><![CDATA[toUser]]></ToUserName>"
                      + "<FromUserName><![CDATA[fromUser]]></FromUserName> " + "<CreateTime>1348831860</CreateTime>"
                      + "<MsgType><![CDATA[text]]></MsgType>" + "<Content><![CDATA[this is a test]]></Content>"
                      + "<MsgId>1234567890123456</MsgId>" + "</xml>";
        handler.paserXML(body);
    }
}
