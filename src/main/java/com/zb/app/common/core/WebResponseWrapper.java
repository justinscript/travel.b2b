/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Jul 16, 2014 10:41:27 AM
 */
public class WebResponseWrapper extends HttpServletResponseWrapper {

    protected static Logger       logger = LoggerFactory.getLogger(WebResponseWrapper.class);

    private StringWriter          sw;

    private ByteArrayOutputStream bos;

    private boolean               isWriterUsed;
    private boolean               isStreamUsed;

    public WebResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public PrintWriter getWriter() {
        if (isStreamUsed) {
            logger.error("Unexpected internal error during import: Target servlet called getWriter(), then getOutputStream()");
            throw new IllegalStateException(
                                            "Unexpected internal error during import: Target servlet called getWriter(), then getOutputStream()");
        }
        isWriterUsed = true;
        if (sw == null) {
            sw = new StringWriter(2048);
        }
        return new PrintWriter(sw);
    }

    public ServletOutputStream getOutputStream() {
        if (isWriterUsed) {
            logger.error("Unexpected internal error during import: Target servlet called getOutputStream(), then getWriter()");
            throw new IllegalStateException(
                                            "Unexpected internal error during import: Target servlet called getOutputStream(), then getWriter()");
        }
        isStreamUsed = true;
        if (bos == null) {
            bos = new ByteArrayOutputStream();
        }
        ServletOutputStream sos = new ServletOutputStream() {

            public void write(int b) throws IOException {
                bos.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }
        };
        return sos;
    }

    public boolean isStreamUseed() {
        return isStreamUsed;
    }

    public byte[] getByte() {
        if (isStreamUsed) {
            return bos.toByteArray();
        }
        return StringUtils.EMPTY.getBytes();
    }

    public String getString() throws UnsupportedEncodingException {
        if (isWriterUsed) {
            return sw.toString();
        } else if (isStreamUsed) {
            return bos.toString(this.getCharacterEncoding());
        } else {
            return StringUtils.EMPTY; // target didn't write anything
        }
    }
}
