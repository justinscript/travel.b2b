/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.response;

import java.io.IOException;
import java.io.StringWriter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.zb.app.external.wechat.request.WeixinRequest;

/**
 * @author zxc Oct 21, 2014 5:19:56 PM
 */
public abstract class WeixinResponse {

    private String toUserName;
    private String fromUserName;
    private Long   createTime;
    private String msgType;

    public WeixinResponse(WeixinRequest request) {
        this.toUserName = request.getFromUserName();
        this.fromUserName = request.getToUserName();
        this.msgType = request.getMsgType();
        this.createTime = request.getCreateTime();
    }

    /**
     * @return the toUserName
     */
    public String getToUserName() {
        return toUserName;
    }

    /**
     * @return the fromUserName
     */
    public String getFromUserName() {
        return fromUserName;
    }

    /**
     * @return the createTime
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * @return the msgType
     */
    public String getMsgType() {
        return msgType;
    }

    public String toXML() throws IOException {
        Element root = new Element("xml");
        addDefault(root);
        addElement(root);
        Document doc = new Document(root);
        StringWriter stringWriter = new StringWriter();
        XMLOutputter xMLOutputter = new XMLOutputter();
        xMLOutputter.setFormat(Format.getPrettyFormat());
        xMLOutputter.output(doc, stringWriter);
        return stringWriter.toString().trim();
    }

    private void addDefault(Element root) {
        Element toUserNameEle = new Element("ToUserName");
        Element fromUserNameEle = new Element("FromUserName");
        Element createTimeEle = new Element("CreateTime");
        Element msgTypeEle = new Element("MsgType");
        // Element funcFlag = new Element("FuncFlag");

        toUserNameEle.setText(toUserName);
        fromUserNameEle.setText(fromUserName);
        createTimeEle.setText(String.valueOf(createTime));
        msgTypeEle.setText(getResponseType());
        // funcFlag.setText("0");

        root.addContent(toUserNameEle);
        root.addContent(fromUserNameEle);
        root.addContent(createTimeEle);
        root.addContent(msgTypeEle);
        // root.addContent(funcFlag);
    }

    /**
     * 返回数据类型
     * 
     * @return
     */
    public abstract String getResponseType();

    /**
     * 添加数据
     * 
     * @param root
     */
    public abstract void addElement(Element root);
}
