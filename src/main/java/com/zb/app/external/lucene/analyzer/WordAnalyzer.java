/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.analyzer;

import java.util.List;

import com.zb.app.external.lucene.analyzer.cons.SegMode;

/**
 * 中文分词器
 * 
 * @author zxc Sep 2, 2014 4:35:27 PM
 */
public interface WordAnalyzer {

    /**
     * 缺省分词方法 -- 不智能合并单个字符
     * 
     * @param input
     * @return
     */
    public List<String> segWords(String input);

    /**
     * @param input
     * @param wordSpilt
     * @param segMode
     * @return
     */
    public String segWords(String input, String wordSpilt, SegMode segMode);

    /**
     * @param input
     * @param filterChain
     * @return
     */
    public List<String> segWords(String input, Boolean wiselyCombineSingleWord);

    /**
     * 词方法仅仅给代理实现，其它的具体实现可以忽略词此方法
     * 
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    public Object invoke(String methodName, Object[] args) throws Exception;

    /**
     * 重新加载词典
     * 
     * @return
     */
    public boolean reload();
}
