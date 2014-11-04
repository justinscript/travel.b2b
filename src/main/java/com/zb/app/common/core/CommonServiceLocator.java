/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zxc Jun 18, 2014 4:06:35 PM
 */
public class CommonServiceLocator {

    protected static ApplicationContext context;

    private static Logger               log = LoggerFactory.getLogger(CommonServiceLocator.class);

    static {
        try {
            context = new ClassPathXmlApplicationContext(new String[] { "classpath*:/config/spring/*.xml" });
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public static boolean hasInitFinish() {
        return context != null;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String beanId) {
        return context.getBean(beanId);
    }

    /**
     * 当JVM退出时（例如Jetty or tomcat关闭），调用Spring容器的Destory方法，目的是让应用完成一些后续操作。<br>
     * 该方法仅仅在JVM退出前调用，否则可能存在问题。
     */
    public static void destory() {
        if (context instanceof AbstractApplicationContext) {
            ((AbstractApplicationContext) context).destroy();
            log.info("Close ApplicationContext! class=" + context.getClass().getName());
        }
    }
}
