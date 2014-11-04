/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.interfaces;

import java.util.List;

import com.zb.app.biz.base.BaseService;
import com.zb.app.biz.domain.MessageData;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.biz.query.TravelMessageQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;

/**
 * 消息表
 * 
 * @author zxc Jun 18, 2014 2:33:12 PM
 */
public interface MessageService extends BaseService {

    MessageData getMessageData(Long toCid);

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    TravelMessageDO find(TravelMessageQuery query);

    List<TravelMessageDO> list(TravelMessageQuery query);

    /**
     * 基础分页查询(IPageUrl分页策略,支持按时间区间查询)
     * 
     * @param query
     * @param iPageUrl
     * @return
     */
    PaginationList<TravelMessageDO> listPagination(TravelMessageQuery query, IPageUrl... iPageUrl);

    TravelMessageDO getTravelMessageById(Long id);

    Integer addTravelMessage(TravelMessageDO... messages);

    boolean updateTravelMessage(TravelMessageDO message);

    /**
     * 逻辑删除
     * 
     * @param id
     * @return
     */
    boolean deleteTravelMessage(Long id);

    /**
     * 物理删除
     * 
     * @param id
     * @return
     */
    boolean realDeleteTravelMessage(Long id);
}
