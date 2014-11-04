/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * @author Administrator 2014-8-11 下午3:47:22
 */
public class TravelPhotoQuery extends Pagination {

    private Long    pId;        // number(20) not null; --图片编号
    private Date    gmtCreate;  // timestamp not null; --创建时间
    private Date    gmtModified; // timestamp not null; --修改时间
    private Long    cId;        // number(20); --公司id
    private Integer pType;      // number(9); --图片类型
    private String  pTitle;     // varchar2(50); --图片标题
    private String  pPath;      // varchar2(100); --图片地址
    private String  pRemark;    // varchar2(300) --图片备注
    private Integer pIsCover;   // number(9) --是否是封面

    public Integer getpIsCover() {
        return pIsCover;
    }

    public void setpIsCover(Integer pIsCover) {
        this.pIsCover = pIsCover;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
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

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpPath() {
        return pPath;
    }

    public void setpPath(String pPath) {
        this.pPath = pPath;
    }

    public String getpRemark() {
        return pRemark;
    }

    public void setpRemark(String pRemark) {
        this.pRemark = pRemark;
    }
}
