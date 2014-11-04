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
 * <MsgType><![CDATA[music]]></MsgType>
 * <Music>
 * <Title><![CDATA[TITLE]]></Title>
 * <Description><![CDATA[DESCRIPTION]]></Description>
 * <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
 * <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
 * <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
 * </Music>
 * </xml>
 * Title     否   音乐标题
 * Description  否   音乐描述
 * MusicURL     否   音乐链接
 * HQMusicUrl   否   高质量音乐链接，WIFI环境优先使用该链接播放音乐
 * ThumbMediaId     是   缩略图的媒体id，通过上传多媒体文件，得到的id
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:24:35 PM
 */
public class WeixinMusicResponse extends WeixinResponse {

    private String thumbMediaId;
    private String title;
    private String description;
    private String musicUrl;
    private String hqMusicUrl;

    public WeixinMusicResponse(WeixinRequest request) {
        super(request);
    }

    /**
     * @return the thumbMediaId
     */
    public String getThumbMediaId() {
        return thumbMediaId;
    }

    /**
     * @param thumbMediaId the thumbMediaId to set
     */
    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    /**
     * @return the musicUrl
     */
    public String getMusicUrl() {
        return musicUrl;
    }

    /**
     * @param musicUrl the musicUrl to set
     */
    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    /**
     * @return the hqMusicUrl
     */
    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    /**
     * @param hqMusicUrl the hqMusicUrl to set
     */
    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
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
        return "music";
    }

    public void addElement(Element root) {
        Element musicEle = new Element("Music");

        Element mediaIdEle = new Element("MediaId");
        mediaIdEle.setText(thumbMediaId);
        musicEle.addContent(mediaIdEle);

        Element titleEle = new Element("Title");
        titleEle.setText(title);
        musicEle.addContent(titleEle);

        Element descriptionEle = new Element("Description");
        descriptionEle.setText(description);
        musicEle.addContent(descriptionEle);

        Element musicUrlEle = new Element("MusicUrl");
        musicUrlEle.setText(musicUrl);
        musicEle.addContent(musicUrlEle);

        Element hqMusicUrlEle = new Element("HQMusicUrl");
        hqMusicUrlEle.setText(hqMusicUrl);
        musicEle.addContent(hqMusicUrlEle);

        root.addContent(musicEle);
    }
}
