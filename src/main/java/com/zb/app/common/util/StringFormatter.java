/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
// import org.joda.time.Duration;
// import org.joda.time.format.PeriodFormatter;
// import org.joda.time.format.PeriodFormatterBuilder;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.core.lang.BeanUtils;

/**
 * 字符串处理工具类
 * 
 * <pre>
 *  参考“http://blog.csdn.net/yohop/article/details/2534907”
 *  格式化表达式：%[零个或多个标志][最小字段宽度][精度][修改符]格式码
 *  标志:
 *      标志 - 含义 值在字段中做对齐，缺省情况下是右对齐。
 *  最小字段宽度:
 *      字段宽度是一个十进制整数，用于指定将出现在结果中的最小字符数。如果值的字符数少于字段宽度，就对它进行填充以增加长度。
 *  精度:
 *       以一个句点开头，后面跟一个可选的十进制数。
 *       对于e,E和f类型的转换，精度决定将出现在小数点之后的数字位数。例如.2小数点后精度为2
 *       当使用s类型的转换时，精度指定将被转换的最多的字符数。
 *       
 *  格式码:
 *       s,f,%(转义符号，类似字符串的"\")
 * 
 * </pre>
 * 
 * @author zxc Jun 16, 2014 12:41:07 AM
 */
public class StringFormatter {

    private static final String  PATTERN           = "[\ufe30-\uffa0]*[‘’“”。…￥【】《》—]*";                   // 中文全角标点符号
    private static final char[]  array             = { ',', ' ', '(', ')' };
    private static final char[]  ignore_char       = { '－', '＆', '．', '！', '’', '‘', '：', '＇', '＋', '–' };
    private static final String  ZERO_STR          = "0";
    private static final Integer SCALE_TWO         = 2;
    private static final String  FORMAT            = "#0.0";
    private static final int     FULL_NUMBER_WORDS = 30;
    private static final String  ENCODE            = "utf-8";

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    // 格式化工具类
    //
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 格式化Float（去除无用0）
     * 
     * @param number
     * @return
     */
    public static <T extends Number> String formatFloat(T number) {
        if (number == null) {
            return 0 + "";
        }
        String s = number.toString();
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 数组转字符串
     * 
     * @param f
     * @return
     */
    public static String listToString(List<Long> zid) {
        return Argument.isEmpty(zid) ? "" : StringUtils.join(zid, ",");
    }

    public static Object objectFieldEscape(Object ob) {
        Field[] fields = BeanUtils.getAllFields(null, ob.getClass());
        for (Field field : fields) {
            if (field == null || field.getName() == null || field.getType() == null) {
                continue;
            }
            if (StringUtils.equals("serialVersionUID", field.getName())) {
                continue;
            }
            if (!StringUtils.equals(field.getType().getSimpleName(), "String")) {
                continue;
            }
            try {
                Object fieldVal = PropertyUtils.getProperty(ob, field.getName());
                if (null == fieldVal) {
                    continue;
                }
                field.setAccessible(true);
                String value = (String) fieldVal;
                PropertyUtils.setProperty(ob, field.getName(), StringEscapeUtils.escapeXml(value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ob;
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    // 去除特殊字符
    //
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 返回连续两个的空格数或斜杆数
    public static int count(String s, char ch) {
        int count = 0;
        if (s.length() < 2) {
            return count;
        }
        int i = 0;
        while (i < s.length()) {
            try {
                s.charAt(i + 1);
            } catch (Exception e) {
                break;
            }
            if (s.charAt(i) == ch && s.charAt(i + 1) == ch) count++;
            i++;
        }
        return count;
    }

    public static boolean isEnWord(String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static boolean isOnlyNum(String str) {
        return str.matches("\\d+");
    }

    public static String trim(String s) {
        return StringUtils.trim(s);
    }

    public static String float2format(Double d) {
        if (d == null) {
            return ZERO_STR;
        }

        BigDecimal b = new BigDecimal(d);
        float fb = b.setScale(SCALE_TWO, BigDecimal.ROUND_DOWN).floatValue();
        DecimalFormat format = new DecimalFormat(FORMAT);
        return format.format(fb).toString();
    }

    public static String replaceZero(String last) {
        int l = last.length();
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < l; i++) {
            char c = last.charAt(i);
            if (c == '.') {
                char s = last.charAt(i + 1);
                if (s == '0') {
                    break;
                }
            }
            sf.append(c);
        }
        return sf.toString();
    }

    // 是否已满30个字
    public static boolean isSaturation(String str) {
        return NumberParser.sub(FULL_NUMBER_WORDS, getWordSize(str)) >= 0;
    }

    // 返回字符串的字数，去尾法
    public static int getWordSizeInt(String str) {
        return (int) Math.floor(getWordSize(str));
    }

    public static boolean isEnglish(String str) {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        char ch;
        for (int i = 0, size = str.length(); i < size; i++) {
            ch = str.charAt(i);
            if (ch >= 256 && !isIgnoreChar(ch)) {
                return false;
            }
        }
        return true;
    }

    // 除去中英文标点符号
    public static String replaceCnEnSign(String str) {
        str = matcherRegex(str, "[.,\"\\?!:']", false);
        return matcherRegex(str, PATTERN, false);
    }

    private static boolean isIgnoreChar(char ch) {
        for (char c : ignore_char) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    // 字符编码
    //
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // static PeriodFormatter formatter = new
    // PeriodFormatterBuilder().appendDays().appendSuffix("天").appendHours().appendSuffix("小时").appendMinutes().appendSuffix("分").appendSeconds().appendSuffix("秒").appendMillis3Digit().appendSuffix("毫秒").toFormatter();

    public static String decode(String s) {
        try {
            return URLDecoder.decode(s, ENCODE);
        } catch (Exception e) {
            return s;
        }
    }

    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, ENCODE);
        } catch (Exception e) {
            return s;
        }
    }

    // public static String formatDuration(long durationMs) {
    // return formatDuration(durationMs, null);
    // }

    // public static String formatDuration(long durationMs, String prefix) {
    // Duration duration = new Duration(durationMs);
    // String print = formatter.print(duration.toPeriod());
    // return (prefix == null ? "" : prefix) + print + " (" + durationMs + ")ms";
    // }

    public static String _formatFloat(Float value, int delimiter, String suffix) {
        float _value = value == null ? 0f : value;
        suffix = suffix == null ? "" : suffix;
        return String.format("%." + delimiter + "f" + suffix, _value);
    }

    public static String _formatFloat(Number denominator, Number molecule) {
        if (denominator == null || molecule == null) {
            return null;
        }
        return _formatFloat(denominator.floatValue() / molecule.floatValue(), 2, null);
    }

    public static String _formatFloat(Float value) {
        return _formatFloat(value, 2, null);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    // 字符串格式化
    //
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 处理百分比
     */
    public static String formatPercent(Float value) {
        return _formatFloat(value, 2, "%%");
    }

    public static String formatString(String value, boolean alignLeft, int minLength, int maxLength) {
        return String.format("%" + (alignLeft ? "-" : "") + minLength + "." + maxLength + "s", value);
    }

    public static String matcherRegex(String str, String regex) {
        return matcherRegex(str, regex, true);
    }

    public static String matcherRegex(String s, String regex, boolean needTrim) {
        if (StringUtils.isBlank(s)) {
            return StringUtils.EMPTY;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return needTrim ? m.replaceAll(StringUtils.EMPTY).trim() : m.replaceAll(StringUtils.EMPTY);
    }

    public static boolean matchsRegex(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean containsRegex(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 将数组中被source包含的返回出去。
     */
    public static Set<String> containsAny(String source, String[] testArray) {
        Assert.assertNotNull(source);
        Assert.assertNotNull(testArray);
        Set<String> result = new HashSet<String>(testArray.length);
        for (String testWord : testArray) {
            if (source.contains(testWord)) {
                result.add(testWord);
            }
        }
        return result;
    }

    /**
     * 将字符串中的字母和数字连词取出来，单独处理。
     */
    public static Set<String> matchLetterAndDigit(String source) {
        Set<String> result = new HashSet<String>();
        char[] charByte = new char[source.length()];
        int offset = 0;
        for (int i = 0, j = source.length(); i < j; i++) {
            char charAt = source.charAt(i);
            if (isMatch(charAt)) {// 单字节
                charByte[offset] = charAt;
                offset++;
            } else {
                if (offset > 0) {
                    char[] copyOfRange = Arrays.copyOfRange(charByte, 0, offset);
                    if (copyOfRange.length > 1) {
                        result.add(new String(copyOfRange));
                    }
                    offset = 0;
                }
            }
            if (i == (j - 1) && offset > 0) {
                char[] copyOfRange = Arrays.copyOfRange(charByte, 0, offset);
                if (copyOfRange.length > 1) {
                    result.add(new String(copyOfRange));
                }
            }
        }
        return result;
    }

    private static boolean isMatch(char charAt) {
        String binaryString = Integer.toBinaryString(charAt);
        if (binaryString.length() > 8) {
            return false;
        } else {
            return !ArrayUtils.contains(array, charAt);
        }
    }

    public static boolean isContainsRegex(String str, String regex) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(regex)) {
            return false;
        }
        return Pattern.compile(regex).matcher(str).find();
    }

    // 返回字符串的字数，精确到double
    public static float getWordSize(String o) {
        int l = o.length();
        o = StringFormatter.matcherRegex(o, "[^\\x00-\\xff]", false);// 除去所有的双字节字符
        return (float) (o.length() * 0.5) + l - o.length();
    }

    // 英文算一个字符长度，其它所有2个字符长度
    public static int getEnWordSize(String o) {
        int l = o.length();
        o = StringFormatter.matcherRegex(o, "[^\\x00-\\xff]", false);// 除去所有的双字节字符
        return 2 * l - o.length();
    }

    public static void main(String[] args) {
        // String word = "!@#3232";
        // if (Pattern.compile("(?i)[a-z]").matcher(word).find()) {
        // System.out.println("有字母");
        // } else if (Pattern.compile("(?i)[0-9]").matcher(word).find()) {
        // System.out.println("有数字");
        // }
        // System.out.println(matchLetterAndDigit("60D- 55Z中 "));
        // System.out.println(matchLetterAndDigit("60D-55Z中 "));
        // System.out.println(matchLetterAndDigit("60D,55Z中 "));
        // System.out.println(matchLetterAndDigit("60D#55Z中 "));
        // System.out.println(matchLetterAndDigit("棉T恤"));

        System.out.println(getEnWordSize("60D中甜美^一^夏季吊带雪纺抹胸裙沙滩裙连衣"));
    }
}
