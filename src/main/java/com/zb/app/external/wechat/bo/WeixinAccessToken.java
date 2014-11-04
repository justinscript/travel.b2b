/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.bo;

/**
 * @author zxc Oct 22, 2014 4:44:24 PM
 */
public class WeixinAccessToken {

    private String accessToken;
    private long   effectTime;

    /**
     * 构造器
     * 
     * @param accessToken
     * @param effectIn
     */
    public WeixinAccessToken(String accessToken, long effectIn) {
        this.accessToken = accessToken;
        this.effectTime = System.currentTimeMillis() + 7200 * 1000;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @return the effectTime
     */
    public long getEffectTime() {
        return effectTime;
    }

    public boolean isEffect() {
        return effectTime > System.currentTimeMillis();
    }
}
