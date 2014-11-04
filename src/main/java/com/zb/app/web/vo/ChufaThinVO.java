/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author zxc Jul 11, 2014 2:59:17 PM
 */
public class ChufaThinVO {

    // 出港点信息
    private Long               cId;       // 出港点ID
    private String             cName;     // 出港点名称
    private Integer            cSort;     // 排序

    private List<ColumnThinVO> columnList;

    public ChufaThinVO() {

    }

    public ChufaThinVO(Long cId, String cName, Integer cSort, List<ColumnThinVO> columnList) {
        setcId(cId);
        setcName(cName);
        setcSort(cSort);
        setColumnList(columnList);
    }

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

    public List<ColumnThinVO> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnThinVO> columnList) {
        this.columnList = columnList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
