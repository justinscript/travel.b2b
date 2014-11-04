/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.CompanyColumnDO;
import com.zb.app.biz.domain.TravelSiteCoreDO;
import com.zb.app.biz.domain.TravelSiteDO;
import com.zb.app.biz.domain.TravelSiteFullDO;
import com.zb.app.biz.mapper.TravelSiteMapper;
import com.zb.app.biz.query.TravelSiteQuery;
import com.zb.app.common.core.lang.Assert;

/**
 * @author zxc Aug 17, 2014 9:06:29 PM
 */
@Repository
public class TravelSiteDao extends BaseDao<TravelSiteDO, TravelSiteMapper, TravelSiteQuery> {

    @Override
    public String getNameSpace() {
        return TravelSiteMapper.class.getName();
    }

    public List<TravelSiteFullDO> getSiteFullBycId(Long cid) {
        Assert.assertNotNull(cid);
        return m.getSiteFullBycId(cid);
    }

    public List<TravelSiteFullDO> getSiteFullCore4All() {
        return m.getSiteFullCore4All();
    }

    public List<TravelSiteCoreDO> getSiteCore4All() {
        return m.getSiteCore4All();
    }

    public List<CompanyColumnDO> getCompanyByzId(Long zId) {
        Assert.assertNotNull(zId);
        return m.getCompanyByzId(zId);
    }
}
