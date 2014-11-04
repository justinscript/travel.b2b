/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.exception;

/**
 * @author zxc Jul 30, 2014 6:03:28 PM
 */
public class WebSocketException extends RuntimeException {

    private static final long serialVersionUID = 457102044387061473L;

    public WebSocketException(String msg) {
        super(msg);
    }

    public WebSocketException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
