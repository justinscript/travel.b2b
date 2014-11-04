/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.analyzer;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.zb.app.external.lucene.analyzer.cons.SegMode;
import com.zb.mmseg.core.ComplexSeg;
import com.zb.mmseg.core.MMSeg;
import com.zb.mmseg.core.MMSegDictionary;
import com.zb.mmseg.core.MMSegWord;
import com.zb.mmseg.core.MaxWordSeg;
import com.zb.mmseg.core.Seg;
import com.zb.mmseg.core.SimpleSeg;

/**
 * MMSeg分词实现
 * 
 * @author zxc Sep 2, 2014 4:40:15 PM
 */
public class MmsegWordAnalyzer extends AbstractWordAnalyzer {

    // 不同的分词方法
    protected Seg complexSeg;
    protected Seg maxWordSeg;
    protected Seg simpleSeg;

    @Override
    public List<String> splitWords(Reader input, SegMode segMode) {
        List<String> result = new ArrayList<String>();
        Seg seg = getSeg(segMode); // 取得不同的分词具体算法
        MMSeg mmSeg = new MMSeg(input, seg);
        MMSegWord word = null;
        try {
            while ((word = mmSeg.next()) != null) {
                result.add(word.getString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public boolean reload() {
        // return getDic().reload();
        return true;
    }

    @Override
    public boolean isSupportSegMode(SegMode segMode) {
        if (segMode == null) {
            return false;
        }
        return true;
    }

    @Override
    public SegMode getDefaultSegMode() {
        return SegMode.COMPLEX;
    }

    @Override
    public List<String> wiselyCombineSingleWord(List<String> result) {
        return result;
    }

    @Override
    public Object invoke(String method, Object[] args) throws Exception {
        return null;
    }

    // ///////////////////////////////////////////////////////////////////////////////
    // 私有方法
    // ///////////////////////////////////////////////////////////////////////////////
    private Seg getSeg(SegMode segMode) {
        Seg seg = null;
        if (segMode == null) {
            return getComplexSeg();
        }
        switch (segMode) {
            case MAX_WORD:
                seg = getMaxWordSeg();
                break;
            case SIMPLE:
                seg = getSimpleSeg();
            default:
                seg = getComplexSeg();
                break;
        }
        return seg;
    }

    private Seg getSimpleSeg() {
        if (simpleSeg == null) {
            simpleSeg = new SimpleSeg(getDic());
        }
        return simpleSeg;
    }

    private Seg getMaxWordSeg() {
        if (maxWordSeg == null) {
            maxWordSeg = new MaxWordSeg(getDic());
        }
        return maxWordSeg;
    }

    private Seg getComplexSeg() {
        if (complexSeg == null) {
            complexSeg = new ComplexSeg(getDic());
        }
        return complexSeg;
    }

    private MMSegDictionary getDic() {
        return MMSegDictionary.getInstance();
    }
}
