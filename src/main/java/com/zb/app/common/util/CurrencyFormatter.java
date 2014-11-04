/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 * 货币单位格式化工具类
 * 
 * @author zxc Jun 16, 2014 12:37:00 AM
 */
public class CurrencyFormatter {

    private static ThreadLocal<HashMap<String, NumberFormat>> formatHolder = new ThreadLocal<HashMap<String, NumberFormat>>();
    private static final String                               CURRENCY     = "#0.00";
    // 带一位小数的格式
    private static final String                               NUMBER       = "#0.0";
    private static final String                               PERCENT      = "#.##%";

    private static final Integer                              SCALE        = 2;

    /**
     * 将元转化为分
     * 
     * @param yuan 输入金额元(类似 10.00或者10)
     * @return
     */
    public static int convert2fen(String yuan) {
        return (int) (CurrencyFormatter.toFloat(yuan) * 100);
    }

    /**
     * 将“分”格式化为“元”的样式
     * 
     * <pre>
     * format(null) 0.00
     * format(－1) 0.00
     * format(0) 0.00
     * format(1) 1.00
     * format(1000000) 10000.00
     * </pre>
     * 
     * @param price 输入的“分”的价格
     * @return 元的价格
     */
    public static String format(Integer fen) {
        double yuan = 0;
        if (isNotNull(fen)) {
            yuan = fen / 100d;
        }
        NumberFormat numberInstance = getFormat(CURRENCY);
        return numberInstance.format(yuan);
    }

    /**
     * 将数字转换为指定的格式
     * 
     * @param data 除数
     * @param divide 被除数
     * @return 返回带两位小数的格式
     */

    public static String format(Integer data, Double divide) {
        double result = 0;
        result = data / divide;
        NumberFormat numberInstance = getFormat(CURRENCY);
        String strValue = numberInstance.format(result);
        // 去掉小数点后面的0
        if (strValue != null && strValue.length() > 0) {
            strValue = strValue.replaceAll("(\\.0+|0+)$", "");
        }
        return strValue;
    }

    /**
     * 保留1为小数的格式
     * 
     * @param data
     * @param divide
     * @return
     */
    public static String formatWith1Dot(Integer data, Double divide) {
        double result = 0;
        result = data / divide;
        NumberFormat numberInstance = getFormat(NUMBER);
        String strValue = numberInstance.format(result);
        // 去掉小数点后面的0
        if (strValue != null && strValue.length() > 0) {
            strValue = strValue.replaceAll("(\\.0+|0+)$", "");
        }
        return strValue;
    }

    /**
     * 将“分”格式化为“元”的样式<br>
     * 负数时,显示""
     * 
     * @param fen
     * @return
     */
    public static String formatShowEmpty(Integer fen) {
        double yuan = 0;
        if (isNotNull(fen)) {
            yuan = fen / 100d;
            NumberFormat numberInstance = getFormat(CURRENCY);
            return numberInstance.format(yuan);
        }
        return "";
    }

    /**
     * 将“分”格式化为“元”的样式,不包含.00部分<br>
     * 负数时,显示""
     * 
     * @param fen
     * @return
     */
    public static String formatFen(Integer fen) {
        int yuan = 0;
        if (isNotNull(fen)) {
            yuan = fen / 100;
            return "" + yuan;
        }
        return "";
    }

    /**
     * 输入元，得到分
     * 
     * @param fen
     * @return
     */
    public static int yuanTofen(String yuan) {
        try {
            float fen = Float.parseFloat(yuan);
            if (fen > 0) {
                return (int) (fen * 100 + 0.001f);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 输入元，得到分
     * 
     * @param fen
     * @return
     */
    public static int yuanTofen(Float yuan) {
        if (yuan != null && yuan > 0) {
            // +0.001是为了消除 java浮点计算小数问题
            return (int) (yuan.floatValue() * 100 + 0.001f);
        }
        return 0;
    }

    /**
     * 输入元，得到分
     * 
     * @param fen
     * @return
     */
    public static int yuanTofen(Integer yuan) {
        if (yuan != null && yuan > 0) {
            return (int) (yuan * 100);
        }
        return 0;
    }

    /**
     * 输入分得到元
     * 
     * @param fen
     * @return
     */
    public static Float fen2yuan(Long fen) {
        if (isNotNull(fen)) {
            float tmp = fen / 100f;
            return tmp;
        }
        return 0f;
    }

    private static boolean isNotNull(Number fen) {
        return fen != null;
    }

    /**
     * 格式化百分比
     * 
     * @param number
     * @return
     */
    public static String formatPercent(float number) {
        NumberFormat format = getFormat(PERCENT);
        return format.format(number);
    }

    public static float toFloat(String number) {
        try {
            return Float.parseFloat(number);
        } catch (Exception e) {
            return 0;
        }
    }

    private static NumberFormat getFormat(String key) {
        HashMap<String, NumberFormat> map = formatHolder.get();
        if (map == null) {
            map = new HashMap<String, NumberFormat>(3);
            formatHolder.set(map);// 保存回去
        }
        NumberFormat format = map.get(key);
        if (format == null) {
            format = new DecimalFormat(key);
            map.put(key, format);
            formatHolder.set(map);// 保存回去
        }
        return format;
    }

    /**
     * 价格从分转化为元
     */
    public static Integer formatPrice(Integer price) {
        if (price == null) {
            return null;
        }
        return price.intValue() / 100;
    }

    /**
     * "###.####"-->"#.00" floatPrice-->formatPrice(四舍五入)
     */
    public static String float2formatPrice(Float price) {
        if (price == null) {
            return null;
        }

        BigDecimal b = new BigDecimal(price);
        float f = b.setScale(SCALE, BigDecimal.ROUND_HALF_UP).floatValue();
        DecimalFormat fnum = new DecimalFormat(CURRENCY);

        return fnum.format(f);
    }

    /**
     * 将一个整数除以100返回一个浮点型数据 用于将淘宝评分的转换
     */
    public static Float formatScore(Integer score) {
        if (score == null) {
            return null;
        }
        return score.floatValue() / 100;
    }

    /**
     * 把字符串转化成整数
     * 
     * @param str
     * @return
     */
    public static Integer strToInt(String str) {
        try {
            Float num = Float.parseFloat(str);
            return num.intValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        // System.out.println("------- null --" + format(null));
        // System.out.println("------- 0 --" + format(0));
        // System.out.println("-------- -1 --" + format(-1));
        // System.out.println("-------- 1 --" + format(1));
        // System.out.println("-------- 99 --" + format(99));
        // System.out.println("-------- 100 --" + format(100));
        // System.out.println("-------- 1000000 --" + format(1000000));
        // System.out.println("--123.45--" + yuanTofen("123.45"));
        // System.out.println("--123.45--" + yuanTofen(123.45f));

        // double divide = 100.0d;
        // for (int i = 16800; i >= -10; i--) {
        // System.out.print(format(i, divide));
        // System.out.print(" ");
        // }
        // int convert2fen = convert2fen("0.01");
        // System.err.println(convert2fen);
        // System.out.println(yuanTofen(18.80f));
        // System.out.println(formatPercent(0.1886f));
        // System.out.println(18.80d * 100);
        // System.out.println(strToInt("500.0000"));
        System.out.println(formatWith1Dot(240, 60d));
    }
}
