/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.comset.interceptor;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.comset.cons.RecordEnum;
import com.zb.app.comset.manager.ResourceHelper;

/**
 * @author zxc Jul 23, 2014 11:37:31 AM
 */
public class SqlMapClientInterceptor implements MethodInterceptor {

    public static Logger logger   = LoggerFactory.getLogger(SqlMapClientInterceptor.class);

    private Object       object;

    private Enhancer     enhancer = new Enhancer();

    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;

        long startTime = System.currentTimeMillis();

        result = proxy.invoke(object, args);
        String statementName = "Unknown";
        if (args.length > 0 && args[0] instanceof String) {
            statementName = (String) args[0];
        }

        long useTime = System.currentTimeMillis() - startTime;

        ResourceHelper.recordRunTime(RecordEnum.DB, statementName, useTime);

        try {
            logSql(statementName, args, useTime);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    public Object proxy(Object object) {
        this.object = object;
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    private boolean isRequireSyslog() {
        // if (SYSLOG.isSyslogOn()) {
        // if (SYSLOG.isEnableClass(SqlLogObject.class)) {
        // return true;
        // } else {
        // return false;
        // }
        // } else {
        // return false;
        // }
        return true;
    }

    private void logSql(String statement, Object[] args, long time) {
        if (!isRequireSyslog()) return;
        if (args == null || args.length <= 0) {
            return;
        }
        if (args[0] != null && args[0].getClass() == String.class) {
            if (args.length == 1) {
                // 没有参数
                logger.debug(statement, null, time);
            } else {
                // 参数是从第二个开始的，第一个是statement
                Object[] dest = new Object[args.length - 1];
                System.arraycopy(args, 1, dest, 0, args.length - 1);
                logger.debug(statement, dest, time);
            }
        }
    }
}
