/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.request;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

/**
 * <pre>
 *      <xml>
 *          <ToUserName><![CDATA[gh_b1d93f10c768]]></ToUserName>
 *          <FromUserName><![CDATA[o3OCLjgf42X038Ceg0zjC46XWWU0]]></FromUserName>
 *          <CreateTime>1398066340</CreateTime>
 *          <MsgType><![CDATA[event]]></MsgType>
 *          <Event><![CDATA[LOCATION]]></Event>
 *          <Latitude>31.224501</Latitude>
 *          <Longitude>121.478600</Longitude>
 *          <Precision>69.000000</Precision>
 *      </xml>
 * </pre>
 * 
 * @author zxc Oct 21, 2014 5:13:11 PM
 */
public class WeixinEventLocationRequest extends WeixinEventRequest {

    private float latitude; // 地理位置纬度
    private float longitude; // 地理位置经度
    private float precision; // 地理位置精度

    public WeixinEventLocationRequest(Map<String, String> datas) {
        super(datas);
        latitude = NumberUtils.toFloat(datas.get("Latitude"));
        longitude = NumberUtils.toFloat(datas.get("Longitude"));
        precision = NumberUtils.toFloat(datas.get("Precision"));
    }

    /**
     * 纬度
     * 
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * 经度
     * 
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * 精度
     * 
     * @return the precision
     */
    public float getPrecision() {
        return precision;
    }
}
