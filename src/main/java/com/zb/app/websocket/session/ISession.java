/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.session;

import java.io.Serializable;

import javax.servlet.http.Cookie;

import org.springframework.web.socket.WebSocketSession;

import com.zb.app.common.pipeline.value.BaseWebUser;
import com.zb.app.websocket.server.core.IMessageCallback;
import com.zb.app.websocket.server.wrapper.SocketMessage;

/**
 * @author zxc Jul 24, 2014 4:41:32 PM
 */
@SuppressWarnings("unchecked")
public interface ISession<T extends BaseWebUser & Serializable, S extends WebSocketSession> {

    String getSessionId();

    int getTimeout();

    long getRegistTime();

    long getLastHeartbeatTime();

    boolean isClosing();

    boolean isCurrentWebUser(T t);

    T containsWebUser(T... t);

    T getWebUser();

    Cookie[] getCookies();

    S getSocketSession();

    // 服务端推送消息
    boolean pushMsg(SocketMessage<?> socketMessage, IMessageCallback... callbacks);

    // 服务端拉取消息
    SocketMessage<?> pullMsg(SocketMessage<?> socketMessage);
}
