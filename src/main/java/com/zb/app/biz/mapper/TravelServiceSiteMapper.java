/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import java.util.List;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelServiceSiteDO;

/**
 * 客服站点类
 * 
 * @author ZhouZhong 2014-9-1 上午11:36:58
 */
public interface TravelServiceSiteMapper extends BaseMapper<TravelServiceSiteDO> {

    List<TravelServiceSiteDO> getBySId(Long id);

}
