/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.CompanyColumnDO;
import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.domain.TravelLineColumnDO;
import com.zb.app.biz.domain.TravelSiteCoreDO;
import com.zb.app.biz.domain.TravelSiteDO;
import com.zb.app.biz.domain.TravelSiteFullDO;
import com.zb.app.biz.query.TravelColumnQuery;
import com.zb.app.biz.query.TravelSiteQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 站点管理 service层 接口
 * 
 * @author zxc Jun 18, 2014 2:29:50 PM
 */
public interface SiteService extends BaseService {

    List<CompanyColumnDO> getCompanyByzId(Long zId);

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////站点管理表(TravelSiteDO)
    // ////////////////////////////////////////////////////////////////////////////////////////

    List<TravelSiteDO> list(TravelSiteQuery query);

    PaginationList<TravelSiteDO> listPagination(TravelSiteQuery query, IPageUrl... iPages);

    List<TravelSiteDO> listTravelSite();

    TravelSiteDO getTravelSiteById(Long id);

    Integer insertTravelSite(TravelSiteDO ts);

    Integer insertTravelSite(TravelSiteDO ts, Long sToId);

    boolean updateTravelSite(TravelSiteDO ts);

    boolean deleteTravelSite(Long id);

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////专线与公司关联表，公司所属专线(TravelLineColumnDO)
    // ////////////////////////////////////////////////////////////////////////////////////////

    List<TravelLineColumnDO> list(Long zId, Long cId);

    List<TravelLineColumnDO> getTravelLineColumnByCid(Long cid);

    List<TravelLineColumnDO> listTravelLineColumn();

    TravelLineColumnDO getTravelLineColumnById(Long id);

    Integer insertTravelLineColumn(TravelLineColumnDO ts);

    boolean updateTravelLineColumn(TravelLineColumnDO ts);

    boolean deleteTravelLineColumn(Long id);

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////站点下专线表，专线分类
    // ////////////////////////////////////////////////////////////////////////////////////////

    Integer insert(TravelColumnDO travelColumnDO);

    boolean delete(Long id);

    List<TravelColumnDO> listQuery(TravelColumnQuery query);

    List<TravelColumnDO> list();

    boolean update(TravelColumnDO travelColumnDO);

    TravelColumnDO getTravelColumnById(Long id);

    List<TravelSiteFullDO> getSiteFull(Long cId);

    List<TravelSiteFullDO> getSiteFullCore4All();

    List<TravelSiteCoreDO> getSiteCoreAll();
}
