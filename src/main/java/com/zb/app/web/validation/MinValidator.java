/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zb.app.web.validation.annotation.Min;

/**
 * @author zxc Jul 2, 2014 3:09:12 PM
 */
public class MinValidator implements ConstraintValidator<Min, Integer> {

    private int minValue;

    public void initialize(Min min) {
        // 把Min限制类型的属性value赋值给当前ConstraintValidator的成员变量minValue
        minValue = min.value();
    }

    public boolean isValid(Integer value, ConstraintValidatorContext arg1) {
        // 在这里我们就可以通过当前ConstraintValidator的成员变量minValue访问到当前限制类型Min的value属性了
        return value >= minValue;
    }
}
