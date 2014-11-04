/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * @author Administrator 2014年10月9日 下午2:56:56
 */
public enum LineDayEnum {

    ONE(1, "1天"), TWO(2, "2天"), THREE(3, "3天"), FOUR(4, "4天"), FIVE(5, "5天"), SIX(6, "6天"), SEVEN(7, "7天"),

    EIGHT(8, "8天"), NINE(9, "9天"), TEN(10, "10天");

    private Integer value;
    private String  name;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private LineDayEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
