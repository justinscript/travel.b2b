/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.comset.filter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zb.app.common.util.StringFormatter;
import com.zb.app.comset.info.LeafInfo;
import com.zb.app.comset.manager.ComsetThreadContextCache;
import com.zb.app.comset.manager.ComsetThreadLocal;
import com.zb.app.comset.manager.ResourceHelper;

/**
 * 开启过滤器 设置JAVA_OPTS:-Dcomset.reoced.urltime.flag=true
 * 
 * @author zxc Jul 23, 2014 11:49:20 AM
 */
public class ResourceFilter implements Filter {

    @SuppressWarnings("unused")
    private boolean check;

    public void init(FilterConfig arg0) throws ServletException {
        final Runnable updateThread = new Runnable() {

            public void run() {
                ResourceHelper.clear();
            }
        };
        final ScheduledExecutorService updateScheduler = Executors.newScheduledThreadPool(1);
        // 30分钟清除一次数据
        int period = 30;
        updateScheduler.scheduleAtFixedRate(updateThread, 1, period, TimeUnit.MINUTES);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException,
                                                                                          ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        uri = StringFormatter.decode(uri);
        if (uri.contains("/comset/show.htm")) {
            fc.doFilter(request, response);
            return;
        }
        if (!ResourceHelper.isTrace() || !uri.endsWith(".htm")) {
            fc.doFilter(request, response);
            return;
        }
        ComsetThreadContextCache.clean();
        ComsetThreadLocal.clean();
        long threadId = Thread.currentThread().getId();
        try {
            LeafInfo rootInfo = ResourceHelper.getRootLeafInfo(threadId);
            rootInfo.setName(uri);
            long start = System.currentTimeMillis();
            fc.doFilter(request, response);
            // 统计执行时间的问题（有异常的请求就不统计了)
            long period = (System.currentTimeMillis() - start);
            rootInfo.addRunTime(1, period);
            ResourceHelper.complete(rootInfo);
        } finally {
            // 最后整理该线程的数据
            // ResourceTools.complete(threadId,uri);
            // 执行一次清空操作
            ComsetThreadContextCache.clean();
            ComsetThreadLocal.clean();
        }

    }

    public void destroy() {

    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
