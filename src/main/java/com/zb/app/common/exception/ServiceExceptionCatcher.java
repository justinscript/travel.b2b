/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

/**
 * @author zxc Jun 18, 2014 10:51:17 AM
 */
public class ServiceExceptionCatcher {

    protected static final Log log = LogFactory.getLog(ServiceExceptionCatcher.class);

    public void log(JoinPoint proxy, Throwable exception) throws ServiceException {

        if (exception instanceof ServiceException) {
            return;
        }

        Object target = proxy.getTarget();
        log.error(target, exception);
        throw new ServiceException(exception);
    }
}
