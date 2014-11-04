/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * 
 * @author zxc Jun 16, 2014 12:29:45 AM
 */
public class DateViewTools {

    /**
     * 确保线程安全
     */
    private static ThreadLocal<HashMap<String, SimpleDateFormat>> formatHolder               = new ThreadLocal<HashMap<String, SimpleDateFormat>>();

    public static final String                                    SIMPLE_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String                                    FULL_DATE_FORMAT_PATTERN   = "yyyy-MM-dd HH:mm:ss";
    private static final Logger                                   logger                     = LoggerFactory.getLogger(DateViewTools.class);

    public static String formatDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return getFormat(SIMPLE_DATE_FORMAT_PATTERN).format(date);
    }

    public static String formatFullDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return getFormat(FULL_DATE_FORMAT_PATTERN).format(date);
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return getFormat(pattern).format(date);
    }

    public static String formatFullDateToday(Date date) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        return getFormat(SIMPLE_DATE_FORMAT_PATTERN).format(date);
    }

    /**
     * 判断cal2是否在cal1之后，判断只精确到天
     * 
     * <pre>
     * 
     * isExpiredForDays(2011-11-4 ，2011-11-4 ) 返回0
     * isExpiredForDays(2011-11-4，2011-11-5 ) 返回小于0
     * isExpiredForDays(2011-11-5，2011-11-4 ) 返回大于0
     * 
     * </pre>
     */
    public static int compareForDays(Calendar cal1, Calendar cal2) {
        if (isSameDay(cal1, cal2)) {
            return 0;
        }
        return cal1.compareTo(cal2);
    }

    public static int compareForDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return compareForDays(cal1, cal2);
    }

    /***
     * 得到两个日期相差天数
     * 
     * @param begin_date
     * @param end_date
     * @return
     */
    public static int getDifferDay(Date end_date, Date begin_date) {
        long day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String sdate = format.format(Calendar.getInstance().getTime());

            if (begin_date == null) {
                begin_date = format.parse(sdate);
            }
            if (end_date == null) {
                end_date = format.parse(sdate);
            }
            day = (end_date.getTime() - begin_date.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return -1;
        }
        return Integer.parseInt(day + "");
    }

    /**
     * 判断是否过期（精确到分钟）
     * 
     * @param date
     * @return
     */
    public static boolean isExpiredForMin(String date) {
        try {
            Date expect = getFormat(FULL_DATE_FORMAT_PATTERN).parse(date);
            long expectTime = expect.getTime();
            long now = System.currentTimeMillis();
            return (now - expectTime) / DateUtils.MILLIS_PER_MINUTE >= 0;
        } catch (Exception e) {
            logger.error("Format date faield", e.getMessage());
            return false;
        }
    }

    /**
     * 传入的日期是否已经过期了,只精确到天比较，且包含当天
     * 
     * <pre>
     * 注意，判断时是采用currentTime > date 比较的。
     * 当前时间 2011-11-4 ,isExpiredForDays(2011-11-4 ) 返回false
     * 当前时间 2011-11-5 ,isExpiredForDays(2011-11-4 ) 返回true
     * 
     * </pre>
     * 
     * @param date 需要表的日期
     * @return true 如果已经过期返回<code>true</code>，如果传入的值是<code>null</code>也将返回<code>true</code>,其他情况返回<code>false</code>
     */
    public static boolean isExpiredForDays(Date date) {
        if (date == null) {
            return true;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        if (cal1.compareTo(cal2) > 0) {// 当前时间比制定日期要大
            if (isSameDay(cal1, cal2)) {// 同一天的认为不过期
                return false;
            } else {
                return true;
            }
        } else {// 当前时间比制定日期要小
            return false;
        }
    }

    /**
     * 传入的日期是否已经过期了,只精确到天比较，且包含当天
     * 
     * <pre>
     * 注意，判断时是采用currentTime > date 比较的。
     * 当前时间 2011-11-4 ,isExpirationTime(2011-11-4 ) 返回ture
     * 当前时间 2011-11-5 ,isExpirationTime(2011-11-4 ) 返回true
     * 
     * </pre>
     * 
     * @param date 需要表的日期
     * @return true 如果已经过期返回<code>true</code>，如果传入的值是<code>null</code>也将返回<code>true</code>,其他情况返回<code>false</code>
     */
    public static boolean isExpirationTime(Date date) {
        if (date == null) {
            return true;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nows = format.format(new Date());
        Date now = null;
        try {
            now = format.parse(nows);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (date.compareTo(now) < 0) {// 指定日期比当前日期小
            return true;
        } else {// 指定日期等于当前日期或大于当前日期
            return false;
        }
    }

    public static boolean isExpiredForHours(Date date) {
        if (date == null) {
            return true;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        if (cal1.compareTo(cal2) > 0) {// 当前时间比制定日期要大
            if (isSameDay(cal1, cal2)) {// 同一天的认为不过期
                return false;
            } else {
                return true;
            }
        } else {// 当前时间比制定日期要小
            return false;
        }
    }

    public static boolean _isExpiredForDays(Date date, int before) {
        if (date == null) {
            return true;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(DateUtils.addDays(date, -before));
        if (cal1.compareTo(cal2) > 0) {// 当前时间比制定日期要大
            if (isSameHour(cal1, cal2)) {// 同一小时的认为不过期
                return false;
            } else {
                return true;
            }
        } else {// 当前时间比制定日期要小
            return false;
        }
    }

    public static String timeDiff(Date date) {
        if (date == null) {
            return "";
        }
        java.util.Date now = new Date();
        long l = date.getTime() - now.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);

        return "" + day + "天" + hour + "小时" + min + "分";
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return false;
        }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
               && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isSameHour(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
               && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
               && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY);
    }

    private static SimpleDateFormat getFormat(String key) {
        HashMap<String, SimpleDateFormat> map = formatHolder.get();
        if (map == null) {
            map = new HashMap<String, SimpleDateFormat>(2);
            formatHolder.set(map);// 保存回去
        }
        SimpleDateFormat simpleDateFormat = map.get(key);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(key, Locale.CHINA);
            map.put(key, simpleDateFormat);
            formatHolder.set(map);// 保存回去
        }
        return simpleDateFormat;
    }

    public static String getDayBefore(int before) {
        return getFormat(SIMPLE_DATE_FORMAT_PATTERN).format(getDateBefore(before));
    }

    public static Date getDateBefore(int before) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -before);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        return cal.getTime();
    }

    public static String getNow() {
        Date date = new Date();
        return getFormat(SIMPLE_DATE_FORMAT_PATTERN).format(date);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String yesterday() {
        return getFormat(SIMPLE_DATE_FORMAT_PATTERN).format(yesterDate());
    }

    public static Date yesterDate() {
        return getDateBefore(1);
    }

    public static String yesterdayFull() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return getFormat(FULL_DATE_FORMAT_PATTERN).format(yesterDate());
    }

    public static String nextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return getFormat(SIMPLE_DATE_FORMAT_PATTERN).format(calendar.getTime());
    }

    public static String nextDayFull() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return getFormat(FULL_DATE_FORMAT_PATTERN).format(calendar.getTime());
    }

    public static String getNowFull() {
        Date date = new Date(System.currentTimeMillis());
        return getFormat(FULL_DATE_FORMAT_PATTERN).format(date);
    }

    /**
     * 将时间字符串(精确到时分秒)解析为Date对象。如果解析失败返回<code>null</code>
     */
    public static Date parseFull(String date) {
        try {
            return getFormat(FULL_DATE_FORMAT_PATTERN).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将时间字符串(精确到天)解析为Date对象。如果解析失败返回<code>null</code>
     */
    public static Date parseSimple(String date) {
        try {
            return getFormat(SIMPLE_DATE_FORMAT_PATTERN).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 特殊的pattern进行格式化处理
     */
    public static String format(String pattern, Date date) {
        if (StringUtils.isBlank(pattern)) {
            return formatDate(date);
        }
        return getFormat(pattern).format(date);

    }

    public static String formatDuration(Date start, Date end, String format) {
        if (start == null || end == null) {
            return " ";
        }

        long durationMillis = end.getTime() - start.getTime();

        return DurationFormatUtils.formatDuration(durationMillis, format);

    }

    public static String getTodayAsKey() {
        return getTodayAsKey(StringUtils.EMPTY);
    }

    public static String getTodayAsKey(String separator) {
        if (separator == null) {
            separator = StringUtils.EMPTY;
        }
        Calendar instance = Calendar.getInstance();
        return buildKey(instance, separator);
    }

    private static String buildKey(Calendar instance, String separator) {
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);
        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int minitue = instance.get(Calendar.MINUTE);
        int second = instance.get(Calendar.SECOND);
        return StringUtils.EMPTY + year + separator + int2Str(month) + separator + int2Str(day) + separator
               + int2Str(hour) + separator + int2Str(minitue) + separator + int2Str(second);
    }

    private static String int2Str(int value) {
        return (value < 10 ? "0" : StringUtils.EMPTY) + value;
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Date convertDate(String adateStrteStr, String format) {
        if (StringUtils.isBlank(adateStrteStr)) {
            return new Date();
        }
        java.util.Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
            date = simpleDateFormat.parse(adateStrteStr);
            return date;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Date();
    }

    /**
     * 格式化当前时间 返回格式HHmmssSSS
     * 
     * @return
     */
    public static String getCurrentHMSSSS() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 格式化当前时间 返回格式yyyyMMdd
     * 
     * @return
     */
    public static String getCurrentDateYYMMDD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 格式化当前时间 返回格式yyyy-MM-dd
     * 
     * @return
     */
    public static String getCurrentDateYY_MM_DD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    public static String getCurrentYY_MM_DD_HMssSS() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    public static boolean isToday(Date date) {
        return isSameDay(date, new Date());
    }
}
