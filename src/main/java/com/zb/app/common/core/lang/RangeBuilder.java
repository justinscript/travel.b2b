/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <pre>
 * 注意：
 *      需要比较的属性必须实现Comparable接口。
 *      Number类型，String类型，原生的类型都可以支持
 * 使用方法：
 *      RangeBuilder.data(data).property("name").keyName("newName").range();
 *      RangeBuilder.data(data).property("age").desc().range();
 *      RangeBuilder.data(data).property("id").asc().range();
 * </pre>
 * 
 * @author zxc Jul 17, 2014 1:48:25 PM
 */
public class RangeBuilder {

    public static class Range {

        public Range(boolean isAsc, String keyName, Object min, Object max) {
            this.setKey(keyName);
            this.setMin(min);
            this.setMax(max);
            this.setAsc(isAsc);
        }

        public Object getMin() {
            return min;
        }

        public void setMin(Object min) {
            this.min = min;
        }

        public Object getMax() {
            return max;
        }

        public void setMax(Object max) {
            this.max = max;
        }

        private String  key;
        private Object  min;
        private Object  max;
        private boolean asc = true;

        @Override
        public String toString() {
            return getKey() + ":[" + min + "," + max + "]";
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }

    }

    @SuppressWarnings("rawtypes")
    private Collection   data;
    private String       property;
    private String       keyName;
    private List<Object> values = new ArrayList<Object>();
    private boolean      asc    = true;

    public static RangeBuilder data(Collection<?> data) {
        RangeBuilder rangeBuilder = new RangeBuilder();
        rangeBuilder.data = data;
        return rangeBuilder;
    }

    public static <T extends Object> RangeBuilder data(T[] data) {
        RangeBuilder rangeBuilder = new RangeBuilder();
        rangeBuilder.data = Wrapper.collection(data);
        return rangeBuilder;
    }

    public RangeBuilder property(String property) {
        this.property = property;
        if (this.keyName == null) {
            this.keyName = property;
        }
        return this;
    }

    public RangeBuilder keyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public RangeBuilder defaultValue(Object... defaultValues) {
        values.addAll(Wrapper.collection(ArrayUtils.removeNullElement(defaultValues)));
        return this;
    }

    public RangeBuilder asc() {
        this.asc = true;
        return this;
    }

    public RangeBuilder desc() {
        this.asc = false;
        return this;
    }

    private Object[] _range(boolean asc, List<Object> values) {
        Object min = null, max = null;
        for (Object value : values) {
            if (biggerThan(value, max)) {
                max = value;
            }
            if (smallThan(value, min)) {
                min = value;
            }
        }
        return asc ? new Object[] { min, max } : new Object[] { max, min };
    }

    public Range range() {
        for (Object obj : data) {
            try {
                // 原生对象也会相应的被包装
                Object value = PropertyUtils.getSimpleProperty(obj, property);
                values.add(value);
            } catch (Exception e) {
                throw new RuntimeException(ToStringBuilder.reflectionToString(obj) + "has not property named "
                                           + property, e);
            }
        }
        Object[] range = _range(asc, values);
        return new Range(asc, keyName, range[0], range[1]);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean smallThan(Object value, Object min) {
        if (min == null) {
            return value != null;
        }
        if (value == null) {
            return false;
        }
        if (value instanceof Comparable) {
            return ((Comparable) value).compareTo(min) < 0;
        } else {
            // 无法比较
            throw new RuntimeException("RangeBuilder只能支持那些Camparable的属性");
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean biggerThan(Object value, Object max) {
        if (max == null) {
            return value != null;
        }
        if (value == null) {
            return false;
        }
        if (value instanceof Comparable) {
            return ((Comparable) value).compareTo(max) > 0;
        } else {
            // 无法比较
            throw new RuntimeException("RangeBuilder只能支持那些Camparable的属性");
        }
    }
}
