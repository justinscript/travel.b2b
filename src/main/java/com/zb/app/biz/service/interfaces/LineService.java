/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelLineThinDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.domain.TravelTrafficDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.biz.query.TravelTrafficQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;

/**
 * 产品管理 service层 接口
 * 
 * @author zxc Jun 18, 2014 2:29:03 PM
 */
public interface LineService extends BaseService {

    TravelLineDO find(TravelLineQuery query);

    List<TravelLineDO> list(TravelLineQuery query);

    PaginationList<TravelLineDO> listPagination(TravelLineQuery query, IPageUrl... iPageUrls);

    TravelLineDO getTravelLineById(Long id);

    Integer addTravelLine(TravelLineDO... travelLines);

    boolean updateTravelLine(TravelLineDO travelLine);

    boolean deleteTravelLine(Long id);

    PaginationList<TravelLineDO> listGroup(TravelLineQuery query, IPageUrl... iPageUrls);

    PaginationList<ProductSearchField> listProductSearch(TravelLineQuery query);

    Integer count(TravelLineQuery query);
    
    Integer countByGroup(TravelLineQuery query);

    List<String> getCityByCid(Long cid);
    /**
     * 根据线路ID数组批量修改线路
     * 
     * @param lids
     * @param trdo
     * @return
     */
    Integer updateLines(Long[] lids, TravelLineThinDO trdo);
    // //////////////////////////////////////////////***********华丽的分界线************////////////////////////////////////////////////////////////////

    TravelRouteDO find(TravelRouteQuery query);

    List<TravelRouteDO> list(TravelRouteQuery query);

    PaginationList<TravelRouteDO> listPagination(TravelRouteQuery query);

    TravelRouteDO getTravelRouteById(Long id);

    Integer addTravelRoute(TravelRouteDO... travelRoutes);

    boolean updateTravelRoute(TravelRouteDO travelRoute);

    boolean deleteTravelRoute(Long id);

    boolean deleteTravelRouteByLineid(Long id);

    // //////////////////////////////////////////////***********华丽的分界线************////////////////////////////////////////////////////////////////

    List<TravelTrafficDO> listTravelTraffic();

    TravelTrafficDO getTravelTrafficById(Long id);

    Integer insertTravelTraffic(TravelTrafficDO... ts);

    boolean updateTravelTraffic(TravelTrafficDO ts);

    boolean deleteTravelTraffic(Long id);

    PaginationList<TravelTrafficDO> listPagination(TravelTrafficQuery query, IPageUrl... pageUrls);
}
