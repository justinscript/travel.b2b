/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.psbc.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author zxc Aug 8, 2014 1:35:53 PM
 */
public abstract class PSBCUtil {

    public PSBCUtil() {
    }

    public static boolean isEmptywithTrim(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isEmpty(Object array[]) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
