/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.notify;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.zb.app.common.notify.NotifyUtils.MethodDescriptor;
import com.zb.app.common.notify.event.Event;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.result.Result;

/**
 * @author zxc Jul 24, 2014 4:55:57 PM
 */
@Service
public class NotifyService implements INotify {

    private static Map<EventType, Set<MethodDescriptor>> container = new ConcurrentHashMap<EventType, Set<MethodDescriptor>>();

    /**
     * 事件队列
     */
    private static Queue<Event>                          events    = new ConcurrentLinkedQueue<Event>();

    protected static int                                 deplaySeconds = 10, corePoolSize = 10;
    protected static int                                 fixRate       = 10;

    @PostConstruct
    void init() {
        Executors.newScheduledThreadPool(corePoolSize).scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    // logger.debug("poll the queue...");
                    fireEvent();
                } catch (Throwable e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }, deplaySeconds, fixRate, TimeUnit.MILLISECONDS);
    }

    /**
     * 注册一个异步事件监听器。系统会有一个定时线程扫描所关注的事件是否发生。 因为是异步所以不会关注监听器中异常和返回值，同时事务也无法保证。
     * 
     * <pre>
     * 
     * </pre>
     * 
     * @param listener 时间发生后的回调接口
     * @return 注册是否成功，如果失败result中会包含失败的原因。
     */
    public Result regist(NotifyListener listener) {
        return NotifyUtils.getListenedEvent(container, listener);
    }

    /**
     * 添加一个事件
     */
    public void notify(Event event) {
        if (logger.isDebugEnabled()) {
            logger.debug("Receive a event " + event.summary());
        }
        events.add(event);
    }

    /**
     * 事件队列调度机制
     */
    protected void fireEvent() {
        Event poll = events.poll();
        if (poll == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("fire a event " + poll.summary());
        }
        EventType eventType = poll.getEventType();
        if (eventType == null) {
            logger.warn("A ERROR Event Found! " + poll.summary());
            return;
        }

        // 将处理也提出让线程池执行
        Set<MethodDescriptor> listseners = container.get(eventType);
        if (listseners != null) {
            for (MethodDescriptor md : listseners) {
                try {
                    md.invoke(poll);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
