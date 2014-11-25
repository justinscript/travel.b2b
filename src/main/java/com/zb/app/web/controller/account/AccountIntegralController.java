/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelIntegralDealDO;
import com.zb.app.biz.domain.TravelIntegralFullDO;
import com.zb.app.biz.query.TravelIntegralDealQuery;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.interceptor.annotation.ExportExcelFile;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;

/**
 * Account 积分管理
 * 
 * @author zxc Jun 17, 2014 6:06:44 PM
 */
@Controller
@RequestMapping("/account")
public class AccountIntegralController extends BaseController {

    @RequestMapping(value = "/integral.htm")
    public ModelAndView integral(ModelAndView mav, TravelIntegralQuery query, Integer page) {
        query.setPageSize(10);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcId(WebUserTools.getCid());
        query.setmId(WebUserTools.getMid());

        PaginationList<TravelIntegralFullDO> list = integralService.fullListPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("integralList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("account/integral/index");
        return mav;
    }

    @RequestMapping(value = "/integral/record.htm")
    public ModelAndView integralrecord(ModelAndView mav, TravelIntegralQuery query, Integer page) {
        TravelIntegralQuery integralQuery = new TravelIntegralQuery();
        integralQuery.setcId(WebUserTools.getCid());

        TravelIntegralDO integralDO = integralService.queryBala(integralQuery);

        query.setPageSize(10);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcId(WebUserTools.getCid());

        PaginationList<TravelIntegralDO> list = integralService.listPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("tidList", list);
        mav.addObject("integralDO", integralDO);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("account/integral/record");
        return mav;
    }

    /**
     * 导出积分excel
     * 
     * @param id
     * @return
     */
    @RequestMapping("/printIntegralExcel.htm")
    @ExportExcelFile(value = "myexcel")
    public ModelAndView exportExcel(ModelAndView mav) {
        TravelIntegralDealQuery query = new TravelIntegralDealQuery(WebUserTools.getCid(), WebUserTools.getMid());

        PaginationList<TravelIntegralDealDO> list = integralService.listPagination(query, new DefaultIpageUrl());
        String[] head = { "ID", "创建时间", "最后修改时间", "公司ID", "用户ID", "积分类型", "消耗积分", "积分订单ID", "积分产品ID", "LId", "操作内容", };

        mav.addObject("list", list);
        mav.addObject("head", head);

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.setViewName(null);
        return mav;
    }
}
