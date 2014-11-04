/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc Jun 15, 2014 11:17:40 PM
 */
public class Argument {

    /**
     * 判断变量是否为空
     * 
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (null == s || StringUtils.EMPTY.equals(s) || StringUtils.EMPTY.equals(s.trim())
            || "null".equalsIgnoreCase(s)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 正整数
     * 
     * @param argument
     * @return
     */
    public static boolean isPositive(Integer argument) {
        return argument != null && argument > 0;
    }

    public static boolean isPositive(Number argument) {
        if (argument == null) {
            return false;
        }
        return argument.floatValue() > 0f || argument.intValue() > 0;
    }

    /**
     * 非正整数
     * 
     * @param argument
     * @return
     */
    public static boolean isNotPositive(Number argument) {
        if (argument == null) {
            return true;
        }
        if (argument instanceof Integer) {
            return (Integer) argument <= 0;
        }
        if (argument instanceof Long) {
            return (Long) argument <= 0;
        }
        return true;
    }

    public static boolean isNull(Object argument) {
        return argument == null;
    }

    public static boolean isBlank(String argument) {
        return StringUtils.isBlank(argument);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection argument) {
        return isNull(argument) || argument.isEmpty();
    }

    public static boolean isNotNull(Object argument) {
        return argument != null;
    }

    /**
     * 判断一个集合部位空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Collection argument) {
        return !isEmpty(argument);
    }

    public static boolean isNotEmptyArray(Object[] array) {
        return !isEmptyArray(array);
    }

    public static boolean isEmptyArray(Object[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isNotBlank(String argument) {
        return StringUtils.isNotBlank(argument);
    }

    /**
     * 2个Integer是否相等 <br>
     * 
     * @param num1
     * @param num2
     * @return
     */
    public static boolean integerEqual(Integer num1, Integer num2) {
        return num1 == null ? num2 == null : num1.equals(num2);
    }
}
