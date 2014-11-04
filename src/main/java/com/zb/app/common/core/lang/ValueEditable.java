/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.util.Map;

/**
 * @author zxc Jun 16, 2014 12:22:47 AM
 */
public interface ValueEditable {

    /**
     * 更新属性的值，将更新后的值放到name2Values中去
     * 
     * @param raw 需要回去属性的对象
     * @param name2Values 当前对象的属性名和对应的值
     */
    @SuppressWarnings("rawtypes")
    void edit(Object raw, Map name2Values);
}
