/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.login;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.ADLocationEnum;
import com.zb.app.biz.cons.ColumnCatEnum;
import com.zb.app.biz.cons.CompanyStateEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.LineStateEnum;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.cons.MemberTypeEnum;
import com.zb.app.biz.cons.PhotoTypeEnum;
import com.zb.app.biz.cons.TravelNewsTypeEnum;
import com.zb.app.biz.domain.TravelAdvertisementDO;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.domain.TravelNewsDO;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.query.TravelAdvertisementQuery;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.biz.query.TravelNewsQuery;
import com.zb.app.biz.service.impl.FileServiceImpl.IFileHandle;
import com.zb.app.common.authority.TokenPolicy;
import com.zb.app.common.component.annotation.FormBean;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.security.EncryptBuilder;
import com.zb.app.common.util.PinyinParser;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.SiteCacheTools;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.ChufaFullVO;
import com.zb.app.web.vo.ColumnThinVO;
import com.zb.app.web.vo.TravelCompanyVO;
import com.zb.app.web.vo.TravelLineVO;
import com.zb.app.web.vo.TravelMemberVO;

/**
 * 登录,注册,授权,权限 控制器
 * 
 * @author zxc Jun 16, 2014 2:33:47 PM
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping(value = "/register2.htm")
    public String register2() {
        return "cms/1409/register";
    }

    @RequestMapping(value = "/register_success.htm")
    public String register_success() {
        return "/login/register_success";
    }

    /**
     * 左边网首页
     * 
     * @return
     */
    @RequestMapping(value = "/index.htm")
    public ModelAndView index() {
        ModelAndView mav = null;
        try {
            mav = initIndexDataModel();
        } catch (Exception e) {
            mav = new ModelAndView("/cms/1409/indextimeout");
        }
        return mav;
    }

    private ModelAndView initIndexDataModel() {
        ModelAndView mav = new ModelAndView("/cms/1409/index");

        // 新闻
        TravelNewsQuery query = new TravelNewsQuery();
        query.setNowPageIndex(0);
        query.setPageSize(10);
        query.setnType(TravelNewsTypeEnum.WEB_NEWS.value);
        PaginationList<TravelNewsDO> list = cmsService.showNewsPagination(query, new DefaultIpageUrl());
        mav.addObject("newsList", list);

        // 出发
        SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
        ChufaFullVO chugang = siteCacheTools.getChugangByChugangId(WebUserTools.getChugangId());
        mav.addObject("chugang", chugang);

        // 线路
        Map<Integer, List<TravelLineVO>> map = new HashMap<Integer, List<TravelLineVO>>();
        map.put(ColumnCatEnum.LONG_LINE.getValue(), getLineByZids(ColumnCatEnum.LONG_LINE, chugang));
        map.put(ColumnCatEnum.SHORT_LINE.getValue(), getLineByZids(ColumnCatEnum.SHORT_LINE, chugang));
        map.put(ColumnCatEnum.INTERNATIONAL_LINE.getValue(), getLineByZids(ColumnCatEnum.INTERNATIONAL_LINE, chugang));
        mav.addObject("lineMap", map);

        // 线路总数
        TravelLineQuery queryline = new TravelLineQuery();
        queryline.setlState(LineStateEnum.NORMAL.getValue());
        queryline.setlTemplateState(LineTemplateEnum.Line.getValue());
        mav.addObject("linecount", lineService.countByGroup(queryline)+20000);
        // 昨日总数
        queryline.setGmtCreate(new Date());
        mav.addObject("todaycount", lineService.countByGroup(queryline)+500);
        // 查询出发城市总数
        queryline = new TravelLineQuery();
        queryline.setlTemplateState(LineTemplateEnum.Line.getValue());
        queryline.setGroupType(1);
        mav.addObject("CityCount", lineService.countByGroup(queryline));

        // 商家推荐
        TravelCompanyQuery companyQuery = new TravelCompanyQuery();
        companyQuery.setNowPageIndex(0);
        companyQuery.setPageSize(6);
        companyQuery.setcType(CompanyTypeEnum.ACCOUNT.getValue());
        companyQuery.setcState(CompanyStateEnum.NORMAL.getValue());
        PaginationList<TravelCompanyDO> companyDOs = companyService.showCompanyPagination(companyQuery,
                                                                                          new DefaultIpageUrl());
        mav.addObject("companyDOs", companyDOs);

        // 总的批发商
        companyQuery.setcType(CompanyTypeEnum.ACCOUNT.getValue());
        mav.addObject("accountCount", companyService.countByAccount(companyQuery));

        // 广告
        List<TravelAdvertisementDO> adList = cmsService.list(new TravelAdvertisementQuery(
                                                                                          ADLocationEnum.INDEX_BANNERS.getValue(),
                                                                                          WebUserTools.getChugangId()));
        List<TravelAdvertisementDO> adFooterList = cmsService.list(new TravelAdvertisementQuery(
                                                                                                ADLocationEnum.INDEX_FOOTER.getValue(),
                                                                                                WebUserTools.getChugangId()));
        mav.addObject("adList", adList);
        mav.addObject("adFooterList", adFooterList);

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        return mav;
    }

    /***
     * 根据专线类别获取线路
     * 
     * @return
     */
    protected List<TravelLineVO> getLineByZids(ColumnCatEnum cat, ChufaFullVO chugang) {
        if (chugang == null || chugang.getColumnMap() == null) {
            return Collections.<TravelLineVO> emptyList();
        }
        // 获取专线Zids
        List<ColumnThinVO> columnThinList = chugang.getColumnMap().get(cat.getValue());
        Long[] zIds = CollectionUtils.getLongValueArrays(columnThinList, "zId");
        if (zIds.length == 0) {
            return null;
        }
        TravelLineQuery query = new TravelLineQuery();
        query.setzIds(zIds);
        query.setPageSize(8);
        query.setlState(LineStateEnum.NORMAL.getValue());
        query.setlTemplateState(LineTemplateEnum.Line.getValue());
        PaginationList<TravelLineDO> list = lineService.listGroup(query, new DefaultIpageUrl());
        return BeanUtils.convert(TravelLineVO.class, list);
    }

    /***
     * 登录页面
     * 
     * @return
     */
    @RequestMapping(value = "/userlogin.htm")
    public ModelAndView userlogin(ModelAndView model, String type) {
        if (WebUserTools.hasLogin()) {
            return new ModelAndView("redirect:" + WebUserTools.getCompanyType().getIndexUrl());
        }
        model.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        model.addObject("type", CompanyTypeEnum.getEnum(type == null ? "tour" : type).getValue());
        model.setViewName("/login/userlogin");
        return model;
    }

    @RequestMapping(value = "/loginbox.htm")
    public String loginbox() {
        return "/login/loginbox";
    }
    
    @RequestMapping(value = "/about2.htm")
    public String about2() {
        return "/cms/1409/about2";
    }

    @RequestMapping(value = "/line2.htm")
    public String line2(Model model) {
        model.addAttribute("type", "account");
        return "/cms/1409/line";
    }

    @RequestMapping(value = "/news2.htm")
    public String news2(Model model) {
        return "/cms/1409/news";
    }

    /**
     * 左边网首页
     * 
     * @return
     */
    @RequestMapping(value = "/index2.htm")
    public ModelAndView index2() {
        ModelAndView mav = new ModelAndView("index");
        if (WebUserTools.hasLogin() && CompanyTypeEnum.isTour(WebUserTools.current().getType())) {
            TravelMemberDO member = memberService.getById(WebUserTools.getMid());
            TravelIntegralDO integral = integralService.queryBala(new TravelIntegralQuery(WebUserTools.getCid(),
                                                                                          WebUserTools.getMid()));
            mav.addObject("mPic", member == null ? StringUtils.EMPTY : member.getmPic());
            mav.addObject("iBalance",
                          integral == null ? 0 : integral.getiBalance() == null ? 0 : integral.getiBalance());
            mav.addObject("reserveOrderCount", 0);
            mav.addObject("modifyOrderCount", 0);
            mav.addObject("hasLogin", true);
        }
        TravelNewsQuery query = new TravelNewsQuery();
        query.setNowPageIndex(0);
        query.setPageSize(10);
        PaginationList<TravelNewsDO> list = cmsService.showNewsPagination(query, new DefaultIpageUrl());

        SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
        ChufaFullVO chugang = siteCacheTools.getSiteAndChugang(WebUserTools.getSiteId(), WebUserTools.getChugangId());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("newsList", list);
        mav.addObject("chugang", chugang);
        return mav;
    }

    @RequestMapping(value = "/allCompany.htm", produces = "application/json")
    @ResponseBody
    public JsonResult allCompany(String type, String q) {
        if (StringUtils.isEmpty(type)) {
            return JsonResultUtils.error("参数不正确!");
        }
        CompanyTypeEnum typeEnum = CompanyTypeEnum.getEnum(type);
        List<TravelCompanyDO> list = companyService.showCompanyPagination(new TravelCompanyQuery(
                                                                                                 typeEnum,
                                                                                                 StringUtils.isEmpty(q) ? null : q));
        if (list == null || list.size() == 0) {
            return JsonResultUtils.error("没有查询到数据!");
        }
        List<Map<String, ?>> mapList = CollectionUtils.toMapList(list, "cId", "cName");
        return JsonResultUtils.success(mapList);
    }

    @RequestMapping(value = "/allMember.htm", produces = "application/json")
    @ResponseBody
    public JsonResult allMember(Long cId) {
        if (cId == null) {
            return JsonResultUtils.error("参数不正确!");
        }
        TravelMemberQuery query = new TravelMemberQuery(cId);
        query.setPageSize(500);
        List<TravelMemberDO> list = memberService.list(query);
        if (list == null || list.size() == 0) {
            return JsonResultUtils.error("没有查询到数据!");
        }
        List<Map<String, ?>> mapList = CollectionUtils.toMapList(list, "mId", "mUserName", "mName", "mMobile", "mTel",
                                                                 "mFax");
        return JsonResultUtils.success(mapList);
    }

    @RequestMapping(value = "/nopermission.htm")
    public String nopermission() {
        return "nopermission";
    }

    /**
     * ajax未登录提示
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/nopermissionAjax.htm")
    public JsonResult nopermissionAjax(Map<String, Object> model) {
        return JsonResultUtils.needLogin(null, "您尚未登录,请重新登录!");
    }

    /**
     * home跳转页面
     * 
     * @return
     */
    @RequestMapping(value = "/home.htm")
    public ModelAndView home(String returnurl) {
        ModelAndView mav = new ModelAndView("home");
        if (StringUtils.isNotEmpty(returnurl)) {
            mav.addObject("returnurl", returnurl);
        }
        return mav;
    }

    /**
     * 组团社,批发商,用户登录
     * 
     * @param account
     * @param password
     * @param remeber 记住密码
     * @return
     */
    @RequestMapping(value = "/doLogin.htm", produces = "application/json")
    @ResponseBody
    public JsonResult doLogin(String account, String password, String remeber, Integer utype) {
        return doLonginWithCheck(account, password, CompanyTypeEnum.getEnum(utype));
    }

    /**
     * 组团社,批发商,退出登录
     * 
     * @return
     */
    @RequestMapping(value = "/loginOut.htm")
    public String loginOut() {
        doLoginOut();
        return "forward:index.htm";
    }

    /**
     * 组团社,批发商,供应商,地接社,注册页面
     * 
     * @return
     */
    @TokenPolicy(save = true)
    @RequestMapping(value = "/register.htm")
    public String register() {
        return "login/register";
    }

    /**
     * 注册用户,保存公司信息,用户信息
     * 
     * @param travelCompanyVO
     * @param travelMemberVO
     * @return
     */
    @RequestMapping(value = "/doRegister.htm")
    @TokenPolicy(remove = true, data = "注册失败,token失效,请刷新页面重新注册!")
    public ModelAndView doRegister(@Valid
    TravelCompanyVO travelCompanyVO, @Valid
    TravelMemberVO travelMemberVO, BindingResult bindingResult, ModelAndView mav) {
        Map<String, Object> model = new HashMap<String, Object>();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                model.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return createErrorJsonMav("注册失败!", model);
        }

        // 添加公司
        TravelCompanyDO travelCompanyDO = new TravelCompanyDO();
        BeanUtils.copyProperties(travelCompanyDO, travelCompanyVO);
        travelCompanyDO.setcState(0);
        travelCompanyDO.setcSpell(PinyinParser.converterToFirstSpell(travelCompanyDO.getcName()));
        companyService.insert(travelCompanyDO);

        // 添加用户
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        BeanUtils.copyProperties(travelMemberDO, travelMemberVO);
        travelMemberDO.setmState(0);
        travelMemberDO.setmPassword(EncryptBuilder.getInstance().encrypt(travelMemberVO.getmPassword()));
        travelMemberDO.setcId(travelCompanyDO.getcId());
        travelMemberDO.setmUserName(StringUtils.lowerCase(travelMemberVO.getmUserName()));
        travelMemberDO.setmType(MemberTypeEnum.SUPERADMIN.getValue());
        memberService.insert(travelMemberDO);
        Map<String, String> map = new HashMap<String, String>();
        map.put("cName", travelCompanyDO.getcName());
        map.put("mUserName", travelMemberDO.getmUserName());
        map.put("mEmail", travelMemberDO.getmEmail());
        return createSuccessJsonMav("注册成功!", map);
    }

    /**
     * 公司名称验证
     * 
     * @param cName
     * @return
     */
    @RequestMapping(value = "/nameVerify.htm", produces = "application/json")
    @ResponseBody
    public JsonResult doCompanyRegister(String cName, Integer type) {
        TravelCompanyQuery query = new TravelCompanyQuery();
        query.setcName(cName);
        query.setcType(type);
        TravelCompanyDO travelCompanyDO = companyService.getByName(query);
        if (travelCompanyDO == null) {
            return JsonResultUtils.success(travelCompanyDO, "验证成功!");
        } else {
            return JsonResultUtils.error(travelCompanyDO, "数据库已存在!");
        }
    }

    /**
     * 公司信息更新
     * 
     * @param travelCompanyVO
     * @return
     */
    @RequestMapping(value = "/updateCompany.htm", produces = "application/json")
    @ResponseBody
    public JsonResult updateCompany(TravelCompanyVO travelCompanyVO) {
        final TravelCompanyDO travelCompanyDO = new TravelCompanyDO();
        BeanUtils.copyProperties(travelCompanyDO, travelCompanyVO);
        Result rusult = fileService.saveFileByPath(travelCompanyDO.getcLogo(), new IFileHandle() {

            @Override
            public String parse(String prefix, String suffix) {
                return prefix + WebUserTools.getCid() + "/" + PhotoTypeEnum.COMPANY.getName() + "/" + suffix;
            }
        });
        travelCompanyDO.setcLogo((String) rusult.getData());
        Boolean bool = companyService.update(travelCompanyDO);
        if (bool) {
            return JsonResultUtils.success(travelCompanyDO, "更新成功!");
        } else {
            return JsonResultUtils.error(travelCompanyDO, "更新失败!");
        }
    }

    /**
     * 用户名称验证
     * 
     * @param mUserName
     * @return
     */
    @RequestMapping(value = "/userNameVerify.htm", produces = "application/json")
    @ResponseBody
    public JsonResult doMemberRegister(String mUserName, Integer type) {
        if (StringUtils.isEmpty(mUserName) || Argument.isNotPositive(type)) {
            return JsonResultUtils.error("参数不正确!");
        }
        TravelMemberQuery query = new TravelMemberQuery();
        query.setmUserName(StringUtils.lowerCase(mUserName));
        query.setcType(type);
        TravelMemberDO travelMemberDO = memberService.getByName(query);
        if (travelMemberDO == null) {
            return JsonResultUtils.success(travelMemberDO, "验证成功!");
        } else {
            return JsonResultUtils.error(travelMemberDO, "数据库已存在!");
        }
    }

    /**
     * 更新用户信息
     * 
     * @param travelMemberVO
     * @return
     */
    @RequestMapping(value = "/updateMember.htm", produces = "application/json")
    @ResponseBody
    public JsonResult updateMember(TravelMemberVO travelMemberVO) {
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        BeanUtils.copyProperties(travelMemberDO, travelMemberVO);
        Result rusult = fileService.saveFileByPath(travelMemberDO.getmPic(), new IFileHandle() {

            @Override
            public String parse(String prefix, String suffix) {
                return prefix + WebUserTools.getCid() + "/" + PhotoTypeEnum.MEMBER.getName() + "/" + suffix;
            }
        });
        travelMemberDO.setmPic((String) rusult.getData());
        Boolean bool = memberService.update(travelMemberDO);
        if (bool) {
            return JsonResultUtils.success(travelMemberDO, "更新成功!");
        } else {
            return JsonResultUtils.error(travelMemberDO, "更新失败!");
        }
    }

    /**
     * 测试页面
     * 
     * @return
     */
    @RequestMapping(value = "/test.htm")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("test");

        modelAndView.addObject("mytest", new Date());
        modelAndView.addObject("mytest2", "<a href=\"/register.htm\" class=\"reg\">免费注册</a>");

        return modelAndView;
    }

    @RequestMapping(value = "/saveTest.htm")
    public ModelAndView saveTest(@FormBean(value = "guest")
    TravelOrderGuestDO[] guest) {
        ModelAndView modelAndView = new ModelAndView("test");

        modelAndView.addObject("mytest", new Date());
        modelAndView.addObject("mytest2", "<a href=\"/register.htm\" class=\"reg\">免费注册</a>");

        return modelAndView;
    }
}
