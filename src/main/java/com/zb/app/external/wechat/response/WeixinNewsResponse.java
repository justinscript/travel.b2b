/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom.Element;

import com.zb.app.external.wechat.request.WeixinRequest;

/**
 * <pre>
 * <xml>
 * <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>12345678</CreateTime>
 * <MsgType><![CDATA[news]]></MsgType>
 * <ArticleCount>2</ArticleCount>
 * <Articles>
 * <item>
 * <Title><![CDATA[title1]]></Title> 
 * <Description><![CDATA[description1]]></Description>
 * <PicUrl><![CDATA[picurl]]></PicUrl>
 * <Url><![CDATA[url]]></Url>
 * </item>
 * <item>
 * <Title><![CDATA[title]]></Title>
 * <Description><![CDATA[description]]></Description>
 * <PicUrl><![CDATA[picurl]]></PicUrl>
 * <Url><![CDATA[url]]></Url>
 * </item>
 * </Articles>
 * </xml>
 * ArticleCount  是   图文消息个数，限制为10条以内
 * Articles     是   多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
 * Title    否   图文消息标题
 * Description  否   图文消息描述
 * PicUrl   否   图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
 * Url  否   点击图文消息跳转链接
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:25:26 PM
 */
public class WeixinNewsResponse extends WeixinResponse {

    private List<NewsItem> articles;

    public WeixinNewsResponse(WeixinRequest request) {
        super(request);
        articles = new ArrayList<WeixinNewsResponse.NewsItem>();
    }

    /**
     * 如果只有一条内容，可显示description<br/>
     * 如果多条内容，则description无法显示
     * 
     * @param title
     * @param description
     * @param url
     * @param picUrl
     * @return
     */
    public boolean add(String title, String description, String url, String picUrl) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(description) && StringUtils.isEmpty(picUrl)
            && StringUtils.isEmpty(url)) {
            return false;
        }
        if (articles.size() >= 10) {
            return false;
        }
        NewsItem e = new NewsItem(title, description, picUrl, url);
        return articles.add(e);
    }

    public String getResponseType() {
        return "news";
    }

    public void addElement(Element root) {
        Element articleCountEle = new Element("ArticleCount");
        articleCountEle.setText("" + articles.size());
        root.addContent(articleCountEle);
        //
        Element articlesEle = new Element("Articles");
        //
        for (NewsItem item : articles) {
            Element itemEle = new Element("item");
            itemEle.addContent(new Element("Title").setText(item.getTitle()));
            itemEle.addContent(new Element("Description").setText(item.getDescription()));
            itemEle.addContent(new Element("PicUrl").setText(item.getPicUrl()));
            itemEle.addContent(new Element("Url").setText(item.getUrl()));
            articlesEle.addContent(itemEle);
        }
        root.addContent(articlesEle);
    }

    public static class NewsItem {

        private String title;
        private String description;
        private String picUrl;
        private String url;

        /**
         * 构造器
         * 
         * @param title
         * @param description
         * @param picUrl
         * @param url
         */
        public NewsItem(String title, String description, String picUrl, String url) {
            this.title = title;
            this.description = description;
            this.picUrl = picUrl;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public String getUrl() {
            return url;
        }
    }
}
