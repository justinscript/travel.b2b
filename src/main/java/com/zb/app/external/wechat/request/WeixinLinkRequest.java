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
 *  <CreateTime>1351776360</CreateTime>
 *  <MsgType><![CDATA[link]]></MsgType>
 *  <Title><![CDATA[公众平台官网链接]]></Title>
 *  <Description><![CDATA[公众平台官网链接]]></Description>
 *  <Url><![CDATA[url]]></Url>
 *  <MsgId>1234567890123456</MsgId>
 *  </xml> 
 * Title  消息标题
 * Description  消息描述
 * Url  消息链接
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:17:32 PM
 */
public class WeixinLinkRequest extends WeixinRequest {

    private String title;
    private String description;
    private String url;

    public WeixinLinkRequest(Map<String, String> datas) {
        super(datas);
        title = datas.get("Title");
        description = datas.get("Description");
        url = datas.get("Url");
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
