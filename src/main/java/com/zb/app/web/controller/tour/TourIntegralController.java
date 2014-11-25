/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.tour;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.domain.TravelGiftOrderFullDO;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.query.TravelGiftOrderQuery;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;

/**
 * Tour 积分管理 控制层
 * 
 * @author zxc Jun 17, 2014 5:14:50 PM
 */
@Controller
@RequestMapping("/tour")
public class TourIntegralController extends BaseController {

    @RequestMapping(value = "/integral.htm")
    public ModelAndView integral(ModelAndView mav, TravelIntegralQuery query, Integer page) {
        TravelIntegralQuery integralQuery = new TravelIntegralQuery();
        integralQuery.setcId(WebUserTools.getCid());
        integralQuery.setmId(WebUserTools.getMid());

        TravelIntegralDO integralDO = integralService.queryBala(integralQuery);

        query.setPageSize(10);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcId(WebUserTools.getCid());
        query.setmId(WebUserTools.getMid());

        PaginationList<TravelIntegralDO> list = integralService.listPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("tidList", list);
        mav.addObject("integralDO", integralDO);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/tour/integral/index");
        return mav;
    }

    @RequestMapping(value = "/integral/cart.htm")
    public ModelAndView cart(ModelAndView mav, TravelGiftOrderQuery query, Integer page) {
        query.setPageSize(10);
        query.setcId(WebUserTools.getCid());
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelGiftOrderFullDO> list = integralService.fullListPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("giftOrderList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/tour/integral/cart");
        return mav;
    }
}
