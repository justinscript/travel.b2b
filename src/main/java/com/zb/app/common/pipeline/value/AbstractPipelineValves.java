/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pipeline.value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.zb.app.common.pipeline.Pipeline;
import com.zb.app.common.pipeline.PipelineMap;
import com.zb.app.common.pipeline.PipelineResult;

/**
 * @author zxc Jul 15, 2014 3:58:54 PM
 */
public class AbstractPipelineValves implements Pipeline {

    public void init(ApplicationContext context) {

    }

    public PipelineResult invoke(HttpServletRequest request, HttpServletResponse response, PipelineMap map)
                                                                                                           throws Exception {
        return null;
    }
}
