/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.Unsafe;

import com.google.gson.Gson;
import com.zb.app.common.util.DateViewTools;

/**
 * @author zxc Jun 16, 2014 12:22:07 AM
 */
public class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static void main(String[] args) throws Exception {
        List<TT> list = new ArrayList<BeanUtils.TT>();
        TT t1 = new TT();
        TT t2 = new TT();
        System.out.println("t1 =====> " + t1);
        System.out.println("t1 ~~~~~> " + getLocation(t1));
        list.add(t1);
        list.add(t2);

        for (TT t : list) {
            System.out.println("list =====> " + t);
            System.out.println("list ~~~~~> " + getLocation(t));
        }

        t1 = new TT();
        t2 = new TT();
        System.out.println("t1 =====> " + t1);
        System.out.println("t1 ~~~~~> " + getLocation(t1));
        for (TT t : list) {
            System.out.println("list =====> " + t);
            System.out.println("list ~~~~~> " + getLocation(t));
        }
    }

    static class TT {
    }

    private Object fromAddress(long address) throws Exception {
        Object[] array = new Object[] { null };
        long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);
        getUnsafe().putLong(array, baseOffset, address);
        return array[0];
    }

    private static long getLocation(Object o) throws Exception {
        Object[] array = new Object[] { o };
        Unsafe unsafe = getUnsafe();
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return objectAddress;
    }

    private static Unsafe getUnsafe() throws Exception {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        return (Unsafe) theUnsafe.get(null);
    }

    /**
     * 获取对象发生改变的属性和值
     * 
     * @param f
     * @return
     */
    public static Map<String, String> fieldEditable(Object oldObj, Object newObj) {
        Map<String, String> oldMap = getFieldValueMap(oldObj);
        Map<String, String> newMap = getFieldValueMap(newObj);

        Map<String, String> oldChangeMap = new HashMap<String, String>();
        Map<String, String> newChangeMap = new HashMap<String, String>();

        for (Entry<String, String> entry : oldMap.entrySet()) {
            String fieldKey = entry.getKey();
            if (StringUtils.equalsIgnoreCase(fieldKey, "gmtModified")
                || StringUtils.equalsIgnoreCase(fieldKey, "gmtCreate")) {
                continue;
            }
            String oldValue = entry.getValue();
            String newValue = newMap.get(fieldKey);
            if (!StringUtils.equalsIgnoreCase(oldValue, newValue)) {
                oldChangeMap.put(fieldKey, oldValue);
                newChangeMap.put(fieldKey, newValue);
            }
        }
        newMap.clear();
        newMap.put("oldString", new Gson().toJson(oldChangeMap));
        newMap.put("newString", new Gson().toJson(newChangeMap));
        return newMap;
    }

    /**
     * 获取对象发生改变的属性和值
     * 
     * @param f
     * @return
     */
    public static List<Map<String, String>> fieldEditableList(List<?> oldlineDOs, List<?> newlineDOs) {
        List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i = 0; i < oldlineDOs.size(); i++) {
            Map<String, String> oldMap = getFieldValueMap(oldlineDOs.get(i));
            Map<String, String> newMap = getFieldValueMap(newlineDOs.get(i));

            Map<String, String> oldChangeMap = new HashMap<String, String>();
            Map<String, String> newChangeMap = new HashMap<String, String>();

            for (Entry<String, String> entry : oldMap.entrySet()) {
                String fieldKey = entry.getKey();
                if (StringUtils.equalsIgnoreCase(fieldKey, "gmtModified")
                    || StringUtils.equalsIgnoreCase(fieldKey, "gmtCreate")) {
                    continue;
                }
                String oldValue = entry.getValue();
                String newValue = newMap.get(fieldKey);
                if (!StringUtils.equalsIgnoreCase(oldValue, newValue)) {
                    oldChangeMap.put(fieldKey, oldValue);
                    newChangeMap.put(fieldKey, newValue);
                }
            }
            newMap.clear();
            newMap.put("oldString", new Gson().toJson(oldChangeMap));
            newMap.put("newString", new Gson().toJson(newChangeMap));
            lst.add(newMap);
        }
        return lst;
    }

    /**
     * 取Bean的属性和值对应关系的map,对象转map
     * 
     * @param bean
     * @return
     */
    public static Map<String, String> getFieldValueMap(Object bean) {
        Class<?> cls = bean.getClass();
        Map<String, String> valueMap = new LinkedHashMap<String, String>();
        Field[] fields = getAllFields(new ArrayList<Field>(), cls);

        for (Field field : fields) {
            try {
                if (field == null || field.getName() == null) {
                    continue;
                }
                if (StringUtils.equals("serialVersionUID", field.getName())) {
                    continue;
                }

                Object fieldVal = PropertyUtils.getProperty(bean, field.getName());

                String result = null;
                if (null != fieldVal) {
                    if (StringUtils.equals("Date", field.getType().getSimpleName())) {
                        result = DateViewTools.formatFullDate((Date) fieldVal);
                    } else {
                        result = String.valueOf(fieldVal);
                    }
                }
                valueMap.put(field.getName(), result == null ? StringUtils.EMPTY : result);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueMap;
    }

    /**
     * 对象转成map
     * 
     * @param entity
     * @return
     */
    public static Map<String, Object> beanToMap(Object entity) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (StringUtils.equals("serialVersionUID", fieldName)) {
                continue;
            }
            Object o = null;
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            Method getMethod;
            try {
                getMethod = entity.getClass().getMethod(getMethodName, new Class[] {});
                o = getMethod.invoke(entity, new Object[] {});
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (o != null) {
                parameter.put(fieldName, o);
            }
        }
        return parameter;
    }

    /**
     * 递归获取类的所有field,包括父类
     * 
     * @param fields
     * @param type
     * @return
     */
    public static Field[] getAllFields(Collection<Field> fields, Class<?> type) {
        if (Argument.isEmpty(fields)) {
            fields = new HashSet<Field>();
        }
        for (Field field : type.getDeclaredFields()) {
            fields.add(field);
        }
        if (type.getSuperclass() != null) {
            fields.addAll(Arrays.asList(getAllFields(fields, type.getSuperclass())));
        }
        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * 转换时 加入init方法会执行，请注意
     * 
     * @param clazz
     * @param raw
     * @param specialConverts
     * @return
     */
    public static <T extends Object> List<T> convert(Class<T> clazz, Collection<?> raw,
                                                     ValueEditable... specialConverts) {
        if (Argument.isEmpty(raw)) {
            return Collections.emptyList();
        }
        List<T> data = new ArrayList<T>(raw.size());
        for (Object obj : raw) {
            T vo;
            try {
                vo = clazz.newInstance();
                copyProperties(vo, obj, specialConverts);
                data.add(vo);

                // 特殊处理
                optInitMethod(vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static <T extends Object> void copyProperties(T target, Object raw,
                                                         Collection<? extends ValueEditable> convert) {
        copyProperties(target, raw, convert.toArray(new ValueEditable[0]));
    }

    /**
     * copy时 加入init方法会执行，请注意
     * 
     * @param target
     * @param raw
     * @param defaultValues
     */
    @SuppressWarnings({ "rawtypes" })
    public static <T extends Object> void copyProperties(T target, Object raw, ValueEditable... defaultValues) {

        try {
            Map values = raw == null ? new HashMap() : PropertyUtils.describe(raw);
            if (Argument.isNotEmptyArray(defaultValues)) {
                for (ValueEditable edit : defaultValues) {
                    edit.edit(raw, values);
                }
            }

            PropertyUtils.copyProperties(target, values);
            // 特殊处理
            optInitMethod(target);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <T extends Object> void copyProperties(Collection<T> target, Collection beans, String key,
                                                         Collection<? extends ValueEditable> defaultValues) {
        Map<String, List<T>> result = CollectionUtils.toListMap(target, key);
        for (Object bean : beans) {
            try {
                Object keyProperty = PropertyUtils.getProperty(bean, key);
                List<T> keywords = result.get(keyProperty);
                if (keywords == null) {
                    continue;
                }
                for (T keyword : keywords) {
                    copyProperties(keyword, bean, defaultValues);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private static <T extends Object> void optInitMethod(T vo) throws IllegalAccessException, InvocationTargetException {
        Method[] methods = vo.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (StringUtils.equals("init", method.getName())) {
                method.invoke(vo);
            }
        }
    }
}
