/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * @author ZhouZhong 2014-9-1 上午11:41:42
 */
public class TravelServiceSiteQuery extends Pagination {
    private Long    szId;                                    // number(20) not null; --主键编号
    private Date    gmtCreate;                              // timestamp not null; --创建时间
    private Date    gmtModified;                            // timestamp not null; --修改时间
    private Integer sId;
    private Integer zId;

    
    public Long getSzId() {
        return szId;
    }

    
    public void setSzId(Long szId) {
        this.szId = szId;
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

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getzId() {
        return zId;
    }

    public void setzId(Integer zId) {
        this.zId = zId;
    }
}
