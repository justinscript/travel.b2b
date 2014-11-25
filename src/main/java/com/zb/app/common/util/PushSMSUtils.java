/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.Argument;

/**
 * <pre>
 * 主要需求：您有新的订单[订单号：XXXXXXXX]，请您及时处理。
 * 次要需求：
 *         1、您的订单[订单号：XXXXXXXX]已被修改，请您及时查看处理。  
 *         2、批发商取消、修改内容短信同次要需求1
 *            您的订单[订单号：XXXXXXXX]已被商家确认。
 * 
 * 说明：  组团社预定、修改、取消等订单操作，短信提醒只针对批发商，不用提醒组团社账户。
 *        批发商修改、取消、确认等订单操作，短信提醒只针对组团社，不用提醒批发商账户。
 * </pre>
 * 
 * @author zxc Nov 11, 2014 12:27:41 PM
 */
@Component
public class PushSMSUtils {

    private static Logger          logger          = LoggerFactory.getLogger(PushSMSUtils.class);
    private static String          PUSH_SERVER_URL = "http://218.8.241.211:8080/api/sendsmsi.php";
    // http://218.8.241.211:8080/api/sendsms.php?
    // loginname=106&password=106lejin&number=13918731742,13795463925,18616799662,18912386146&content=zuobian%20Test!
    @Autowired
    private ThreadPoolTaskExecutor executor;

    public static PushSMSUtils getInstance() {
        return (PushSMSUtils) SpringContextAware.getBean("pushSMSUtils");
    }

    /**
     * 发送短信通知批发商,有新订单
     * 
     * @param mobile 接收短信的人手机号
     * @param orderID 订单编号
     */
    public void sendNewOrderSMS(final String orderID, final String... mobile) {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                String msg = String.format("您有新的订单[订单号：%s]，请您及时处理。", orderID);
                sendPushMsg(msg, mobile);
            }
        });
    }

    /**
     * 发送短信通知组团社,订单发生变更
     * 
     * @param mobile 接收短信的人手机号
     * @param orderID 订单编号
     */
    public void sendOrderModifySMS(final String orderID, final String... mobile) {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                String msg = String.format("您的订单[订单号：%s]已被修改，请您及时查看处理。", orderID);
                sendPushMsg(msg, mobile);
            }
        });
    }

    /**
     * 发送短信通知组团社,订单已经被批发商确认
     * 
     * @param mobile 接收短信的人手机号
     * @param orderID 订单编号
     */
    public void sendOrderConfirmSMS(final String orderID, final String... mobile) {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                String msg = String.format("您的订单[订单号：%s]已被商家确认。", orderID);
                sendPushMsg(msg, mobile);
            }
        });
    }

    /**
     * 发送短信通知组团社,订单已经被批发商取消
     * 
     * @param mobile 接收短信的人手机号
     * @param orderID 订单编号
     */
    public void sendOrderCancelSMS(final String orderID, final String... mobile) {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                String msg = String.format("您的订单[订单号：%s]已被商家取消。", orderID);
                sendPushMsg(msg, mobile);
            }
        });
    }

    private void sendPushMsg(String msg, String... mobile) {
        if (Argument.isEmptyArray(mobile)) {
            return;
        }
        sendPushMsg(StringUtils.join(mobile, ","), msg, StringUtils.EMPTY);
    }

    private void sendPushMsg(String mobiles, String msg, String sequeid) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(PUSH_SERVER_URL);
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("loginname", "zbw"));
        params.add(new BasicNameValuePair("password", "zbw123"));
        params.add(new BasicNameValuePair("number", mobiles));
        params.add(new BasicNameValuePair("content", msg));
        // params.add(new BasicNameValuePair("sequeid", sequeid));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "gb2312"));
            HttpResponse response = client.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                logger.debug("手机号{}，消息{}，推送成功！", mobiles, msg);
            } else {
                logger.debug("手机号{}，消息{}，推送失败！", mobiles, msg);
            }
        } catch (Exception e) {
            logger.debug("http post error!{}", e.getMessage());
        } finally {
            httpPost.releaseConnection();
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i < 11; i++) {
            PushSMSUtils pushUtils = new PushSMSUtils();
            System.out.println("左边网,短信测试!!!收到请不要回复,谢谢! 第" + i + "次测试,Test Start!");

            pushUtils.sendPushMsg("您有新的订单[订单号：3]，请您及时处理【左边网】", "15001968175", "18616799662", "18912386146");

            System.out.println("左边网,短信测试!!!收到请不要回复,谢谢! 第" + i + "次测试,Test End!");

            System.out.println("Now,start sleep!");
            try {
                Thread.sleep(1000 * 180);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Now,sleep end!");
        }
    }
}
