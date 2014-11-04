/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.base;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxc Jul 25, 2014 10:23:03 AM
 */
public interface IBase<T extends Serializable> {

    static Logger       log   = LoggerFactory.getLogger(IBase.class);

    static final String POINT = ".";

    String getNameSpace();

    Class<T> getEntityClass();
}
