/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cons;

/**
 * @author zxc Jun 19, 2014 10:28:31 AM
 */
public enum ResultCode {
    // 成功
    SUCCESS(0),
    // 失败
    ERROR(1),
    // 未登录
    NEED_LOGIN(2),
    // 重复提交
    SUBMITED(3),
    // 重定向
    FORBIDDEN(4),
    // 心跳
    HEARTBEAT(6),
    // 错误数据格式
    ERROR_FORMAT(10);

    public int value;

    private ResultCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isError() {
        return this == ERROR;
    }

    public boolean isNeedLogin() {
        return this == NEED_LOGIN;
    }

    public boolean isSubmited() {
        return this == SUBMITED;
    }

    public static boolean isSuccess(int value) {
        return SUCCESS.getValue() == value;
    }

    public static boolean isError(int value) {
        return ERROR.getValue() == value;
    }

    public static boolean isNeedLogin(int value) {
        return NEED_LOGIN.getValue() == value;
    }

    public static boolean isSubmited(int value) {
        return SUBMITED.getValue() == value;
    }

    public static ResultCode getEnum(int value) {
        for (ResultCode code : values()) {
            if (value == code.getValue()) return code;
        }
        return null;
    }
}
