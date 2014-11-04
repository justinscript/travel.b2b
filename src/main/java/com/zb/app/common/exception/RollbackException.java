/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.exception;

import org.springframework.transaction.TransactionException;

/**
 * @author zxc Jun 17, 2014 2:05:02 PM
 */
public class RollbackException extends TransactionException {

    private static final long serialVersionUID = -8139406723353941539L;

    public RollbackException(String msg) {
        super(msg);
    }

    public RollbackException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
