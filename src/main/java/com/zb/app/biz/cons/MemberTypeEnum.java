/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * @author ZhouZhong 2014-7-4 上午11:35:22
 */
public enum MemberTypeEnum {

    /**
     * 超级管理员
     */
    SUPERADMIN(0, "superAdmin", "超级管理员"),
    /**
     * 普通管理员
     */
    COMMONADMIN(1, "commonAdmin", "普通管理员");

    private int    value;
    private String name;
    private String desc;

    private MemberTypeEnum(int value, String name, String desc) {
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

    public static boolean isSupper(MemberTypeEnum typeEnum) {
        return typeEnum == null ? false : typeEnum.value == SUPERADMIN.value;
    }

    /**
     * 根据value获取类型
     */
    public static MemberTypeEnum getEnum(Integer value) {
        if (value == null) {
            return COMMONADMIN;
        }
        for (MemberTypeEnum current : values()) {
            if (current.value == value) {
                return current;
            }
        }
        return COMMONADMIN;
    }
}
