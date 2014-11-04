/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.comset.manager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.comset.cons.RecordEnum;
import com.zb.app.comset.info.LeafInfo;

/**
 * @author zxc Jul 23, 2014 11:42:41 AM
 */
public class ComsetThreadContextCache {

    public static Logger                                          log       = LoggerFactory.getLogger(ComsetThreadContextCache.class);

    protected static final ThreadLocal<Map<RecordEnum, LeafInfo>> resources = new ThreadLocal<Map<RecordEnum, LeafInfo>>() {

                                                                                protected Map<RecordEnum, LeafInfo> initialValue() {
                                                                                    // 解决子线程和父线程并发产生的问题和ConcurrentModificationException
                                                                                    return new HashMap<RecordEnum, LeafInfo>();
                                                                                }
                                                                            };

    /**
     * 清除全部
     */
    public static void clean() {
        if (log.isDebugEnabled()) {
            log.debug("Clear all elements from  thread [" + Thread.currentThread().getName() + "]");
            log.debug("Size : " + size());
        }
        currentMap().clear();
    }

    /**
     * 获得一个模块的线程上下文空间
     * 
     * @param module - 模块
     * @param autoCreate - 是否自动创建
     */
    protected static LeafInfo getModuleContext(RecordEnum module, boolean autoCreate) {
        Map<RecordEnum, LeafInfo> threadContext = currentMap();
        LeafInfo moduleContext = threadContext.get(module);
        if (moduleContext == null && autoCreate) {
            moduleContext = new LeafInfo();
            threadContext.put(module, moduleContext);
        }

        return moduleContext;
    }

    /**
     * 加入一个变量
     * 
     * @param module
     * @param key
     * @param value
     */
    public static void put(RecordEnum key, LeafInfo value) {
        Map<RecordEnum, LeafInfo> threadContext = currentMap();
        threadContext.put(key, value);
    }

    /**
     * 方法的描述.
     * 
     * @param key
     * @return
     */
    public static Object get(RecordEnum key) {
        return getModuleContext(key, false);
    }

    /**
     * 方法的描述.
     * 
     * @return
     */
    protected static Map<RecordEnum, LeafInfo> currentMap() {
        return resources.get();
    }

    /**
     * 方法的描述.
     * 
     * @return
     */
    public static int size() {
        Map<RecordEnum, LeafInfo> threadContext = currentMap();
        return (threadContext != null) ? threadContext.size() : 0;
    }

    /**
     * 方法的描述.
     */
    public static void destroy() {
        if (log.isDebugEnabled()) {
            log.debug("ThreadContextCache resource destroy. Thread = " + Thread.currentThread().getName(),
                      new Exception());
        }

        // 清除所有的cache
        currentMap().clear();

        resources.set(null);
    }
}
