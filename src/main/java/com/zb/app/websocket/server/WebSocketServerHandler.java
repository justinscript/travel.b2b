/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.zb.app.common.cache.ConcurrentHashSet;
import com.zb.app.common.cons.ResultCode;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.utilities.CoreUtilities;
import com.zb.app.common.notify.NotifyService;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.util.DateViewTools;
import com.zb.app.web.webuser.ZuobianWebUser;
import com.zb.app.websocket.api.IMessageHandler;
import com.zb.app.websocket.api.MessageEvent;
import com.zb.app.websocket.api.MessageMapper;
import com.zb.app.websocket.cons.ActionEnum;
import com.zb.app.websocket.exception.WebSocketUnSupportException;
import com.zb.app.websocket.server.core.IMessageCallback;
import com.zb.app.websocket.server.core.ISocketCallback;
import com.zb.app.websocket.server.wrapper.ClientWrapper;
import com.zb.app.websocket.server.wrapper.SessionWrapper;
import com.zb.app.websocket.server.wrapper.SocketMessage;

/**
 * @author zxc Jul 24, 2014 4:02:38 PM
 */
@SuppressWarnings("unused")
@Service
public class WebSocketServerHandler {

    @Autowired
    private NotifyService                                  notifyService;

    private static Logger                                  logger                = LoggerFactory.getLogger(WebSocketServerHandler.class);

    // 用户session缓存,保持每个Client过来的连接
    private static Map<ClientWrapper, Set<SessionWrapper>> sessionCacheMap       = new ConcurrentHashMap<ClientWrapper, Set<SessionWrapper>>();

    // 当SocketServer端有操作时会通知外面。例如：日志更新会通知
    private ISocketCallback                                callBack;

    // 定时器,监控任务
    private Timer                                          timer;
    // 同步监视锁
    private final Object                                   lock                  = new Object();
    // 定时器设置
    private final static int                               TIMER_START_MILLIS    = 10;
    private final static int                               TIMER_INTERVAL_MILLIS = 100 * 1000;

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 初始化
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    private WebSocketServerHandler() {

    }

    public static WebSocketServerHandler getInstance() {
        return (WebSocketServerHandler) SpringContextAware.getBean("webSocketServerHandler");
    }

    @PostConstruct
    public void init() {
        // 创建并启动定时器
        if (timer != null) {
            stop();
        }
        timer = new Timer(false);
        timer.schedule(new CheckTimerTask(), TIMER_START_MILLIS, TIMER_INTERVAL_MILLIS);
        logger.info("CheckTimerTask started; interval={} ms", TIMER_INTERVAL_MILLIS);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        sessionCacheMap.clear();
        logger.info("CheckTimerTask stopped,and sessionCacheMap has clear!");
    }

    /**
     * apply 调用方法接口
     * 
     * @author zxc
     */
    public interface ApplyMethod {

        public void invoke(ClientWrapper cw, SessionWrapper sw);
    }

    /**
     * 检查Session是否有效
     * 
     * @author zxc
     */
    class CheckTimerTask extends TimerTask implements ApplyMethod {

        private long lastRun = System.currentTimeMillis();
        private long delta;

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            delta = now - lastRun;
            lastRun = now;
            apply(this);
        }

        @Override
        public void invoke(ClientWrapper cw, SessionWrapper sw) {
            clientHeartbeat(new SocketMessage<Object>(cw), sw.getSocketSession());
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 异步事件支持
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    public void notifyNewClientMessageEvent(final ZuobianWebUser webUser, final MessageMapper<?, ?> mm) {
        notifyService.notify(new MessageEvent(webUser, EventType.newClientMessage, mm));
    }

    public void notifyNewClientConnectEvent(final ZuobianWebUser webUser, final MessageMapper<?, ?> mm) {
        notifyService.notify(new MessageEvent(webUser, EventType.newClientConnect, mm));
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 推送API
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    /**
     * session打开时调用,保存当前回话
     */
    public void sessionOpened(WebSocketSession session) {
        log(session.getRemoteAddress() + " session打开时调用,保存当前回话");
        addSession(session);
    }

    /**
     * session打开时调用,更新当前回话
     */
    public void sessionUpdate(WebSocketSession session) {
        log(session.getRemoteAddress() + " session打开时调用,更新当前回话");
        addSession(session);
    }

    /**
     * 返回所有已经在Server端注册的Client SessionInfo
     * 
     * @return
     */
    public Collection<SessionWrapper> getAllSessionInfo() {
        Collection<SessionWrapper> wsList = new ArrayList<SessionWrapper>();
        for (Set<SessionWrapper> sessionInfoList : sessionCacheMap.values()) {
            wsList.addAll(sessionInfoList);
        }
        return wsList;
    }

    /**
     * 返回所有在线的WebSocketSession
     * 
     * @return
     */
    public Collection<WebSocketSession> getAllSocketSession() {
        Collection<WebSocketSession> wsList = new ArrayList<WebSocketSession>();
        for (Set<SessionWrapper> sessionInfoList : sessionCacheMap.values()) {
            for (SessionWrapper sw : sessionInfoList) {
                wsList.add(sw.getSocketSession());
            }
        }
        return wsList;
    }

    /**
     * sessionCacheMap长度,缓存大小,当前在线人数统计
     * 
     * @return 统计
     */
    public int getSessionCacheSize() {
        return sessionCacheMap.size();
    }

    /**
     * session打开时调用,服务端从客户端拉取消息,客户端向服务端推送消息
     */
    public void pullTextMessage(WebSocketSession session, TextMessage message) {
        log(session.getRemoteAddress() + " session打开时调用,服务端从客户端拉取消息,客户端向服务端推送消息!");
        SocketMessage<String> socketMessage = new SocketMessage<String>(getClienInfo(session), message);
        ActionEnum action = socketMessage.getAction();
        switch (action) {
            case CLIENT_REGIST:
                SessionWrapper _sw = getSessionInfo(session);
                addSession(session);
                notifyNewClientConnectEvent(_sw.getWebUser(),
                                            new MessageMapper<SocketMessage<?>, IMessageHandler>(socketMessage));
                break;

            case CLIENT_CLOSED:
                sessionClosed(session);
                break;

            case CLIENT_RETURN_RESULT:
                SessionWrapper sessionWrapper = getSessionInfo(session);
                sessionWrapper.setSocketMessage(socketMessage);
                break;

            case CLIENT_SEND_MESSAGE:
                SessionWrapper sw = getSessionInfo(session);
                sw.pullMsg(socketMessage);
                notifyNewClientMessageEvent(sw.getWebUser(),
                                            new MessageMapper<SocketMessage<?>, IMessageHandler>(socketMessage));
                break;

            case CLIENT_HEARTBEAT:
                clientHeartbeat(socketMessage, session);
                break;

            case ERROR_FORMAT_MESSAGE:
                handleDataFormatError(socketMessage, session);
                break;

            default:
                throw new WebSocketUnSupportException("不支持的类型");
        }
    }

    /**
     * session打开时调用,服务端向客户端推送消息,客户端从服务端拉取消息
     */
    public void pushTextMessage(SessionWrapper sessionWrapper, final SocketMessage<String> socketMessage) {
        WebSocketSession session = sessionWrapper.getSocketSession();
        log(session.getRemoteAddress() + " session打开时调用,服务端向客户端推送消息,客户端从服务端拉取消息");
        ActionEnum action = socketMessage.getAction();
        switch (action) {
            case SERVER_SEND_MESSAGE:
                sessionWrapper.pushMsg(socketMessage, new IMessageCallback() {

                    @Override
                    public void doAction(SessionWrapper sw) {
                        sw.pullMsg(socketMessage);
                        notifyNewClientMessageEvent(sw.getWebUser(),
                                                    new MessageMapper<SocketMessage<?>, IMessageHandler>(socketMessage));
                    }
                });
                break;

            case SERVER_CLOSED:
                sessionClosed(session);
                break;

            case SERVER_HEARTBEAT:
                clientHeartbeat(socketMessage, session);
                break;

            case ERROR_FORMAT_MESSAGE:
                break;

            default:
                throw new WebSocketUnSupportException("不支持的类型");
        }
    }

    /**
     * session连接关时才调用
     */
    public void sessionClosed(WebSocketSession session) {
        if (session != null && !session.isOpen()) {
            logger.debug("sessionClosed session is closed!by auto sessionClosed");
            removeSession(session);
            return;
        }
        if (session != null && session.isOpen()) {
            removeSession(session);
        }
    }

    /**
     * 如果出异常,就关闭session
     */
    public void exceptionCaught(WebSocketSession session, Throwable cause) {
        cause.printStackTrace();
        log(CoreUtilities.getExceptionText(cause));
        sessionClosed(session);
    }

    /**
     * 处理数据格式异常
     * 
     * @param receiverMessage
     * @param session
     */
    public void handleDataFormatError(SocketMessage<?> receiverMessage, WebSocketSession session) {
        ClientWrapper cw = receiverMessage.getClientInfo();
        SessionWrapper sessionWrapper = getSessionInfo(session);
        if (sessionWrapper != null) {
            sessionWrapper.setLastHeartbeatTime(System.currentTimeMillis());
        }
        SocketMessage<String> sm = new SocketMessage<String>(cw);
        sm.setAction(ActionEnum.ERROR_FORMAT_MESSAGE);
        sm.setData(JsonResultUtils.createJsonResult(ResultCode.ERROR_FORMAT, "客户端向服务器发送的数据格式错误"));
        sessionWrapper.pushMsg(sm);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 推送服务器实现
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    /**
     * 添加Session缓存,初始化
     */
    public void addSession(WebSocketSession session) {
        ClientWrapper clientInfo = new ClientWrapper(session);
        if (Argument.isNotPositive(clientInfo.getmId()) || !clientInfo.getWebUser().hasLogin()) {// 未登录处理
            try {
                sessionClosed(session);
            } catch (Exception e) {
                logger.debug("addSession session cookie invalid,no find webUser mId or member no login! Unregistered visitors!");
            }
            return;
        }
        Long mId = clientInfo.getmId();
        if (!sessionCacheMap.containsKey(clientInfo)) {
            Set<SessionWrapper> set = new ConcurrentHashSet<SessionWrapper>();
            set.add(new SessionWrapper(clientInfo, session));
            sessionCacheMap.put(clientInfo, set);

            logger.info("id是：{}的用户连接WebSocket服务器，ip是：{},mId:{},nick:{}", session.getId(), clientInfo.getIp(), mId,
                        clientInfo.getWebUser().getUserName());

        } else {
            Set<SessionWrapper> set = sessionCacheMap.get(clientInfo);
            if (Argument.isEmpty(set)) {
                set = new ConcurrentHashSet<SessionWrapper>();
                set.add(new SessionWrapper(clientInfo, session));
                sessionCacheMap.put(clientInfo, set);
            }
            if (!set.contains(new SessionWrapper(clientInfo, session))) {
                set.add(new SessionWrapper(clientInfo, session));
            }

            logger.info("SessionManager update Session:{},IP:{},mId:{}", session.getId(), clientInfo.getIp(), mId);
        }
        notifyNewClientConnectEvent(clientInfo.getWebUser(), null);
    }

    /**
     * 指定mid对应ClientWrapper是否存在
     * 
     * @param mid 指定Session mid
     * @return true/false
     */
    public boolean hasClientInfo(Long mId) {
        return sessionCacheMap.containsKey(new ClientWrapper(mId));
    }

    /**
     * 根据用户mId获得当前回话client端信息
     * 
     * @param mId
     * @return
     */
    public ClientWrapper getClienInfo(Long mId) {
        if (hasClientInfo(mId)) {
            Set<ClientWrapper> set = sessionCacheMap.keySet();
            for (ClientWrapper cw : set) {
                if (cw != null && cw.equals(new ClientWrapper(mId))) {
                    return cw;
                }
            }
        }
        return null;
    }

    /**
     * 根据WebSocketSession获得当前回话client端信息
     * 
     * @param mId
     * @return
     */
    public ClientWrapper getClienInfo(WebSocketSession session) {
        Collection<SessionWrapper> registSessionWrappers = getAllSessionInfo();
        if (Argument.isEmpty(registSessionWrappers)) {
            return null;
        }
        for (SessionWrapper sw : registSessionWrappers) {
            if (sw != null && StringUtils.equalsIgnoreCase(session.getId(), sw.getId())) {
                return sw.getClientInfo();
            }
        }
        return null;
    }

    /**
     * 获得当前回话server端信息
     * 
     * @param mId
     * @return
     */
    public Set<SessionWrapper> getSessionInfo(Long mId) {
        return hasClientInfo(mId) ? sessionCacheMap.get(getClienInfo(mId)) : Collections.<SessionWrapper> emptySet();
    }

    /**
     * 根据用户mId获得当前回话WebSocketSession
     * 
     * @param mId
     * @return Session
     */
    public Collection<WebSocketSession> getSession(Long mId) {
        Set<SessionWrapper> set = getSessionInfo(mId);
        Collection<WebSocketSession> wsList = new ArrayList<WebSocketSession>();
        for (SessionWrapper sw : set) {
            wsList.add(sw.getSocketSession());
        }
        return wsList;
    }

    /**
     * 根据WebSocketSession获得当前回话client端信息
     * 
     * @param mId
     * @return
     */
    public SessionWrapper getSessionInfo(WebSocketSession session) {
        Collection<SessionWrapper> registSessionWrappers = getAllSessionInfo();
        if (Argument.isEmpty(registSessionWrappers)) {
            return null;
        }
        for (SessionWrapper sw : registSessionWrappers) {
            if (sw != null && StringUtils.equalsIgnoreCase(session.getId(), sw.getId())) {
                return sw;
            }
        }
        return null;
    }

    /**
     * 获得当前用户所有server端信息
     * 
     * @param mId
     * @return
     */
    public Set<SessionWrapper> getSessionInfoSet(WebSocketSession session) {
        SessionWrapper sw = getSessionInfo(session);
        if (sw != null && sw.getClientWrapper() != null && !Argument.isNotPositive(sw.getClientWrapper().getmId())) {
            return sessionCacheMap.get(new ClientWrapper(sw.getClientWrapper().getmId()));
        }
        return Collections.<SessionWrapper> emptySet();
    }

    /**
     * 根据mId,移除Session,并关闭与client通信
     * 
     * @param mId
     */
    public void removeSession(Long mId) {
        Set<SessionWrapper> set = getSessionInfo(mId);
        if (Argument.isEmpty(set)) {
            return;
        }
        for (SessionWrapper sessionWrapper : set) {
            ClientWrapper clientInfo = sessionWrapper.getClientInfo();
            String sessionId = sessionWrapper.getId();
            String ip = clientInfo.getIp();
            try {
                sessionWrapper.getSocketSession().close(CloseStatus.NORMAL);
                logger.error("WebSocketServerHandler removeSession() successfull! mid={},sessionId={},ip={}", mId,
                             sessionId, ip);
            } catch (Exception e) {
                logger.error("WebSocketServerHandler removeSession() faild! mid={},sessionId={},ip={}", mId, sessionId,
                             ip);
            } finally {
                sessionCacheMap.remove(clientInfo);
            }
        }
    }

    /**
     * 根据WebSocketSession,移除并关闭Session
     * 
     * @param session Session
     */
    public void removeSession(WebSocketSession session) {
        SessionWrapper sw = getSessionInfo(session);
        if (sw == null || StringUtils.isEmpty(sw.getId())) {
            return;
        }
        ClientWrapper clientInfo = sw.getClientInfo();
        String sessionId = sw.getId();
        String ip = clientInfo.getIp();
        Long mId = clientInfo.getmId();
        try {
            session.close(CloseStatus.NORMAL);
            logger.error("WebSocketServerHandler removeSession() successfull! mid={},sessionId={},ip={}", mId,
                         sessionId, ip);
        } catch (Exception e) {
            if (session != null && !session.isOpen()) {
                logger.error("WebSocketServerHandler removeSession() is Normally closed!  mid={},sessionId={},ip={}",
                             mId, sessionId, ip);
            } else {
                logger.error("WebSocketServerHandler removeSession() faild!  mid={},sessionId={},ip={}", mId,
                             sessionId, ip);
            }
        } finally {
            Set<SessionWrapper> set = sessionCacheMap.get(clientInfo);
            set.remove(sw);
            if (Argument.isEmpty(set)) {
                sessionCacheMap.remove(clientInfo);
            }
        }
    }

    /**
     * 处理客户端发送过来检查心跳的消息
     * 
     * @param receiverMessage
     * @param session
     */
    public void clientHeartbeat(SocketMessage<?> receiverMessage, WebSocketSession session) {
        ClientWrapper cw = receiverMessage.getClientInfo();
        SessionWrapper sw = getSessionInfo(session);
        String json = JsonResultUtils.createJsonResult(ResultCode.HEARTBEAT, "$zuobian$",
                                                       "Server端心跳检测 zxc,hello world!");
        if (session == null) {
            return;
        }
        if (session != null && !session.isOpen()) {
            removeSession(session);
        }
        try {
            session.sendMessage(new TextMessage(json));
            if (sw != null) {
                sw.setLastHeartbeatTime(System.currentTimeMillis());
            }
            logger.debug("clientHeartbeat 心跳：sessionId={} ,mId={},cId={}, date={}", sw.getId(), cw.getmId(),
                         cw.getWebUser().getcId(), DateViewTools.formatFullDate(new Date()));
        } catch (Exception e) {
            if (System.currentTimeMillis() - sw.getLastHeartbeatTime() >= 1000 * 60 * 5) { // 5分钟没有反应就直接把Client端剔除掉
                removeSession(session);
            }
            logger.debug("clientHeartbeat send error：sessionId={} ,mId={},cId={}, date={}", sw.getId(), cw.getmId(),
                         cw.getWebUser().getcId(), DateViewTools.formatFullDate(new Date()));
        }
    }

    public void clientReturnResult(SocketMessage<?> receiverMessage) {
        log("服务器收到" + receiverMessage.getClientInfo() + "的处理结果，内容是" + receiverMessage.getRemark());
    }

    /**
     * 外界主动要求Server端通知所有Client
     * 
     * @param socketMessage
     * @return
     */
    public boolean pushAllClient(SocketMessage<String> socketMessage) {
        if (sessionCacheMap != null && sessionCacheMap.size() > 0) {
            Collection<SessionWrapper> allSocketSession = getAllSessionInfo();
            for (SessionWrapper ws : allSocketSession) {
                ClientWrapper cw = ws.getClientInfo();
                logger.debug("服务器找到mId={},cId={},sessionId={} 的Session,准备广播!", cw.getmId(), cw.getWebUser().getcId(),
                             ws.getId());
                try {
                    ws.getSocketSession().sendMessage(socketMessage.getServerMessage());
                } catch (Exception e) {
                    logger.error("pushAllClient error!");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 空闲时调用
     */
    public void sessionIdle(WebSocketSession session) throws Exception {

    }

    /**
     * 遍历执行Seesion并调用ApplyMethod invoke执行 >>添加缓存支持
     * 
     * @param method
     */
    private void apply(ApplyMethod method) {
        if (sessionCacheMap == null || sessionCacheMap.size() == 0) {
            return;
        }
        for (Entry<ClientWrapper, Set<SessionWrapper>> entry : sessionCacheMap.entrySet()) {
            Set<SessionWrapper> set = entry.getValue();
            ClientWrapper cw = entry.getKey();
            if (!Argument.isNotEmpty(set)) {
                continue;
            }
            for (SessionWrapper sw : set) {
                method.invoke(cw, sw);
            }
        }
    }

    public void log(String msg) {
        if (callBack != null) {
            callBack.notifyAction(msg);
        }
    }

    public ISocketCallback getCallBack() {
        return callBack;
    }

    public void setCallBack(ISocketCallback callBack) {
        this.callBack = callBack;
    }
}
