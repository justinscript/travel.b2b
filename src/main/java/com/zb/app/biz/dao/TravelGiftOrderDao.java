/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelGiftOrderDO;
import com.zb.app.biz.domain.TravelGiftOrderFullDO;
import com.zb.app.biz.mapper.TravelGiftOrderMapper;
import com.zb.app.biz.query.TravelGiftOrderQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author ZhouZhong 2014-7-24 下午5:51:20
 */
@Repository
@SuppressWarnings("unchecked")
public class TravelGiftOrderDao extends BaseDao<TravelGiftOrderDO, TravelGiftOrderMapper, TravelGiftOrderQuery> {

    @Override
    public String getNameSpace() {
        return TravelGiftOrderMapper.class.getName();
    }

    public PaginationList<TravelGiftOrderFullDO> fullListPagination(TravelGiftOrderQuery query, IPageUrl... iPages) {
        Assert.assertNotNull(query);
        return super.paginationList("fullCount", "fullListPagination", query, iPages);
    }

    public TravelGiftOrderFullDO fullGetById(Integer id) {
        Assert.assertNotNull(id);
        return m.fullGetById(id);
    }
}
