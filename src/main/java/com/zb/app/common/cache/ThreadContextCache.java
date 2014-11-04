/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cache;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Jul 15, 2014 5:25:09 PM
 */
public class ThreadContextCache {

    public static Logger                          logger       = LoggerFactory.getLogger(ThreadContextCache.class);

    private static final Set<ThreadLocalCallback> callbackList = new ConcurrentHashSet<ThreadLocalCallback>();

    /**
     * 清除全部
     */
    public static void clean() {
        if (logger.isDebugEnabled()) {
            logger.debug("Clear all elements from  thread [" + Thread.currentThread().getName() + "]");
        }

        for (ThreadLocalCallback callback : callbackList) {
            try {
                callback.cleanAll();
            } catch (Exception e) {
                logger.error("Clean up ThreadLocal failed:" + e.getMessage(), e);
            }
        }
    }

    public static void regist(ThreadLocalCallback callback) {
        callbackList.add(callback);
    }

    public static interface ThreadLocalCallback {

        public void cleanAll();
    }
}
