/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.biz.domain.TravelServiceDO;
import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Aug 20, 2014 5:21:19 PM
 */
public class TravelServiceQuery extends Pagination {

    private TravelServiceDO service = new TravelServiceDO();

    // 按时间查询,创建时间的起始查询时间,截止查询时间
    private String          startGmtCreate;
    private String          endGmtCreate;

    // 按时间查询,更新时间的起始查询时间,截止查询时间
    private String          startGmtModified;
    private String          endGmtModified;

    public TravelServiceQuery() {

    }

    public TravelServiceQuery(Long cId, String sName) {
        this(cId, sName, null);
    }

    public TravelServiceQuery(Long cId, String sName, String sQQ) {
        service.setcId(cId);
        service.setsName(sName);
        service.setsQQ(sQQ);
    }

    public TravelServiceDO getService() {
        return service;
    }

    public void setService(TravelServiceDO service) {
        this.service = service;
    }

    public String getStartGmtCreate() {
        return startGmtCreate;
    }

    public void setStartGmtCreate(String startGmtCreate) {
        this.startGmtCreate = startGmtCreate;
    }

    public String getEndGmtCreate() {
        return endGmtCreate;
    }

    public void setEndGmtCreate(String endGmtCreate) {
        this.endGmtCreate = endGmtCreate;
    }

    public String getStartGmtModified() {
        return startGmtModified;
    }

    public void setStartGmtModified(String startGmtModified) {
        this.startGmtModified = startGmtModified;
    }

    public String getEndGmtModified() {
        return endGmtModified;
    }

    public void setEndGmtModified(String endGmtModified) {
        this.endGmtModified = endGmtModified;
    }
}
