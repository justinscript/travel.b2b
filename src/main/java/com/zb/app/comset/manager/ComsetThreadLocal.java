/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.comset.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Jul 23, 2014 11:51:34 AM
 */
public class ComsetThreadLocal {

    public static Logger                                    log       = LoggerFactory.getLogger(ComsetThreadLocal.class);

    protected static final ThreadLocal<Map<String, Object>> resources = new ThreadLocal<Map<String, Object>>() {

                                                                          protected Map<String, Object> initialValue() {
                                                                              // 解决子线程和父线程并发产生的问题和ConcurrentModificationException
                                                                              return new ConcurrentHashMap<String, Object>();
                                                                          }
                                                                      };

    public static void clean() {
        if (log.isDebugEnabled()) {
            log.debug("Clear ComsetThreadLocal  [" + Thread.currentThread().getName() + "]");
            log.debug("Size : " + size());
        }

        resources.get().clear();
    }

    private static int size() {
        return resources.get().size();
    }

    public static enum CacheType {

        DAILY_PROMOTION("dailypromotion");

        private String nameSpace;

        private CacheType(String nameSpace) {
            this.nameSpace = nameSpace;
        }

        public String getNameSpace() {
            return this.nameSpace;
        }

        public Object get(String key) {
            String cacheKey = buildKey(key);
            Map<String, Object> map = resources.get();
            Object result = map.get(cacheKey);

            if (log.isDebugEnabled()) {
                log.debug("ComsetThreadLocal  Get[" + cacheKey + ":" + result + "]");
            }
            return result;
        }

        public void put(String key, Object value) {
            String cacheKey = buildKey(key);
            Map<String, Object> map = resources.get();
            map.put(cacheKey, value);

            if (log.isDebugEnabled()) {
                log.debug("ComsetThreadLocal  Put[" + cacheKey + ":" + value + "]");
            }
        }

        private String buildKey(String key) {
            String cacheKey = this.nameSpace + "_" + key;
            return cacheKey;
        }
    }
}
