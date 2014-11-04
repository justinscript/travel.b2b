/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.domain.TravelFinanceDO;
import com.zb.app.biz.domain.TravelFinanceDetailDO;
import com.zb.app.biz.domain.TravelFinanceViewDO;
import com.zb.app.biz.query.TravelFinanceDetailQuery;
import com.zb.app.biz.query.TravelFinanceQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 财务管理 service层
 * 
 * @author zxc Aug 4, 2014 4:11:26 PM
 */
public interface FinanceService {

    // /////////////////////////////////////////////////////////////////////
    // TravelFinanceDO 财务表
    // /////////////////////////////////////////////////////////////////////

    TravelFinanceDO find(TravelFinanceQuery query);

    List<TravelFinanceViewDO> listQuery(TravelFinanceQuery query);

    PaginationList<TravelFinanceViewDO> listPagination(TravelFinanceQuery query, IPageUrl... iPages);

    TravelFinanceDO getTravelFinanceById(Integer id);

    Integer addTravelFinance(TravelFinanceDO... travels);

    boolean updateTravelFinance(TravelFinanceDO travel);

    boolean deleteTravelFinance(Integer id);

    // /////////////////////////////////////////////////////////////////////
    // TravelFinanceDetailDO 财务详情表表
    // /////////////////////////////////////////////////////////////////////

    TravelFinanceDetailDO find(TravelFinanceDetailQuery query);

    List<TravelFinanceDetailDO> list(TravelFinanceDetailQuery query);

    PaginationList<TravelFinanceDetailDO> listPagination(TravelFinanceDetailQuery query);

    TravelFinanceDetailDO getTravelFinanceDetailById(Integer id);

    Integer addTravelFinanceDetail(TravelFinanceDetailDO... travels);

    boolean updateTravelFinanceDetail(TravelFinanceDetailDO travel);

    boolean deleteTravelFinanceDetail(Integer id);
}
