/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc.exception;

/**
 * @author zxc Aug 8, 2014 1:37:37 PM
 */
public class SignException extends RuntimeException {

    private static final long serialVersionUID = 0x9d781e8ed84c2db2L;

    public SignException() {
    }

    public SignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignException(String message) {
        super(message);
    }

    public SignException(Throwable cause) {
        super(cause);
    }
}
