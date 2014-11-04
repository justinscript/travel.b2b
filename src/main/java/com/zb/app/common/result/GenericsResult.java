/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.result;


/**
 * @author zxc Jun 15, 2014 11:17:18 PM
 */
public class GenericsResult<T> implements IResult {

    private boolean success;
    private String  message;
    private T       data;

    private String  subMsg;
    private String  errorCode;

    public GenericsResult(boolean success, String message) {
        this(success, message, null);
    }

    public GenericsResult(boolean success, T data) {
        this(success, null, data);
    }

    public GenericsResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public GenericsResult(boolean success, String message, String subMsg, String errorCode, T data) {
        this.success = success;
        this.message = message;
        this.subMsg = subMsg;
        this.errorCode = errorCode;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public static <T> GenericsResult<T> successResult() {
        return (GenericsResult<T>) new SuccessResult();
    }

    public static <T> GenericsResult<T> successResult(T data) {
        return new GenericsResult<T>(true, null, data);
    }

    public static <T> GenericsResult<T> successResult(String message, T data) {
        return new GenericsResult<T>(true, message, data);
    }

    @SuppressWarnings("unchecked")
    public static <T> GenericsResult<T> failResult() {
        return (GenericsResult<T>) new FailResult();
    }

    public static <T> GenericsResult<T> failResult(String message) {
        return new GenericsResult<T>(false, message, null);
    }

    public static <T> GenericsResult<T> failResult(String subMsg, String errorCode) {
        return new GenericsResult<T>(false, subMsg + errorCode, subMsg, errorCode, null);
    }

    public static <T> GenericsResult<T> failResult(String message, T data) {
        return new GenericsResult<T>(false, message, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message.toString();
    }

    public T getData() {
        return data;
    }

    public boolean dataIsNull() {
        return data == null;
    }

    private static class SuccessResult extends GenericsResult<Object> {

        public SuccessResult() {
            super(true, null, null);
        }
    }

    private static class FailResult extends GenericsResult<Object> {

        public FailResult() {
            super(false, null, null);
        }
    }

    public String getSubMsg() {
        return subMsg;
    }

    @Override
    public boolean isFailed() {
        return !isSuccess();
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getDesc() {
        return getSubMsg();
    }
}
