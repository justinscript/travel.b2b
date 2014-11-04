/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.response;

import org.jdom.Element;

import com.zb.app.external.wechat.request.WeixinRequest;

/**
 * <pre>
 * <xml>
 * <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>12345678</CreateTime>
 * <MsgType><![CDATA[voice]]></MsgType>
 * <Voice>
 * <MediaId><![CDATA[media_id]]></MediaId>
 * </Voice>
 * </xml>
 * </pre>
 * 
 * @author zxc Oct 22, 2014 1:01:40 PM
 */
public class WeixinVoiceResponse extends WeixinResponse {

    private String mediaId;

    public WeixinVoiceResponse(WeixinRequest request) {
        super(request);
    }

    /**
     * @return the mediaId
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * @param mediaId the mediaId to set
     */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getResponseType() {
        return "voice";
    }

    public void addElement(Element root) {
        Element voiceEle = new Element("Voice");
        Element mediaIdEle = new Element("MediaId");
        mediaIdEle.setText(mediaId);
        voiceEle.addContent(mediaIdEle);
        root.addContent(voiceEle);
    }
}
