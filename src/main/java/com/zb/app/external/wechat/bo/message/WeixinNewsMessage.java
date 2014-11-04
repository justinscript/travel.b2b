/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.bo.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 图文消息条数限制在10条以内，注意，如果图文数超过10，则将会无响应。
 * 
 * <pre>
 * {
 *     "touser":"OPENID",
 *     "msgtype":"news",
 *     "news":{
 *         "articles": [
 *          {
 *              "title":"Happy Day",
 *              "description":"Is Really A Happy Day",
 *              "url":"URL",
 *              "picurl":"PIC_URL"
 *          },
 *          {
 *              "title":"Happy Day",
 *              "description":"Is Really A Happy Day",
 *              "url":"URL",
 *              "picurl":"PIC_URL"
 *          }
 *          ]
 *     }
 * }
 * title     否   标题
 * description  否   描述
 * url  否   点击后跳转的链接
 * picurl   否   图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
 * </pre>
 * 
 * @author zxc Oct 22, 2014 4:47:15 PM
 */
public class WeixinNewsMessage extends WeixinMessage {

    private WeixinNews news;

    public WeixinNewsMessage(String touser) {
        super(touser, "news");
        news = new WeixinNews();
    }

    public boolean addArticle(String title, String description, String url, String picurl) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(description) && StringUtils.isEmpty(url)
            && StringUtils.isEmpty(picurl)) {
            return false;
        }
        WeixinArticle article = new WeixinArticle(title, description, url, picurl);
        news.add(article);
        return false;
    }

    /**
     * @return the news
     */
    public WeixinNews getNews() {
        return news;
    }

    public static class WeixinNews {

        private List<WeixinArticle> articles;

        public WeixinNews() {
            articles = new ArrayList<WeixinNewsMessage.WeixinArticle>();
        }

        public boolean add(WeixinArticle article) {
            if (articles.size() >= 10) {
                return false;
            }
            return articles.add(article);
        }

        /**
         * @return the articles
         */
        public List<WeixinArticle> getArticles() {
            return articles;
        }

    }

    public static class WeixinArticle {

        /**
         * 构造器
         * 
         * @param title
         * @param description
         * @param url
         * @param picurl
         */
        public WeixinArticle(String title, String description, String url, String picurl) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.picurl = picurl;
        }

        private String title;
        private String description;
        private String url;
        private String picurl;

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

        /**
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url the url to set
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * @return the picurl
         */
        public String getPicurl() {
            return picurl;
        }

        /**
         * @param picurl the picurl to set
         */
        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }
    }
}
