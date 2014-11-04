/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.result;

/**
 * Result 通用返回结果包装类
 * 
 * <pre>
 * 用于方法的返回值
 *      很多方法除了要返回成功与否，还要返回错误信息和数据
 *      isSuccess()得到操作是否成功
 *      getMessage()得到错误信息
 *      getData()数据
 * </pre>
 * 
 * @author zxc Jun 15, 2014 11:16:31 PM
 */
public interface IResult {

    boolean isSuccess();

    boolean isFailed();

    String getErrorCode();

    String getDesc();

    String toString();
}
