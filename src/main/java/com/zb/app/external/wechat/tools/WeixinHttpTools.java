/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.wechat.tools;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * @author zxc Nov 4, 2014 4:12:17 PM
 */
public class WeixinHttpTools {

    private static MultiThreadedHttpConnectionManager connectionManager = null;
    private static HttpClient                         client            = null;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(10000);
        params.setSoTimeout(20000);
        params.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 1000);
        params.setMaxTotalConnections(10000);
        connectionManager.setParams(params);
        client = new HttpClient(connectionManager);
        client.getParams().setParameter("http.protocol.max-redirects", 3);
    }

    public static String post(String url) {
        return null;
    }
}
