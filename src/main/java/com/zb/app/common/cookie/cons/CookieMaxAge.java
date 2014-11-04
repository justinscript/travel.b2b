/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.cookie.cons;

/**
 * @author zxc Jul 3, 2014 4:43:08 PM
 */
public final class CookieMaxAge {

    /***
     * 用于临时Cookie的MaxAge
     */
    public static final int TEMP        = -1;
    /***
     * 用于永久的Cookie的MaxAge
     */
    public static final int FOREVER     = Integer.MAX_VALUE;
    /***
     * 用于删除Cookie
     */
    public static final int OUT_OF_DATE = 0;
}
