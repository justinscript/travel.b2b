/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.analyzer.cons;


/**
 * @author zxc Sep 2, 2014 4:36:10 PM
 */
public enum SegMode {

    MAX_WORD("maxword"), COMPLEX("complex"), SIMPLE("simple");

    private String value;

    private SegMode(String value) {
        this.value = value;
    }

    public static SegMode getByValue(String value) {
        for (SegMode seg : values()) {
            if (seg.value.equalsIgnoreCase(value)) {
                return seg;
            }
        }
        return null;
    }
}
