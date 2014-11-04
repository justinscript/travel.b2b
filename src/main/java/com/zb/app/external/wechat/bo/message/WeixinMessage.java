/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.bo.message;

/**
 * @author zxc Oct 22, 2014 4:46:18 PM
 */
public class WeixinMessage {

    private String touser;
    private String msgtype;

    /**
     * 构造器
     * 
     * @param touser
     * @param msgtype
     */
    public WeixinMessage(String touser, String msgtype) {
        this.touser = touser;
        this.msgtype = msgtype;
    }

    /**
     * @return the touser
     */
    public String getTouser() {
        return touser;
    }

    /**
     * @return the msgtype
     */
    public String getMsgtype() {
        return msgtype;
    }
}
