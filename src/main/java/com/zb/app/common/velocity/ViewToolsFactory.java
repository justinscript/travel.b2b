/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.velocity;

import java.util.Map;

/**
 * @author zxc Jul 14, 2014 12:19:00 PM
 */
public class ViewToolsFactory {

    private Map<String, Object> viewTools;

    public Map<String, Object> getViewTools() {
        return viewTools;
    }

    public void setViewTools(Map<String, Object> viewTools) {
        this.viewTools = viewTools;
    }
}
