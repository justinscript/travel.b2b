/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.notify.event;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zxc Jul 24, 2014 4:55:18 PM
 */
public interface Event extends Serializable {

    /**
     * 事件类型
     */
    EventType getEventType();

    /**
     * 事件参数
     */
    <T extends Object> T getData();

    /**
     * 事件的简要描述
     */
    String summary();

    /**
     * 时间发生的时间
     */
    Date time();
}
