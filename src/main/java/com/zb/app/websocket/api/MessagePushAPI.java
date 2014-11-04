/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.zb.app.biz.domain.MessageData;
import com.zb.app.biz.service.interfaces.MemberService;
import com.zb.app.biz.service.interfaces.MessageService;
import com.zb.app.common.cons.ResultCode;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.notify.NotifyListener;
import com.zb.app.common.notify.NotifyService;
import com.zb.app.common.notify.event.EventConfig;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.pipeline.value.BaseWebUser;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.Result;
import com.zb.app.web.webuser.ZuobianWebUser;
import com.zb.app.websocket.server.WebSocketServerHandler;
import com.zb.app.websocket.server.wrapper.SocketMessage;

/**
 * @author zxc Jun 19, 2014 1:49:40 PM
 */
@Component
public class MessagePushAPI {

    private static final Logger    logger   = LoggerFactory.getLogger(MessagePushAPI.class);

    @Autowired
    private NotifyService          notifyService;

    @Autowired
    protected MessageService       messageService;

    @Autowired
    private MemberService          memberService;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private WebSocketServerHandler sessionHandler;

    static transient AtomicBoolean isInited = new AtomicBoolean(false);

    @PostConstruct
    public void init() {
        if (isInited.compareAndSet(false, true)) {
            // order notify listener
            Result orderRegist = notifyService.regist(new NotifyListener() {

                @EventConfig(events = { EventType.orderAdd })
                public void orderAdd(MessageEvent event) {
                    ZuobianWebUser webUser = event.getWebUser();
                    if (webUser == null) {
                        logger.debug("MessagePushAPI new message comon in,but webuser is null!");
                        return;
                    }
                    String json = fetchMessageData(webUser);
                    logger.debug("MessagePushAPI new message comon in,json is :【{}】", json);
                    sendOrderPushMsg(event.getWebUser(), json);
                }
            });
            if (orderRegist.isFailed()) {
                logger.error("orderAdd 【failed!】");
            } else {
                logger.error("orderAdd 【success!】");
            }

            // new client message notify listener
            Result clientMessageRegist = notifyService.regist(new NotifyListener() {

                @EventConfig(events = { EventType.newClientMessage })
                public void newClientMessage(MessageEvent event) {
                    MessageMapper<?, ?> mm = event.getData();
                    if (mm == null) {
                        return;
                    }
                    SocketMessage<?> json = (SocketMessage<?>) mm.getData();
                    TextMessage textMessage = json.getClienMessage();
                    logger.error("MessagePushAPI newClientMessage:【{}】", textMessage.getPayload());
                }
            });
            if (clientMessageRegist.isFailed()) {
                logger.error("newClientMessage 【failed!】");
            } else {
                logger.error("newClientMessage 【success!】");
            }

            // new client connect notify listener
            Result clientConnectRegist = notifyService.regist(new NotifyListener() {

                @EventConfig(events = { EventType.newClientConnect })
                public void newClientMessage(MessageEvent event) {
                    ZuobianWebUser webUser = event.getWebUser();
                    if (webUser == null) {
                        return;
                    }
                    logger.error("MessagePushAPI newClientConnect webUser:【{}】", webUser.toString());

                    String json = fetchMessageData(webUser);
                    sendOrderPushMsg(event.getWebUser(), json);
                }
            });
            if (clientConnectRegist.isFailed()) {
                logger.error("newClientConnect 【failed!】");
            } else {
                logger.error("newClientConnect 【success!】");
            }
        }
    }

    private String fetchMessageData(ZuobianWebUser webUser) {
        String json = StringUtils.EMPTY;
        MessageData messageData = messageService.getMessageData(webUser.getcId());
        if (messageData == null) {
            json = JsonResultUtils.createJsonResult(ResultCode.SUCCESS, "暂无消息");
        } else {
            json = JsonResultUtils.createJsonResult(ResultCode.SUCCESS, messageData, "推送成功");
        }
        return json;
    }

    public <T extends BaseWebUser & Serializable, DATA> void sendOrderPushMsg(final T webUser, final DATA msg,
                                                                              final IMessageHandler... handler) {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                sendPushMsg((ZuobianWebUser) webUser, (String) msg);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseWebUser & Serializable, DATA> void sendOrderPushMsg(final T webUser,
                                                                              final IMessageHandler handler,
                                                                              final DATA... msg) {
        executor.submit(new Runnable() {

            @Override
            public void run() {
                for (DATA data : msg) {
                    sendPushMsg((ZuobianWebUser) webUser, (String) data);
                }
            }
        });
    }

    private <T extends BaseWebUser & Serializable> void sendPushMsg(ZuobianWebUser webUser, String msg) {
        Long cId = webUser.getcId();

        List<Long> mIdList = memberService.getMidListByCid(cId);
        for (Long mId : mIdList) {
            doSend(cId, mId, msg);
        }
    }

    private void doSend(Long cId, Long mId, String msg) {
        Collection<WebSocketSession> set = sessionHandler.getSession(mId);
        if (Argument.isEmpty(set)) {
            logger.error("MessagePushAPI:sendPushMsg msg error:【{}】,cid:【{}】,mid:【{}】is offLine!webSocketSession set is null!",
                         msg, cId, mId);
        }
        for (WebSocketSession session : set) {
            if (session == null) {
                logger.error("MessagePushAPI:sendPushMsg msg error:【{}】,cid:【{}】,mid:【{}】is offLine!session is null!",
                             msg, cId, mId);
            }
            try {
                session.sendMessage(new TextMessage(msg));
                logger.debug("MessagePushAPI:sendPushMsg msg success:【{}】,cid:【{}】,mid:【{}】,sessionId:【{}】", msg, cId,
                             mId, session.getId());
            } catch (Exception e) {
                logger.error("MessagePushAPI:sendPushMsg msg error:【{}】,cid:【{}】,mid:【{}】,sessionId:【{}】", msg, cId,
                             mId, session.getId());
            }
        }
    }
}
