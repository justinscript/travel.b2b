/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.security;

import java.util.UUID;

/**
 * @author zxc Jul 3, 2014 4:49:13 PM
 */
public class UUIDGenerator {

    public static String createUUID() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    public static void main(String[] args) {
        System.out.println(createUUID());
        System.out.println(EncryptBuilder.getInstance().encrypt(Long.toString(System.nanoTime()), "test$@2014061911"));
    }
}
