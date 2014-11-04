/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.exception;

/**
 * @author zxc Jul 31, 2014 1:35:27 PM
 */
public class WebSocketDataFormatException extends RuntimeException {

    private static final long serialVersionUID = 157102044387063473L;

    public WebSocketDataFormatException(String msg) {
        super(msg);
    }

    public WebSocketDataFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
