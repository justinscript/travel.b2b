/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.biz.cons.TravelNewsTypeEnum;
import com.zb.app.biz.domain.TravelBlackListDO;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.domain.TravelNewsDO;
import com.zb.app.biz.domain.TravelOperationLogFullDO;
import com.zb.app.biz.domain.TravelServiceDO;
import com.zb.app.biz.domain.TravelServiceSiteDO;
import com.zb.app.biz.query.TravelBlackListQuery;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.biz.query.TravelNewsQuery;
import com.zb.app.biz.query.TravelOperationLogQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.biz.query.TravelServiceQuery;
import com.zb.app.common.authority.AuthorityHelper;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.security.EncryptBuilder;
import com.zb.app.common.util.ObjectUtils;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelCompanyVO;
import com.zb.app.web.vo.TravelMemberVO;
import com.zb.app.web.vo.TravelServiceVO;

/**
 * Account 客户管理,公司信息,用户信息管理 控制层
 * 
 * @author zxc Jun 17, 2014 6:05:58 PM
 */
@Controller
@RequestMapping("/account")
public class AccountCustomerController extends BaseController {

    // /////
    //
    // ####################################################Account公司个人管理模块###################################################
    //
    // /////

    /**
     * 个人信息
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value = "/myinfo.htm")
    public ModelAndView myinfo(ModelAndView mav) {
        Long mId = WebUserTools.getMid();
        TravelMemberDO travelMemberDO = memberService.getById(mId);
        TravelMemberVO travelMemberVO = new TravelMemberVO();
        BeanUtils.copyProperties(travelMemberVO, travelMemberDO);

        mav.addObject("memberVO", travelMemberVO);
        mav.setViewName("account/customer/myinfo");
        return mav;
    }

    @RequestMapping(value = "/password.htm")
    public String password() {
        return "account/customer/password";
    }

    @RequestMapping(value = "/userlist.htm")
    public ModelAndView user(ModelAndView mav, TravelMemberQuery query, Integer page) {
        query.setPageSize(3);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcId(WebUserTools.getCid());

        PaginationList<TravelMemberDO> list = memberService.showMemberPagination(query, new DefaultIpageUrl());
        for (TravelMemberDO member : list) {
            if (StringUtils.isNotEmpty(member.getmPassword())) {
                member.setmPassword(EncryptBuilder.getInstance().decrypt(member.getmPassword()));
            }
        }

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("memberList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("account/customer/userlist");
        return mav;
    }

    @RequestMapping(value = "/useradd.htm")
    public String useradd() {
        return "account/customer/useradd";
    }

    @RequestMapping(value = "/user.htm")
    public String user() {
        return "account/customer/user";
    }

    @RequestMapping(value = "/addUser.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addUser(TravelMemberVO travelMemberVO) {
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        BeanUtils.copyProperties(travelMemberDO, travelMemberVO);
        travelMemberDO.setmPassword(EncryptBuilder.getInstance().encrypt(travelMemberVO.getmPassword()));
        travelMemberDO.setmUserName(StringUtils.lowerCase(travelMemberVO.getmUserName()));
        travelMemberDO.setcId(WebUserTools.getCid());
        if (StringUtils.isNotEmpty(travelMemberVO.getmRole())) {
            String role = travelMemberVO.getmRole();
            role = AuthorityHelper.makeAuthority(role);
            travelMemberDO.setmRole(role);
        }

        Integer i = memberService.insert(travelMemberDO);
        return i == 0 ? JsonResultUtils.error(travelMemberDO, "注册失败!") : JsonResultUtils.success(travelMemberDO,
                                                                                                 "注册成功!");
    }

    /**
     * 编辑用户
     * 
     * @return
     */
    @RequestMapping(value = "/ljUpdateUser.htm")
    public ModelAndView ljUpdateUser(ModelAndView mav, Long id) {
        TravelMemberDO memberDO = memberService.getById(id);
        if (StringUtils.isNotEmpty(memberDO.getmRole())) {
            String role = AuthorityHelper.createRightStr(memberDO.getmRole());
            memberDO.setmRole(role);
        }
        if (StringUtils.isNotEmpty(memberDO.getmPassword())) {
            memberDO.setmPassword(EncryptBuilder.getInstance().decrypt(memberDO.getmPassword()));
        }
        mav.addObject("member", memberDO);
        mav.addObject("type", "update");
        mav.setViewName("/account/customer/useradd");
        return mav;
    }

    @RequestMapping(value = "/updateUser.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUser(TravelMemberDO memberDO) {
        if (StringUtils.isNotEmpty(memberDO.getmPassword())) {
            memberDO.setmPassword(EncryptBuilder.getInstance().encrypt(memberDO.getmPassword()));
        }
        if (StringUtils.isNotEmpty(memberDO.getmRole()) && StringUtils.contains(memberDO.getmRole(), ",")) {
            String role = memberDO.getmRole();
            role = AuthorityHelper.makeAuthority(role);
            memberDO.setmRole(role);
        }
        boolean b = memberService.update(memberDO);
        if (b) {
            return JsonResultUtils.success(memberDO, "修改成功!");
        } else {
            return JsonResultUtils.error(memberDO, "修改失败!");
        }
    }

    /**
     * 删除用户
     * 
     * @return
     */
    @RequestMapping(value = "/deleteUser.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteUser(Long id) {
        boolean b = memberService.delete(id);
        if (b) {
            return JsonResultUtils.success(id, "删除成功!");
        } else {
            return JsonResultUtils.error(id, "删除失败!");
        }
    }

    @RequestMapping(value = "/company.htm")
    public ModelAndView company(ModelAndView mav) {
        Long cId = WebUserTools.getCid();
        TravelCompanyDO travelCompanyDO = companyService.getById(cId);
        TravelCompanyVO travelCompanyVO = new TravelCompanyVO();
        BeanUtils.copyProperties(travelCompanyVO, travelCompanyDO);

        mav.addObject("company", travelCompanyVO);
        mav.setViewName("account/customer/company");
        return mav;
    }

    // /////
    //
    // ####################################################Account公司新闻模块###################################################
    //
    // /////

    @RequestMapping(value = "/newslist.htm")
    public ModelAndView news(ModelAndView mav, TravelNewsQuery query, Integer page) {
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setPageSize(20);
        query.setcId(WebUserTools.getCid());
        query.setnType(TravelNewsTypeEnum.TRAVEL_NEWS.value);
        PaginationList<TravelNewsDO> list = cmsService.showNewsPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("newsList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("account/customer/newslist");
        return mav;
    }

    @RequestMapping(value = "/news.htm")
    public String news() {
        return "account/customer/news";
    }

    @RequestMapping(value = "/newadd.htm")
    public String newsadd() {
        return "account/customer/newadd";
    }

    /**
     * 新建新闻
     * 
     * @return
     */
    @RequestMapping(value = "/addNews.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addNews(TravelNewsDO newsDO) {
        newsDO.setcId(WebUserTools.getCid());
        newsDO.setnType(TravelNewsTypeEnum.TRAVEL_NEWS.value);
        cmsService.addTravelNews(newsDO);
        return JsonResultUtils.success(newsDO, "创建成功!");
    }

    /**
     * 编辑新闻
     * 
     * @return
     */
    @RequestMapping(value = "/ljUpdate.htm")
    public ModelAndView ljUpdate(ModelAndView mav, Long id) {
        TravelNewsDO newsDO = cmsService.getById(id);
        mav.addObject("news", newsDO);
        mav.addObject("type", "update");
        mav.setViewName("/account/customer/newadd");
        return mav;
    }

    /**
     * 更新新闻
     * 
     * @param newsDO
     * @return
     */
    @RequestMapping(value = "/updateNews.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateNews(TravelNewsDO newsDO) {
        boolean b = cmsService.updateById(newsDO);
        if (b) {
            return JsonResultUtils.success(newsDO, "修改成功!");
        } else {
            return JsonResultUtils.error(newsDO, "修改失败!");
        }
    }

    /**
     * 删除新闻
     * 
     * @return
     */
    @RequestMapping(value = "/deleteNews.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteNews(Long id) {
        boolean b = cmsService.deleteById(id);
        if (b) {
            return JsonResultUtils.success(id, "删除成功!");
        } else {
            return JsonResultUtils.error(id, "删除失败!");
        }
    }

    // /////
    //
    // ####################################################Account公共客户,黑名单模块###################################################
    //
    // /////

    /**
     * 公共客户
     * 
     * @param mav
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "/customerlist.htm")
    public ModelAndView customer(ModelAndView mav, TravelCompanyQuery query, Integer page) {
        mav.setViewName("account/customer/customerlist");
        if (query == null) {
            query = new TravelCompanyQuery();
        }
        TravelCompanyDO myCompany = companyService.getById(WebUserTools.getCid());

        List<Long> cIdList = orderService.getTourCompany(new TravelOrderQuery(myCompany.getcId(),
                                                                              OrderStateEnum.CONFIRM));
        if (cIdList == null || cIdList.size() == 0) {
            return mav;
        }
        query.setcIds(cIdList.toArray(new Long[cIdList.size()]));
        if (StringUtils.isEmpty(query.getcProvince())) {
            // query.setcProvince(myCompany.getcProvince());
        }
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcType(CompanyTypeEnum.TOUR.getValue());
        query.setPageSize(10);

        Map<Long, Integer> map = new HashMap<Long, Integer>();
        PaginationList<TravelCompanyDO> list = companyService.showCompanyPagination(query, new DefaultIpageUrl());
        for (TravelCompanyDO travelCompanyDO : list) {
            Integer count = companyService.getBlackCount(new TravelBlackListQuery(travelCompanyDO.getcId()));
            map.put(travelCompanyDO.getcId(), count);
        }
        mav.addObject("list", list);
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("map", map);

        return mav;
    }

    @RequestMapping(value = "/customer.htm")
    public String customer() {
        return "/account/customer/customer";
    }

    /**
     * 查看公共客户
     * 
     * @param mav
     * @param cid
     * @return
     */
    @RequestMapping(value = "/customeruser.htm")
    public ModelAndView customeruser(ModelAndView mav, Long cid) {
        mav.setViewName("account/customer/customeruser");
        if (Argument.isNotPositive(cid)) {
            return mav;
        }
        TravelMemberQuery query = new TravelMemberQuery(cid);
        query.setPageSize(2000);
        List<TravelMemberDO> memberList = memberService.list(query);
        mav.addObject("memberList", memberList);
        return mav;
    }

    /**
     * 黑名单反馈
     * 
     * @return
     */
    @RequestMapping(value = "/customeradd.htm")
    public ModelAndView customeradd(ModelAndView mav, Long id) {
        TravelCompanyDO companyDO = companyService.getById(WebUserTools.getCid());
        mav.addObject("company", companyDO);
        mav.addObject("beCId", id);
        mav.setViewName("account/customer/customeradd");
        TravelBlackListQuery query = new TravelBlackListQuery();
        query.setcId(WebUserTools.getCid());
        query.setmId(WebUserTools.getMid());
        query.setBeCId(id);
        TravelBlackListDO travelBlackListDO = companyService.find(query);
        mav.addObject("blackListDO", travelBlackListDO);
        return mav;
    }

    /**
     * 新建黑名单
     * 
     * @return
     */
    @RequestMapping(value = "/addBlack.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addBlack(TravelBlackListDO blackListDO) {
        TravelBlackListQuery query = new TravelBlackListQuery();
        query.setcId(WebUserTools.getCid());
        query.setmId(WebUserTools.getMid());
        query.setBeCId(blackListDO.getBeCId());
        TravelBlackListDO travelBlackListDO = companyService.find(query);
        if (travelBlackListDO != null) return JsonResultUtils.error(blackListDO, "当前用户只能拉黑该公司一次");
        blackListDO.setcId(WebUserTools.getCid());
        blackListDO.setmId(WebUserTools.getMid());
        Integer i = companyService.addTravelBlackList(blackListDO);
        return i == 0 ? JsonResultUtils.error(blackListDO, "拉黑失败!") : JsonResultUtils.success(blackListDO, "拉黑成功!");
    }

    // /////
    //
    // ####################################################Account公司客服模块###################################################
    //
    // /////

    /**
     * 查看客服
     * 
     * @return
     */
    @RequestMapping(value = "/qqlist.htm")
    public ModelAndView qq(ModelAndView mav) {
        mav.setViewName("account/customer/qqlist");

        List<TravelServiceDO> list = companyService.list(new TravelServiceQuery(WebUserTools.getCid()));
        mav.addObject("list", list);
        return mav;
    }

    @RequestMapping(value = "/qq.htm")
    public String qq() {
        return "account/customer/qq";
    }

    /**
     * 增加客服
     * 
     * @return
     */
    @RequestMapping(value = "/qqadd.htm")
    public ModelAndView qqadd(ModelAndView mav) {
        mav.setViewName("account/customer/qqadd");
        return mav;
    }

    @RequestMapping(value = "/qqedit.htm")
    public ModelAndView qqedit(ModelAndView mav, Long id) {
        mav.setViewName("account/customer/qqadd");
        if (Argument.isNotPositive(id)) {
            return mav;
        }
        TravelServiceDO serviceDO = companyService.getServiceById(id);
        if (serviceDO == null) {
            return mav;
        }
        List<TravelServiceSiteDO> serviceSiteDOs = companyService.getServiceSiteBySId(id);
        TravelServiceVO service = new TravelServiceVO();
        BeanUtils.copyProperties(service, serviceDO);

        List<Long> zIdList = CollectionUtils.getLongValues(serviceSiteDOs, "zId");
        service.setzId(zIdList);
        mav.addObject("service", service);

        return mav;
    }

    @RequestMapping(value = "/qqSave.htm", produces = "application/json")
    @ResponseBody
    public JsonResult qqSave(@Valid
    TravelServiceVO service, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        ObjectUtils.trim(service);
        TravelServiceDO serviceDO = new TravelServiceDO();
        BeanUtils.copyProperties(serviceDO, service);
        serviceDO.setcId(WebUserTools.getCid());
        if (serviceDO.getsIsReceive() == null) {
            serviceDO.setsIsReceive(0);
        }
        if (service.getsId() != null && service.getsId() > 0) {
            companyService.updateById(serviceDO);
            companyService.realDelServiceSite(serviceDO.getsId());
            for (Long l : service.getzId()) {
                TravelServiceSiteDO serviceSiteDO = new TravelServiceSiteDO();
                serviceSiteDO.setsId(serviceDO.getsId().intValue());
                serviceSiteDO.setzId(l.intValue());
                companyService.addServiceSite(serviceSiteDO);
            }
            return JsonResultUtils.success("修改成功!");
        }
        List<TravelServiceDO> list = companyService.list(new TravelServiceQuery(WebUserTools.getCid(),
                                                                                service.getsName()));
        if (list != null && list.size() > 0) {
            return JsonResultUtils.error("客服已经存在!");
        }
        companyService.addService(serviceDO);
        for (Long l : service.getzId()) {
            TravelServiceSiteDO serviceSiteDO = new TravelServiceSiteDO();
            serviceSiteDO.setsId(serviceDO.getsId().intValue());
            serviceSiteDO.setzId(l.intValue());
            companyService.addServiceSite(serviceSiteDO);
        }

        return JsonResultUtils.success("增加客服成功");
    }

    @RequestMapping(value = "/delqq.htm", produces = "application/json")
    @ResponseBody
    public JsonResult delqq(Long id) {
        if (id == null || id == 0) {
            return JsonResultUtils.error("id不能为空!");
        }
        companyService.realDelServiceSite(id);
        boolean isDel = companyService.realDelService(id);
        return isDel ? JsonResultUtils.success("删除成功!") : JsonResultUtils.error("删除失败!");
    }

    // 操作日志
    @RequestMapping(value = "/log.htm")
    public String log() {

        return "/account/log/index";
    }

    @RequestMapping(value = "/loglist.htm")
    public ModelAndView loglist(ModelAndView mav, TravelOperationLogQuery query, Integer page) {
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setPageSize(20);
        query.setcId(WebUserTools.getCid());
        PaginationList<TravelOperationLogFullDO> list = operationLogService.listPagination(query, new DefaultIpageUrl());
        List<TravelOperationLogFullDO> lists = BeanUtils.convert(TravelOperationLogFullDO.class, list);

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("logList", lists);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/account/log/indexlist");
        return mav;
    }
}
