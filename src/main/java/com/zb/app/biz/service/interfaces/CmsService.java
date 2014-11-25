/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.TravelAdvertisementDO;
import com.zb.app.biz.domain.TravelArticlesDO;
import com.zb.app.biz.domain.TravelLabelCategoryDO;
import com.zb.app.biz.domain.TravelNewsDO;
import com.zb.app.biz.query.TravelAdvertisementQuery;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.biz.query.TravelLabelCategoryQuery;
import com.zb.app.biz.query.TravelNewsQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.web.vo.TravelArticlesVO;

/**
 * CMS内容管理 service层 接口
 * 
 * @author zxc Jun 18, 2014 2:28:45 PM
 */
public interface CmsService extends BaseService {

    // ////////////////////////////////////////////////////////////////////////////////////////
    //
    // //////////新闻管理表(TravelNewsDO)
    //
    // ////////////////////////////////////////////////////////////////////////////////////////

    Integer addTravelNews(TravelNewsDO tr);

    PaginationList<TravelNewsDO> showNewsPagination(TravelNewsQuery query, IPageUrl... ipPages);

    TravelNewsDO getById(Long id);

    boolean updateById(TravelNewsDO newsDO);

    boolean deleteById(Long id);

    // ////////////////////////////////////////////////////////////////////////////////////////
    //
    // //////////文章管理表(TravelArticlesDO)
    //
    // ////////////////////////////////////////////////////////////////////////////////////////

    Integer addArticles(TravelArticlesDO... ta);

    TravelArticlesDO find(TravelArticlesQuery query);

    List<TravelArticlesDO> list(TravelArticlesQuery query);

    PaginationList<TravelArticlesDO> listPagination(TravelArticlesQuery query, IPageUrl... ipPages);

    TravelArticlesDO getArticlesById(Long id);

    boolean updateById(TravelArticlesDO articles);

    boolean deleteArticlesById(Long id);

    boolean realDelArticles(Long id);

    // ////////////////////////////////////////////////////////////////////////////////////////
    //
    // //////////广告管理表(TravelAdvertisementDO)
    //
    // ////////////////////////////////////////////////////////////////////////////////////////

    Integer addTravelAdvertisement(TravelAdvertisementDO... tad);

    TravelAdvertisementDO find(TravelAdvertisementQuery query);

    List<TravelAdvertisementDO> list(TravelAdvertisementQuery query);

    PaginationList<TravelAdvertisementDO> listPagination(TravelAdvertisementQuery query, IPageUrl... ipPages);

    TravelAdvertisementDO getAdvertisementById(Long id);

    boolean updateById(TravelAdvertisementDO ad);

    boolean deleteAdvertisementById(Long id);

    boolean realDelAdvertisement(Long id);

    // /////////////////////////////////////////////////////////////////////////////////////
    //
    // //////////标签类目表
    //
    // /////////////////////////////////////////////////////////////////////////////////////
    List<TravelLabelCategoryDO> list(TravelLabelCategoryQuery query);

    PaginationList<TravelLabelCategoryDO> listPagination(TravelLabelCategoryQuery query, IPageUrl... iPages);

    List<TravelLabelCategoryDO> listTravelLabelCategory();

    TravelLabelCategoryDO getTravelLabelCategoryById(Long id);

    Integer insertTravelLabelCategory(TravelLabelCategoryDO ts);

    boolean updateTravelLabelCategory(TravelLabelCategoryDO ts);

    boolean deleteTravelLabelCategory(Long id);

	List<TravelArticlesVO> listQueryVO(TravelArticlesQuery travelArticlesQuery);
}
