/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.velocity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * @author zxc Jul 14, 2014 11:57:43 AM
 */
public class Widget {

    private static final Logger logger            = LoggerFactory.getLogger(Widget.class);

    public static final String  IS_WIDGET         = "_$_WIDGET_$_";
    public static final String  WIDGET_URI        = "_$_WIDGET_URI_$_";

    private Map<String, Object> widgetObjectParam = new HashMap<String, Object>();
    private String              uri;

    private HttpServletRequest  request;
    private HttpServletResponse response;
    private ApplicationContext  applicationContent;
    private ViewResolver        viewResolver      = null;

    public Widget(ApplicationContext applicationContent, HttpServletRequest request, HttpServletResponse response) {
        this.applicationContent = applicationContent;
        this.request = request;
        this.response = response;
    }

    // 应为在一个vm中，使用的是同一个widget对象,每次使用时,清楚参数
    public Widget setTemplate(String name) {
        this.uri = name;
        widgetObjectParam.clear();
        return this;
    }

    public Widget setParameter(String name, Object value) {
        return this.addParam(name, value);
    }

    /**
     * 添加参数
     * 
     * @param name 参数名称
     * @param value 参数值
     * @return
     */
    public Widget addParam(String name, Object value) {
        if (value == null) {
            return this;
        }
        this.widgetObjectParam.put(name, value);
        return this;
    }

    public String toString() {
        try {
            return this.buildContent(this.uri.toString());
        } catch (Exception e) {
            logger.warn("Widget toString:", e);
            return StringUtils.EMPTY;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String buildContent(String url) throws IOException, Exception {
        initContext();
        if (viewResolver == null) {
            logger.warn("fail to excute widget : " + url);
            return StringUtils.EMPTY;
        }
        try {
            request.setAttribute(IS_WIDGET, "true");
            request.setAttribute(WIDGET_URI, url);

            // widget中加入的widgetObjectParam，只能在当前widget中有效
            ModelAndView mv = new ModelAndView(buildViewName(url));
            Map model = mv.getModel();
            for (String key : this.widgetObjectParam.keySet()) {
                model.put(key, this.widgetObjectParam.get(key));
            }
            return doRender(request, response, mv);
        } catch (Exception e) {
            logger.error("fail to excute widget : " + url, e);
            throw e;
        }
    }

    private String buildViewName(String url) {
        if (StringUtils.startsWith(url, "/")) {
            url = url.substring(1, url.length());
        }
        return CustomVelocityLayoutView.DEFAULT_WIDGET_DIRECTORY + "/" + url;
    }

    private String doRender(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        View view = viewResolver.resolveViewName(mv.getViewName(), Locale.getDefault());
        WebResultResponseWrapper bufferdResponse = new WebResultResponseWrapper(response);
        view.render(mv.getModel(), request, bufferdResponse);
        // 获取渲染后的内容结果
        return bufferdResponse.getString();
    }

    private void initContext() throws Exception {
        if (viewResolver == null) {
            viewResolver = (ViewResolver) applicationContent.getBean(DispatcherServlet.VIEW_RESOLVER_BEAN_NAME);
        }
    }

    /**
     * @author zxc Jul 14, 2014 12:03:22 PM
     */
    private static class WebResultResponseWrapper extends HttpServletResponseWrapper {

        private StringWriter          sw;

        private ByteArrayOutputStream bos;

        private boolean               isWriterUsed;

        private boolean               isStreamUsed;

        private int                   status = 200;

        public WebResultResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        public PrintWriter getWriter() {
            if (isStreamUsed) {
                throw new IllegalStateException("Unexpected internal error during import: "
                                                + "Target servlet called getWriter(), then getOutputStream()");
            }
            isWriterUsed = true;
            if (sw == null) {
                sw = new StringWriter(2048);
            }
            return new PrintWriter(sw);
        }

        public ServletOutputStream getOutputStream() {
            if (isWriterUsed) {
                throw new IllegalStateException("Unexpected internal error during import: "
                                                + "Target servlet called getOutputStream(), then getWriter()");
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

        public void setContentType(String x) {
            // ignore
        }

        public void setLocale(Locale x) {
            // ignore
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
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
}
