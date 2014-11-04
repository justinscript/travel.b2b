/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;

import com.zb.app.common.core.lang.Argument;

/**
 * 拼音工具类
 * 
 * @author zxc 2014-9-18 下午8:37:41
 */
public class PinyinParser {

    public static Set<String> converter2AllSpell(String chines) {
        if (StringUtils.isEmpty(chines)) {
            return Collections.<String> emptySet();
        }
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null) {
                        pinyinName.append(StringUtils.join(strs, ","));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }

            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));

    }

    public static String converterToFirstSpell(String chines) {
        return StringUtils.join(converter2FirstSpell(chines), ",");
    }

    public static Set<String> converter2FirstSpell(String chines) {
        if (StringUtils.isEmpty(chines)) {
            return Collections.<String> emptySet();
        }
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            // 取得首字母
                            pinyinName.append(strs[j].charAt(0));
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }

            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    // 要支持多音字提示，对查询串转换成拼音后，需要实现一个全排列组合，字符串多音字全排列算法如下：
    public static List<String> getPermutationSentence(List<String> termArrays, int start) {
        if (Argument.isEmpty(termArrays)) {
            return Collections.<String> emptyList();
        }
        int size = termArrays.size();
        if (start < 0 || start >= size) {
            return Collections.<String> emptyList();
        }
        if (start == size - 1) {
            return termArrays.subList(0, start);
        }
        List<String> strings = termArrays.subList(0, start);
        List<String> permutationSentences = getPermutationSentence(termArrays, start + 1);
        if (Argument.isEmpty(strings)) {
            return permutationSentences;
        }
        if (Argument.isEmpty(permutationSentences)) {
            return strings;
        }
        List<String> result = new ArrayList<String>();
        for (String pre : strings) {
            for (String suffix : permutationSentences) {
                result.add(pre + suffix);
            }
        }
        return result;
    }

    /**
     * 去除重复字重复数据
     * 
     * @param args
     */
    private static List<Map<String, Integer>> discountTheChinese(String pinyin) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = pinyin.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }

    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     * 
     * @param args
     */
    private static Set<String> parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        Set<String> result = new HashSet<String>();
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                result.add(str);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String str = "新世纪大厦宝利金";
        System.out.println(converter2FirstSpell(str));
        System.out.println(converter2AllSpell(str));
    }
}
