/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelBlackListDO;
import com.zb.app.biz.domain.TravelBlackListThinDO;
import com.zb.app.biz.query.TravelBlackListQuery;
import com.zb.app.common.pagination.PaginationList;

/**
 * @author ZhouZhong 2014-8-15 下午5:31:20
 */
public interface TravelBlackListMapper extends BaseMapper<TravelBlackListDO> {
	PaginationList<TravelBlackListThinDO> queryAllCompanyBlack(TravelBlackListQuery query);
}
