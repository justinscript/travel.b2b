/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.comset.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.comset.cons.RecordEnum;
import com.zb.app.comset.manager.ResourceHelper;

/**
 * @author zxc Jul 23, 2014 12:11:33 PM
 */
public class ServiceMethodAdviceInterceptor implements MethodInterceptor {

    public static Logger logger = LoggerFactory.getLogger(BaseDao.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.debug("ServiceMethodAdvice:invoke 事务");
        long start = System.currentTimeMillis();
        Object invokeResult = invocation.proceed();
        long end = System.currentTimeMillis();
        String thisClassName = invocation.getThis().getClass().getSimpleName();
        if (thisClassName.indexOf("$$") == -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(thisClassName).append(".").append(invocation.getMethod().getName()).append("()");
            ResourceHelper.recordRunTime(RecordEnum.SERVICE, sb.toString(), end - start);
        }
        return invokeResult;
    }
}
