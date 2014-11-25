/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.TravelBlackListDO;
import com.zb.app.biz.domain.TravelBlackListFullDO;
import com.zb.app.biz.domain.TravelBlackListThinDO;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelServiceDO;
import com.zb.app.biz.domain.TravelServiceSiteDO;
import com.zb.app.biz.query.TravelBlackListQuery;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelServiceQuery;
import com.zb.app.biz.query.TravelServiceSiteQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 公司商家管理 service层 接口
 * 
 * @author zxc Jun 18, 2014 2:20:12 PM
 */
public interface CompanyService extends BaseService {

    // //////////////////////////////////////////////***********华丽的分界线************////////////////////////////////////////////////////////////////

    Integer insert(TravelCompanyDO travelCompanyDO);

    boolean delete(Integer id);

    List<TravelCompanyDO> listPage(TravelCompanyQuery query);

    List<TravelCompanyDO> list();

	List<TravelCompanyDO> listQuery(TravelCompanyQuery query);

    boolean update(TravelCompanyDO travelCompanyDO);

    TravelCompanyDO getById(Long cId);

    PaginationList<TravelCompanyDO> showCompanyPagination(TravelCompanyQuery query, IPageUrl... iPages);

    TravelCompanyDO getByName(TravelCompanyQuery query);

    // //////////////////////////////////////////////***********华丽的分界线************////////////////////////////////////////////////////////////////

    TravelBlackListDO find(TravelBlackListQuery query);

    List<TravelBlackListDO> list(TravelBlackListQuery query);

    PaginationList<TravelBlackListFullDO> listPagination(TravelBlackListQuery query, IPageUrl... iPages);

    TravelBlackListDO getTravelBlackListById(Integer id);

    Integer addTravelBlackList(TravelBlackListDO... travels);

    boolean updateTravelBlackList(TravelBlackListDO travel);

    boolean deleteTravelBlackList(Integer id);

    Integer getBlackCount(TravelBlackListQuery travelBlackListQuery);

    PaginationList<TravelBlackListThinDO> queryAllCompanyBlack(TravelBlackListQuery query, IPageUrl... iPages);

    // //////////////////////////////////////////////***********华丽的分界线************////////////////////////////////////////////////////////////////

    Integer addService(TravelServiceDO... service);

    TravelServiceDO find(TravelServiceQuery query);

    List<TravelServiceDO> list(TravelServiceQuery query);

    PaginationList<TravelServiceDO> listPagination(TravelServiceQuery query, IPageUrl... ipPages);

    TravelServiceDO getServiceById(Long id);

    boolean updateById(TravelServiceDO service);

    boolean deleteServiceById(Long id);

    boolean realDelService(Long id);

    // //////////////////////////////////////////////***********华丽的分界线************////////////////////////////////////////////////////////////////
    Integer addServiceSite(TravelServiceSiteDO... service);

    TravelServiceSiteDO find(TravelServiceSiteQuery query);

    List<TravelServiceSiteDO> list(TravelServiceSiteQuery query);

    PaginationList<TravelServiceSiteDO> listPagination(TravelServiceSiteQuery query, IPageUrl... ipPages);

    TravelServiceSiteDO getServiceSiteById(Long id);

    boolean updateById(TravelServiceSiteDO service);

    boolean deleteServiceSiteById(Long id);

    boolean realDelServiceSite(Long id);

    List<TravelServiceSiteDO> getServiceSiteBySId(Long id);

	Integer countByAccount(TravelCompanyQuery companyQuery);
}
