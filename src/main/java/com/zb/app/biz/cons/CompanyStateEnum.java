/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 公司状态（0=未审核，1=正常，2=停止）
 * 
 * @author ZhouZhong 2014-7-1 下午5:39:05
 */
public enum CompanyStateEnum {

    /**
     * 未审核
     */
    UNAUDITED(0, "unaudited", "未审核"),
    /**
     * 正常
     */
    NORMAL(1, "normal", "正常"),
    /**
     * 停止
     */
    STOP(2, "stop", "停止"),
    /**
     * 黑名单
     */
    BLACK(3, "black", "黑名单");

    private int    value;
    private String name;
    private String desc;

    private CompanyStateEnum(int value, String name, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据value获取类型
     */
    public static CompanyStateEnum getEnum(int value) {
        for (CompanyStateEnum current : values()) {
            if (current.value == value) {
                return current;
            }
        }
        return null;
    }
}
