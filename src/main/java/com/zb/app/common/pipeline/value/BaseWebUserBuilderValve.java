/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pipeline.value;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.cookie.manager.CookieManagerLocator;
import com.zb.app.common.pipeline.PipelineMap;
import com.zb.app.common.pipeline.PipelineResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.util.HttpUtil;

/**
 * 公用WebUser构建逻辑
 * 
 * @author zxc Jul 8, 2014 10:03:10 AM
 */
public abstract class BaseWebUserBuilderValve<T extends BaseWebUser> extends AbstractPipelineValves {

    public static Logger        logger               = LoggerFactory.getLogger(BaseWebUserBuilderValve.class);

    private UrlPathHelper       urlPathHelper        = new UrlPathHelper();

    /**
     * ANT 风格的URL路径匹配。目前是使用Spring的AntPathMatcher，用空可以自己实现一个简单的。
     * 
     * <pre>
     * 通配符：
     *  ? 匹配任何单字符
     *  * 匹配0或者任意数量的字符
     *  ** 匹配0或者更多的目录
     *   最长匹配原则(has more characters)
     * </pre>
     */
    protected PathMatcher       pathMatcher          = new AntPathMatcher();

    @SuppressWarnings("unused")
    private String              noPermissionUrl      = "/nopermission.htm";
    protected String            loginUrl             = "/login.htm";
    protected String            homeUrl              = "/home.htm";
    private List<String>        noCheckUrlLikeList;
    private List<String>        noCheckUrlList;
    // 默认的最大登陆状态保存时间
    protected static final long MAX_LAST_ACCESS_TIME = 1000 * 3600;

    protected boolean           checkUrl;

    public PipelineResult invoke(HttpServletRequest request, HttpServletResponse response, PipelineMap map)
                                                                                                           throws Exception {
        // 保存当前请求的信息
        RequestDigger.saveRequestInfo(request);
        String uri = urlPathHelper.getLookupPathForRequest(request);
        // String uri = request.getRequestURI();
        // logger.info("<value>" + uri + "</value>");

        // 1. 构建WebUser对象
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        T webUser = createWebUser(request, cookieManager);

        // 2. 判断是否匿名访问
        boolean canAccessAnonymous = canAccessAnonymous(uri);
        if (!webUser.hasLogin()) {
            if (!canAccessAnonymous) {
                return getToLogin(request, response, uri);
            } else {
                judgeAccessTime(cookieManager);
                return null;
            }
        }

        // //////////////////////下面都是用户已经登陆的情况/////////////////////////////

        // 3. 判断访问时间(只针对已经登陆的情况,为了防止对login的重复调转，加上匿名访问的判断。注意把judgeAccessTime放前面，理面需要写最后一次访问时间）
        if (!judgeAccessTime(cookieManager) && !canAccessAnonymous) {
            return getToLogin(request, response, uri);
        }

        // 4. 判断URL权限(只针对已经登陆的情况）
        if (!canAccessAnonymous) {
            Result result = judgePermission(request, response, uri, webUser);
            if (result.isFailed()) {
                return PipelineResult.gotoAfterCompletion("gotoLogin",
                                                          (result.getData() == null ? null : (String) result.getData()));
            }
        }
        return null;
    }

    protected PipelineResult getToLogin(HttpServletRequest request, HttpServletResponse response, String uri)
                                                                                                             throws Exception {
        String url = loginUrl;
        // 现在参数不会传递，所以如果有参数的情况下就不追加returnurl了。
        if (request.getParameterMap().isEmpty() && !StringUtils.contains("/login", uri)) {
            url = loginUrl + "?returnurl=" + uri;
        }
        return PipelineResult.gotoAfterCompletion("gotoLogin", url);
    }

    /**
     * 判断一个URL是否可以匿名访问
     * 
     * @param uri
     * @return 如果可以匿名访问返回<code>true</code>否则返回<code>false</code>
     */
    private boolean canAccessAnonymous(String uri) {
        if (!checkUrl) {
            return true;
        }
        boolean needcheck = needcheck(uri);
        if (!needcheck) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前用户是否具有某个URL的访问权限。默认都是有权限的，子类需要覆写该方法以便实现符合自己网站业务逻辑的权限判断。
     * 
     * @return true 表示当前用户具有访问权限，否则返回false。
     * @throws IOException
     * @throws Exception
     */
    protected Result judgePermission(HttpServletRequest request, HttpServletResponse response, String uri, T webUser)
                                                                                                                     throws Exception {
        return Result.success();
    }

    /**
     * 判断当前用户的不活跃时间。默认都是返回true，子类需要按照自己的业务覆写。(只针对已经登陆的情况）
     * 
     * @return true 表示当前用户具有访问权限，否则返回false。
     */
    protected boolean judgeAccessTime(CookieManager cookieManager) {
        return true;
    }

    /**
     * 所有的自己必须按照自己的业务从Cookie或者存储中获取当前用户的信息。并且注意需要把构建的WebUser放到当前的线程缓存中(只针对已经登陆的情况）
     * 
     * @param request
     */
    protected abstract T createWebUser(HttpServletRequest request, CookieManager cookieManager);

    protected boolean needcheck(String uri) {
        if (noCheckUrlList != null && noCheckUrlList.contains(uri)) {
            return false;
        }
        if (noCheckUrlLikeList != null) {
            for (String s : noCheckUrlLikeList) {
                if (this.pathMatcher.match(s, uri)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected static long parserLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void setNoPermissionUrl(String noPermissionUrl) {
        this.noPermissionUrl = noPermissionUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setNoCheckUrlLikeList(List<String> noCheckUrlLikeList) {
        this.noCheckUrlLikeList = noCheckUrlLikeList;
    }

    public boolean isCheckUrl() {
        return checkUrl;
    }

    public void setCheckUrl(boolean checkUrl) {
        this.checkUrl = checkUrl;
    }

    public List<String> getNoCheckUrlList() {
        return noCheckUrlList;
    }

    public void setNoCheckUrlList(List<String> noCheckUrlList) {
        this.noCheckUrlList = noCheckUrlList;
    }

    /**
     * 获取当前请求的详细信息，一边出异常时使用
     * 
     * @author zxc Jul 8, 2014 10:03:40 AM
     */
    public static class RequestDigger {

        @SuppressWarnings("rawtypes")
        public static StringBuilder saveRequestInfo(HttpServletRequest request) {
            // 获取header
            Enumeration headerNames = request.getHeaderNames();
            StringBuilder sb = new StringBuilder();
            while (headerNames.hasMoreElements()) {
                Object object = (Object) headerNames.nextElement();
                sb.append((String) object).append(":").append(request.getHeader((String) object)).append("\r\n");
            }
            // 获取IP，可能有代理地址在header中
            sb.append("remoteAddr:").append(HttpUtil.getIpAddr(request)).append("\r\n");
            // 获取当前请求的路径以及参数
            sb.append("requestURI:").append(request.getRequestURI()).append("\r\n");
            Map parameterMap = request.getParameterMap();
            for (Object key : parameterMap.keySet()) {
                sb.append(key.toString()).append(":").append(request.getParameter(key.toString())).append("\r\n");
            }
            // 保存到当前线程上下文中去
            RequestInfo.set(sb.toString());
            return sb;
        }

        public static String getSavedRequestInfo() {
            return RequestInfo.get();
        }
    }

    /**
     * 封装请求信息
     * 
     * @author zxc Jul 8, 2014 10:03:30 AM
     */
    public static class RequestInfo {

        private static ThreadLocal<String> cache = new ThreadLocal<String>() {

                                                     protected String initialValue() {
                                                         return "unKnow";
                                                     }
                                                 };

        public static String get() {
            return cache.get();
        }

        public static void set(String info) {
            cache.set(info);
        }
    }
}
