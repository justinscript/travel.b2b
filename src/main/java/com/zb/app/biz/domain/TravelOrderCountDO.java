/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;

/**
 * @author zxc Aug 6, 2014 5:46:09 PM
 */
public class TravelOrderCountDO implements Serializable {

    private static final long serialVersionUID = -7756302040988875020L;

    private Integer           orState;                                 // 状态
    private Integer           orAdultCount;                            // 成人数
    private Integer           orChildCount;                            // 儿童数
    private Integer           orBabyCount;                             // 婴儿数
    private Integer           allCount;                                // 总人数
    private Double            orPirceCount;                            // 订单总金额

    public TravelOrderCountDO() {

    }

    public TravelOrderCountDO(Integer init) {
        setOrAdultCount(init);
        setOrChildCount(init);
        setOrBabyCount(init);
        setAllCount(init);
        setOrPirceCount(0d);
    }

    public Integer getOrState() {
        return orState;
    }

    public void setOrState(Integer orState) {
        this.orState = orState;
    }

    public Integer getOrAdultCount() {
        return parseIntegerNull(orAdultCount);
    }

    public void setOrAdultCount(Integer orAdultCount) {
        this.orAdultCount = orAdultCount;
    }

    public Integer getOrChildCount() {
        return parseIntegerNull(orChildCount);
    }

    public void setOrChildCount(Integer orChildCount) {
        this.orChildCount = orChildCount;
    }

    public Integer getOrBabyCount() {
        return parseIntegerNull(orBabyCount);
    }

    public void setOrBabyCount(Integer orBabyCount) {
        this.orBabyCount = orBabyCount;
    }

    public Integer getAllCount() {
        return parseIntegerNull(allCount);
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Double getOrPirceCount() {
        return parseDoubleNull(orPirceCount);
    }

    public void setOrPirceCount(Double orPirceCount) {
        this.orPirceCount = orPirceCount;
    }

    @SuppressWarnings("unchecked")
    protected static <T extends Number> T parseNull(T n) {
        return (T) (n == null ? 0 : n);
    }

    protected static <T extends Number> Integer parseIntegerNull(T n) {
        return parseNull(n).intValue();
    }

    protected static <T extends Number> Long parseLongNull(T n) {
        return parseNull(n).longValue();
    }

    protected static <T extends Number> Double parseDoubleNull(T n) {
        return parseNull(n).doubleValue();
    }
}
