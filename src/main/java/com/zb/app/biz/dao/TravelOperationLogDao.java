/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelOperationLogDO;
import com.zb.app.biz.mapper.TravelOperationLogMapper;
import com.zb.app.biz.query.TravelOperationLogQuery;

/**
 * 操作日志DAO
 * 
 * @author ZhouZhong
 */
@Repository
public class TravelOperationLogDao extends BaseDao<TravelOperationLogDO, TravelOperationLogMapper, TravelOperationLogQuery> {
	
}
