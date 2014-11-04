/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

/**
 * @author zxc Oct 21, 2014 5:10:23 PM
 */
public class WeixinEventRequest extends WeixinRequest {

    private String event;

    public WeixinEventRequest(Map<String, String> datas) {
        super(datas);
        event = datas.get("Event");
    }

    public String getEvent() {
        return event;
    }
}
