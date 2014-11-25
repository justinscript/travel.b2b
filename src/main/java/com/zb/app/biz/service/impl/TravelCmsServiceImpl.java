/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.app.biz.dao.TravelAdvertisementDao;
import com.zb.app.biz.dao.TravelArticlesDao;
import com.zb.app.biz.dao.TravelLabelCategoryDao;
import com.zb.app.biz.dao.TravelNewsDao;
import com.zb.app.biz.domain.TravelAdvertisementDO;
import com.zb.app.biz.domain.TravelArticlesDO;
import com.zb.app.biz.domain.TravelLabelCategoryDO;
import com.zb.app.biz.domain.TravelNewsDO;
import com.zb.app.biz.query.TravelAdvertisementQuery;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.biz.query.TravelLabelCategoryQuery;
import com.zb.app.biz.query.TravelNewsQuery;
import com.zb.app.biz.service.interfaces.CmsService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.web.vo.TravelArticlesVO;

/**
 * 新闻
 * 
 * @author zxc Jul 25, 2014 10:53:52 AM
 */
@SuppressWarnings("unchecked")
@Service
public class TravelCmsServiceImpl implements CmsService {

    @Autowired
    private TravelNewsDao          travelNewsDao;
    @Autowired
    private TravelArticlesDao      articlesDao;
    @Autowired
    private TravelAdvertisementDao advertisementDao;
    @Autowired
    private TravelLabelCategoryDao labelCategoryDao;

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Integer addTravelNews(TravelNewsDO tn) {
        if (tn == null) {
            return 0;
        }
        return travelNewsDao.insert(tn);
    }

    @Override
    public PaginationList<TravelNewsDO> showNewsPagination(TravelNewsQuery query, IPageUrl... ipPages) {
        if (query == null) {
            return (PaginationList<TravelNewsDO>) Collections.<TravelNewsDO> emptyList();
        }
        return (PaginationList<TravelNewsDO>) travelNewsDao.showNewsPagination(query, ipPages);
    }

    @Override
    public TravelNewsDO getById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelNewsDao.getById(id);
    }

    @Override
    public boolean updateById(TravelNewsDO newsDO) {
        if (newsDO == null) {
            return false;
        }
        return travelNewsDao.updateById(newsDO);
    }

    @Override
    public boolean deleteById(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelNewsDao.deleteById(id);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Integer addArticles(TravelArticlesDO... ta) {
        if (ta == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(ta);
        if (Argument.isEmptyArray(ta)) {
            return 0;
        }
        Integer count = articlesDao.insert(ta);
        if (ta.length == 1) {
            return ta[0].getaId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public TravelArticlesDO find(TravelArticlesQuery query) {
        if (query == null) {
            return null;
        }
        return articlesDao.find(query);
    }

    @Override
    public List<TravelArticlesDO> list(TravelArticlesQuery query) {
        if (query == null) {
            return Collections.<TravelArticlesDO> emptyList();
        }
        return articlesDao.list(query);
    }

    @Override
    public PaginationList<TravelArticlesDO> listPagination(TravelArticlesQuery query, IPageUrl... ipPages) {
        if (query == null) {
            return (PaginationList<TravelArticlesDO>) Collections.<TravelArticlesDO> emptyList();
        }
        return (PaginationList<TravelArticlesDO>) articlesDao.paginationList(query, ipPages);
    }

    @Override
    public TravelArticlesDO getArticlesById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return articlesDao.getById(id);
    }

    @Override
    public boolean updateById(TravelArticlesDO articles) {
        if (articles == null) {
            return false;
        }
        if (Argument.isNotPositive(articles.getaId())) {
            return false;
        }
        return articlesDao.updateById(articles);
    }

    @Override
    public boolean deleteArticlesById(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return updateById(new TravelArticlesDO(id));
    }

    @Override
    public boolean realDelArticles(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return articlesDao.deleteById(id);
    }
    


	@Override
	public List<TravelArticlesVO> listQueryVO(
			TravelArticlesQuery travelArticlesQuery) {
		if (travelArticlesQuery == null) {
            return Collections.<TravelArticlesVO> emptyList();
        }
        return articlesDao.listQueryVO(travelArticlesQuery);
	}

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Integer addTravelAdvertisement(TravelAdvertisementDO... tad) {
        if (tad == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(tad);
        if (Argument.isEmptyArray(tad)) {
            return 0;
        }
        Integer count = advertisementDao.insert(tad);
        if (tad.length == 1) {
            return tad[0].getAdId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public TravelAdvertisementDO find(TravelAdvertisementQuery query) {
        if (query == null) {
            return null;
        }
        return advertisementDao.find(query);
    }

    @Override
    public List<TravelAdvertisementDO> list(TravelAdvertisementQuery query) {
        if (query == null) {
            return Collections.<TravelAdvertisementDO> emptyList();
        }
        return advertisementDao.list(query);
    }

    @Override
    public PaginationList<TravelAdvertisementDO> listPagination(TravelAdvertisementQuery query, IPageUrl... ipPages) {
        if (query == null) {
            return (PaginationList<TravelAdvertisementDO>) Collections.<TravelAdvertisementDO> emptyList();
        }
        return (PaginationList<TravelAdvertisementDO>) advertisementDao.paginationList(query, ipPages);
    }

    @Override
    public TravelAdvertisementDO getAdvertisementById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return advertisementDao.getById(id);
    }

    @Override
    public boolean updateById(TravelAdvertisementDO ad) {
        if (ad == null) {
            return false;
        }
        if (Argument.isNotPositive(ad.getAdId())) {
            return false;
        }
        return advertisementDao.updateById(ad);
    }

    @Override
    public boolean deleteAdvertisementById(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return updateById(new TravelAdvertisementDO(id));
    }

    @Override
    public boolean realDelAdvertisement(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return advertisementDao.deleteById(id);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 标签类目表
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<TravelLabelCategoryDO> list(TravelLabelCategoryQuery query) {
        if (query == null) {
            return Collections.<TravelLabelCategoryDO> emptyList();
        }
        return labelCategoryDao.list(query);
    }

    @Override
    public PaginationList<TravelLabelCategoryDO> listPagination(TravelLabelCategoryQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelLabelCategoryDO>) Collections.<TravelLabelCategoryDO> emptyList();
        }
        return (PaginationList<TravelLabelCategoryDO>) labelCategoryDao.paginationList(query, iPages);
    }

    @Override
    public List<TravelLabelCategoryDO> listTravelLabelCategory() {
        List<TravelLabelCategoryDO> list = labelCategoryDao.list();
        return list == null ? Collections.<TravelLabelCategoryDO> emptyList() : list;
    }

    @Override
    public TravelLabelCategoryDO getTravelLabelCategoryById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return labelCategoryDao.getById(id);
    }

    @Override
    public Integer insertTravelLabelCategory(TravelLabelCategoryDO ts) {
        Integer count = labelCategoryDao.insert(ts);
        if (count == null || count == 0) {
            return 0;
        }
        return ts.getLcId().intValue();
    }

    @Override
    public boolean updateTravelLabelCategory(TravelLabelCategoryDO ts) {
        if (ts == null) {
            return false;
        }
        return labelCategoryDao.updateById(ts);
    }

    @Override
    public boolean deleteTravelLabelCategory(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return labelCategoryDao.deleteById(id);
    }
}
