/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.analyzer;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.external.lucene.analyzer.cons.SegMode;
import com.zb.jcseg.util.WordUnionUtils;

/**
 * @author zxc Sep 2, 2014 4:37:00 PM
 */
public abstract class AbstractWordAnalyzer implements WordAnalyzer {

    protected static Logger    logger                = LoggerFactory.getLogger(WordAnalyzer.class);

    public static final char[] TITLE_DELIMETER_CHARS = { '　', ' ', '\\', '/', '，', ',', ';', '；', '|', '(', ')', '[',
            ']', '（', '）', '+', '〡', '【', '】'       /* , ':', '：' */};

    /**
     * 缺省分词方法 -- 不智能合并单个字符
     * 
     * @param input
     * @return
     */
    @Override
    public List<String> segWords(String input) {
        return segWords(input, Boolean.FALSE);
    }

    public List<String> segWords(String input, SegMode segMode) {
        if (StringUtils.isBlank(input)) {
            return new ArrayList<String>(0);
        }
        return _segWords(input, segMode, Boolean.FALSE);
    }

    @Override
    public String segWords(String input, String wordSpilt, SegMode segMode) {
        List<String> segWords = segWords(input, segMode);
        return StringUtils.join(segWords, wordSpilt);
    }

    @Override
    public List<String> segWords(String input, Boolean wiselyCombineSingleWord) {
        if (StringUtils.isBlank(input)) {
            return new ArrayList<String>(0);
        }
        return _segWords(input, SegMode.COMPLEX, wiselyCombineSingleWord);
    }

    public List<String> _segWords(String input, SegMode segMode, Boolean wiselyCombineSingleWord) {
        if (StringUtils.isBlank(input)) {
            return new ArrayList<String>();
        } else {
            return wiselySplit(input, segMode, wiselyCombineSingleWord);
        }
    }

    public List<String> segWords(Reader input, SegMode segMode, boolean wiselyCombineSingleWord) {
        // 分词
        List<String> result = _splitWords(input, segMode);
        // 智能合并词
        if (wiselyCombineSingleWord) {
            result = wiselyCombineSingleWord(result);
        }
        return result;
    }

    // 智能分词
    private List<String> wiselySplit(String str, SegMode segMode, Boolean wiselyCombineSingleWord) {
        List<String> result = new ArrayList<String>();
        int index = 0;
        for (int i = 0, len = str.length(), lastIndex = len - 1; i < len; i++) {
            if (isDelimeter(str.charAt(i))) {
                if (index < i) {
                    String word = StringUtils.substring(str, index, i);
                    _wiselySplit(result, segMode, wiselyCombineSingleWord, word);
                }
                index = i + 1;
            }
            // 最后一个字符
            if (i == lastIndex) {
                String word = StringUtils.substring(str, index);
                _wiselySplit(result, segMode, wiselyCombineSingleWord, word);
            }
        }
        return result;
    }

    // 智能分词--这里没有调用StringUtils.split，主要原因有两个
    // 1) 不支持多个分隔符 2)会多产生一个List对象
    private void _wiselySplit(List<String> result, SegMode segMode, boolean wiselyCombineSingleWord, String input) {
        if (StringUtils.isBlank(input)) {
            return;
        }
        // 只有长度大于4才继续分词
        int len = StringUtils.length(input);
        if (len <= 2 || (len == 3 && !WordUnionUtils.isContainSingleWord(input))) {
            result.add(input);
        } else {
            List<String> segWords = segWords(new StringReader(input), segMode, wiselyCombineSingleWord);
            if (segWords.size() > 0) {
                result.addAll(segWords);
            }
        }
    }

    // 校验SegMode
    private List<String> _splitWords(Reader input, SegMode segMode) {
        if (!isSupportSegMode(segMode)) {
            logger.error("当前分词器支持" + segMode + "分词模式,替换为" + getDefaultSegMode());
            segMode = getDefaultSegMode();
        }
        return splitWords(input, segMode);
    }

    // 智能合并单字
    public abstract List<String> wiselyCombineSingleWord(List<String> result);

    // 具体分词方法
    public abstract List<String> splitWords(Reader input, SegMode segMode);

    public abstract boolean isSupportSegMode(SegMode segMode);

    public abstract SegMode getDefaultSegMode();

    /**
     * 词方法仅仅给代理实现，其它的具体实现可以忽略词此方法
     * 
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public Object invoke(String methodName, Object[] args) throws Exception {
        if (StringUtils.equals(methodName, "invoke")) {
            return null;
        }
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Method method = this.getClass().getMethod(methodName, parameterTypes);
        return method.invoke(this, args);
    }

    // ////////////////////////////////////////////////////////////////////////////
    //
    // 工具方法
    //
    // ////////////////////////////////////////////////////////////////////////////

    // 去除字符串中的空格、回车、换行符、制表符
    public static String replaceBlank(String str) {
        return str != null ? matcherRegex(str, "\\s*|\t|\r|\n") : str;
    }

    // 判断字符是否为分割符
    public boolean isDelimeter(char c) {
        for (char x : TITLE_DELIMETER_CHARS) {
            if (x == c) {
                return true;
            }
        }
        return false;
    }

    public static String matcherRegex(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll(StringUtils.EMPTY).trim();
    }
}
