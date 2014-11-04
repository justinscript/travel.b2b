/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.component;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.zb.app.common.cons.ResultCode;
import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.cookie.manager.CookieManagerLocator;
import com.zb.app.web.tools.InvokeTypeTools;

/**
 * @author zxc Jul 14, 2014 4:22:06 PM
 */
public abstract class ComponentController {

    // request,response 不可随意使用
    protected HttpServletRequest  request;
    protected HttpServletResponse response;
    protected HttpSession         session;
    protected CookieManager       cookieManager;

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleIOException(Throwable e) throws Throwable {

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        if (request == null && response == null) {
            throw e;
        }

        if (request == null && response != null) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            OutputStream out = response.getOutputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
            pw.println("{\"code\":1,\"msg\":\"访问异常,服务器出现错误!\",\"data\":\"\"}");
            pw.flush();
            pw.close();
        }

        ModelAndView mav = new ModelAndView();
        if (InvokeTypeTools.isAjax(request)) {
            return createJsonMav("server exceptin or error", ResultCode.ERROR, e.getMessage());
        }

        mav.addObject("exception", e.getCause() == null ? StringUtils.EMPTY : e.getCause().toString());
        mav.addObject("msg", StringUtils.isEmpty(e.getMessage()) ? e.toString() : e.getMessage());
        mav.addObject("stackTrace", e.getStackTrace().toString());
        if (request.getRequestURI() != null) {
            mav.addObject("url", request.getRequestURI().toString());
        }
        mav.setViewName("error");
        return mav;
    }

    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.cookieManager = CookieManagerLocator.get(request, response);
    }

    protected ModelAndView createErrorJsonMav(String msg, Object object) {
        return createJsonMav(msg, ResultCode.ERROR, object);
    }

    protected ModelAndView createSuccessJsonMav(String msg, Object object) {
        return createJsonMav(msg, ResultCode.SUCCESS, object);
    }

    protected ModelAndView createJsonMav(String msg, ResultCode code) {
        return createJsonMav(msg, code, null);
    }

    protected ModelAndView createJsonMav(ResultCode code, Object object) {
        return createJsonMav(StringUtils.EMPTY, code, object);
    }

    protected ModelAndView createFileJsonMav(ResultCode code, String msg, String object) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new MappingJackson2JsonView());
        mav.addObject("error", code.value);
        mav.addObject("message", msg);
        mav.addObject("url", object == null ? StringUtils.EMPTY : object);
        return mav;
    }

    protected ModelAndView createJsonMav(String msg, ResultCode code, Object object) {
        ModelAndView mav = new ModelAndView();
        mav.setView(new MappingJackson2JsonView());
        mav.addObject("code", Integer.toString(code.value));
        mav.addObject("message", msg);
        mav.addObject("data", object == null ? StringUtils.EMPTY : object);
        return mav;
    }
}
