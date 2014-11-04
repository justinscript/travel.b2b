/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.velocity;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

/**
 * @author zxc Jul 14, 2014 11:52:36 AM
 */
public class CustomVelocityLayoutViewResolver extends VelocityLayoutViewResolver {

    public static final String FORWARD_URL_PREFIX  = "forward:";
    public static final String REDIRECT_URL_PREFIX = "redirect:";

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Class requiredViewClass() {
        return CustomVelocityLayoutView.class;
    }

    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        CustomVelocityLayoutView view = (CustomVelocityLayoutView) super.buildView(viewName);
        view.setSuffix(getSuffix());
        return view;
    }

    /**
     * 处理forward情况<br>
     * springMVC的forward时会把model中的数据放入request的Attribute中,<br>
     * velocity页面渲染取值时先取attribute中值,后取model值
     * 
     * @see org.springframework.web.servlet.view.UrlBasedViewResolver#createView(java.lang.String, java.util.Locale)
     */
    protected View createView(String viewName, Locale locale) throws Exception {
        // If this resolver is not supposed to handle the given view,
        // return null to pass on to the next resolver in the chain.
        if (!canHandle(viewName, locale)) {
            return null;
        }
        // Check for special "redirect:" prefix.
        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX.length());
            return new RedirectView(redirectUrl, isRedirectContextRelative(), isRedirectHttp10Compatible());
        }
        // Check for special "forward:" prefix.
        if (viewName.startsWith(FORWARD_URL_PREFIX)) {
            String forwardUrl = viewName.substring(FORWARD_URL_PREFIX.length());
            return new ForwardView(forwardUrl);
        }
        // Else fall back to superclass implementation: calling loadView.
        return super.createView(viewName, locale);
    }

    /**
     * @author zxc Jul 14, 2014 11:54:54 AM
     */
    @SuppressWarnings("rawtypes")
    public class ForwardView extends InternalResourceView {

        public ForwardView(String forwardUrl) {
            super(forwardUrl);
        }

        /**
         * 覆盖父类方法,不需要把model中的所有属性放入request的setAttribute中
         */
        protected void exposeModelAsRequestAttributes(Map model, HttpServletRequest request) throws Exception {

        }
    }
}
