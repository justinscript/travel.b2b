/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author zxc Oct 22, 2014 1:03:55 PM
 */
public class WeixinLocationRequest extends WeixinRequest {

    private float  locationX;
    private float  locationY;
    private float  scale;
    private String label;

    public WeixinLocationRequest(Map<String, String> datas) {
        super(datas);
        locationX = NumberUtils.toFloat(datas.get("Location_X"));
        locationY = NumberUtils.toFloat(datas.get("Location_Y"));
        scale = NumberUtils.toFloat(datas.get("Scale"));
        label = datas.get("Label");
    }

    public float getLocationX() {
        return locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public float getScale() {
        return scale;
    }

    public String getLabel() {
        return label;
    }
}
