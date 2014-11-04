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
 * <MsgType><![CDATA[video]]></MsgType>
 * <MediaId><![CDATA[media_id]]></MediaId>
 * <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
 * <MsgId>1234567890123456</MsgId>
 * </xml>
 * MediaId   视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
 * ThumbMediaId     视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:18:37 PM
 */
public class WeixinVideoRequest extends WeixinRequest {

    private String thumbMediaId;
    private String mediaId;

    public WeixinVideoRequest(Map<String, String> datas) {
        super(datas);
        thumbMediaId = datas.get("ThumbMediaId");
        mediaId = datas.get("mediaId");
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public String getMediaId() {
        return mediaId;
    }
}
