/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.web.servlet.DispatcherServlet;

import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.cookie.manager.CookieManagerLocator;
import com.zb.app.web.webuser.ZuobianWebUser;
import com.zb.app.web.webuser.ZuobianWebUserBuilder;

/**
 * @author zxc Jun 17, 2014 11:01:54 AM
 */
public class ZuobianDispatcherServlet extends DispatcherServlet {

    private static final long serialVersionUID = -1838835292361204923L;

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String contentType = request.getHeader("Content-Type");

        if (!StringUtils.isBlank(contentType) && contentType.toLowerCase().startsWith("application/json")) {
            request = new FilterRequestWrapper(request);
        }
        super.doService(request, response);
    }

    @Override
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            CookieManager cookieManager = CookieManagerLocator.get(request, response);
            ZuobianWebUser webUser = ZuobianWebUserBuilder.create(cookieManager);
            if (webUser.hasLogin()) {
                super.noHandlerFound(request, response);
                return;
            }
        } catch (Exception e) {
            logger.debug("url noHandlerFound and parse cookies error!");
        }
        response.sendRedirect("/index.htm");
    }

    public static class FilterRequestWrapper extends HttpServletRequestWrapper {

        private final String payload;
        private String       encoding = null;

        public FilterRequestWrapper(HttpServletRequest request) {
            super(request);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            String encoding = request.getCharacterEncoding();
            try {
                // read the payload into the StringBuilder
                // 按照正确的encoding，将inputStream中的内容写入到String中
                InputStream inputStream = request.getInputStream();
                int total = request.getContentLength();
                if (inputStream != null && total > 0) {
                    if (StringUtils.isEmpty(encoding)) {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    } else {
                        this.encoding = encoding;
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
                    }

                    int bytesRead = -1;
                    int remaining = total;
                    char[] charBuffer = new char[total];
                    while ((bytesRead = bufferedReader.read(charBuffer, total - remaining, remaining)) != -1
                           && remaining > 0) {
                        remaining -= bytesRead;
                    }
                    stringBuilder.append(charBuffer);
                } else {
                    // make an empty string since there is no payload
                    stringBuilder.append("");
                }
            } catch (IOException ex) {
                throw new RuntimeException("Error reading the request payload", ex);
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException iox) {
                        // ignore
                    }
                }
            }
            payload = stringBuilder.toString();
        }

        public String getPayload() {
            return this.payload;
        }

        // 重载getInputStream方法，获取ServletInputStream流
        @Override
        public ServletInputStream getInputStream() throws IOException {

            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                                                                                       StringUtils.isEmpty(encoding) ? payload.getBytes() : payload.getBytes(encoding));

            DelegatingServletInputStream inputStream = new DelegatingServletInputStream(byteArrayInputStream);

            // ServletInputStream inputStream = new ServletInputStream() {
            // public int read() throws IOException {
            // return byteArrayInputStream.read();
            // }
            // };
            return inputStream;
        }
    }
}
