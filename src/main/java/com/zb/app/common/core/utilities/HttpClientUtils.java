/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Oct 22, 2014 4:54:00 PM
 */
public class HttpClientUtils {

    private static final Logger                       logger            = LoggerFactory.getLogger(HttpClientUtils.class);
    private static MultiThreadedHttpConnectionManager connectionManager = null;
    private static HttpClient                         client            = null;

    public static String getResponseBodyAsString(String url) {
        HttpMethod method = new GetMethod(url);
        return getResponseBodyAsString(method);
    }

    public static String getResponseBodyAsString(HttpMethod method) {
        return getResponseBodyAsString(method, null, null, null, null);
    }

    public static String getResponseBodyAsString(HttpMethod method, Integer tryTimes, String responseCharSet,
                                                 Integer maximumResponseByteSize, Integer soTimeoutMill) {
        init();
        if (tryTimes == null) {
            tryTimes = 1;
        }
        if (StringUtils.isBlank(responseCharSet)) {
            responseCharSet = "utf-8";
        }
        if (maximumResponseByteSize == null) {
            maximumResponseByteSize = 50 * 1024 * 1024;
        }
        if (soTimeoutMill == null) {
            soTimeoutMill = 20000;
        }
        method.getParams().setSoTimeout(soTimeoutMill);
        InputStream httpInputStream = null;
        for (int i = 0; i < tryTimes; i++) {
            try {
                int responseCode = client.executeMethod(method);
                if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    if (method instanceof HttpMethodBase) {
                        responseCharSet = ((HttpMethodBase) method).getResponseCharSet();
                    }
                    int read = 0;
                    byte[] cbuf = new byte[4096];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    httpInputStream = method.getResponseBodyAsStream();
                    while ((read = httpInputStream.read(cbuf)) >= 0) {
                        baos.write(cbuf, 0, read);
                        if (baos.size() >= maximumResponseByteSize) {
                            break;
                        }
                    }
                    String content = baos.toString(responseCharSet);
                    return content;
                }
                logger.error(String.format("getResponseBodyAsString failed, responseCode: %s, should be 200, 301, 302",
                                           responseCode));
                return "";
            } catch (Exception e) {
                logger.error("getResponseBodyAsString failed", e);
            } finally {
                IOUtils.closeQuietly(httpInputStream);
                method.releaseConnection();
            }
        }
        return "";
    }

    /**
     * <pre>
     * 你必须这么调用
     * 
     * try {
     *      inputStream = getResponseBodyAsStream(method);
     * } finally {
     *      IOUtils.closeQuietly(inputStream);
     *      method.releaseConnection();
     * }
     * 
     * </pre>
     * 
     * @param method
     * @return
     */
    public static InputStream getResponseBodyAsStream(HttpMethod method) {
        return getResponseBodyAsStream(method, null, null);
    }

    /**
     * 这时候返回的byte[]数组是在内存中的，你要注意 uri 返回内容不会占用太多内存，如果会的话请使用getResponseBodyAsStream
     * 
     * @param method
     * @return
     */
    public static byte[] getResponseBodyAsByte(String uri) {
        return getResponseBodyAsByte(new GetMethod(uri));
    }

    /**
     * 这时候返回的byte[]数组是在内存中的，你要注意method里面的链接返回内容不会占用太多内存，如果会的话请使用getResponseBodyAsStream
     * 
     * @param method
     * @return
     */
    public static byte[] getResponseBodyAsByte(HttpMethod method) {
        init();
        InputStream inputStream = null;
        try {
            inputStream = getResponseBodyAsStream(method, null, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            method.releaseConnection();
        }
        return new byte[] {};
    }

    /**
     * <pre>
     * 你必须这么调用
     * 
     * try {
     *      inputStream = getResponseBodyAsStream(method, tryTimes, soTimeoutMill);
     * } finally {
     *      IOUtils.closeQuietly(inputStream);
     *      method.releaseConnection();
     * }
     * 
     * @param method
     * @param tryTimes
     * @param soTimeoutMill
     * @return
     */
    public static InputStream getResponseBodyAsStream(HttpMethod method, Integer tryTimes, Integer soTimeoutMill) {
        init();
        if (tryTimes == null) {
            tryTimes = 1;
        }
        if (soTimeoutMill == null) {
            soTimeoutMill = 20000;
        }
        method.getParams().setSoTimeout(soTimeoutMill);
        for (int i = 0; i < tryTimes; i++) {
            try {
                int responseCode = client.executeMethod(method);
                if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_MOVED_PERMANENTLY
                    || responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    return method.getResponseBodyAsStream();
                }
                logger.error(String.format("getResponseBodyAsString failed, responseCode: %s, should be 200, 301, 302",
                                           responseCode));
            } catch (Exception e) {
                logger.error("getResponseBodyAsString failed", e);
            } finally {
                // method releaseConnection 的时候导致 ResponseStream 被关闭，没办法返回ResponseStream，这是一个奇怪的设计，
                // 所以必须在上层做finally { method.releaseConnection }
                // method.releaseConnection();
            }
        }
        return null;
    }

    public static synchronized void init() {
        if (connectionManager != null) {
            return;
        }
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
}
