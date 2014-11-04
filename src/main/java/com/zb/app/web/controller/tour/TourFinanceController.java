/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.tour;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.domain.TravelFinanceViewDO;
import com.zb.app.biz.query.TravelFinanceQuery;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.interceptor.annotation.ExportExcelFile;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.util.DateViewTools;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.FinanceExcelVO;

/**
 * Tour 财务管理 控制层
 * 
 * @author zxc Jun 17, 2014 5:24:50 PM
 */
@Controller
@RequestMapping(value = "/tour")
public class TourFinanceController extends BaseController {

    @RequestMapping(value = "/financelist.htm")
    public String finance(TravelFinanceQuery query, Model model, Integer page, Integer pagesize) {
        query.setPageSize(Argument.isNotPositive(pagesize) ? 15 : pagesize);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setTourCid(WebUserTools.getCid());
        PaginationList<TravelFinanceViewDO> list = financeService.listPagination(query, new DefaultIpageUrl());

        model.addAttribute("finance", list);
        model.addAttribute("pagination", list.getQuery());
        return "/tour/finance/financelist";
    }

    @RequestMapping(value = "/finance.htm")
    public String financelist() {
        return "/tour/finance/index";
    }

    /**
     * 导出excel
     * 
     * @param id
     * @return
     */
    @RequestMapping("/printExcel.htm")
    @ExportExcelFile(value = "myexcel")
    public ModelAndView exportExcel(TravelFinanceQuery query, ModelAndView mav) {
        List<TravelFinanceViewDO> list = financeService.listQuery(query);
        for (TravelFinanceViewDO view : list) {
            view.setGoGroupTimeString(DateViewTools.formatDate(view.getGoGroupTime()));
            view.setOrCreateTimeString(DateViewTools.formatDate(view.getOrCreateTime()));
            view.setGmtModifiedString(DateViewTools.formatDate(view.getGmtModified()));
        }
        List<FinanceExcelVO> lists = BeanUtils.convert(FinanceExcelVO.class, list);
        String[] head = { "财务流水号", "订单编号", "产品编号", "标题", "出团日期", "组团社名称", "预定账户", "联系人", "地接社名称", "总金额", "已收金额",
                "应收金额", "订单生成时间", "生成时间" };

        mav.addObject("list", lists);
        mav.addObject("head", head);

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.setViewName(null);
        return mav;
    }
}
