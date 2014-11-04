/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.util.List;

import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.domain.TravelSiteDO;

/**
 * @author zxc Jul 10, 2014 1:45:54 PM
 */
public class ChufaVO {

    private TravelSiteDO         chufa;

    private List<TravelColumnDO> columnList;

    public ChufaVO() {

    }

    public ChufaVO(TravelSiteDO site, List<TravelColumnDO> columnList) {
        setChufa(site);
        setColumnList(columnList);
    }

    public TravelSiteDO getChufa() {
        return chufa;
    }

    public void setChufa(TravelSiteDO chufa) {
        this.chufa = chufa;
    }

    public List<TravelColumnDO> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<TravelColumnDO> columnList) {
        this.columnList = columnList;
    }
}
