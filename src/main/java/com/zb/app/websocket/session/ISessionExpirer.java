/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.session;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author zxc Jul 24, 2014 4:43:10 PM
 */
public interface ISessionExpirer {

    void expire(WebSocketSession session);

    long getServerId();
}
