/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelTrafficDO;
import com.zb.app.biz.query.TravelTrafficQuery;

/**
 * @author Administrator 2014-7-9 下午3:46:49
 */
public interface TravelTrafficMapper extends BaseMapper<TravelTrafficDO> {

    /**
     * count 操作
     * 
     * @param query
     * @return
     */
    public Integer count(TravelTrafficQuery query);
}
