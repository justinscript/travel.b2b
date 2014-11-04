/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * @author ZhouZhong 2014-7-4 上午11:35:53
 */
public enum MemberSexEnum {

    /**
     * 男
     */
    MAN(0, "man", "男"),
    /**
     * 女
     */
    WOMAN(1, "woman", "女");

    private int    value;
    private String name;
    private String desc;

    private MemberSexEnum(int value, String name, String desc) {
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
    public static MemberSexEnum getEnum(int value) {
        for (MemberSexEnum current : values()) {
            if (current.value == value) {
                return current;
            }
        }
        return null;
    }
}
