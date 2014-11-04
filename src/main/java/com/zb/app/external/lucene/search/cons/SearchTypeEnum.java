/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.cons;

import com.zb.app.external.lucene.search.build.base.DataFetcher;
import com.zb.app.external.lucene.search.build.base.Param;
import com.zb.app.external.lucene.search.build.fetcher.ProductFetcher;
import com.zb.app.external.lucene.search.build.fetcher.WordFetcher;
import com.zb.app.external.lucene.search.service.ProductSearch;
import com.zb.app.external.lucene.search.service.WordSearch;
import com.zb.app.external.lucene.solr.exception.SolrUnSupportException;

/**
 * 对外服务的Search类型
 * 
 * @author zxc Sep 2, 2014 1:54:07 PM
 */
public enum SearchTypeEnum {

    WORD("word", "词库", VersionType.word.name(), WordSearch.class),

    PRODUCT("product", "线路产品库", VersionType.product.name(), ProductSearch.class);

    private String value;
    private String desc;
    private String versionType;

    @SuppressWarnings("rawtypes")
    private Class  type;

    @SuppressWarnings("rawtypes")
    private SearchTypeEnum(String value, String desc, String versionType, Class type) {
        this.value = value;
        this.desc = desc;
        this.versionType = versionType;
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }

    public static SearchTypeEnum getByValue(String value) {
        for (SearchTypeEnum searchType : values()) {
            if (searchType.getValue().equals(value)) {
                return searchType;
            }
        }
        return null;
    }

    public boolean isWord() {
        return this == WORD;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public Class<?> getType() {
        return type;
    }

    public DataFetcher<?> getDateFetcher(Param param) {
        switch (this) {
            case WORD:
                return WordFetcher.create(param);
            case PRODUCT:
                return ProductFetcher.create(param);
            default:
                throw new SolrUnSupportException("不支持的类型,Unsupport type " + this);
        }
    }
}
