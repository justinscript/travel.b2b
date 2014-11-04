/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.biz.base.BaseQuery;
import com.zb.app.biz.domain.TravelAdvertisementDO;

/**
 * @author zxc Aug 19, 2014 4:04:39 PM
 */
public class TravelAdvertisementQuery extends BaseQuery<TravelAdvertisementDO> {

    // 按时间查询,创建时间的起始查询时间,截止查询时间
    private String startGmtCreate;
    private String endGmtCreate;

    // 按时间查询,更新时间的起始查询时间,截止查询时间
    private String startGmtModified;
    private String endGmtModified;

    private String likeTitle;       // 相似标题Query

    public TravelAdvertisementQuery() {

    }

    public TravelAdvertisementQuery(String title) {
        this(null, null, title, null, 1);
    }

    public TravelAdvertisementQuery(Integer location, Long siteId) {
        this(location, siteId, null, null, 1);
    }

    public TravelAdvertisementQuery(Integer location, Long siteId, String title) {
        this(location, siteId, title, null, 1);
    }

    public TravelAdvertisementQuery(Integer location, Long siteId, String title, Integer sort, Integer state) {
        entity.setLocation(location);
        entity.setSiteId(siteId);
        entity.setTitle(title);
        entity.setSort(sort);
        entity.setState(state);
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

    public String getLikeTitle() {
        return likeTitle;
    }

    public void setLikeTitle(String likeTitle) {
        this.likeTitle = likeTitle;
    }
}
