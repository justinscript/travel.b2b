/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zb.app.biz.base.BaseDao;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.mapper.TravelLineMapper;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.util.ObjectUtils;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;

/**
 * @author zxc Jun 25, 2014 10:03:50 AM
 */
@SuppressWarnings("unchecked")
@Repository
public class TravelLineDao extends BaseDao<TravelLineDO, TravelLineMapper, TravelLineQuery> {

    /**
     * 插入,return 实体的ID属性值
     * 
     * @param travelLine
     * @return
     */
    @Override
    public Integer insert(TravelLineDO travelLine) {
        m.insert(travelLine);
        return travelLine.getlId().intValue();
    }

    /**
     * PaginationList 查询数据
     * 
     * @param q
     */
    @Override
    public PaginationList<TravelLineDO> paginationList(TravelLineQuery query) {
        Assert.assertNotNull(query);
        return super.paginationList("listPagination", query);
    }

    /**
     * find 查询
     * 
     * @param query
     * @return
     */
    @Override
    public TravelLineDO find(TravelLineQuery query) {
        ObjectUtils.trim(query);
        return m.find(query);
    }

    /**
     * update 更新
     * 
     * @param travelLine
     * @return
     */
    public Integer update(TravelLineDO travelLine) {
        ObjectUtils.trim(travelLine);
        return m.update(travelLine);
    }

    /***
     * 批量更新线路
     * 
     * @param map
     * @return
     */
    public Integer updateLines(Map<String, Object> map) {
        return m.updateByLines(map);
    }

    /**
     * count 操作
     * 
     * @param query
     * @return
     */
    @Override
    public Integer count(TravelLineQuery query) {
        ObjectUtils.trim(query);
        return m.count(query);
    }

    /**
     * 分组count 操作
     * 
     * @param query
     * @return
     */
    public Integer countByGroup(TravelLineQuery query) {
        ObjectUtils.trim(query);
        return m.countByGroup(query);
    }

    @Override
    public String getNameSpace() {
        return TravelLineMapper.class.getName();
    }

    public PaginationList<TravelLineDO> listGroup(TravelLineQuery q, IPageUrl... pageUrls) {
        Assert.assertNotNull(q);
        return super.paginationList("countByGroup", "listGroup", q, pageUrls);
    }

    /***
     * 查询所有线路&&行程数据
     * 
     * @return
     */
    public PaginationList<ProductSearchField> listProductSearch(TravelLineQuery query, IPageUrl... pageUrls) {
        Assert.assertNotNull(query);
        return super.paginationList("countProductSearch", "listProductSearch", query, pageUrls);
    }

    public Integer countProductSearch(TravelLineQuery query) {
        ObjectUtils.trim(query);
        return m.countProductSearch(query);
    }
}
