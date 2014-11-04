/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelServiceSiteDO;
import com.zb.app.biz.mapper.TravelServiceSiteMapper;
import com.zb.app.biz.query.TravelServiceSiteQuery;

/**
 * 客服站点
 * 
 * @author ZhouZhong 2014-9-1 上午11:39:31
 */
@Repository
public class TravelServiceSiteDao extends BaseDao<TravelServiceSiteDO, TravelServiceSiteMapper, TravelServiceSiteQuery> {
    public List<TravelServiceSiteDO> getServiceSiteBySId(Long id) {
        // TODO Auto-generated method stub
        return m.getBySId(id);
    }
}
