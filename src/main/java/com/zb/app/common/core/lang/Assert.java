/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.util.Collection;

/**
 * 断言,对断言失败一律抛出IllegalArgumentException
 * 
 * @author zxc Jun 16, 2014 12:35:47 AM
 */
public class Assert {

    /**
     * 字符串非空
     */
    public static void assertNotBlank(String argument) {
        assertNotBlank(argument, null);
    }

    public static void assertNotBlank(String argument, String message) {
        if (Argument.isBlank(argument)) throwException(message);
    }

    /**
     * 正数
     */
    public static void assertPositive(Integer argument, String message) {
        if (!Argument.isPositive(argument)) throwException(message);
    }

    public static void assertPositive(Integer argument) {
        assertPositive(argument, null);
    }

    /**
     * 正数
     */
    public static void assertPositive(Number argument, String message) {
        if (!Argument.isPositive(argument)) throwException(message);
    }

    public static void assertPositive(Number argument) {
        assertPositive(argument, null);
    }

    /**
     * 对象非null
     */
    public static void assertNotNull(Object argument) {
        assertNotNull(argument, null);
    }

    public static void assertNotNull(Object argument, String message) {
        if (argument == null) throwException(message);
    }

    public static void assertTrue(boolean argument) {
        assertTrue(argument, null);
    }

    public static void assertTrue(boolean argument, String message) {
        if (!argument) throwException(message);
    }

    @SuppressWarnings("rawtypes")
    public static void assertNotEmpty(Collection argument) {
        assertNotEmpty(argument, null);
    }

    @SuppressWarnings("rawtypes")
    public static void assertNotEmpty(Collection argument, String message) {
        if (Argument.isEmpty(argument)) throwException(message);
    }

    public static void assertNotEmpty(Object[] array, String message) {
        if (Argument.isEmptyArray(array)) throwException(message);
    }

    public static void throwException(String message) {
        throw new IllegalArgumentException(message);
    }
}
