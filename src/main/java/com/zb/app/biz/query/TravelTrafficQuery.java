/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import java.util.Date;

import com.zb.app.common.pagination.Pagination;

/**
 * @author Administrator 2014-7-9 下午3:48:49
 */
public class TravelTrafficQuery extends Pagination {

    private Long    tId;        // 自动编号
    private Integer tType;      // 交通类型
    private Integer tCat;       // 往返类型
    private String  tTraffic;   // 交通备注
    private Long    cId;        // 公司id
    private Date    gmtCreate;  // 创建时间
    private Date    gmtModified; // 修改时间

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public Integer gettCat() {
        return tCat;
    }

    public void settCat(Integer tCat) {
        this.tCat = tCat;
    }

    public String gettTraffic() {
        return tTraffic;
    }

    public void settTraffic(String tTraffic) {
        this.tTraffic = tTraffic;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
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
}
