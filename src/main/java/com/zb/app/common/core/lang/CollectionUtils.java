/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Jun 16, 2014 12:12:52 AM
 */
@SuppressWarnings({ "unchecked", "hiding" })
public class CollectionUtils {

    private static Logger logger = LoggerFactory.getLogger(CollectionUtils.class);

    /**
     * Collection c 其中包含了重复的元素，你想再创建一个 Collection 并清除重复元素
     * 
     * @param c
     * @return
     */
    public static <E> Set<E> removeDups(Collection<E> c) {
        return new LinkedHashSet<E>(c);
    }

    /**
     * List<T> ---> List<Map<key,?>>
     */
    public static <T extends Object> List<Map<String, ?>> toMapList(Collection<T> values, String... propertys) {
        if (Argument.isEmptyArray(propertys) || values == null || values.isEmpty()) {
            return Collections.<Map<String, ?>> emptyList();
        }
        List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
        for (T value : values) {
            Map<String, Object> valueMap = new HashMap<String, Object>(propertys.length);
            for (String property : propertys) {
                try {
                    Object propertyValue = PropertyUtils.getProperty(value, property);
                    if (propertyValue == null) {
                        valueMap.put(property, null);
                    }
                    Object wapper = valueMap.get(property);
                    if (wapper == null) {
                        valueMap.put(property, propertyValue);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    continue;
                }
            }
            list.add(valueMap);
        }
        return list;
    }

    /**
     * 用来去掉List中空值和相同项的。
     * 
     * @param list
     * @return
     */
    public static List<T> removeSameItem(List<T> list) {
        List<T> difList = new ArrayList<T>();
        for (T t : list) {
            if (t != null && !difList.contains(t)) {
                difList.add(t);
            }
        }
        return difList;
    }

    public static List<String> getProperty(Collection<?> values, String property) {
        if (values == null || values.isEmpty()) {
            return Collections.<String> emptyList();
        }

        List<String> valueResult = new ArrayList<String>(values.size());
        for (Object value : values) {
            try {
                String propertyValue = BeanUtils.getProperty(value, property);
                valueResult.add(propertyValue);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueResult;
    }

    // set ---> list
    public static <T> List<T> convertSetToList(Set<T> set) {
        List<T> list = new ArrayList<T>();
        if (set != null && set.size() > 0) {
            for (T t : set) {
                list.add(t);
            }
        }
        return list;
    }

    public static <T extends Object> List<T> removeAll(Collection<T> collection, Collection<T> remove) {
        List<T> list = new ArrayList<T>();
        for (Iterator<T> iter = collection.iterator(); iter.hasNext();) {
            T obj = iter.next();
            if (remove.contains(obj) == false) {
                list.add(obj);
            }
        }
        return list;
    }

    public static <T extends Object> void addAll(Collection<T> target, Collection<T> source) {
        if (target != null && source != null && source.size() > 0) {
            target.addAll(source);
        }
    }

    private static <K extends Number, V extends Object> Map<K, V> toNumberMap(Collection<V> values, String property,
                                                                              Number keyValue) {
        if (values == null) {
            return Collections.emptyMap();
        }
        Map<K, V> valueMap = new HashMap<K, V>(values.size());
        for (V value : values) {
            try {
                String keyValueStr = BeanUtils.getProperty(value, property);
                if (NumberUtils.isNumber(keyValueStr)) {
                    try {
                        // K valueTypeInstance = keyType.newInstance();
                        Object key = MethodUtils.invokeExactMethod(keyValue, "valueOf", keyValueStr);
                        valueMap.put((K) key, value);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Unsupport key Type：" + keyValue.getClass().getName(), e);
                    }
                } else {
                    throw new IllegalArgumentException("Expect：" + keyValue.getClass().getName() + ",Value Actul is "
                                                       + keyValueStr);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return valueMap;
    }

    public static <T extends Object> Map<Integer, T> toIntegerMap(Collection<T> values, String property) {
        return CollectionUtils.<Integer, T> toNumberMap(values, property, new Integer(0));
    }

    public static <T extends Object> Map<Long, T> toLongMap(Collection<T> values, String property) {
        return CollectionUtils.<Long, T> toNumberMap(values, property, new Long(0l));
    }

    public static <T extends Object> Map<Float, T> toFloatMap(Collection<T> values, String property) {
        return CollectionUtils.<Float, T> toNumberMap(values, property, new Float(0f));
    }

    public static <T extends Object> Map<Double, T> toDoubleMap(Collection<T> values, String property) {
        return CollectionUtils.<Double, T> toNumberMap(values, property, new Double(0f));
    }

    public static <T extends Object> Map<Object, Object> toMap(T[] beans, String keyProperty, String valueProperty) {
        return toMap(Wrapper.collection(beans), keyProperty, valueProperty);
    }

    private static <T extends Object, K extends Object, V extends Object> Map<K, V> toMap(Collection<T> beans,
                                                                                          String keyProperty,
                                                                                          String valueProperty) {
        Map<K, V> map = new HashMap<K, V>();
        if (Argument.isEmpty(beans)) {
            return map;
        }
        for (T bean : beans) {
            try {
                K key = (K) PropertyUtils.getProperty(bean, keyProperty);
                V value = null;
                if (valueProperty != null) {
                    value = (V) PropertyUtils.getProperty(bean, valueProperty);
                } else {
                    value = (V) bean;
                }
                if (key != null) {
                    map.put(key, value);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return map;
    }

    /**
     * 将一个集合对象变为HashMap。
     */
    public static <T extends Object> Map<String, T> toMap(Collection<T> values, String property) {
        return toMap(values, property, null);
    }

    /**
     * List<T> ---> Map<key,List<T>>
     */
    public static <T extends Object> Map<String, List<T>> toListMap(Collection<T> values, String property) {
        Map<String, List<T>> valueMap = new HashMap<String, List<T>>(values.size());
        for (T value : values) {
            try {
                String propertyValue = BeanUtils.getProperty(value, property);
                if (propertyValue == null) {
                    continue;
                }
                List<T> list = valueMap.get(propertyValue);
                if (list == null) {
                    list = new ArrayList<T>();
                    valueMap.put(propertyValue, list);
                }
                list.add(value);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueMap;
    }

    public static <T extends Object> Map<Long, List<T>> toLongListMap(Collection<T> values, String property) {
        Map<Long, List<T>> valueMap = new HashMap<Long, List<T>>(values.size());
        for (T value : values) {
            try {
                String propertyValue = BeanUtils.getProperty(value, property);
                Long longValue = NumberUtils.toLong(propertyValue);
                if (longValue == null) {
                    continue;
                }
                List<T> list = valueMap.get(longValue);
                if (list == null) {
                    list = new ArrayList<T>();
                    valueMap.put(longValue, list);
                }
                list.add(value);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueMap;
    }

    public static <M> void merge(M original, M destination, List<String> ignore) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(original.getClass());
        // Iterate over all the attributes
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            // Only copy writable attributes
            if (descriptor.getWriteMethod() != null) {
                Object originalValue = descriptor.getReadMethod().invoke(original);
                String attributeName = descriptor.getName();
                // ignore this values,do'not merge
                if (ignore != null && !ignore.isEmpty() && ignore.contains(attributeName)) {
                    continue;
                }
                Object defaultValue = descriptor.getReadMethod().invoke(destination);
                // Only copy values values where the destination values is null
                if (originalValue == null) {
                    descriptor.getWriteMethod().invoke(original, defaultValue);
                }
                if (defaultValue instanceof Number && originalValue instanceof Number) {
                    descriptor.getWriteMethod().invoke(original,
                                                       MathUtils.add((Number) originalValue, (Number) defaultValue));
                }
            }
        }
    }

    public static <T extends Object> T mergeList(List<T> list, List<String> ignore) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        if (list.size() == 2) {
            try {
                merge(list.get(0), list.get(1), ignore);
                return list.get(0);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
        }
        try {
            merge(list.get(0), mergeList(list.subList(1, list.size()), ignore), ignore);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return list.get(0);
    }

    public static Long[] getLongValueArrays(Collection<?> values, String property) {
        List<Long> result = getProperty(values, property, new Long(0));
        return result != null ? result.toArray(new Long[0]) : null;
    }

    public static List<Long> getLongValues(Collection<?> values) {
        return getLongValues(values, null);
    }

    public static List<Long> getLongValues(Collection<?> values, String property) {
        return getProperty(values, property, new Long(0));
    }

    public static List<Float> getFloatValues(Collection<?> values) {
        return getFloatValues(values, null);
    }

    public static Float[] getFloatValueArray(Collection<?> values) {
        List<Float> floatValues = getFloatValues(values);
        return floatValues != null ? floatValues.toArray(new Float[0]) : null;
    }

    public static List<Float> getFloatValues(Collection<?> values, String property) {
        return getProperty(values, property, new Float(0f));
    }

    public static List<Integer> getIntegerValues(Collection<?> values) {
        return getIntegerValues(values, null);
    }

    public static Integer[] getIntegerValueArray(Collection<?> values) {
        List<Integer> integerValues = getIntegerValues(values);
        return integerValues != null ? integerValues.toArray(new Integer[0]) : null;
    }

    public static Integer[] getIntegerValueArray(Collection<?> values, String property) {
        List<Integer> integerValues = getIntegerValues(values, property);
        return integerValues != null ? integerValues.toArray(new Integer[0]) : null;
    }

    public static List<Integer> getIntegerValues(Collection<?> values, String property) {
        return getProperty(values, property, new Integer(0));
    }

    public static List<Double> getDoubleValues(Collection<?> values) {
        return getDoubleValues(values, null);
    }

    public static Double[] getDoubleValueArray(Collection<?> values) {
        List<Double> result = getDoubleValues(values);
        return result != null ? result.toArray(new Double[0]) : null;
    }

    public static List<Double> getDoubleValues(Collection<?> values, String property) {
        return getProperty(values, property, new Double(0));
    }

    private static <T extends Number> List<T> getProperty(Collection<?> values, String property, T rawValue) {
        if (values == null || values.isEmpty()) {
            return Collections.<T> emptyList();
        }
        List<T> result = new ArrayList<T>(values.size());
        for (Object value : values) {
            try {
                String propertyValue = value.toString();
                if (property != null) {
                    propertyValue = BeanUtils.getProperty(value, property);
                }
                Object key = MethodUtils.invokeExactMethod(rawValue, "valueOf", propertyValue);
                result.add((T) key);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return result;
    }

    public static List<Number> getNumberProperty(Collection<?> values, String property) {
        if (values == null || values.isEmpty()) {
            return Collections.<Number> emptyList();
        }
        List<Number> valueResult = new ArrayList<Number>(values.size());
        for (Object value : values) {
            try {
                String propertyValue = BeanUtils.getProperty(value, property);
                Number createNumber = NumberUtils.createNumber(propertyValue);
                valueResult.add(createNumber);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueResult;
    }

    public static String[] getPropertyArray(Collection<?> values, String property) {
        if (values == null || values.isEmpty()) {
            return new String[0];
        }

        List<String> valueResult = new ArrayList<String>(values.size());
        for (Object value : values) {
            try {
                if (value == null) {
                    continue;
                }
                String propertyValue = BeanUtils.getProperty(value, property);
                valueResult.add(propertyValue);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueResult.toArray(new String[0]);
    }

    public static <T extends Object> boolean isAll(Collection<T> array, Op op, Collection<T> search) {
        return matchs(MatchMode.all, array, op, search);
    }

    public static <T extends Object> boolean isAll(Collection<T> array, Op op, T search) {
        return match(MatchMode.all, array, op, search);
    }

    public static <T extends Object> boolean isAny(Collection<T> array, Op op, T search) {
        return match(MatchMode.any, array, op, search);
    }

    public static <T extends Object> boolean isAny(Collection<T> array, Op op, Collection<T> search) {
        return matchs(MatchMode.any, array, op, search);
    }

    public static <T extends Object> boolean isAnyArray(T word, Op op, Collection<T> searhArray) {
        for (T search : searhArray) {
            if (op.match(word, search)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean matchs(MatchMode mode, Collection<T> array, Op op, Collection<T> searchArray) {
        for (T word : array) {
            switch (mode) {
                case all:
                    if (isAnyArray(word, op, searchArray) == false) {
                        return false;
                    }
                    break;
                case any:
                    if (isAnyArray(word, op, searchArray)) {
                        return true;
                    }
                    break;
            }
        }
        // 如果是全匹配模式，上面的循环里面任何一个不匹配就已经推广推出了。所以到这里就只有TRUE了
        // 如果是任一匹配模式，上面如果有任何一个匹配就已经推广了，到这里就只有FALSE了
        return mode.isAll() ? true : false;
    }

    public static <T> boolean match(MatchMode mode, Collection<T> array, Op op, T search) {
        if (search == null) {
            return false;
        }
        for (T source : array) {
            switch (mode) {
                case all:
                    if (op.match(source, search) == false) {
                        return false;
                    }
                    break;
                case any:
                    if (op.match(source, search) == true) {
                        return true;
                    }
                    break;
            }
        }
        // 如果是全匹配模式，上面的循环里面任何一个不匹配就已经推广推出了。所以到这里就只有TRUE了
        // 如果是任一匹配模式，上面如果有任何一个匹配就已经推广了，到这里就只有FALSE了
        return mode.isAll() ? true : false;
    }

    public static <T extends Object> List<T> filter(Collection<T> values, Grep<T> grep) {
        List<T> result = new ArrayList<T>(values.size());
        if (Argument.isEmpty(values)) {
            return result;
        }
        for (T value : values) {
            if (grep.grep(value)) {
                result.add(value);
            }
        }
        return result;
    }

    public static <T extends Object> Set<T> filterSet(Collection<T> values, Grep<T> grep) {
        Set<T> result = new HashSet<T>(values.size());
        if (Argument.isEmpty(values)) {
            return result;
        }
        for (T value : values) {
            if (grep.grep(value)) {
                result.add(value);
            }
        }
        return result;
    }

    public static <T extends Object> void remove(Collection<T> values, Grep<T> grep) {
        if (Argument.isEmpty(values)) {
            return;
        }
        Iterator<T> iterator = values.iterator();
        while (iterator.hasNext()) {
            if (grep.grep(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public static <T extends Object> void remove(Collection<T> values, Grep<T> grep, int limit) {
        Iterator<T> iterator = values.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            if (grep.grep(iterator.next())) {
                iterator.remove();
                i++;
            }
            if (i >= limit) {
                break;
            }
        }
    }

    public static interface Grep<T> {

        boolean grep(T value);

    }

    /**
     * 是匹配所有的关键词还是匹配某一个关键词
     */
    public static enum MatchMode {

        any("任何"), all("所有");

        private String desc;

        private MatchMode(String desc) {
            this.desc = desc;
        }

        public boolean isAll() {
            return this == all;
        }

        public boolean isAny() {
            return this == any;
        }

        public String getDesc() {
            return desc;
        }

    }

    /**
     * 操作
     */
    public static enum Op {
        equal,
        //
        equalIgnoreCase,
        //
        contain,
        //
        containIgnoreCase,

        startWith,

        endWith,

        containsBy;

        public boolean match(Object source, Object search) {
            String searchStr = search.toString(), sourceStr = source.toString();
            switch (this) {
                case equal:
                    return source.equals(search);
                case equalIgnoreCase:
                    return sourceStr.equalsIgnoreCase(searchStr);
                case contain:
                    return sourceStr.contains(searchStr);
                case containsBy:
                    return searchStr.contains(sourceStr);
                case containIgnoreCase:
                    return StringUtils.containsIgnoreCase(sourceStr, searchStr);
                case startWith:
                    return sourceStr.startsWith(searchStr);
                case endWith:
                    return sourceStr.endsWith(searchStr);
            }
            return false;
        }

    }

    public static boolean isEqualCollection(Collection<Long> currentCids, Collection<Long> newCids) {
        if (currentCids == null) {
            return newCids == null;
        } else if (newCids == null) {
            return false;
        }
        // nether are null
        else {
            return org.apache.commons.collections.CollectionUtils.isEqualCollection(currentCids, newCids);
        }
    }

    public static <T extends Object> List<T> join(String property, Collection<T>... arrays) {
        Map<String, T> resultMap = new HashMap<String, T>();
        for (Collection<T> array : arrays) {
            if (array == null || array.isEmpty()) continue;
            Map<String, T> map = toMap(array, property);
            resultMap.putAll(map);
        }
        return new ArrayList<T>(resultMap.values());
    }

    public static String[] getArrayProperty(Object[] objects, String property) {
        if (Argument.isNotEmptyArray(objects)) {
            return getPropertyArray(Wrapper.collection(objects), property);
        }
        return null;
    }

    public static <T extends Object> Map<Integer, List<T>> toIntegerListMap(List<T> values, String property) {
        Map<Integer, List<T>> valueMap = new HashMap<Integer, List<T>>(values.size());
        for (T value : values) {
            try {
                String propertyValue = BeanUtils.getProperty(value, property);
                Integer integerValue = NumberUtils.toInt(propertyValue);
                if (integerValue == null) {
                    continue;
                }
                List<T> list = valueMap.get(integerValue);
                if (list == null) {
                    list = new ArrayList<T>();
                    valueMap.put(integerValue, list);
                }
                list.add(value);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
        return valueMap;
    }

    public static <T extends Number, E extends Object> T sum(T rawValue, List<E> array,
                                                             ObjectConvert<E, ? extends Object> convert) {
        for (E obj : array) {
            String propertyValue = null;
            try {
                Object value = convert.convert(obj);
                if (value == null) {
                    continue;
                } else {
                    propertyValue = value.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (NumberUtils.isNumber(propertyValue) == false) {
                continue;
            }
            if (rawValue instanceof Integer) {
                rawValue = (T) new Integer(((Integer) rawValue) + NumberUtils.createInteger(propertyValue));
            } else if (rawValue instanceof Long) {
                rawValue = (T) new Long(((Long) rawValue) + NumberUtils.createLong(propertyValue));
            } else if (rawValue instanceof Float) {
                rawValue = (T) new Float(((Float) rawValue) + NumberUtils.createFloat(propertyValue));
            } else if (rawValue instanceof Double) {
                rawValue = (T) new Double(((Double) rawValue) + NumberUtils.createDouble(propertyValue));
            } else if (rawValue instanceof BigInteger) {
                rawValue = (T) NumberUtils.createBigInteger(propertyValue).add((BigInteger) rawValue);
            } else if (rawValue instanceof BigDecimal) {
                rawValue = (T) NumberUtils.createBigDecimal(propertyValue).add((BigDecimal) rawValue);
            }
        }
        return rawValue;
    }

    /**
     * 加总 Number value = sum(new Long(0),array,"value");
     */
    public static <T extends Number, E extends Object> T sum(T rawValue, List<E> array, final String property) {
        return sum(rawValue, array, new ObjectConvert<E, String>() {

            @Override
            public String convert(Object object) {
                try {
                    return BeanUtils.getProperty(object, property);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    public static <T extends Object, E extends Object> Map<E, List<T>> toListMap(List<T> nonChangedItem,
                                                                                 KeyGen<T, E> keyGen) {

        if (nonChangedItem == null) {
            return new HashMap<E, List<T>>(0);
        }

        Map<E, List<T>> result = new HashMap<E, List<T>>();
        for (T obj : nonChangedItem) {
            E key = keyGen.key(obj);
            List<T> list = result.get(key);
            if (list == null) {
                result.put(key, list = new ArrayList<T>());
            }
            list.add(obj);
        }

        return result;
    }

    public static interface KeyGen<VALUE_TYPE, KEY_TYPE> {

        KEY_TYPE key(VALUE_TYPE obj);

    }

    /**
     * 对象值转化，如果返回null，则视为转换失败
     * 
     * @author wanghai 2014年7月31日 下午8:35:27
     */
    public static interface ObjectConvert<E, T> {

        T convert(E object);
    }

    public static <T extends Object, E extends Object> List<T> getValues(Collection<E> values,
                                                                         ObjectConvert<E, T> propertyReader) {
        if (Argument.isEmpty(values)) {
            return new ArrayList<T>(0);
        }

        List<T> result = new ArrayList<T>(values.size());
        for (E obj : values) {
            T convert = propertyReader.convert(obj);
            if (convert != null) {
                result.add(convert);
            }
        }

        return result;

    }

    public static <KEY_TYPE extends Object, VALUE_TYPE extends Object> Map<KEY_TYPE, VALUE_TYPE> toMap(Collection<VALUE_TYPE> values,
                                                                                                       KeyGen<VALUE_TYPE, KEY_TYPE> keyGen) {
        if (values == null) {
            return new HashMap<KEY_TYPE, VALUE_TYPE>(0);
        }

        Map<KEY_TYPE, VALUE_TYPE> result = new HashMap<KEY_TYPE, VALUE_TYPE>(values.size());
        for (VALUE_TYPE v : values) {
            KEY_TYPE key = keyGen.key(v);
            result.put(key, v);
        }
        return result;
    }

    public static <VALUE_TYPE extends Object, KEY_TYPE extends Object> List<KEY_TYPE> convert(List<VALUE_TYPE> values,
                                                                                              ObjectConvert<VALUE_TYPE, KEY_TYPE> propertyReader) {
        if (values == null) {
            return new ArrayList<KEY_TYPE>(0);
        }
        List<KEY_TYPE> result = new ArrayList<KEY_TYPE>(values.size());
        for (VALUE_TYPE obj : values) {
            KEY_TYPE valueOf = propertyReader.convert(obj);
            if (valueOf != null) {
                result.add(valueOf);
            }
        }
        return result;
    }

    public static <VALUE_TYPE extends Object, KEY_TYPE extends Object> List<KEY_TYPE> convert(VALUE_TYPE[] values,
                                                                                              ObjectConvert<VALUE_TYPE, KEY_TYPE> propertyReader) {
        if (values == null) {
            return new ArrayList<KEY_TYPE>(0);
        }
        List<KEY_TYPE> result = new ArrayList<KEY_TYPE>(values.length);
        for (VALUE_TYPE obj : values) {
            KEY_TYPE valueOf = propertyReader.convert(obj);
            if (valueOf != null) {
                result.add(valueOf);
            }
        }
        return result;
    }

    public static <T extends Object> void realRemoveAll(Collection<T> array, Collection<T> c) {
        // 不能使用原生得removeAll方法
        for (Object obj : c) {
            array.remove(obj);
        }
    }
}
