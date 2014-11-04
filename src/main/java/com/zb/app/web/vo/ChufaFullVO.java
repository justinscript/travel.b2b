/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.util.List;
import java.util.Map;

/**
 * @author zxc Aug 19, 2014 12:13:31 PM
 */
public class ChufaFullVO {

    // 出港点信息
    private Long                             cId;      // 出港点ID
    private String                           cName;    // 出港点名称
    private Integer                          cSort;    // 排序

    private Map<Integer, List<ColumnThinVO>> columnMap;

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcSort() {
        return cSort;
    }

    public void setcSort(Integer cSort) {
        this.cSort = cSort;
    }

    public Map<Integer, List<ColumnThinVO>> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<Integer, List<ColumnThinVO>> columnMap) {
        this.columnMap = columnMap;
    }
}
