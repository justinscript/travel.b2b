/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.exception;

/**
 * @author zxc Sep 4, 2014 12:21:05 AM
 */
public class UnSupportBaseDaoException extends RuntimeException {

    private static final long serialVersionUID = -246345309677115693L;

    public UnSupportBaseDaoException() {

    }

    public UnSupportBaseDaoException(String message) {
        super(message);
    }

    public UnSupportBaseDaoException(Throwable cause) {
        super(cause);
    }

    public UnSupportBaseDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
