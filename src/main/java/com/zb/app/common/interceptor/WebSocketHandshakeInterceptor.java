/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author zxc Jul 3, 2014 11:19:02 PM
 */
@Service
@EnableWebSocket
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    public static Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    /**
     * 握手前
     * 
     * @param request
     * @param response
     * @param webSocketHandler
     * @param stringObjectMap
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.debug("#######################################Zuobian Before WebSocket Handshake############################################");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    /**
     * 握手成功后
     * 
     * @param request
     * @param response
     * @param handler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        logger.debug("#######################################Zuobian After WebSocket Handshake###############################################");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
