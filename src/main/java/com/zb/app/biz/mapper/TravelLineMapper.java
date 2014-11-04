/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.mapper;

import java.util.List;
import java.util.Map;

import com.zb.app.biz.base.BaseMapper;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;

/**
 * @author zxc Jun 25, 2014 9:57:23 AM
 */
public interface TravelLineMapper extends BaseMapper<TravelLineDO> {

    /**
     * find 查询
     * 
     * @param query
     * @return
     */
    public TravelLineDO find(TravelLineQuery query);

    /**
     * update 更新
     * 
     * @param query
     * @return
     */
    public Integer update(TravelLineDO travelLine);

    /**
     * count 操作
     * 
     * @param query
     * @return
     */
    public Integer count(TravelLineQuery query);

    /**
     * count分组 操作
     * 
     * @param query
     * @return
     */
    public Integer countByGroup(TravelLineQuery query);

    /**
     * 分组查询出团日期
     * 
     * @param query
     * @return
     */
    public List<TravelLineDO> listGroup(TravelLineQuery query);

    /***
     * 批量修改线路
     * 
     * @param map
     * @return
     */
    public Integer updateByLines(Map<String, Object> map);

    /***
     * 查询线路以及行程（建索引）
     * 
     * @param query
     * @return
     */
    public PaginationList<ProductSearchField> listProductSearch(TravelLineQuery query);
    
    /***
     * countProductSearch
     * @param query
     * @return
     */
    public Integer countProductSearch(TravelLineQuery query);
}
