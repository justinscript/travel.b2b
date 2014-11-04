/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Apr 12, 2013 1:23:30 PM
 */
public class ObjectUtils {

    private static Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * 把对象中的string数据类型进行一次trim操作
     * 
     * @param object
     * @throws Exception
     */
    public static void trim(Object object) {
        if (object == null) {
            return;
        }
        try {
            trimStringField(object, object.getClass());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 把对象中的string数据类型进行一次trim操作
     * 
     * @param object
     * @param parent 所有的父类是否需要trim
     * @throws Exception
     */
    public static void trim(Object object, boolean parentClass) {
        if (object == null) {
            return;
        }
        if (parentClass) {
            trim(object, null);
        } else {
            trim(object);
        }
    }

    /**
     * @param obj
     */
    public static void trim(Object obj, Class<?> stopClass) {
        if (obj == null) {
            return;
        }
        if (stopClass == null) {
            stopClass = Object.class;
        }
        Class<?> objClass = obj.getClass();
        boolean nextBreak = false;
        while (true) {
            try {
                trimStringField(obj, objClass);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                break;
            }
            if (nextBreak) {
                break;
            }
            objClass = objClass.getSuperclass();
            if (objClass == null || objClass == Object.class) {
                break;
            }
            if (objClass == stopClass) {
                nextBreak = true;
            }
        }
    }

    /**
     * 把对象中的string数据类型进行一次trim操作
     * 
     * @param object
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void trimStringField(Object object, Class<?> clazz) throws Exception {
        if (object instanceof Map<?, ?>) {
            Map<Object, Object> target = new HashMap<Object, Object>();
            for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();

                if (key instanceof String) {
                    key = StringUtils.trim((String) key);
                } else {
                    trim(key);
                }

                if (value instanceof String) {
                    value = StringUtils.trim((String) value);
                    value = StringUtils.replace((String) value, "\"", StringUtils.EMPTY);
                } else {
                    trim(value);
                }
                target.put(key, value);
            }
            ((Map<?, ?>) object).clear();
            ((Map) object).putAll((Map) target);
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                boolean isFoolback = false;
                if (field.isAccessible() == false) {
                    isFoolback = true;
                    field.setAccessible(true);
                }
                String value = (String) field.get(object);
                if (StringUtils.isNotEmpty(value)) {
                    value = value.trim();
                    field.set(object, value);
                }
                if (isFoolback) {
                    field.setAccessible(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<String, String> kvMap = new HashMap<String, String>();
        kvMap.put(" _zll_ ", " zsdfasdaaaaaaaaaaa ");
        kvMap.put("                               zxc ", " zsdfasdaaqrwerqweraaaaa ");
        kvMap.put(" _qwl", " rrrrrrrrrrrrrrrrrrrr  ");
        kvMap.put("12safa", "qweweq");
        trim(kvMap);
        System.out.println(kvMap);
    }
}
