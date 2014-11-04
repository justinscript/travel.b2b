/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.server.wrapper;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.web.socket.WebSocketSession;

import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.web.webuser.ZuobianWebUser;
import com.zb.app.websocket.exception.WebSocketException;
import com.zb.app.websocket.server.core.IMessageCallback;
import com.zb.app.websocket.session.ISession;

/**
 * @author zxc Jul 25, 2014 2:30:21 PM
 */
public class SessionWrapper implements ISession<ZuobianWebUser, WebSocketSession>, Runnable, Serializable {

    private static final long  serialVersionUID = 2519409553790805959L;

    private ClientWrapper      clientWrapper;
    private long               registTime;
    private long               lastHeartbeatTime;                      // 最近一次心跳检查时间
    private WebSocketSession   socketSession;                          // 长连接

    private String             id;

    private SocketMessage<?>   socketMessage;
    private IMessageCallback[] callbacks;

    public SessionWrapper(String id) {
        this.id = id;
    }

    public SessionWrapper(ClientWrapper clientInfo, WebSocketSession socketSession) {
        if (clientInfo == null) {
            throw new WebSocketException("The WebSocketMessage is Error! ClientKey is null !");
        }
        this.clientWrapper = clientInfo;
        this.socketSession = socketSession;
        this.registTime = System.currentTimeMillis();
        this.lastHeartbeatTime = System.currentTimeMillis();
        this.id = socketSession.getId();
    }

    @Override
    public void run() {
        pushMsg2Client(socketMessage);
        socketMessage = null;
        if (Argument.isNotEmptyArray(callbacks)) {
            for (IMessageCallback callback : callbacks) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                callback.doAction(this);
            }
        }
    }

    private boolean pushMsg2Client(SocketMessage<?> socketMessage) {
        try {
            socketSession.sendMessage(socketMessage.getServerMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 服务端推送消息
     */
    @Override
    public boolean pushMsg(SocketMessage<?> socketMessage, IMessageCallback... callbacks) {
        this.socketMessage = socketMessage;
        this.callbacks = callbacks;
        new Thread(this).start();
        return true;
    }

    /**
     * 服务端拉取消息
     */
    @Override
    public synchronized SocketMessage<?> pullMsg(SocketMessage<?> socketMessage) {
        return null;
    }

    @Override
    public String getSessionId() {
        return socketSession.getId();
    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public boolean isClosing() {
        return false;
    }

    @Override
    public boolean isCurrentWebUser(ZuobianWebUser t) {
        return false;
    }

    @Override
    public ZuobianWebUser containsWebUser(ZuobianWebUser... t) {
        return null;
    }

    @Override
    public ZuobianWebUser getWebUser() {
        return clientWrapper.getWebUser();
    }

    @Override
    public Cookie[] getCookies() {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public WebSocketSession getSocketSession() {
        return socketSession;
    }

    @Override
    public long getRegistTime() {
        return registTime;
    }

    /**
     * 返回最近一次接收Server端发送回来的心态时间
     * 
     * @return
     */
    @Override
    public long getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public ClientWrapper getClientInfo() {
        return clientWrapper;
    }

    public void setRegistTime(long registTime) {
        this.registTime = registTime;
    }

    public void setClientInfo(ClientWrapper clientInfo) {
        this.clientWrapper = clientInfo;
    }

    public void setSocketSession(WebSocketSession socketSession) {
        this.socketSession = socketSession;
    }

    public void setLastHeartbeatTime(long lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }

    public ClientWrapper getClientWrapper() {
        return clientWrapper;
    }

    public void setClientWrapper(ClientWrapper clientWrapper) {
        this.clientWrapper = clientWrapper;
    }

    public SocketMessage<?> getSocketMessage() {
        return socketMessage;
    }

    public void setSocketMessage(SocketMessage<?> socketMessage) {
        this.socketMessage = socketMessage;
    }

    public IMessageCallback[] getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(IMessageCallback[] callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SessionWrapper other = (SessionWrapper) obj;
        if (!StringUtils.equalsIgnoreCase(this.id, other.id)) {
            return false;
        }
        // if (StringUtils.equalsIgnoreCase(this.id, other.id) && !this.clientWrapper.equals(other.clientWrapper)) {
        // return false;
        // }
        if (this == obj) {
            return true;
        }
        return true;
    }

    @Override
    public int hashCode() {
        Assert.assertNotNull(this.id);
        return HashCodeBuilder.reflectionHashCode(this.id);
    }
}
