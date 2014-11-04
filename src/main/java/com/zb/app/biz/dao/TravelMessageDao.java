/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.biz.mapper.TravelMessageMapper;
import com.zb.app.biz.query.TravelMessageQuery;

/**
 * @author zxc Aug 1, 2014 2:45:50 PM
 */
@Repository
public class TravelMessageDao extends BaseDao<TravelMessageDO, TravelMessageMapper, TravelMessageQuery> {

}
