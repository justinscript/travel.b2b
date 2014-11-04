/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.server.core;

/**
 * @author zxc Jul 25, 2014 2:26:16 PM
 */
public interface ISocketCallback {

    /**
     * 当WebSocketServer端有操作时会通知外面。例如：日志更新会通知
     * 
     * @param obj
     * @return
     */
    Object notifyAction(Object... obj);
}
