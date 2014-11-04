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
 * <MsgType><![CDATA[video]]></MsgType>
 * <Video>
 * <MediaId><![CDATA[media_id]]></MediaId>
 * <Title><![CDATA[title]]></Title>
 * <Description><![CDATA[description]]></Description>
 * </Video> 
 * </xml>
 * MediaId   是   通过上传多媒体文件，得到的id
 * Title    否   视频消息的标题
 * Description  否   视频消息的描述
 * </pre>
 * 
 * @author zxc Oct 22, 2014 1:00:23 PM
 */
public class WeixinVideoResponse extends WeixinResponse {

    private String mediaId;
    private String title;
    private String description;

    public WeixinVideoResponse(WeixinRequest request) {
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

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponseType() {
        return "video";
    }

    public void addElement(Element root) {
        Element videoEle = new Element("Video");

        Element mediaIdEle = new Element("MediaId");
        mediaIdEle.setText(mediaId);
        videoEle.addContent(mediaIdEle);

        Element titleEle = new Element("Title");
        titleEle.setText(title);
        videoEle.addContent(titleEle);

        Element descriptionEle = new Element("Description");
        descriptionEle.setText(description);
        videoEle.addContent(descriptionEle);

        root.addContent(videoEle);
    }
}
