/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zb.app.biz.cons.MemberTypeEnum;
import com.zb.app.common.authority.AuthorityHelper;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.authority.ResultTypeEnum;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.webuser.ZuobianWebUser;

/**
 * @author zxc Jul 14, 2014 11:05:42 PM
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

    public static Logger logger = LoggerFactory.getLogger(AuthorityAnnotationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request == null || response == null || handler == null) {
            return false;
        }
        HandlerMethod hm = (HandlerMethod) handler;
        AuthorityPolicy authority = hm.getMethodAnnotation(AuthorityPolicy.class);

        if (null == authority) {// 没有声明权限,放行
            return true;
        }

        ZuobianWebUser user = WebUserTools.current();
        if (user != null && MemberTypeEnum.isSupper(user.getmType())) {
            return true;
        }

        boolean aflag = false;
        for (Right at : authority.authorityTypes()) {
            if (AuthorityHelper.hasAuthority(at.getIndex(), user.getRole()) == true) {
                aflag = true;
                break;
            }
        }

        if (false == aflag) {
            if (authority.resultType() == ResultTypeEnum.page) {
                response.sendRedirect("/home.htm?returnurl=/nopermission.htm");
            } else if (authority.resultType() == ResultTypeEnum.json) {
                String needLogin = JsonResultUtils.getNeedLoginJson();
                response.getOutputStream().write(needLogin.getBytes("utf-8"));
                return false;
            }
            return false;
        }
        return true;
    }
}
