/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author zxc Jun 18, 2014 2:02:31 PM
 */
public interface MemberService extends BaseService {

    List<Long> getMidListByCid(Long cId);

    // /////////////////////////////////////////////////////////////////////////////////////////////////

    Integer insert(TravelMemberDO travelMemberDO);

    boolean delete(Long id);

    List<TravelMemberDO> list(TravelMemberQuery query);

    List<TravelMemberDO> list();

    boolean update(TravelMemberDO travelMemberDO);

    TravelMemberDO getById(Long mId);

    PaginationList<TravelMemberDO> showMemberPagination(TravelMemberQuery query, IPageUrl... ipPages);

    TravelMemberDO getByName(TravelMemberQuery query);

	List<TravelMemberDO> listQuery(TravelMemberQuery query);
}
