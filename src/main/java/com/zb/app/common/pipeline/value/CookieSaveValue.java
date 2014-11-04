/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pipeline.value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.cookie.manager.CookieManagerLocator;
import com.zb.app.common.pipeline.PipelineMap;
import com.zb.app.common.pipeline.PipelineResult;

/**
 * 负责将Cookie写到Response中去
 * 
 * @author zxc Jul 15, 2014 5:08:14 PM
 */
public class CookieSaveValue extends AbstractPipelineValves {

    public PipelineResult invoke(HttpServletRequest request, HttpServletResponse response, PipelineMap map)
                                                                                                           throws Exception {
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        cookieManager.save();
        return null;
    }
}
