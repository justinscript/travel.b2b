/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.result;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc Jun 16, 2014 12:35:04 AM
 */
public class Result implements IResult {

    private boolean       isSuccess;
    private StringBuilder message = new StringBuilder();
    private Object        data;

    public static Result success() {
        return new Result().setSuccess(true);
    }

    public static Result success(String message) {
        return new Result().setSuccess(true).setMessage(message);
    }

    public static Result success(Object data) {
        return new Result().setSuccess(true).setData(data);
    }

    public static Result success(String message, Object data) {
        return new Result().setSuccess(true).setMessage(message).setData(data);
    }

    public static Result failed() {
        return new Result().setSuccess(false).setMessage("失败");
    }

    public static Result failed(String msg) {
        return new Result().setSuccess(false).setMessage(msg);
    }

    public static Result failed(Object data) {
        return new Result().setSuccess(false).setData(data);
    }

    public static Result failed(String message, Object data) {
        return new Result().setSuccess(false).setMessage(message).setData(data);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public Result setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }

    public String getMessage() {
        return message.toString();
    }

    public Result setMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            this.message = new StringBuilder(message);
        } else {
            this.message = new StringBuilder();
        }
        return this;
    }

    public Result appendMessage(String message) {
        this.message.append(message).append(";");
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String getErrorCode() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }
}
