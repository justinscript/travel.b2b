/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zxc Jul 15, 2014 5:28:23 PM
 */
public class ConcurrentHashSet<E> extends MapBackedSet<E> {

    private static final long serialVersionUID = 1518578988740277828L;

    public ConcurrentHashSet() {
        super(new ConcurrentHashMap<E, Boolean>());
    }

    public ConcurrentHashSet(Collection<E> c) {
        super(new ConcurrentHashMap<E, Boolean>(), c);
    }

    @Override
    public boolean add(E o) {
        Boolean answer = ((ConcurrentMap<E, Boolean>) map).putIfAbsent(o, Boolean.TRUE);
        return answer == null;
    }
}
