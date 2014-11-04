/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.cons;

/**
 * @author zxc Jul 25, 2014 2:21:37 PM
 */
public enum ActionEnum {
    // Client注册服务
    CLIENT_REGIST(1),

    // Client注册服务失败,可能未登录
    CLIENT_REGIST_FAILED(2),

    // Client取消注册服务,客户端主动关闭连接
    CLIENT_CLOSED(3),

    // Client处理完后返回结果
    CLIENT_RETURN_RESULT(4),

    // Client主动请求数据,Client发送消息
    CLIENT_SEND_MESSAGE(5),

    // Client端心跳检测
    CLIENT_HEARTBEAT(6),

    // Server发送消息
    SERVER_SEND_MESSAGE(7),

    // Server主动关闭连接
    SERVER_CLOSED(8),

    // Server端心跳检测
    SERVER_HEARTBEAT(9),

    // 错误数据格式
    ERROR_FORMAT_MESSAGE(10);

    public int value;

    private ActionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ActionEnum getAction(int value) {
        for (ActionEnum action : values()) {
            if (value == action.getValue()) return action;
        }
        return null;
    }
}
