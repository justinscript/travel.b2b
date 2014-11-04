/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.biz.cons.ColumnCatEnum;
import com.zb.app.biz.cons.ColumnHotEnum;
import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Jul 9, 2014 4:25:21 PM
 */
public class TravelColumnQuery extends Pagination {

    private Long    sId;   // 站点ID
    private Long    sToId; // 出港点ID
    private String  zName; // 专线名称
    private Integer zCat;  // 专线分类(0=短线，1=长线，2=国际线)
    private Integer zHot;  // 状态(1=热门，2=推荐)
    private Integer zState; // 状态(0=正常，1=停止)

    public TravelColumnQuery() {

    }

    public TravelColumnQuery(Long sId) {
        this(sId, null, null, null, null, 0);
    }

    public TravelColumnQuery(String zName) {
        this(null, null, zName);
    }

    public TravelColumnQuery(ColumnCatEnum zCat) {
        this(null, null, null, zCat, null, 0);
    }

    public TravelColumnQuery(Long sId, Long sToId) {
        this(sId, sToId, null, null, null, 0);
    }

    public TravelColumnQuery(Long sId, Long sToId, String zName) {
        this(sId, sToId, zName, null, null, 0);
    }

    public TravelColumnQuery(Long sId, ColumnCatEnum zCat, ColumnHotEnum zHot) {
        this(sId, null, null, zCat, zHot, 0);
    }

    public TravelColumnQuery(Long sId, ColumnCatEnum zCat, ColumnHotEnum zHot, Integer zState) {
        this(sId, null, null, zCat, zHot, zState);
    }

    public TravelColumnQuery(Long sId, Long sToId, String zName, ColumnCatEnum zCat, ColumnHotEnum zHot, Integer zState) {
        setsId(sId);
        setsToId(sToId);
        setzName(zName);
        setzCat(zCat == null ? null : zCat.getValue());
        setzHot(zHot == null ? null : zHot.getValue());
        setzState(zState);
    }

    public String getzName() {
        return zName;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Long getsToId() {
        return sToId;
    }

    public void setsToId(Long sToId) {
        this.sToId = sToId;
    }

    public void setzName(String zName) {
        this.zName = zName;
    }

    public Integer getzCat() {
        return zCat;
    }

    public void setzCat(Integer zCat) {
        this.zCat = zCat;
    }

    public Integer getzHot() {
        return zHot;
    }

    public void setzHot(Integer zHot) {
        this.zHot = zHot;
    }

    public Integer getzState() {
        return zState;
    }

    public void setzState(Integer zState) {
        this.zState = zState;
    }
}
