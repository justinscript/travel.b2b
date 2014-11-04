/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.api;

import com.zb.app.common.result.GenericsResult;

/**
 * @author zxc Jul 24, 2014 4:27:22 PM
 */
public interface IMessageHandler {

    <M> GenericsResult<M> exec(Object... obj);

    Long weghit();
}
