/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * @author Administrator 2014-8-7 下午2:45:29
 */
public enum LineTypeEnum {

    NEW(0, "NEW"), HOT(1, "热销"), RECOMMENDED(2, "推荐"), SPECIAL(3, "特价"), LUXURY(4, "豪华"), PLAY(5, "纯玩"), BOOKING(6,
                                                                                                                 "预约"),
    TRAIN(7, "火车专列"), FREE(8, "自由行"), NATIONAL(9, "国庆线"), NEWYEAR(10, "春节线"), SUMMERCAMP(11, "夏令营"), SUN(12, "夕阳红");

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

    private LineTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static LineTypeEnum getAction(int value) {
        for (LineTypeEnum type : values()) {
            if (value == type.getValue()) return type;
        }
        return null;
    }
}
