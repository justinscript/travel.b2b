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
 * <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>1357290913</CreateTime>
 * <MsgType><![CDATA[voice]]></MsgType>
 * <MediaId><![CDATA[media_id]]></MediaId>
 * <Format><![CDATA[Format]]></Format>
 * <MsgId>1234567890123456</MsgId>
 * </xml>
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:19:03 PM
 */
public class WeixinVoiceRequest extends WeixinRequest {

    private String mediaId;
    private String format;
    private String recognition; // 语音识别出来的数据

    public WeixinVoiceRequest(Map<String, String> datas) {
        super(datas);
        mediaId = datas.get("MediaId");
        format = datas.get("Format");
        recognition = datas.get("Recognition");
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getFormat() {
        return format;
    }

    public String getRecognition() {
        return recognition;
    }
}
