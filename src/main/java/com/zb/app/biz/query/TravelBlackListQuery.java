/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * @author ZhouZhong 2014-8-15 下午5:32:48
 */
public class TravelBlackListQuery extends Pagination {

    private Long   bId;        // 自动编号,主键
    private Date   gmtCreate;  // 记录创建时间
    private Date   gmtModified; // 记录最后修改时间
    private Long   beCId;      // 被拉黑公司ID
    private Long   cId;        // 操作公司ID
    private Long   mId;        // 操作用户ID
    private String bRemark;    // 拉黑备注

    public TravelBlackListQuery(Long beCId) {
        super();
        this.beCId = beCId;
    }

    public TravelBlackListQuery() {
        super();
    }

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
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

    public Long getBeCId() {
        return beCId;
    }

    public void setBeCId(Long beCId) {
        this.beCId = beCId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getbRemark() {
        return bRemark;
    }

    public void setbRemark(String bRemark) {
        this.bRemark = bRemark;
    }
}
