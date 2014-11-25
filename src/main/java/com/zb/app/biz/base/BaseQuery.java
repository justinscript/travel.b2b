/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Aug 20, 2014 10:23:59 PM
 */
@SuppressWarnings("unchecked")
public abstract class BaseQuery<T extends Serializable> extends Pagination {

    protected T      entity;

    private Class<T> entityClass;

    public BaseQuery() {
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }

        if (entity == null && entityClass != null) {
            try {
                entity = entityClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public T getT() {
        return entity;
    }

    public void setT(T t) {
        this.entity = t;
    }
}
