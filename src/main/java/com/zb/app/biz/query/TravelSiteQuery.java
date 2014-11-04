/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Jul 9, 2014 4:53:14 PM
 */
public class TravelSiteQuery extends Pagination {

    private String  sName;    // 站点名称
    private String  sProvince; // 站点省
    private String  sCity;    // 站点市
    private Long    sToid;    // 站点上级ID,0 出港地
    private Integer sState;   // 状态:0=正常，1=停止

    public TravelSiteQuery() {
        setsToid(0l);
    }

    public TravelSiteQuery(Long sToid) {
        this(sToid, null);
    }

    public TravelSiteQuery(String sName) {
        this(null, sName);
    }

    public TravelSiteQuery(Long sToid, String sName) {
        this(sToid, 0, sName, null, null);
    }

    public TravelSiteQuery(String sName, String sProvince, String sCity) {
        this(null, 0, sName, sProvince, sCity);
    }

    public TravelSiteQuery(Long sToid, Integer sState, String sName, String sProvince, String sCity) {
        setsToid(sToid);
        setsState(sState);
        setsName(sName);
        setsProvince(sProvince);
        setsCity(sCity);
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsProvince() {
        return sProvince;
    }

    public void setsProvince(String sProvince) {
        this.sProvince = sProvince;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public Long getsToid() {
        return sToid;
    }

    public void setsToid(Long sToid) {
        this.sToid = sToid;
    }

    public Integer getsState() {
        return sState;
    }

    public void setsState(Integer sState) {
        this.sState = sState;
    }
}
