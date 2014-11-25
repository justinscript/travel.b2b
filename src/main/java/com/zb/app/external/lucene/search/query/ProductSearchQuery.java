/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.query;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.zb.app.external.lucene.search.utils.ZuobianSolrQueryConvert;
import com.zb.app.external.lucene.solr.query.SearchQuery;

/**
 * @author zxc Sep 3, 2014 12:20:39 PM
 */
public class ProductSearchQuery implements SearchQuery, Serializable {

    private static final long serialVersionUID = 3401107234009904866L;

    // 当前页面处于第几页，页面从0开始计数，在页面上显示则从1开始
    private int               nowPageIndex     = 0;
    // 所有的记录数
    private int               allRecordNum;

    private int               rows             = 10;                  // 行数
    private int               start;                                  // 开始

    private String            Title;                                  // 线路标题
    private String            product;                                // 线路分组
    private Integer           lType;                                  // 线路类型
    private Integer           lDay;                                   // 线路天数
    private Long[]            zIds;                                   // 专线类别
    private Long              zId;                                    // 专线类别
    private String            lArrivalCity;                           // 到达城市
    private List<String>      products;                               // 分词后集合
    private String            lGroupNumber;                           // 产品编号
    private boolean           expectMatch      = false;               // 是否精确

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Integer getlType() {
        return lType;
    }

    public void setlType(Integer lType) {
        this.lType = lType;
    }

    public Integer getlDay() {
        return lDay;
    }

    public void setlDay(Integer lDay) {
        this.lDay = lDay;
    }

    public Long[] getzIds() {
        return zIds;
    }

    public void setzIds(Long... zIds) {
        this.zIds = zIds.length == 0 ? new Long[] { -1L } : zIds;
    }

    public String getlArrivalCity() {
        return lArrivalCity;
    }

    public void setlArrivalCity(String lArrivalCity) {
        this.lArrivalCity = lArrivalCity;
    }

    public ProductSearchQuery(String lGroupNumber, String lProduct) {
        this.lGroupNumber = lGroupNumber;
        this.product = lProduct;
    }

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
    }

    public ProductSearchQuery(List<String> products) {
        this.products = products;
    }

    public ProductSearchQuery(String title, List<String> products) {
        this.Title = title;
        this.products = products;
    }

    public boolean isExpectMatch() {
        return expectMatch;
    }

    public void setExpectMatch(boolean expectMatch) {
        this.expectMatch = expectMatch;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ProductSearchQuery() {
        super();
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getStart() {
        return start;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    @Override
    public String getSortFiled() {
        return StringUtils.EMPTY;
    }

    @Override
    public ORDER getOrderBy() {
        return ORDER.desc;
    }

    public int getNowPageIndex() {
        return nowPageIndex;
    }

    public void setNowPageIndex(int nowPageIndex) {
        this.nowPageIndex = nowPageIndex;
    }

    public int getAllRecordNum() {
        return allRecordNum;
    }

    public void setAllRecordNum(int allRecordNum) {
        this.allRecordNum = allRecordNum;
    }

    @Override
    public SolrQuery toSolrQuery() {
        return ZuobianSolrQueryConvert.to(this);
    }
}
