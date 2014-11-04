/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.zb.app.common.cons.ResultCode;
import com.zb.app.common.interceptor.WebSocketHandshakeInterceptor;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.websocket.cons.ActionStatusEnum;

/**
 * @author zxc Jul 3, 2014 11:26:33 PM
 */
@Service
@EnableWebSocket
public class WebsocketEndPointHandler extends TextWebSocketHandler {

    private static Logger          logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    @Autowired
    private WebSocketServerHandler sessionManager;

    /**
     * 接收客户端消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sessionManager.pullTextMessage(session, message);
        try {
            String json = JsonResultUtils.createJsonResult(ResultCode.SUCCESS, ActionStatusEnum.RECEIVE_SUCCESS.name,
                                                           "接收成功");
            session.sendMessage(new TextMessage(json));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 双工通讯连接后,在这里心跳
     * 
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionManager.sessionOpened(session);

        String json;
        try {
            json = JsonResultUtils.createJsonResult(ResultCode.SUCCESS, ActionStatusEnum.CONNECTION_SUCCESS.name,
                                                    "连接成功");
        } catch (Exception e) {
            json = JsonResultUtils.createJsonResult(ResultCode.ERROR, ActionStatusEnum.CONNECTION_ERROR.name, "连接失败");
            logger.error(e.getMessage());
        }
        session.sendMessage(new TextMessage(json));
    }

    /**
     * 客户端异常断开(所谓异常断开，例如：突然关闭HTML页面等等，总之不是用户正常关闭的)
     * 
     * @param session
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        String sessionId = session.getId();
        String reason = throwable == null ? "异常" : throwable.getMessage() == null ? "异常" : throwable.getMessage();
        sessionManager.exceptionCaught(session, throwable);
        logger.error("sessionId: {} 异常断开连接,关闭码: 0,缘由: {}", sessionId, reason);
    }

    /**
     * 连接已经断开(只要是断开连接！不管是异常断开，还是普通正常断开，一定会进入这个方法)
     * 
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessionManager.sessionClosed(session);
        String reason = status.getReason();
        if (reason == null) {
            reason = "客户端按指令正常退出";
        }
        logger.error("sessionId: {} 已经主动关闭连接,关闭码: {},缘由: {}", sessionId, status.getCode(), reason);
    }

    /**
     * 握手成功初始化操作在这里面进行
     * 
     * <pre>
     *      一旦HTTP认证成功 这个方法先被调用 
     *          如果返回true 则进行上面那么N多方法的流程。
     *          如果返回的是false 就直接拦截掉了,不会调用上面那些方法了
     * </pre>
     * 
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        logger.debug("WebsocketEndPoint supportsPartialMessages,return new WebSocketSession");
        return true;
    }
}
