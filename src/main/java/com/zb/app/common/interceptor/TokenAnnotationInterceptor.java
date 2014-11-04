/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zb.app.common.authority.TokenPolicy;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.security.UUIDGenerator;

/**
 * 配置Token拦截器，防止用户重复提交数据
 * 
 * @author zxc Jul 2, 2014 3:49:45 PM
 */
@Component
public class TokenAnnotationInterceptor extends HandlerInterceptorAdapter {

    public static Logger  logger = LoggerFactory.getLogger(TokenAnnotationInterceptor.class);

    private static String TOKEN  = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            TokenPolicy annotation = method.getAnnotation(TokenPolicy.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    request.getSession().setAttribute(TOKEN, UUIDGenerator.createUUID());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        String needLogin = JsonResultUtils.getSubmitedJson(annotation.data());
                        response.getOutputStream().write(needLogin.getBytes("utf-8"));
                        return false;
                    }
                    // request.getSession(false).removeAttribute("token");
                    request.getSession().removeAttribute(TOKEN);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        // String serverToken = (String) request.getSession(false).getAttribute(TOKEN);
        String serverToken = (String) request.getSession().getAttribute(TOKEN);
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter(TOKEN);
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}
