/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelFinanceViewDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.query.TravelFinanceQuery;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.interceptor.annotation.ExportWordFile;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;

/**
 * Account 财务管理 Controller层 接口
 * 
 * @author zxc Jun 17, 2014 6:05:32 PM
 */
@Controller
@RequestMapping("/account")
public class AccountFinanceController extends BaseController {

    @RequestMapping(value = "/finance.htm")
    public String finance(Model model) {
        model.addAttribute("url", "/account/financelist.htm");
        return "account/finance/index";
    }

    @RequestMapping(value = "financelist.htm", method = RequestMethod.POST)
    public ModelAndView financelist(TravelFinanceQuery query, ModelAndView model, Integer pagesize, Integer page) {
        query.setPageSize(Argument.isNotPositive(pagesize) ? 15 : pagesize);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setAccountCid(WebUserTools.getCid());
        PaginationList<TravelFinanceViewDO> list = financeService.listPagination(query, new DefaultIpageUrl());
        model.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        model.addObject("finance", list);
        model.addObject("pagination", list.getQuery());
        model.setViewName("account/finance/financelist");
        return model;
    }

    @RequestMapping(value = "/reminder.htm")
    public String reminder() {
        return "account/finance/reminder";
    }

    @RequestMapping(value = "/printReminder.htm")
    @ExportWordFile(value = "Finance")
    public ModelAndView printReminder(ModelAndView model, TravelFinanceQuery query) {
        // 查询当前登录用户
        TravelMemberDO loginmember = memberService.getById(WebUserTools.getMid());
        // 查询地接社公司
        TravelCompanyDO accountcom = companyService.getById(WebUserTools.getCid());
        // 查询欠款
        query.setfType(1);
        query.setAccountCid(WebUserTools.getCid());
        List<TravelFinanceViewDO> viewlist = financeService.listQuery(query);

        // 组团社名[0]、收件人[1]、电话[2]、传真[3]、日期[4]、终止日期[5]、地接社公司账号[6]
        String[] tour = null;
        if (query.getmId() == null) {
            // 查询组团社
            TravelCompanyDO tourcom = companyService.getById(query.getTourCid());
            tour = new String[] { tourcom.getcName(), tourcom.getcCustomname(), tourcom.getcTel(), tourcom.getcFax(),
                    query.getOrderTime(), query.getOrderEndTime(), accountcom.getcBank() };
        } else {
            // 查询计调信息
            TravelMemberDO member = memberService.getById(query.getmId());
            tour = new String[] { query.getTourName(), member.getmName(), member.getmTel(), member.getmFax(),
                    query.getOrderTime(), query.getOrderEndTime(), accountcom.getcBank() };
        }

        model.addObject("loginmember", loginmember);// 当前登录用户
        model.addObject("financelist", viewlist);// 财务明细
        model.addObject("tourMessage", tour);// 组团社用户及地接社公司账号
        model.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        model.setViewName("word/exportFinance");
        return model;
    }
}
