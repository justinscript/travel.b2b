/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelNewsDO;
import com.zb.app.biz.mapper.TravelNewsMapper;
import com.zb.app.biz.query.TravelNewsQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author ZhouZhong 2014-7-24 下午5:32:00
 */
@SuppressWarnings("unchecked")
@Repository
public class TravelNewsDao extends BaseDao<TravelNewsDO, TravelNewsMapper, TravelNewsQuery> {

    @Override
    public String getNameSpace() {
        return TravelNewsMapper.class.getName();
    }

    public PaginationList<TravelNewsDO> showNewsPagination(TravelNewsQuery query, IPageUrl... ipPages) {
        Assert.assertNotNull(query);
        return super.paginationList("count", "showNewsPagination", query, ipPages);
    }
}
