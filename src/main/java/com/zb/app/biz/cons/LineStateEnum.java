/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/***
 * 状态（0=正常,1=停止,2=客满,3=过期）
 * 
 * @author Administrator 2014-7-3 下午5:39:29
 */
public enum LineStateEnum {

    NORMAL(0, "预定"), STOP(1, "停止"), FULL(2, "客满"), EXPIRE(3, "过期"), END(4, "截止");

    private int    value;
    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private LineStateEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据value获取类型
     */
    public static LineStateEnum getEnum(int value) {
        for (LineStateEnum current : values()) {
            if (current.value == value) {
                return current;
            }
        }
        return null;
    }
}
