/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Jun 26, 2014 1:56:25 PM
 */
public interface BaseService {

    public final int     max_size = 2000;

    public static Logger logger   = LoggerFactory.getLogger(BaseService.class);
}
