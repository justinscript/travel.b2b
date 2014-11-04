/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.server.core;

import com.zb.app.websocket.server.wrapper.SessionWrapper;

/**
 * @author zxc Jul 31, 2014 3:31:35 PM
 */
public interface IMessageCallback {

    void doAction(SessionWrapper sw);
}
