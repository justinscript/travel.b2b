/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.TravelOperationLogDO;
import com.zb.app.biz.domain.TravelOperationLogFullDO;
import com.zb.app.biz.query.TravelOperationLogQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * @author ZhouZhong
 */
public interface OperationLogService extends BaseService {

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////操作日志表(TravelOperationLog)
    // ////////////////////////////////////////////////////////////////////////////////////////

    List<TravelOperationLogDO> list(TravelOperationLogQuery query);

    PaginationList<TravelOperationLogFullDO> listPagination(TravelOperationLogQuery query, IPageUrl... iPages);

    List<TravelOperationLogDO> listTravelOperationLog();

    TravelOperationLogDO getTravelOperationLogById(Long id);

    Integer insertTravelOperationLog(TravelOperationLogDO ts);

    boolean updateTravelOperationLog(TravelOperationLogDO ts);

    boolean deleteTravelOperationLog(Long id);
}
