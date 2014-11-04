/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.response;

import org.jdom.Element;

import com.zb.app.external.wechat.request.WeixinRequest;

/**
 * @author zxc Oct 22, 2014 12:59:21 PM
 */
public class WeixinTextResponse extends WeixinResponse {

    private String content;

    public WeixinTextResponse(WeixinRequest request) {
        super(request);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponseType() {
        return "text";
    }

    public void addElement(Element root) {
        Element contentEle = new Element("Content");
        contentEle.setText(content);
        root.addContent(contentEle);
    }
}
