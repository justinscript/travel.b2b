/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.service;

import com.zb.app.external.wechat.request.WeixinEventClickRequest;
import com.zb.app.external.wechat.request.WeixinEventLocationRequest;
import com.zb.app.external.wechat.request.WeixinEventScanRequest;
import com.zb.app.external.wechat.request.WeixinEventSubscribeRequest;
import com.zb.app.external.wechat.request.WeixinEventViewRequest;
import com.zb.app.external.wechat.request.WeixinImageRequest;
import com.zb.app.external.wechat.request.WeixinLinkRequest;
import com.zb.app.external.wechat.request.WeixinLocationRequest;
import com.zb.app.external.wechat.request.WeixinTextRequest;
import com.zb.app.external.wechat.request.WeixinVideoRequest;
import com.zb.app.external.wechat.request.WeixinVoiceRequest;
import com.zb.app.external.wechat.response.WeixinResponse;

/**
 * @author zxc Oct 22, 2014 1:03:06 PM
 */
public interface WeixinResponseService {

    /**
     * 处理微信的text请求
     * 
     * @param request
     * @return
     */
    WeixinResponse textRequest(WeixinTextRequest request);

    /**
     * 处理图片消息
     * 
     * @param request
     * @return
     */
    WeixinResponse imageRequest(WeixinImageRequest request);

    /**
     * 处理语音消息
     * 
     * @param request
     * @return
     */
    WeixinResponse voiceRequest(WeixinVoiceRequest request);

    /**
     * 处理视频消息
     * 
     * @param request
     * @return
     */
    WeixinResponse videoRequest(WeixinVideoRequest request);

    /**
     * 地理位置请求
     * 
     * @param request
     * @return
     */
    WeixinResponse locationRequest(WeixinLocationRequest request);

    /**
     * 链接消息
     * 
     * @param request
     * @return
     */
    WeixinResponse linkRequest(WeixinLinkRequest request);

    // --------------------------event--------------------

    /**
     * 微信关注事件
     * 
     * @param request
     * @return
     */
    WeixinResponse eventSubscribeRequest(WeixinEventSubscribeRequest request);

    /**
     * 扫描事件
     * 
     * @param request
     * @return
     */
    WeixinResponse eventScanRequest(WeixinEventScanRequest request);

    /**
     * 上报地理位置事件
     * 
     * @param weixinEventLocationRequest
     * @return
     */
    WeixinResponse eventLocationRequest(WeixinEventLocationRequest request);

    /**
     * 点击菜单拉取消息事件
     * 
     * @param request
     * @return
     */
    WeixinResponse eventClickRequest(WeixinEventClickRequest request);

    /**
     * 点击菜单跳转链接事件
     * 
     * @param request
     * @return
     */
    WeixinResponse eventViewRequest(WeixinEventViewRequest request);
}
