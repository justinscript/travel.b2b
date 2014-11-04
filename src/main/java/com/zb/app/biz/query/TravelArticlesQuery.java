/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.biz.base.BaseQuery;
import com.zb.app.biz.domain.TravelArticlesDO;

/**
 * @author zxc Aug 19, 2014 4:04:58 PM
 */
public class TravelArticlesQuery extends BaseQuery<TravelArticlesDO> {

    // 按时间查询,创建时间的起始查询时间,截止查询时间
    private String startGmtCreate;
    private String endGmtCreate;

    // 按时间查询,更新时间的起始查询时间,截止查询时间
    private String startGmtModified;
    private String endGmtModified;

    private String likeTitle;       // 相似标题Query

    public TravelArticlesQuery() {

    }

    public TravelArticlesQuery(String title) {
        this(null, title, null, 1);
    }

    public TravelArticlesQuery(Integer source) {
        this(source, null, null, 1);
    }

    public TravelArticlesQuery(Integer source, String title) {
        this(source, title, null, 1);
    }

    public TravelArticlesQuery(Integer source, String title, Integer sort, Integer state) {
        entity.setSource(source);
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
