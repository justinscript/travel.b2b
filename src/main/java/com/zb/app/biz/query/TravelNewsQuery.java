/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.biz.cons.TravelNewsTypeEnum;
import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Aug 5, 2014 3:49:28 PM
 */
public class TravelNewsQuery extends Pagination {

    private Long    cId;   // 公司ID
    private Long    zId;   // 专线ID
    private Integer nType; // 积分类型
    private String  nTitle; // 新闻标题
    private Integer nState; // 状态(0=正常,1=停止)

    public TravelNewsQuery() {
        setnState(0);
    }

    public TravelNewsQuery(String nTitle) {
        this(nTitle, null);
    }

    public TravelNewsQuery(TravelNewsTypeEnum newsType) {
        this(null, newsType);
    }

    public TravelNewsQuery(String nTitle, TravelNewsTypeEnum newsType) {
        this(nTitle, null, null, newsType);
    }

    public TravelNewsQuery(String nTitle, Long zId, Long cId, TravelNewsTypeEnum newsType) {
        setnTitle(nTitle);
        setzId(zId);
        setcId(cId);
        setnType(newsType.value);
        setnState(0);
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Integer getnType() {
        return nType;
    }

    public void setnType(Integer nType) {
        this.nType = nType;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public Integer getnState() {
        return nState;
    }

    public void setnState(Integer nState) {
        this.nState = nState;
    }
}
