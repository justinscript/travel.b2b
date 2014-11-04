/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.component.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 绑定请求参数到模型，并且暴露到模型中供页面使用
 * </p>
 * <p>
 * 不同于@ModelAttribute
 * </p>
 * 
 * @author zxc Jul 21, 2014 2:09:31 PM
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormBean {

    /**
     * 指定请求参数的前缀和暴露到模型对象的名字供视图使用
     * <p>
     * 1、绑定请求参数到模型，绑定规则<br/>
     * 如请求表单：<br>
     * 
     * <pre>
     *    <input name="student.name" value="zxc" /><br>
     *    <input name="student.type" value="male" /><br>
     * </pre>
     * 
     * 则请求处理方法：<br>
     * 
     * <pre>
     *    @RequestMapping(value = "/test")  
     *    public String test(@FormModel("user") User user) //这样将绑定  user.name user.type两个参数
     * </pre>
     * 
     * 而springmvc默认<br>
     * 如请求表单：<br>
     * 
     * <pre>
     *    <input name="name" value="zxc" /><br>
     *    <input name="type" value="female" /><br>
     * </pre>
     * 
     * 则请求处理方法：<br>
     * 
     * <pre>
     *    public String test(@ModelAttribute("user") User user) //这样将绑定name type两个参数
     * </pre>
     * 
     * 2、根据value中的名字暴露到模型对象中供视图使用
     */
    String value();
}
