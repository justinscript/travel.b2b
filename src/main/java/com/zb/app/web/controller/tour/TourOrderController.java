/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.tour;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.component.annotation.FormBean;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.interceptor.annotation.ExportExcelFile;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.OrderExcelVO;
import com.zb.app.web.vo.TravelOrderFullVO;

/**
 * Tour 订单管理 控制层
 * 
 * @author zxc Jun 17, 2014 5:43:48 PM
 */
@Controller
@RequestMapping(value = "/tour")
public class TourOrderController extends BaseController {

    @AuthorityPolicy(authorityTypes = { Right.VIEW_ORDER })
    @RequestMapping(value = "/orderlist.htm")
    public ModelAndView orderindex(ModelAndView mav, TravelOrderQuery query, Integer page) {
        query.setPageSize(3);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setCustomCompanyId(WebUserTools.getCid());

        PaginationList<TravelOrderFullDO> list = orderService.showOrderPagination(query, new DefaultIpageUrl());
        List<TravelOrderFullVO> lists = BeanUtils.convert(TravelOrderFullVO.class, list);

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("orderList", lists);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/tour/order/orderlist");
        return mav;
    }

    @RequestMapping(value = "/order.htm")
    public String order() {
        return "/tour/order/index";
    }

    /**
     * 修改订单方法
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_ORDER })
    @RequestMapping(value = "/updateOrder.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateOrder(TravelOrderDO orderVO, @FormBean(value = "guestVOs")
    TravelOrderGuestDO[] guestVOs) {
        TravelOrderDO orderDO = new TravelOrderDO();
        BeanUtils.copyProperties(orderDO, orderVO);
        Boolean orBool = orderService.updateTravelOrder(orderDO);
        if (!orBool) return JsonResultUtils.error(orderDO, "保存失败!");

        List<TravelOrderGuestDO> addList = new ArrayList<TravelOrderGuestDO>();
        int b = 1;

        for (TravelOrderGuestDO guestVO : guestVOs) {
            TravelOrderGuestDO guestDO = new TravelOrderGuestDO();
            BeanUtils.copyProperties(guestDO, guestVO);
            if (guestDO.getgId() == 0) {
                guestDO.setOrId(orderDO.getOrId());
                addList.add(guestDO);
                if (b == 0) {
                    return JsonResultUtils.error(guestDO, "保存失败!");
                } else {
                    TravelOrderFullDO travelOrderFullDO = orderService.find(new TravelOrderQuery(orderDO.getOrId()));
                    TravelOrderDO travelOrderDO = new TravelOrderDO();
                    travelOrderDO.setOrId(travelOrderFullDO.getOrId());
                    if (guestDO.getgType() == 0) {
                        travelOrderDO.setOrAdultCount(travelOrderFullDO.getOrAdultCount() + 1);
                    } else if (guestDO.getgType() == 1) {
                        travelOrderDO.setOrChildCount(travelOrderFullDO.getOrChildCount() + 1);
                    } else if (guestDO.getgType() == 2) {
                        travelOrderDO.setOrBabyCount(travelOrderFullDO.getOrBabyCount() + 1);
                    }
                    Boolean bool = orderService.updateTravelOrder(travelOrderDO);
                    if (!bool) {
                        return JsonResultUtils.error(orderDO, "保存失败!");
                    } else {
                        TravelLineDO lineDO = lineService.find(new TravelLineQuery(travelOrderFullDO.getlId()));
                        TravelLineDO travelLine = new TravelLineDO();
                        travelLine.setlId(lineDO.getlId());
                        if (guestDO.getgType() == 0) {
                            travelLine.setlCrCount(lineDO.getlCrCount() + 1);
                        } else if (guestDO.getgType() == 1) {
                            travelLine.setlXhCount(lineDO.getlXhCount() + 1);
                        } else if (guestDO.getgType() == 2) {
                            travelLine.setlYCount(lineDO.getlYCount() + 1);
                        }
                        Boolean lBool = lineService.updateTravelLine(travelLine);
                        if (!lBool) return JsonResultUtils.error(orderDO, "保存失败!");
                    }
                }
            } else {
                Boolean gBool = orderService.updateTravelOrderGuest(guestDO);
                if (!gBool) return JsonResultUtils.error(guestDO, "保存失败!");
            }
        }

        orderService.addTravelOrderGuest(addList.toArray(new TravelOrderGuestDO[addList.size()]));

        return JsonResultUtils.success(orderDO, "保存成功!");
    }

    /**
     * 取消订单方法
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CANCEL_ORDER })
    @RequestMapping(value = "/cancelOrder.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult clearorder(TravelOrderDO orderDO) {
        Boolean b = orderService.cancelOrder(orderDO);
        if (b) {
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
        mav.setViewName("tour/order/clearorder");
        TravelOrderDO orderDO = orderService.getById(id);
        mav.addObject("order", orderDO);
        return mav;
    }

    /**
     * 订单的统计分析
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value = "/count.htm")
    public ModelAndView ordercount(ModelAndView mav) {
        mav.setViewName("order/count");
        TravelOrderQuery query = new TravelOrderQuery();
        query.setCustomCompanyId(WebUserTools.getCid());
        List<TravelOrderCountDO> orderCountList = orderService.getOrderCount(query);
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
     * 导出订单excel
     * 
     * @param id
     * @return
     */
    @RequestMapping("/printOrderExcel.htm")
    @ExportExcelFile(value = "myexcel")
    public ModelAndView exportExcel(TravelOrderQuery query, ModelAndView mav, Integer page) {
        query.setPageSize(3);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setCustomCompanyId(WebUserTools.getCid());

        PaginationList<TravelOrderFullDO> list = orderService.showOrderPagination(query, new DefaultIpageUrl());
        List<TravelOrderFullVO> lists = BeanUtils.convert(TravelOrderFullVO.class, list);
        List<OrderExcelVO> excelVOs = new ArrayList<OrderExcelVO>();
        for (TravelOrderFullVO fullVO : lists) {
            OrderExcelVO excelVO = new OrderExcelVO(fullVO.getGmtCreateString(),
                                                    OrderStateEnum.getAction(fullVO.getOrState()).getName(),
                                                    fullVO.getOrOrderId(), fullVO.getOrGoGroupTimeString(),
                                                    fullVO.getlTile(), fullVO.getcName(), fullVO.getmName(),
                                                    fullVO.getmTel(), fullVO.getOrPirceCount(),
                                                    fullVO.getGmtModifiedString());
            excelVOs.add(excelVO);
        }
        String[] head = { "下单时间", "状态", "订单编号", "出团时间", "线路名称", "预定旅行社", "预定用户", "联系电话", "订单总额", "操作时间" };

        mav.addObject("list", excelVOs);
        mav.addObject("head", head);

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.setViewName(null);
        return mav;
    }
}
