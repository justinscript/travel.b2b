/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.common.cache.ThreadContextCache.ThreadLocalCallback;

/**
 * 线程缓存，这个类可能会迁移到更底层，供全网使用
 * 
 * @author zxc Jul 15, 2014 5:24:03 PM
 */
public class ThreadLocalCacheManager {

    private static final Logger                                                  logger         = LoggerFactory.getLogger(ThreadLocalCacheManager.class);

    private static ThreadLocal<ConcurrentHashMap<Class<?>, Map<String, Object>>> cache          = new ThreadLocal<ConcurrentHashMap<Class<?>, Map<String, Object>>>() {

                                                                                                    @Override
                                                                                                    protected ConcurrentHashMap<Class<?>, Map<String, Object>> initialValue() {

                                                                                                        return new ConcurrentHashMap<Class<?>, Map<String, Object>>();
                                                                                                    }

                                                                                                };

    private static boolean                                                       useCache       = false;

    private static CacheHandler                                                  defaultHandler = new DefaultCacheHander();

    static {
        useCache = "true".equalsIgnoreCase(System.getProperty("soft.threadlocalcache.switch"));
        logger.error("ThreadLocalCache is:({}),Use by soft.threadlocalcache.switch=true", useCache ? "y" : "n");
        if (useCache) {
            ThreadContextCache.regist(new ThreadLocalCallback() {

                @Override
                public void cleanAll() {
                    logger.debug("clean up threadlocalCache ....");
                    ConcurrentHashMap<Class<?>, Map<String, Object>> concurrentHashMap = cache.get();
                    for (Class<?> object : concurrentHashMap.keySet()) {
                        Map<String, Object> value = concurrentHashMap.get(object);
                        if (logger.isDebugEnabled()) {
                            logger.debug("Clean  【{}】's size  【{}】 ", object.getSimpleName(), value.size());
                        }
                        value.clear();
                    }
                    concurrentHashMap.clear();
                }
            });
        }
    }

    public static Object add(Class<?> typeOfCache, Object key, Object value) {
        return add(typeOfCache, key, value, defaultHandler);
    }

    public static void clearAll(Class<?> typeOfCache) {
        Map<String, Object> regist = regist(typeOfCache);
        if (regist != null) {
            regist.clear();
        }
    }

    public static void del(Class<?> typeOfCache, Object key) {
        del(typeOfCache, key, defaultHandler);
    }

    public static Object query(Class<?> typeOfCache, Object key) {
        return query(typeOfCache, key, defaultHandler);
    }

    public static Object add(Class<?> typeOfCache, Object key, Object value, CacheHandler handler) {
        if (useCache) {
            Map<String, Object> dataContiainer = regist(typeOfCache);
            if (dataContiainer != null) {
                handler.add(dataContiainer, key, value);
            }
        }
        return value;
    }

    public static void del(Class<?> typeOfCache, Object key, CacheHandler handler) {
        if (useCache) {
            Map<String, Object> regist = regist(typeOfCache);
            if (regist != null) {
                handler.remove(regist, key);
            }
        }
    }

    public static Object query(Class<?> typeOfCache, Object key, CacheHandler handler) {
        if (useCache) {
            Map<String, Object> regist = regist(typeOfCache);
            if (regist != null) {
                Object value = handler.query(regist, key);
                if (logger.isDebugEnabled()) {
                    logger.debug("Query From ThreadLocalCache,key【{}】,value【{}】", key, value);
                }
                return value;
            }
        }
        return null;
    }

    public interface CacheHandler {

        void add(Map<String, Object> dataContiainer, Object key, Object value);

        Object query(Map<String, Object> regist, Object key);

        void remove(Map<String, Object> regist, Object key);

    }

    // 处理Key为String 类型的
    public static class DefaultCacheHander implements CacheHandler {

        @Override
        public void add(Map<String, Object> dataContiainer, Object key, Object value) {
            String _key = key.toString().toLowerCase();
            dataContiainer.put(_key, value);
        }

        @Override
        public Object query(Map<String, Object> dataContiainer, Object key) {
            String _key = key.toString().toLowerCase();
            return dataContiainer.get(_key);
        }

        @Override
        public void remove(Map<String, Object> dataContiainer, Object key) {
            String _key = key.toString().toLowerCase();
            dataContiainer.remove(_key.toString());
        }

    }

    private static Map<String, Object> regist(Class<?> typeOfCache) {
        ConcurrentHashMap<Class<?>, Map<String, Object>> concurrentHashMap = cache.get();
        if (!concurrentHashMap.containsKey(typeOfCache)) {
            synchronized (cache) {
                if (!concurrentHashMap.containsKey(typeOfCache)) {
                    concurrentHashMap.put(typeOfCache, new ConcurrentHashMap<String, Object>());
                }
            }
        }
        Map<String, Object> map = concurrentHashMap.get(typeOfCache);
        if (map == null) {
            logger.error("Create Map for {}'s Zuobian ThreadLocalCache Failed!!", typeOfCache.getName());
        }
        return map;
    }
}
