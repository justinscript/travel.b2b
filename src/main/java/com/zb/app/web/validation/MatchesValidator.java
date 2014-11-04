/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.validation;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import com.zb.app.web.validation.annotation.Matches;

/**
 * <pre>
 * 
 * @Matches(field = "password", verifyField = "confirmPassword",message = "two password not the same")
 * 
 * <pre>
 * 
 * @author zxc Jul 2, 2014 3:18:06 PM
 */
@SuppressWarnings("deprecation")
public class MatchesValidator implements ConstraintValidator<Matches, Object> {

    private String field;
    private String verifyField;

    public void initialize(Matches matches) {
        this.field = matches.field();
        this.verifyField = matches.verifyField();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            String fieldValue = BeanUtils.getProperty(value, field);
            String verifyFieldValue = BeanUtils.getProperty(value, verifyField);
            boolean valid = (fieldValue == null) && (verifyFieldValue == null);
            if (valid) {
                return true;
            }

            boolean match = (fieldValue != null) && fieldValue.equals(verifyFieldValue);
            if (!match) {
                String messageTemplate = context.getDefaultConstraintMessageTemplate();
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(messageTemplate).addNode(verifyField).addConstraintViolation();
            }
            return match;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return true;
    }
}
