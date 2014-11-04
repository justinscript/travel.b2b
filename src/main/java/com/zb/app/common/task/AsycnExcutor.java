/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步任务执行器，对于一些边缘业务（比如发送注册成功邮件）需要单独启动线程执行。
 * 
 * @author zxc Aug 8, 2014 1:28:10 PM
 */
public class AsycnExcutor {

    private static final Logger logger = LoggerFactory.getLogger(AsycnExcutor.class);

    public static void excutor(final Runnable task) {
        new Thread() {

            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            };
        }.start();
    }
}
