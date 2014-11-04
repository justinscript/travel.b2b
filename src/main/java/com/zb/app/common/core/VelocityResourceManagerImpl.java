/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.velocity.runtime.RuntimeServices;

/**
 * 用于清除Velocity中vm的缓存
 * 
 * @author zxc Jul 2, 2014 11:49:51 AM
 */
@SuppressWarnings("rawtypes")
public class VelocityResourceManagerImpl extends org.apache.velocity.runtime.resource.ResourceManagerImpl {

    public static VelocityResourceManagerImpl instance;

    public synchronized void initialize(final RuntimeServices rsvc) {
        try {
            super.initialize(rsvc);
            log.error("zxc ! Velocity initialize globalCache =" + globalCache);
            instance = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        Iterator ir = globalCache.enumerateKeys();
        List<Object> keyList = new ArrayList<Object>();
        while (ir.hasNext()) {
            Object key = ir.next();
            if (key != null) {
                keyList.add(key);
            }
        }
        for (Object key : keyList) {
            log.error("Clear velocity cache . key=" + key);
            globalCache.remove(key);
        }
    }
}
