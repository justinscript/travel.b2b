/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Jun 16, 2014 12:11:55 AM
 */
public class ConfigMap {

    private static final Logger           log      = LoggerFactory.getLogger(ConfigMap.class);
    private HashMap<String, Serializable> paramMap = new HashMap<String, Serializable>();

    public ConfigMap copy() {
        ConfigMap newConfigMap = new ConfigMap();
        if (paramMap != null) {
            Iterator<String> ir = paramMap.keySet().iterator();
            while (ir.hasNext()) {
                String key = ir.next();
                Serializable value = paramMap.get(key);
                if (value != null) {
                    newConfigMap.putKV(key, value);
                }
            }
        }
        return newConfigMap;
    }

    public int size() {
        return paramMap.size();
    }

    public boolean containsKey(String key) {
        return paramMap.containsKey(key);
    }

    public void putKV(String key, Serializable value) {
        paramMap.put(key, value);
    }

    public String getKV(String key, String defaultValue) {
        Serializable value = paramMap.get(key);
        return value == null ? defaultValue : (String) value;
    }

    public int getKV(String key, int defaultValue) {
        Serializable value = paramMap.get(key);
        return value == null ? defaultValue : (Integer) value;
    }

    public float getKV(String key, float defaultValue) {
        Serializable value = paramMap.get(key);
        return value == null ? defaultValue : (Float) value;
    }

    public boolean getKV(String key, boolean defaultValue) {
        Serializable value = paramMap.get(key);
        return value == null ? defaultValue : (Boolean) value;
    }

    public String[] getKVStringArray(String key) {
        return (String[]) paramMap.get(key);
    }

    public float[] getKVFloatArray(String key) {
        return (float[]) paramMap.get(key);
    }

    public int[] getKVIntArray(String key) {
        return (int[]) paramMap.get(key);
    }

    public boolean[] getKVBooleanArray(String key) {
        return (boolean[]) paramMap.get(key);
    }

    public void printConfigMap() {
        String suffix = "zxc--#--";
        if (paramMap == null) {
            log.info(suffix + "paramMap is null!");
            return;
        }
        log.info(suffix + "ConfigMap hashcode()=" + hashCode() + " size=" + paramMap.size());
        Iterator<String> ir = paramMap.keySet().iterator();
        while (ir.hasNext()) {
            String key = ir.next();
            Serializable value = paramMap.get(key);
            if (value != null) {
                log.info(suffix + "Key=" + key + " Value=" + value);
            }
        }
    }
}
