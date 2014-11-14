/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * 生成序列号,流水号
 * 
 * <pre>
 * 序列号规则：
 *      第0位特殊开始位（F:测试订单,C:account订单，T:tour订单)
 *      第[1,6]位时当前日期（精确到天），第[7,最有一位]位时当前订单数量。
 *      例如：2014年8月29日的第100个订单的序列号就是：
 *      201408291000
 * </pre>
 * 
 * @author zxc Aug 8, 2014 2:04:45 PM
 */
public class SerialNumGenerator {

    public static String        c_prefix          = "C";
    public static String        t_prefix          = "T";
    public static String        l_prefix          = "L";
    public static String        p_prefix          = "P";

    private static final String DATEFORMATTOR_KEY = "yyyyMMdd";

    public static String createTradeNo(Long id) {
        return createSerNo(id, c_prefix);
    }

    public synchronized static String createSerNo(Long id, String prefix) {
        String middle = DateViewTools.format(new Date(), DATEFORMATTOR_KEY);
        String num = getSuffix(id);
        return prefix + middle + num;
    }

    public synchronized static String createProductSerNo(Long id) {
        return getSuffix(id);
    }

    private static String getSuffix(Long id) {
        Long num = 1l;
        Random random = new Random();
        if (id != null) {
            num = id * (random.nextInt(1000));
            if (num < 100) {
                num = num * (random.nextInt());
            }
            num = (id * (random.nextInt(1000))) % 100000;// 对id进行取模运算
        }

        StringBuffer suffixBuffer = new StringBuffer();
        suffixBuffer.append(String.valueOf(num)).append(random.nextInt(1000));
        return StringUtils.leftPad(suffixBuffer.toString(), 8, '0');
    }

    public static void main(String[] args) {
        for (long i = 1; i < 100000; i++) {
            String id = createProductSerNo(i);
            System.out.println(id);
            System.out.println(id.length());
            System.out.println(id.length() == 8);
        }
    }
}
