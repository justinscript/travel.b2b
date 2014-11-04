/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.analyzer;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zb.app.external.lucene.analyzer.cons.SegMode;
import com.zb.jcseg.core.ISegment;
import com.zb.jcseg.core.IWord;
import com.zb.jcseg.core.JcsegDictionary;
import com.zb.jcseg.core.JcsegDictionaryFactory;
import com.zb.jcseg.core.JcsegTaskConfig;
import com.zb.jcseg.core.SegmentFactory;
import com.zb.jcseg.solr.JcsegTokenizerFactory;

/**
 * jcseg分词器实现
 * 
 * @author zxc Sep 2, 2014 4:40:56 PM
 */
@Service
public class JcsegWordAnalyzer extends AbstractWordAnalyzer {

    private static Object[]              SEG_PARAS         = null;
    private static SegMode[]             supportedSegMode  = { SegMode.COMPLEX, SegMode.SIMPLE };

    private String                       jcsegPropertyPath = "jcseg.properties_path";
    private String                       lexPath           = "jcseg.lexicon_path";

    private static JcsegTokenizerFactory jcsegTokenizerFactory;

    private static ISegment              seg;

    @PostConstruct
    public void init() {
        try {
            // 非反射机制
            jcsegTokenizerFactory = new JcsegTokenizerFactory(new HashMap<String, String>());
            JcsegTaskConfig config = jcsegTokenizerFactory.getTaskConfig();
            JcsegDictionary dic = jcsegTokenizerFactory.getDict();
            seg = SegmentFactory.createJcseg(config, dic, JcsegTaskConfig.COMPLEX_MODE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public List<String> splitWords(Reader input, SegMode segMode) {
        List<String> result = new ArrayList<String>();
        // jcseg分词
        Object[] paras = createParas(false);
        IWord word = null;
        try {
            seg.reset(input);
            while ((word = seg.next()) != null) {
                result.add(word.getValue());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public boolean reload() {
        createParas(true);
        return true;
    }

    @Override
    public boolean isSupportSegMode(SegMode segMode) {
        if (segMode == null) {
            return false;
        }
        for (SegMode mode : supportedSegMode) {
            if (segMode == mode) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SegMode getDefaultSegMode() {
        return SegMode.COMPLEX;
    }

    // ///////////////////////////////////////////////////////////////////////////////
    // 私有方法
    // ///////////////////////////////////////////////////////////////////////////////
    private Object[] createParas(boolean forceInitialize) {
        if (SEG_PARAS == null || forceInitialize) {
            synchronized (WordAnalyzer.class) {
                if (StringUtils.isBlank(lexPath)) {
                    lexPath = jcsegPropertyPath;
                }
                JcsegTaskConfig config = new JcsegTaskConfig(jcsegPropertyPath + File.separator + "jcseg.properties");
                config.setLexPath(lexPath);
                JcsegDictionary dic = JcsegDictionaryFactory.createDefaultDictionary(config);
                Object[] paras = new Object[] { config, dic };
                SEG_PARAS = paras;
            }
        }
        return SEG_PARAS;
    }

    @Override
    public List<String> wiselyCombineSingleWord(List<String> result) {
        return result;
    }

    @Override
    public List<String> segWords(String input, Boolean wiselyCombineSingleWord) {
        return splitWords(new StringReader(input), SegMode.COMPLEX);
    }
}
