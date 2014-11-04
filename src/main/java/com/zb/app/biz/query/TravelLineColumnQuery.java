/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Jul 25, 2014 9:59:12 AM
 */
public class TravelLineColumnQuery extends Pagination {

    private Long zId; // 专线ID
    private Long cId; // 公司ID

    public TravelLineColumnQuery() {

    }

    public TravelLineColumnQuery(Long zId, Long cId) {
        setzId(zId);
        setcId(cId);
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }
}
