/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数组数据处理工具类
 * 
 * @author zxc Jun 16, 2014 12:01:48 AM
 */
public class ArrayUtils {

    private static final Logger logger = LoggerFactory.getLogger(ArrayUtils.class);

    public interface IHandle<T> {

        // 自定义初始化规则
        public T init(Class<T> clazz);

        // 自定义字段为null规则
        public boolean isNull(T obj);
    }

    public static <E extends Object> List<E> arrayConvert(E[] array) {
        return Argument.isEmptyArray(array) ? Collections.<E> emptyList() : Arrays.asList(array);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Object> E[] listConvert(List<E> list) {
        return Argument.isEmpty(list) ? null : (E[]) list.toArray();
    }

    public static <T extends Object> String[] convert(T[] array) {
        if (Argument.isEmptyArray(array)) {
            logger.debug("ArrayUtils.convert array is null!");
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0, j = result.length; i < j; i++) {
            result[i] = array[i].toString();
        }
        return result;
    }

    public static String[] removeBlankElement(String[] array) {
        if (Argument.isEmptyArray(array)) {
            logger.debug("ArrayUtils.removeBlankElement array is null!");
            return null;
        }
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (Argument.isBlank(iterator.next())) {
                iterator.remove();
            }
        }
        if (list.isEmpty()) {
            return null;
        } else {
            return list.toArray(new String[0]);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removeNullElement(E[] array) {
        if (Argument.isEmptyArray(array)) {
            logger.debug("ArrayUtils.removeNullElement array is null!");
            return null;
        }
        int notNullValueCount = array.length;
        for (int i = 0, j = array.length; i < j; i++) {
            if (array[i] == null) {
                notNullValueCount--;
            }
        }
        if (notNullValueCount == 0) {
            return null;
        }
        E[] newInstance = (E[]) Array.newInstance(array.getClass().getComponentType(), notNullValueCount);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (array[i] != null) {
                newInstance[j++] = array[i];
            }
        }
        return newInstance;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <E> E[] replaceNullElement(E[] array, IHandle handle) {
        if (handle == null) {
            logger.error("ArrayUtils.replaceNullElement handle is null!");
            throw new RuntimeException();
        }
        if (Argument.isEmptyArray(array)) {
            return null;
        }
        for (int i = 0, j = array.length; i < j; i++) {
            if (array[i] == null || handle.isNull(array[i])) {
                array[i] = (E) handle.init(array.getClass().getComponentType());
            }
        }
        return array;
    }

    /**
     * 数组尾部添加
     */
    public static <T extends Object> T[] shift(T[] array, T newValue) {
        return add(array, array == null ? 0 : array.length, newValue);
    }

    /**
     * 数组头部增加
     */
    public static <T extends Object> T[] unshift(T[] array, T newValue) {
        return add(array, 0, newValue);
    }

    /**
     * <pre>
     *      从数据指定位置添加。 
     *      插入点为index后面，index的位置就是 [-1,length]
     *      特别注意如果是末尾添加index就是原数组的length就好了。
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T[] add(T[] array, int index, T newValue) {
        int length = array == null ? 0 : array.length;
        index = Math.max(0, index);
        index = Math.min(length, index);
        Class<?> clazz = array == null ? newValue.getClass() : array.getClass().getComponentType();
        T[] newInstance = (T[]) Array.newInstance(clazz, length + 1);

        for (int i = 0, j = 0; i < newInstance.length;) {
            T _value = i == index ? newValue : array[j++];
            newInstance[i++] = _value;
        }
        return newInstance;
    }

    public static void main(String[] args) {
        String[] s = { "zxc", null, "zuobian", "" };
        s = ArrayUtils.replaceNullElement(s, new IHandle<String>() {

            @Override
            public boolean isNull(String obj) {
                return obj == null || StringUtils.isEmpty((String) obj);
            }

            @Override
            public String init(Class<String> clazz) {
                if (clazz.equals(String.class)) {
                    return (String) new String("wu");
                }
                try {
                    return clazz.newInstance();
                } catch (InstantiationException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("replaceNullElement: init error");
                return null;
            }
        });
        System.out.println(StringUtils.join(s, ";"));

        String[] a = removeBlankElement(new String[] { "1", null, "2", "", "3" });
        for (int i = 0, j = a.length; i < j; i++) {
            System.out.println(a[i]);
        }

        String[] t = null;
        System.out.println("Orignal:" + org.apache.commons.lang.ArrayUtils.toString(t, "NULL"));
        System.out.println("shift:" + org.apache.commons.lang.ArrayUtils.toString(shift(t, "a"), "NULL"));
        System.out.println("unshift:" + org.apache.commons.lang.ArrayUtils.toString(unshift(t, "a"), "NULL"));
        System.out.println("add(-1):" + org.apache.commons.lang.ArrayUtils.toString(add(t, -1, "a"), "NULL"));
        System.out.println("add(0):" + org.apache.commons.lang.ArrayUtils.toString(add(t, 0, "a"), "NULL"));
        System.out.println("add(length):" + org.apache.commons.lang.ArrayUtils.toString(add(t, 0, "a"), "NULL"));
        System.out.println("add(length+1):" + org.apache.commons.lang.ArrayUtils.toString(add(t, 1, "a"), "NULL"));
        t = new String[] { "1", null, "2", "", "3" };
        System.out.println("Orignal:" + org.apache.commons.lang.ArrayUtils.toString(t, "NULL"));
        System.out.println("shift:" + org.apache.commons.lang.ArrayUtils.toString(shift(t, "a"), "NULL"));
        System.out.println("unshift:" + org.apache.commons.lang.ArrayUtils.toString(unshift(t, "a"), "NULL"));
        System.out.println("add(-1):" + org.apache.commons.lang.ArrayUtils.toString(add(t, -1, "a"), "NULL"));
        System.out.println("add(0):" + org.apache.commons.lang.ArrayUtils.toString(add(t, 0, "a"), "NULL"));
        System.out.println("add(length):" + org.apache.commons.lang.ArrayUtils.toString(add(t, 0, "a"), "NULL"));
        System.out.println("add(length+1):" + org.apache.commons.lang.ArrayUtils.toString(add(t, 1, "a"), "NULL"));
    }
}
