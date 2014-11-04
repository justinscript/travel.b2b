/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author zxc Aug 8, 2014 1:35:12 PM
 */
public abstract class PSBCAssert {

    public PSBCAssert() {

    }

    public static void isNull(Object object, String message) {
        if (object != null) throw new IllegalArgumentException(message);
        else return;
    }

    public static void isNull(Object object) {
        isNull(object, "断言失败,参数必须是null");
    }

    public static void notNull(Object object, String message) {
        if (object == null) throw new IllegalArgumentException(message);
        else return;
    }

    public static void notNull(Object object) {
        notNull(object, "断言失败,参数不能为null");
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) throw new IllegalArgumentException(message);
        else return;
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "断言失败,表达式为false");
    }

    public static void notEmpty(Object array[], String message) {
        if (PSBCUtil.isEmpty(array)) throw new IllegalArgumentException(message);
        else return;
    }

    public static void notEmpty(Object array[]) {
        notEmpty(array, "断言失败,数组为空");
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (PSBCUtil.isEmpty(collection)) throw new IllegalArgumentException(message);
        else return;
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "断言失败,集合为空");
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (PSBCUtil.isEmpty(map)) throw new IllegalArgumentException(message);
        else return;
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    public static void notNullorEmpty(String object) {
        if (PSBCUtil.isEmptywithTrim(object)) throw new IllegalArgumentException("断言失败,参数不能为null或空");
        else return;
    }

    public static void notNullorEmpty(String object, String message) {
        if (PSBCUtil.isEmptywithTrim(object)) throw new IllegalArgumentException(message);
        else return;
    }
}
