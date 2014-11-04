/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import java.util.List;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.CompanyColumnDO;
import com.zb.app.biz.domain.TravelSiteCoreDO;
import com.zb.app.biz.domain.TravelSiteDO;
import com.zb.app.biz.domain.TravelSiteFullDO;

/**
 * @author zxc Jul 7, 2014 2:47:05 PM
 */
public interface TravelSiteMapper extends BaseMapper<TravelSiteDO> {

    /**
     * getSiteFullBycId 查询
     * 
     * @param query
     * @return
     */
    public List<TravelSiteFullDO> getSiteFullBycId(Long cid);

    public List<TravelSiteFullDO> getSiteFullCore4All();

    public List<TravelSiteCoreDO> getSiteCore4All();

    public List<CompanyColumnDO> getCompanyByzId(Long zId);
}
