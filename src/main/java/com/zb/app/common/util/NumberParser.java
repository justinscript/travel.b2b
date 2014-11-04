/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 数字处理工具类
 * 
 * @author zxc Jun 16, 2014 12:30:24 AM
 */
public class NumberParser {

    public static boolean isNumber(String number) {
        String pattern = "[0-9]+(.[0-9]+)?";
        // 对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
        // (.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * 使用率计算
     * 
     * @return
     */
    public static String fromUsage(long free, long total) {
        Double d = new BigDecimal(free * 100 / total).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(d);
    }

    public static Number parserNumber(String number) {
        try {
            return NumberUtils.createNumber(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int convertToInt(Long price, int defaultValue) {
        if (price == null) {
            return defaultValue;
        }
        return parseInt(price.toString(), defaultValue);
    }

    public static float parseFloat(String data, float defaultValue) {
        if (StringUtils.isBlank(data)) {
            return defaultValue;
        }
        return NumberUtils.toFloat(data, defaultValue);
    }

    public static int parseInt(String qscore, int defaultValue) {
        if (StringUtils.isBlank(qscore)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(qscore);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long parseLong(String qscore, long defaultValue) {
        if (StringUtils.isBlank(qscore)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(qscore);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    // 判断两个数字是否相等
    public static boolean isEqual(Number a, Number b) {
        return a == null ? b == null : a.equals(b);
    }

    /**
     * 四舍五入转化为字符串
     * 
     * @param number
     * @param precision 小数保留的位数
     * @return
     */
    public static String format2Str(double number, int precision) {
        String pattern = "0.";
        for (int i = 0; i < precision; i++) {
            pattern += "0";
        }
        DecimalFormat dg = new DecimalFormat(pattern); // 保留两位小数
        return dg.format(number);
    }

    /**
     * 四舍五入格式化double
     * 
     * @param number 原始数值
     * @param precision 保留的小数位数
     * @return
     */
    public static double format(double number, int precision) {
        int tmp = 1;
        for (int i = 0; i < precision; i++) {
            tmp *= 10;
        }
        int value = (int) Math.round(number * tmp);
        return (value * 1d) / tmp;
    }

    // 除法
    public static double div(double a, double b, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 乘法
    public static double mul(double a, double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.multiply(b2).doubleValue();
    }

    // 减法
    public static double sub(double a, double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.subtract(b2).doubleValue();
    }
}
