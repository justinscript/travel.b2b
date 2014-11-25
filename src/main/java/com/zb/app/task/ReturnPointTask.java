/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zb.app.biz.cons.IntegralSourceEnum;
import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.biz.service.interfaces.IntegralService;
import com.zb.app.biz.service.interfaces.OrderService;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.task.AbstractTask;

/**
 * 返积分Job
 * 
 * @author zxc Aug 14, 2014 4:35:14 PM
 */
@Component
public class ReturnPointTask extends AbstractTask {

    @Autowired
    private OrderService    orderService;
    @Autowired
    private IntegralService integralService;

    @Override
    public void initTask() throws Exception {

    }

    /**
     * 每天凌晨0点执行一次
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void returnPointTask() {
        init();
    }

    @Override
    public void doTask() throws Exception {
        logger.error("start init doTask!");
        TravelOrderQuery query = new TravelOrderQuery();
        query.setPageSize(2000);
        query.setOrState(OrderStateEnum.CONFIRM.getValue());
        query.setOrGoGroupTime(new Date());
        do {
            PaginationList<TravelOrderDO> result = orderService.listPagination(query);
            if (result == null || result.isEmpty()) {
                break;
            }
            for (TravelOrderDO order : result) {
                Integer orAdultCount = order.getOrAdultCount(); // 成人数
                Integer orChildCount = order.getOrChildCount(); // 儿童数
                Integer orAdultIntegral = order.getOrAdultIntegral();// 成人积分
                Integer orChildrenIntegral = order.getOrChildrenIntegral(); // 儿童积分
                Integer totalIntegral = orAdultCount * orAdultIntegral + orChildCount * orChildrenIntegral;

                Long customId = order.getCustomId(); // 组团社用户id
                Long customCompanyId = order.getCustomCompanyId(); // 组团社公司id

                TravelIntegralQuery integralQuery = new TravelIntegralQuery();
                integralQuery.setcId(customCompanyId);
                integralQuery.setmId(customId);
                TravelIntegralDO integralDO = integralService.queryBala(integralQuery);

                TravelIntegralDO integralDJ = new TravelIntegralDO();
                integralDJ.setiSource(IntegralSourceEnum.return_point.value);
                integralDJ.setcId(customCompanyId);
                integralDJ.setmId(customId);
                integralDJ.setiAddintegral(new Integer(totalIntegral));
                if (integralDO == null) {
                    integralDJ.setiBalance(new Integer(totalIntegral));
                    integralDJ.setiFrozen(0);
                    integralDJ.setiAltogether(integralDJ.getiBalance() + integralDJ.getiFrozen());
                } else {
                    integralDJ.setiBalance(integralDO.getiBalance() + totalIntegral);
                    integralDJ.setiFrozen(integralDO.getiFrozen() == null ? 0 : integralDO.getiFrozen());
                    integralDJ.setiAltogether(integralDJ.getiBalance() + integralDJ.getiFrozen());
                }
                integralService.addTravelIntegral(integralDJ);

                // 计算归还积分
                integralQuery.setcId(order.getcId());
                integralQuery.setmId(null);
                integralDO = integralService.queryBala(integralQuery);
                if (integralDO.getiFrozen() > totalIntegral) {
                    TravelIntegralDO integral = new TravelIntegralDO();
                    integral.setiSource(IntegralSourceEnum.frozen_return.value);
                    integral.setcId(order.getcId());
                    integral.setiBalance(integralDO.getiBalance() + integralDO.getiFrozen() - totalIntegral);
                    integral.setiFrozen(0);
                    integral.setiAltogether(integral.getiBalance() + integral.getiFrozen());
                    integralService.addTravelIntegral(integral);
                }
                // write message
            }
        } while (query.toNextPage());
        logger.error("doTask finish!");
    }
}
