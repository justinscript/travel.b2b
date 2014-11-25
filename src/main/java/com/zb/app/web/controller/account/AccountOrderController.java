/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.FinanceTypeEnum;
import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.biz.domain.TravelFinanceDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.util.DateViewTools;
import com.zb.app.common.util.PushSMSUtils;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelLineVO;
import com.zb.app.web.vo.TravelOrderFullVO;

/**
 * Account 订单管理 控制层
 * 
 * @author zxc Jun 17, 2014 6:04:20 PM
 */
@Controller
@RequestMapping("/account")
public class AccountOrderController extends BaseController {

    @RequestMapping(value = "/index.htm")
    public ModelAndView pageOrder() {
        return new ModelAndView("account/order/index");
    }

    @RequestMapping(value = "/count.htm")
    public ModelAndView ordercount(ModelAndView mav) {
        List<TravelOrderCountDO> orderCountList = orderService.getOrderCount(new TravelOrderQuery(null,
                                                                                                  WebUserTools.getCid()));
        mav.setViewName("order/count");
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
     * 分页查询订单
     * 
     * @param page 当前页码
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.VIEW_ORDER })
    @RequestMapping(value = "/showorderlist.htm")
    public ModelAndView pageOrderList(ModelAndView mav, TravelOrderQuery query, Integer page) {
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setPageSize(20);
        query.setcId(WebUserTools.getCid());
        PaginationList<TravelOrderFullDO> list = orderService.showOrderPagination(query, new DefaultIpageUrl());

        List<TravelOrderFullVO> lists = BeanUtils.convert(TravelOrderFullVO.class, list);
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("orderList", lists);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("account/order/orderlist");
        return mav;
    }

    /**
     * 进入分页查看订单页面
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.VIEW_ORDER })
    @RequestMapping(value = "/orderlist.htm")
    public ModelAndView orderlist(ModelAndView mav, TravelOrderQuery query) {
        query.setPageSize(5);
        List<TravelOrderDO> lists = BeanUtils.convert(TravelOrderDO.class,
                                                      orderService.showOrderPagination(query, new DefaultIpageUrl()));
        mav.addObject("orderList", lists);
        mav.setViewName("account/index");
        return mav;
    }

    /**
     * 取消订单方法
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CANCEL_ORDER })
    @RequestMapping(value = "/cancelOrder.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cancelOrder(TravelOrderDO orderDO) {
        Boolean b = orderService.cancelOrder(orderDO);
        if (b) {
        	if(orderDO.getOrMobile() != null){
        		logger.debug("发送信息");
            	PushSMSUtils.getInstance().sendOrderCancelSMS(orderDO.getOrOrderId(), orderDO.getOrMobile());
        	}
            return JsonResultUtils.success(orderDO, "取消成功!");
        } else {
            return JsonResultUtils.success(orderDO, "取消失败!");
        }
    }

    /**
     * 连接取消订单页面
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CANCEL_ORDER })
    @RequestMapping(value = "/clearorder.htm")
    public ModelAndView clearorder(ModelAndView mav, Long id) {
        TravelOrderDO orderDO = orderService.getById(id);
        mav.addObject("order", orderDO);
        mav.setViewName("account/order/clearorder");
        return mav;
    }


    @RequestMapping(value = "/lineOrder.htm")
    public ModelAndView lineOrder(Long id, ModelAndView mav) {
        // 设置路线Id,查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));
        TravelOrderQuery query = new TravelOrderQuery();
        query.setlId(id);
        PaginationList<TravelOrderFullDO> list = orderService.showOrderPagination(query);

        List<TravelOrderFullVO> lists = BeanUtils.convert(TravelOrderFullVO.class, list);
        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("orderList", lists);
        mav.setViewName("account/order/lineorder");
        return mav;
    }

    /**
     * 确认订单
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/affirmOrder.htm")
    @ResponseBody
    public JsonResult affirmOrder(Long id) {
        TravelOrderDO travelOrderDO = orderService.getById(id);
        TravelOrderDO orderDO = new TravelOrderDO();
        orderDO.setOrId(id);
        orderDO.setOrState(OrderStateEnum.CONFIRM.getValue());
        Boolean boolean1 = orderService.updateTravelOrder(orderDO);
        if (boolean1) {
            TravelFinanceDO financeDO = new TravelFinanceDO();
            financeDO.setOrId(id);
            financeDO.setfType(FinanceTypeEnum.ARREARS.getValue());
            financeDO.setfSerialNumber(getSerialNumber());
            financeDO.setAccountCid(travelOrderDO.getcId());
            financeDO.setTourCid(travelOrderDO.getCustomCompanyId());
            financeDO.setfReceivable(travelOrderDO.getOrPirceCount().floatValue());
            financeDO.setfReceipt((float) 0);
            financeService.addTravelFinance(financeDO);
            if(travelOrderDO.getOrMobile() != null){
            	logger.debug("发送信息");
            	PushSMSUtils.getInstance().sendOrderConfirmSMS(travelOrderDO.getOrOrderId(), travelOrderDO.getOrMobile());
            }
            return JsonResultUtils.success(orderDO, "确认成功!");
        } else {
            return JsonResultUtils.error(orderDO, "确认失败!");
        }
    }

    /**
     * 生成流水号
     * 
     * @return
     */
    private String getSerialNumber() {
        String now = DateViewTools.format(new Date(), "yyyyMMddHHmmssSSS");
        int num = new Random().nextInt(50000) + 10000;
        return now + num;
    }
}
