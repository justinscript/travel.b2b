/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 1:欠款 ； 2：不欠
 * 
 * @author Administrator 2014-8-26 下午4:35:53
 */
public enum FinanceTypeEnum {

    ARREARS(1, "欠款"), NOOWE(2, "不欠");

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

    private FinanceTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
