/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.zb.app.common.core.utilities.HttpClientUtils;
import com.zb.app.external.wechat.bo.OAuthResult;
import com.zb.app.external.wechat.bo.WeixinAccessToken;
import com.zb.app.external.wechat.bo.WeixinUser;
import com.zb.app.external.wechat.bo.message.WeixinMessage;

/**
 * @author zxc Oct 22, 2014 4:43:06 PM
 */
public class WeixinService {

    private static Logger     logger = LoggerFactory.getLogger(OAuthResult.class);

    private WeixinConfig      config;
    private WeixinAccessToken weixinToken;

    public WeixinService(WeixinConfig config) {
        this.config = config;
    }

    private String getAccessToken() {
        if (weixinToken == null) {
            weixinToken = initAccessToken();
        }
        if (weixinToken != null && !weixinToken.isEffect()) {
            weixinToken = initAccessToken();
        }
        if (weixinToken == null) {
            return StringUtils.EMPTY;
        }
        return weixinToken.getAccessToken();
    }

    private WeixinAccessToken initAccessToken() {
        StringBuilder sb = new StringBuilder(500);
        sb.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential");
        sb.append("&appid=").append(config.getAppid());
        sb.append("&secret=").append(config.getSecret());
        String result = HttpClientUtils.getResponseBodyAsString(sb.toString());
        if (StringUtils.isEmpty(result)) {
            return null;
        }

        JSONObject jsonObject = JSONObject.fromObject(result);
        String accessToken = jsonObject.getString("access_token");
        String expiresIn = jsonObject.getString("expires_in");

        // TODO:需要处理错误状态
        WeixinAccessToken token = new WeixinAccessToken(accessToken, NumberUtils.toLong(expiresIn));
        logger.debug("----------------------");
        logger.debug(token.getAccessToken());
        logger.debug("----------------------");
        return token;
    }

    public WeixinUser getUserInfo(String openId) {
        // https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        StringBuilder sb = new StringBuilder(500);
        sb.append("https://api.weixin.qq.com/cgi-bin/user/info?");
        sb.append("&access_token=").append(getAccessToken());
        sb.append("&openid=").append(openId);
        sb.append("&lang=zh_CN");
        byte[] result = HttpClientUtils.getResponseBodyAsByte(sb.toString());
        String ss = null;
        try {
            ss = new String(result, "UTF-8");
            logger.debug(ss);
        } catch (UnsupportedEncodingException e) {
        }
        String responseBodyAsString = HttpClientUtils.getResponseBodyAsString(sb.toString());
        logger.debug("*********************");
        logger.debug(responseBodyAsString);
        logger.debug("*********************");
        JSONObject jsonObject = JSONObject.fromObject(ss);
        Object bean = JSONObject.toBean(jsonObject, WeixinUser.class);

        logger.error("JSONObject:  " + bean);
        return (WeixinUser) bean;
    }

    @SuppressWarnings("deprecation")
    public String sendMessage(WeixinMessage weixinMessage) throws Exception {
        StringBuilder sb = new StringBuilder(400);
        sb.append("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=");
        sb.append(getAccessToken());
        // https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
        PostMethod method = new PostMethod(sb.toString());
        method.getParams().setContentCharset("utf-8");
        String object2Json = new Gson().toJson(weixinMessage);
        logger.error(object2Json);
        // method.addParameter("body", object2Json);
        // method.setRequestBody(object2Json);
        RequestEntity entity = new StringRequestEntity(object2Json);
        method.setRequestEntity(entity);
        String result = HttpClientUtils.getResponseBodyAsString(method);
        logger.error(result);
        return result;
    }

    public void upload(File file, String type) {
        StringBuilder sb = new StringBuilder(400);
        sb.append("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=");
        sb.append(getAccessToken());
        sb.append("&type=").append(type);

        PostMethod postMethod = new PostMethod(sb.toString());
        try {
            // FilePart：用来上传文件的类
            FilePart fp = new FilePart("filedata", file);
            Part[] parts = { fp };
            // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
            postMethod.setRequestEntity(mre);
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置连接时间
            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                logger.error(postMethod.getResponseBodyAsString());
            } else {
                logger.error("fail");
            }
            byte[] responseBody = postMethod.getResponseBody();
            String result = new String(responseBody, "utf-8");
            logger.error("result : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
    }

    public String oauthUrl(String redirectUri) {
        StringBuilder sb = new StringBuilder(500);
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize");
        sb.append("?appid=").append(config.getAppid());
        sb.append("&redirect_uri=").append(encode(redirectUri));
        sb.append("&response_type=code");
        sb.append("&scope=snsapi_base");
        sb.append("&state=abc");
        sb.append("#wechat_redirect");
        return sb.toString();
    }

    // https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID
    // &secret=SECRET&code=CODE&grant_type=authorization_code
    public OAuthResult oauthResult(String code) {
        StringBuilder sb = new StringBuilder(500);
        sb.append("https://api.weixin.qq.com/sns/oauth2/access_token");
        sb.append("?appid=").append(config.getAppid());
        sb.append("&secret=").append(config.getSecret());
        sb.append("&code=").append(code);
        sb.append("&grant_type=authorization_code");
        byte[] result = HttpClientUtils.getResponseBodyAsByte(sb.toString());
        String s = null;
        try {
            s = new String(result, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        logger.error(s);
        logger.error("OAuthResult:  " + s);
        JSONObject jsonObject = JSONObject.fromObject(s);
        Object bean = JSONObject.toBean(jsonObject, OAuthResult.class);
        return (OAuthResult) bean;
    }

    private String encode(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }
}
