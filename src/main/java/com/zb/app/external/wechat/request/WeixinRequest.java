/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author zxc Oct 21, 2014 5:11:50 PM
 */
public class WeixinRequest {

    private String toUserName;
    private String fromUserName;
    private Long   createTime;
    private String msgType;
    private Long   msgId;

    public WeixinRequest(Map<String, String> datas) {
        init(datas);
    }

    /**
     * 初始化数据
     * 
     * @param datas
     */
    private void init(Map<String, String> datas) {
        msgType = datas.get("MsgType");
        toUserName = datas.get("ToUserName");
        fromUserName = datas.get("FromUserName");
        long ct = NumberUtils.toLong(datas.get("CreateTime"), 0);
        if (ct > 0) {
            createTime = ct;
        }
        long mid = NumberUtils.toLong(datas.get("MsgId"), 0);
        if (mid > 0) {
            msgId = mid;
        }
    }

    public String getToUserName() {
        return toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public Long getMsgId() {
        return msgId;
    }
}
