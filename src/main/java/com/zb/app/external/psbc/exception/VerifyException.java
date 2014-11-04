/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc.exception;

/**
 * @author zxc Aug 8, 2014 1:41:59 PM
 */
public class VerifyException extends RuntimeException {

    private static final long serialVersionUID = 0x6c1cdb15f41bfc6L;

    public VerifyException() {

    }

    public VerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyException(String message) {
        super(message);
    }

    public VerifyException(Throwable cause) {
        super(cause);
    }
}
