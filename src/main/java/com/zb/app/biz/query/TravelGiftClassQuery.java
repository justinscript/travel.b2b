/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * @author Administrator 2014-7-22 下午4:39:24
 */
public class TravelGiftClassQuery extends Pagination {

    private Long   gcId;       // 自动编号
    private Date   gmtCreate;  // 记录创建时间
    private Date   gmtModified; // 记录修改时间
    private String gcName;     // 分类名称
    private Long   gcParentId; // 父类ID

    public TravelGiftClassQuery() {

    }

    public TravelGiftClassQuery(Long gcParentId, String gcName) {
        setGcParentId(gcParentId);
        setGcName(gcName);
    }

    public Long getGcId() {
        return gcId;
    }

    public void setGcId(Long gcId) {
        this.gcId = gcId;
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

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public Long getGcParentId() {
        return gcParentId;
    }

    public void setGcParentId(Long gcParentId) {
        this.gcParentId = gcParentId;
    }
}
