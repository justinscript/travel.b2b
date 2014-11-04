/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zb.app.common.util.DateViewTools;

/**
 * 任务调度基类，所有任务或者定时Job都需要继承，并实现doTask方法，调用init()方法
 * 
 * @author zxc Jul 1, 2014 10:26:15 AM
 */
@Component
public abstract class AbstractTask {

    public static Logger logger = LoggerFactory.getLogger(AbstractTask.class);

    public abstract void initTask() throws Exception;

    public abstract void doTask() throws Exception;

    public AbstractTask() {
        init();
    }

    protected void init() {
        long start = System.currentTimeMillis();
        logger.debug("##################################cron task start####################################");
        logger.info("cron task start time: {}", DateViewTools.getCurrentYY_MM_DD_HMssSS());
        try {
            initTask();
        } catch (Exception e1) {
            logger.debug("##################################init task exception####################################");
        }

        try {
            doTask();
        } catch (Exception e) {
            logger.debug("##################################cron task exception####################################");
        }
        logger.debug("##################################cron task end####################################");
        long end = System.currentTimeMillis();
        logger.info("cron task end time: {};Time-consuming:{} Millisecond", DateViewTools.getCurrentYY_MM_DD_HMssSS(),
                    end - start);
    }
}
