/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 返回交通类型（1=飞机,2=汽车,3=火车,4=游船,5=待定）
 * 
 * @author zxc Jun 24, 2014 5:33:13 PM
 */
public enum TrafficBackTypeEnum {

    /**
     * 飞机
     */
    AIRCRAFT(0, "aircraft", "飞机"),
    /**
     * 汽车
     */
    CAR(1, "car", "汽车"),
    /**
     * 火车
     */
    TRAIN(2, "train", "火车"),
    /**
     * 游船
     */
    PLEASURE_BOAT(3, "pleasureBoat", "游船"),
    /**
     * 待定
     */
    PENDING(4, "pending", "待定");

    private int    value;
    private String name;
    private String desc;

    private TrafficBackTypeEnum(int value, String name, String desc) {
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
    public static TrafficBackTypeEnum getEnum(int value) {
        for (TrafficBackTypeEnum current : values()) {
            if (current.value == value) {
                return current;
            }
        }
        return null;
    }
}
