/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zb.app.common.cookie.cons.CookieDomain;
import com.zb.app.common.cookie.cons.CookieMaxAge;
import com.zb.app.common.cookie.cons.CookiePath;

/**
 * @author zxc Jul 3, 2014 4:43:08 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CookieNamePolicy {

    /**
     * cookie 域
     * 
     * @return
     */
    CookieDomain domain();

    /**
     * cookie的生命周期
     * 
     * @return
     */
    int maxAge() default CookieMaxAge.FOREVER;

    /***
     * 是否对cookie加密
     * 
     * @return
     */
    boolean isEncrypt() default true;

    /***
     * 返回cookie所在的域
     * 
     * @return
     */
    CookiePath path() default CookiePath.ROOT;

    /**
     * 是否是单一的值，没有kev-value对的。注意，如果是，一定要设置为true
     */
    boolean isSimpleValue() default false;
}
