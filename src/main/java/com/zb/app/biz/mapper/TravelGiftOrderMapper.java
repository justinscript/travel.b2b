/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelGiftOrderDO;
import com.zb.app.biz.domain.TravelGiftOrderFullDO;
import com.zb.app.biz.query.TravelGiftOrderQuery;
import com.zb.app.common.pagination.PaginationList;

/**
 * @author ZhouZhong 2014-7-24 下午5:53:11
 */
public interface TravelGiftOrderMapper extends BaseMapper<TravelGiftOrderDO> {

    PaginationList<TravelGiftOrderFullDO> fullListPagination(TravelGiftOrderQuery query);

    TravelGiftOrderFullDO fullGetById(Integer id);
}
