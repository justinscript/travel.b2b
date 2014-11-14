/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.manage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.vo.TravelOrderFullVO;

/**
 * 订单管理 控制层
 * 
 * @author zxc Jun 16, 2014 3:50:05 PM
 */
@Controller
@RequestMapping("/zbmanlogin")
public class ManageOrderController extends BaseController {
	@AuthorityPolicy(authorityTypes = { Right.VIEW_ORDER })
    @RequestMapping(value = "/orderlist.htm")
    public ModelAndView order(ModelAndView mav, TravelOrderQuery query, Integer page) {
        query.setPageSize(20);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelOrderFullDO> list = orderService.showOrderPagination(query, new DefaultIpageUrl());

        List<TravelOrderFullVO> lists = BeanUtils.convert(TravelOrderFullVO.class, list);
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("orderList", lists);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/manage/order/orderlist");
        return mav;
    }
    
    @RequestMapping(value = "/order.htm")
    public String order(){
    	return "/manage/order/index";
    }
    
    @RequestMapping(value = "/ordercount.htm")
    public ModelAndView ordercount(ModelAndView mav) {
        List<TravelOrderCountDO> orderCountList = orderService.getOrderCount(new TravelOrderQuery());
        mav.setViewName("/manage/order/count");
        if (orderCountList == null || orderCountList.size() == 0) {
            return mav;
        }
        TravelOrderCountDO orderCountDO = new TravelOrderCountDO(0);
        for (TravelOrderCountDO orderCount : orderCountList) {
            orderCountDO.setOrAdultCount(orderCount.getOrAdultCount() + orderCountDO.getOrAdultCount());
            orderCountDO.setOrBabyCount(orderCount.getOrBabyCount() + orderCountDO.getOrBabyCount());
            orderCountDO.setOrChildCount(orderCount.getOrChildCount() + orderCountDO.getOrChildCount());
            orderCountDO.setOrPirceCount(orderCount.getOrPirceCount() + orderCountDO.getOrPirceCount());
            orderCountDO.setAllCount(orderCount.getAllCount() + orderCountDO.getAllCount());
        }
        mav.addObject("list", orderCountList);
        mav.addObject("orderCount", orderCountDO);
        return mav;
    }

    /**
     * @param mav
     * @param id == orState
     * @return
     */
    @RequestMapping(value = "/count_view.htm")
    public ModelAndView count_view(ModelAndView mav, Integer id) {
        mav.setViewName("/manage/order/count_view");
        if (id == null) {
            return mav;
        }
        List<TravelOrderFullDO> list = orderService.showOrderQuery(new TravelOrderQuery(id, null));
        if (list == null || list.size() == 0) {
            return mav;
        }
        TravelOrderCountDO orderCountDO = new TravelOrderCountDO(0);
        for (TravelOrderFullDO orderFullDO : list) {
            orderCountDO.setOrAdultCount(orderFullDO.getOrAdultCount() + orderCountDO.getOrAdultCount());
            orderCountDO.setOrBabyCount(orderFullDO.getOrBabyCount() + orderCountDO.getOrBabyCount());
            orderCountDO.setOrChildCount(orderFullDO.getOrChildCount() + orderCountDO.getOrChildCount());
            orderCountDO.setOrPirceCount(orderFullDO.getOrPirceCount() + orderCountDO.getOrPirceCount());
        }
        orderCountDO.setAllCount(orderCountDO.getOrAdultCount() + orderCountDO.getOrBabyCount()
                                 + orderCountDO.getOrChildCount());
        mav.addObject("list", list);
        mav.addObject("orderCount", orderCountDO);
        return mav;
    }
}
