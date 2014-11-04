/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.solr.query;

/**
 * @author zxc Sep 2, 2014 1:47:01 PM
 */
public enum SearchSortFields {

    GMT_CREATE("gmtCreate"),

    GMT_MODIFIED("gmtModifed"),

    ID("Id"),

    HOTWORD_CLICKCOUNT("clickCount");

    private String value;

    private SearchSortFields(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
