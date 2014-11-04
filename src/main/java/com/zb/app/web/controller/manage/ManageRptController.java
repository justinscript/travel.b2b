/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.web.controller.BaseController;

/**
 * 统计分析 报表 控制层
 * 
 * @author zxc Jun 16, 2014 3:46:15 PM
 */
@Controller
@RequestMapping("/zbmanlogin")
public class ManageRptController extends BaseController {

    /**
     * 批发商收客统计
     * 
     * @return
     */
    @RequestMapping(value = "/count/recruiting.htm")
    public ModelAndView recruiting(ModelAndView mav) {
        mav.setViewName("/manage/count/recruiting");
        return mav;
    }
    public ModelAndView accountCount(){
        return null;
    }
    /**
     * 用户登录统计
     * 
     * @return
     */
    @RequestMapping(value = "/count/login.htm")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("/manage/count/logincount");
        return mav;
    }

    /**
     * 用户注册统计
     * 
     * @return
     */
    @RequestMapping(value = "/count/reg.htm")
    public ModelAndView reg(ModelAndView mav, TravelCompanyQuery query) {
        mav.setViewName("/manage/count/regcount");

        PaginationList<TravelCompanyDO> list = companyService.showCompanyPagination(query);
        mav.addObject("list", list);
        return mav;
    }
}
