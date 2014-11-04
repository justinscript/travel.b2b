/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * 操作日志
 * 
 * @author ZhouZhong
 */
public class TravelOperationLogQuery extends Pagination {

    private Long   olId;          // 主键编号
    private Date   gmtCreate;     // 创建时间
    private Date   gmtModified;   // 修改时间
    private String olTable;       // 表名
    private Long   olTablePk;     // 相关表主键值
    private String olTablePb;     // 相关表候选列值
    private String olChangeBefore; // 修改前值
    private String olChangeLater; // 修改后值
    private Long   mId;           // 操作人
    private Long   cId;           // 所属公司
    private String olCorrModule;  // 相关模块

    public Long getOlId() {
        return olId;
    }

    public void setOlId(Long olId) {
        this.olId = olId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getOlTable() {
        return olTable;
    }

    public void setOlTable(String olTable) {
        this.olTable = olTable;
    }

    public Long getOlTablePk() {
        return olTablePk;
    }

    public void setOlTablePk(Long olTablePk) {
        this.olTablePk = olTablePk;
    }

    public String getOlTablePb() {
        return olTablePb;
    }

    public void setOlTablePb(String olTablePb) {
        this.olTablePb = olTablePb;
    }

    public String getOlChangeBefore() {
        return olChangeBefore;
    }

    public void setOlChangeBefore(String olChangeBefore) {
        this.olChangeBefore = olChangeBefore;
    }

    public String getOlChangeLater() {
        return olChangeLater;
    }

    public void setOlChangeLater(String olChangeLater) {
        this.olChangeLater = olChangeLater;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getOlCorrModule() {
        return olCorrModule;
    }

    public void setOlCorrModule(String olCorrModule) {
        this.olCorrModule = olCorrModule;
    }
}
