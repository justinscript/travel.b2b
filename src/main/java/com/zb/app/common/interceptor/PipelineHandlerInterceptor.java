/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zb.app.common.core.WebResponseWrapper;
import com.zb.app.common.pipeline.PipelineHandler;
import com.zb.app.common.pipeline.PipelineMap;
import com.zb.app.common.pipeline.PipelineResult;
import com.zb.app.common.pipeline.PipelineType;
import com.zb.app.common.pipeline.PipelineValvesMapper;

/**
 * @author zxc Jul 14, 2014 11:09:13 PM
 */
public class PipelineHandlerInterceptor extends HandlerInterceptorAdapter {

    public static Logger               logger = LoggerFactory.getLogger(PipelineHandlerInterceptor.class);

    private static final String        INDEX  = "/index.htm";

    @Resource(name = "preHandlePipelineValves")
    private List<PipelineValvesMapper> preHandlePipelineValves;

    @Resource(name = "afterCompletionPipelineValves")
    private List<PipelineValvesMapper> afterCompletionPipelineValves;

    @Autowired
    private PipelineHandler            pipelineHandler;

    public void init() {
        logger.debug("LoginAnnotationInterceptor init()");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        WebResponseWrapper webResponseWrapper = new WebResponseWrapper(response);
        PipelineMap map = new PipelineMap();
        PipelineResult result = pipelineHandler.doPreHandlePipelineValues(request, webResponseWrapper, map,
                                                                          preHandlePipelineValves);
        if (result == null || result.getType() != PipelineType.PIPELINE_AFTERCOMPLETION) {
            return true;
        }
        String redirecUrl = (result == null) ? INDEX : result.getRedirectUrl();
        pipelineHandler.doAfterCompletionPipelineValues(request, webResponseWrapper, map, result,
                                                        afterCompletionPipelineValves);

        /**
         * 截住所有response的输出,把流的输出放在最后处理<br>
         * doAfterCompletionPipelineValues的valve中有对cookie的处理,如果cookie处理放在流输出后面，会丢失cookie<br>
         */
        if (webResponseWrapper.isStreamUseed()) {
            response.getOutputStream().write(webResponseWrapper.getByte());
        } else {
            response.getWriter().write(webResponseWrapper.getString());
        }
        // 如果返回的条件中需要页面重定向，那么就执行此操作
        if (redirecUrl != null) {
            response.sendRedirect(redirecUrl);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        pipelineHandler.doAfterCompletionPipelineValues(request, new WebResponseWrapper(response), new PipelineMap(),
                                                        null, afterCompletionPipelineValves);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                                                                                                                       throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
                                                                                                                        throws Exception {
        pipelineHandler.doAfterCompletionPipelineValues(request, new WebResponseWrapper(response), new PipelineMap(),
                                                        null, afterCompletionPipelineValves);
    }
}
