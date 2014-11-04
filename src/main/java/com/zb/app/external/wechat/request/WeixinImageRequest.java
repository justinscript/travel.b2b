/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

/**
 * <pre>
 *  <xml>
 *  <ToUserName><![CDATA[toUser]]></ToUserName>
 *  <FromUserName><![CDATA[fromUser]]></FromUserName>
 *  <CreateTime>1348831860</CreateTime>
 *  <MsgType><![CDATA[image]]></MsgType>
 *  <PicUrl><![CDATA[this is a url]]></PicUrl>
 *  <MediaId><![CDATA[media_id]]></MediaId>
 *  <MsgId>1234567890123456</MsgId>
 *  </xml>
 *  PicUrl   图片链接
 *  MediaId  图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:17:01 PM
 */
public class WeixinImageRequest extends WeixinRequest {

    private String picUrl;
    private String mediaId;

    public WeixinImageRequest(Map<String, String> datas) {
        super(datas);
        picUrl = datas.get("PicUrl");
        picUrl = datas.get("MediaId");
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }
}
