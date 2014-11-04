/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import com.zb.app.biz.BaseTestCase;

/**
 * @author zxc Oct 20, 2014 3:56:22 PM
 */
public class WeixinTest extends BaseTestCase {

    private String     loginUrl = "https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN";
    private String     sendUrl  = "https://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&f=json&lang=zh_CN";
    private String     account  = "zuobiancom";                                                                   // 公众平台用户
    private String     password = "long1024";                                                                     // 公众平台密码
    private boolean    isLogin  = false;
    private String     cookiestr;
    private String     token    = null;
    private HttpClient httpClient;

    public void login() {
        httpClient = new HttpClient();
        PostMethod post = new PostMethod(loginUrl);
        post.addParameter(new NameValuePair("username", account));
        post.addParameter(new NameValuePair("pwd", DigestUtils.md5Hex(password)));
        post.addParameter(new NameValuePair("imgcode", ""));
        post.addParameter(new NameValuePair("f", "json"));
        post.setRequestHeader("Host", "mp.weixin.qq.com");
        post.setRequestHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/loginpage?t=wxm2-login&lang=zh_CN");

        try {
            int code = httpClient.executeMethod(post);
            if (HttpStatus.SC_OK == code) {
                String res = post.getResponseBodyAsString();
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(res);
                JSONObject _obj = (JSONObject) obj.get("base_resp");
                @SuppressWarnings("unused")
                String msg = (String) _obj.get("err_msg");
                String redirect_url = (String) obj.get("redirect_url");
                Long errCode = (Long) _obj.get("ret");
                if (0 == errCode) {
                    isLogin = true;
                    token = StringUtils.substringAfter(redirect_url, "token=");
                    if (null == token) {
                        token = StringUtils.substringBetween(redirect_url, "token=", "&");
                    }
                    StringBuffer cookie = new StringBuffer();
                    for (Cookie c : httpClient.getState().getCookies()) {
                        cookie.append(c.getName()).append("=").append(c.getValue()).append(";");
                    }
                    this.cookiestr = cookie.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendMessage(String fakeid, String content) {
        if (isLogin) {
            PostMethod post = new PostMethod(sendUrl);
            post.setRequestHeader("Cookie", this.cookiestr);
            post.setRequestHeader("Host", "mp.weixin.qq.com");
            post.setRequestHeader("Referer",
                                  "https://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&token="
                                          + token + "&tofakeid=" + fakeid + "&lang=zh_CN");
            post.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
            post.addParameter(new NameValuePair("type", "1"));
            post.addParameter(new NameValuePair("content", content));
            post.addParameter(new NameValuePair("error", "false"));
            post.addParameter(new NameValuePair("imgcode", ""));
            post.addParameter(new NameValuePair("tofakeid", "681435581"));
            post.addParameter(new NameValuePair("token", token));
            post.addParameter(new NameValuePair("ajax", "1"));
            try {
                int code = httpClient.executeMethod(post);
                if (HttpStatus.SC_OK == code) {
                    System.out.println(post.getResponseBodyAsString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            login();
            sendMessage(fakeid, content);
        }
        return false;
    }

    public String getFakeid(String openid) {
        if (isLogin) {
            GetMethod get = new GetMethod("https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&token=" + token
                                          + "&lang=zh_CN&pagesize=10&pageidx=0&type=0");
            get.setRequestHeader("Cookie", this.cookiestr);
            get.setRequestHeader("Host", "mp.weixin.qq.com");
            get.setRequestHeader("Referer",
                                 "https://mp.weixin.qq.com/cgi-bin/message?t=message/list&count=20&day=7&token="
                                         + token + "&lang=zh_CN");
            get.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
            try {
                int code = httpClient.executeMethod(get);
                System.out.println(code);
                if (HttpStatus.SC_OK == code) {
                    System.out.println("Fake Success");
                    String str = get.getResponseBodyAsString();
                    System.out.println(str.length());
                    // String userjson = StringUtils.substringBetween(str,
                    // "<script id=\"json-friendList\" type=\"json/text\">", "</script>");
                    String userjson = StringUtils.substringBetween(str, "\"contacts\":", "})");
                    System.out.println(userjson);
                    JSONParser parser = new JSONParser();
                    try {
                        JSONArray array = (JSONArray) parser.parse(userjson);
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject obj = (JSONObject) array.get(i);
                            String fakeid = obj.get("id").toString();
                            if (compareFakeid(fakeid, openid)) {
                                return fakeid;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            login();
            return getFakeid(openid);
        }
        return null;
    }

    private boolean compareFakeid(String fakeid, String openid) {
        PostMethod post = new PostMethod(
                                         "https://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&token="
                                                 + token + "&tofakeid=" + fakeid + "&lang=zh_CN");
        post.setRequestHeader("Cookie", this.cookiestr);
        post.setRequestHeader("Host", "mp.weixin.qq.com");
        post.setRequestHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&token=" + token
                                         + "&lang=zh_CN&pagesize=10&pageidx=0&type=0");
        post.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
        post.addParameter(new NameValuePair("token", token));
        post.addParameter(new NameValuePair("ajax", "1"));
        try {
            int code = httpClient.executeMethod(post);
            if (HttpStatus.SC_OK == code) {
                String str = post.getResponseBodyAsString();
                String msgJson = StringUtils.substringBetween(str, "<script id=\"json-msgList\" type=\"json\">",
                                                              "</script>");
                JSONParser parser = new JSONParser();
                try {
                    JSONArray array = (JSONArray) parser.parse(msgJson);
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = (JSONObject) array.get(i);
                        String content = (String) obj.get("content");
                        if (content.contains(openid)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void test() {
        WeixinTest util = new WeixinTest();
        util.login();
        String fakeId = util.getFakeid("openid");// 可以在消息体中获得
        System.out.println(fakeId);
        util.sendMessage(fakeId, "测试微信消息");
    }

    private boolean _compareFakeid(String fakeid, String openid) {
        String _url = "http://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&token=" + token
                      + "&tofakeid=" + fakeid + "&lang=zh_CN";

        GetMethod get = new GetMethod(_url);
        get.setRequestHeader("Cookie", this.cookiestr);
        get.setRequestHeader("Host", "mp.weixin.qq.com");
        get.setRequestHeader("Referer",
                             "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid=0&token="
                                     + token + "&lang=zh_CN");
        get.setRequestHeader("Content-Type", "text/html;charset=UTF-8");

        try {
            int code = httpClient.executeMethod(get);
            if (HttpStatus.SC_OK == code) {
                String str = get.getResponseBodyAsString();
                String msgJson = StringUtils.substringBetween(str, "{\"msg_item\":", "}};");
                JSONParser parser = new JSONParser();
                try {
                    JSONArray array = (JSONArray) parser.parse(msgJson);
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = (JSONObject) array.get(i);
                        String content = (String) obj.get("content");
                        if (content.contains(openid)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // log.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            // log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void onSetUp() throws Exception {

    }
}
