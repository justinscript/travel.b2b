/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zb.app.web.validation.annotation.Money;

/**
 * @author zxc Jul 2, 2014 3:07:28 PM
 */
public class MoneyValidator implements ConstraintValidator<Money, Double> {

    private String  moneyReg     = "^\\d+(\\.\\d{1,2})?$";   // 表示金额的正则表达式
    private Pattern moneyPattern = Pattern.compile(moneyReg);

    public void initialize(Money money) {

    }

    public boolean isValid(Double value, ConstraintValidatorContext arg1) {
        return value == null ? true : moneyPattern.matcher(value.toString()).matches();
    }
}
