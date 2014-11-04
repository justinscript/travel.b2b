/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.service;

/**
 * @author zxc Oct 22, 2014 1:02:41 PM
 */
public class WeixinConfig {

    private String appid;
    private String secret;
    private String token;

    /**
     * 构造器
     * 
     * @param appid
     * @param secret
     * @param token
     */
    public WeixinConfig(String appid, String secret, String token) {
        this.appid = appid;
        this.secret = secret;
        this.token = token;
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getToken() {
        return token;
    }
}
