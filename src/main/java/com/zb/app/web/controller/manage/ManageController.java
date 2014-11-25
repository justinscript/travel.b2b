/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.CompanyStateEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.PhotoTypeEnum;
import com.zb.app.biz.domain.TravelBlackListFullDO;
import com.zb.app.biz.domain.TravelBlackListThinDO;
import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelLineColumnDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.domain.TravelSiteDO;
import com.zb.app.biz.query.TravelBlackListQuery;
import com.zb.app.biz.query.TravelColumnQuery;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.biz.query.TravelSiteQuery;
import com.zb.app.biz.service.impl.FileServiceImpl.IFileHandle;
import com.zb.app.common.authority.AuthorityHelper;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
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
import com.zb.app.web.vo.ChufaVO;
import com.zb.app.web.vo.TravelCompanyVO;
import com.zb.app.web.vo.TravelMemberVO;

/**
 * Manage 公司及用户管理 控制层
 * 
 * @author zxc Jun 16, 2014 3:43:02 PM
 */
@Controller
@RequestMapping("/zbmanlogin")
public class ManageController extends BaseController {

    // /////
    //
    // ####################################################Manage后台登录管理###################################################
    //
    // /////

    @RequestMapping(value = "/login.htm")
    public String login(Model model) {
        if (WebUserTools.hasLogin() && CompanyTypeEnum.isManage(WebUserTools.current().getType())) {
            return "redirect:m/manage.htm";
        }
        model.addAttribute("type", "manage");
        return "login/login";
    }

    @RequestMapping(value = "/loginOut.htm")
    public String loginOut() {
        doLoginOut();
        return "forward:login.htm";
    }

    /**
     * 左边网管理员登录
     * 
     * @param account
     * @param password
     * @param remeber
     * @return
     */
    @RequestMapping(value = "/doLogin.htm", produces = "application/json")
    @ResponseBody
    public JsonResult doLogin(String account, String password, String remeber) {
        return doLonginWithCheck(account, password, CompanyTypeEnum.MANAGE);
    }

    // /////
    //
    // ####################################################Manage公司及用户管理###################################################
    //
    // /////

    @RequestMapping(value = "/company/index.htm")
    public ModelAndView pageCompany() {
        return new ModelAndView("manage/company/index");
    }

    /**
     * 公司管理:总管理
     * 
     * @return
     */
    @RequestMapping(value = "/m/{type}.htm")
    public ModelAndView companymanage(ModelAndView mav, @PathVariable("type")
    String type) {
        mav.addObject("url", "/zbmanlogin/company/companylist/" + type + ".htm");
        mav.addObject("type", CompanyTypeEnum.getEnum(type).getValue());
        mav.setViewName("/manage/company/index");
        return mav;
    }

    @RequestMapping(value = "/company/companylist/{type}.htm")
    public ModelAndView companyList(ModelAndView mav, TravelCompanyQuery query, Integer page, @PathVariable("type")
    String type) {
        query.setPageSize(20);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcType(CompanyTypeEnum.getEnum(type).getValue());

        PaginationList<TravelCompanyDO> list = companyService.showCompanyPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("companyList", list);
        mav.addObject("pagination", list.getQuery());
        mav.addObject("type", CompanyTypeEnum.getEnum(type).getValue());
        mav.setViewName("/manage/company/companylist");
        return mav;
    }

    @RequestMapping(value = "/company/add.htm")
    public ModelAndView companyadd(ModelAndView mav, Long type) {
        mav.addObject("type", type);
        mav.setViewName("/manage/company/add");
        return mav;
    }

    /**
     * 创建公司
     * 
     * @param travelCompanyVO
     * @param travelMemberVO
     * @return
     */
    @RequestMapping(value = "/company/addCompany.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addCompany(@Valid
    TravelCompanyVO travelCompanyVO, TravelMemberVO travelMemberVO) {

        // 添加公司
        TravelCompanyDO travelCompanyDO = new TravelCompanyDO();
        BeanUtils.copyProperties(travelCompanyDO, travelCompanyVO);
        companyService.insert(travelCompanyDO);

        // 添加用户
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        BeanUtils.copyProperties(travelMemberDO, travelMemberVO);
        travelMemberDO.setmPassword(EncryptBuilder.getInstance().encrypt(travelMemberVO.getmPassword()));
        travelMemberDO.setcId(travelCompanyDO.getcId());
        int i = memberService.insert(travelMemberDO);
        return i == 0 ? JsonResultUtils.error(travelMemberDO, "创建失败!") : JsonResultUtils.success(travelMemberDO,
                                                                                                 "创建成功!");
    }

    /**
     * 编辑公司
     * 
     * @return
     */
    @RequestMapping(value = "/company/ljUpdateCompany.htm")
    public ModelAndView ljUpdateCompany(ModelAndView mav, Long id) {
        TravelCompanyDO companyDO = companyService.getById(id);
        TravelCompanyVO companyVO = new TravelCompanyVO();
        BeanUtils.copyProperties(companyVO, companyDO);
        String[] tmp = StringUtils.split(companyVO.getcCityTop(), ",");
        if (tmp != null && tmp.length == 2) {
            companyVO.setcMoProvince(Integer.parseInt(tmp[0]));
            companyVO.setcMoCity(Integer.parseInt(tmp[1]));
        }
        mav.addObject("company", companyVO);
        mav.addObject("cType", "update");
        mav.setViewName("/manage/company/add");
        return mav;
    }

    @RequestMapping(value = "/company/updateCompany.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateCompany(TravelCompanyDO companyDO) {
        if (companyDO == null) {
            return JsonResultUtils.error("修改失败!");
        }
        boolean b = companyService.update(companyDO);
        if (b) {
            return JsonResultUtils.success(companyDO, "修改成功!");
        } else {
            return JsonResultUtils.error(companyDO, "修改失败!");
        }
    }

    /**
     * 删除公司
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/company/deleteCompany.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteCompany(Integer id) {
        boolean b = companyService.delete(id);
        if (b) {
            return JsonResultUtils.success(id, "删除成功!");
        } else {
            return JsonResultUtils.error(id, "删除失败!");
        }
    }

    @RequestMapping(value = "/company/user.htm")
    public ModelAndView companyuser(ModelAndView mav, TravelMemberQuery query, Integer page, Long id) {
        query.setPageSize(100);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcId(id);

        PaginationList<TravelMemberDO> list = memberService.showMemberPagination(query, new DefaultIpageUrl());
        for (TravelMemberDO member : list) {
            if (StringUtils.isNotEmpty(member.getmPassword())) {
                member.setmPassword(EncryptBuilder.getInstance().decrypt(member.getmPassword()));
            }
        }

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("memberList", list);
        mav.addObject("pagination", list.getQuery());
        mav.addObject("cId", id);
        mav.setViewName("/manage/company/user");
        return mav;
    }

    @RequestMapping(value = "/company/useradd.htm")
    public ModelAndView companyuseradd(ModelAndView mav, Long cId) {
        TravelCompanyDO travelCompanyDO = companyService.getById(cId);
        mav.addObject("cId", cId);
        mav.addObject("cType", travelCompanyDO.getcType());
        mav.setViewName("/manage/company/useradd");
        return mav;
    }

    /**
     * 添加用户
     * 
     * @param mav
     * @param travelMemberVO
     * @return
     */
    @RequestMapping(value = "/company/addUser.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addUser(ModelAndView mav, TravelMemberVO travelMemberVO, Long cId) {
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        BeanUtils.copyProperties(travelMemberDO, travelMemberVO);
        travelMemberDO.setmPassword(EncryptBuilder.getInstance().encrypt(travelMemberVO.getmPassword()));
        travelMemberDO.setmUserName(StringUtils.lowerCase(travelMemberVO.getmUserName()));

        if (StringUtils.isNotEmpty(travelMemberVO.getmRole())) {
            String role = AuthorityHelper.createRightStr(travelMemberVO.getmRole());
            travelMemberDO.setmRole(role);
        }

        Integer i = memberService.insert(travelMemberDO);
        return i == 0 ? JsonResultUtils.error(travelMemberDO, "添加失败!") : JsonResultUtils.success(travelMemberDO,
                                                                                                 "添加成功!");
    }

    /**
     * 编辑用户
     * 
     * @return
     */
    @RequestMapping(value = "/company/ljUpdateUser.htm")
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
        mav.setViewName("/manage/company/useradd");
        return mav;
    }

    @RequestMapping(value = "/company/updateUser.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUser(TravelMemberDO memberDO) {
        if (memberDO == null) {
            return JsonResultUtils.error("修改失败!");
        }
        if (StringUtils.isNotEmpty(memberDO.getmRole())) {
            String role = memberDO.getmRole();
            role = AuthorityHelper.makeAuthority(role);
            memberDO.setmRole(role);
        }
        if (StringUtils.isNotEmpty(memberDO.getmPassword())) {
            memberDO.setmPassword(EncryptBuilder.getInstance().encrypt(memberDO.getmPassword()));
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/company/deleteUser.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteUser(Long id) {
        boolean b = memberService.delete(id);
        if (b) {
            return JsonResultUtils.success(id, "删除成功!");
        } else {
            return JsonResultUtils.error(id, "删除失败!");
        }
    }

    // /////
    //
    // ####################################################Manage后台站点,出港地,专线管理###################################################
    //
    // /////

    @RequestMapping(value = "/company/site.htm")
    public ModelAndView companysite(ModelAndView mav, Long id) {
        mav.setViewName("/manage/company/site");
        if (id == null) {
            return mav;
        }
        List<TravelLineColumnDO> list = siteService.getTravelLineColumnByCid(id);
        StringBuffer sf = new StringBuffer();
        for (TravelLineColumnDO lineColumnDO : list) {
            sf.append(lineColumnDO.getzId());
            sf.append("|");
        }
        mav.addObject("id", id);
        mav.addObject("currentUsed", sf.toString());
        return mav;
    }

    /**
     * 公司绑定专线
     * 
     * <pre>
     *      t=1取消成功;t=2添加成功
     * </pre>
     * 
     * @param cId
     * @param zId
     * @param t
     * @return
     */
    @RequestMapping(value = "/company/bindColumn.htm", produces = "application/json")
    @ResponseBody
    public JsonResult bindColumn(Long cId, Long zId, Integer isAdd) {
        if (cId == null || zId == null || isAdd <= 0) {
            return JsonResultUtils.error("cId,zId,t不能为空!");
        }
        List<TravelLineColumnDO> list = siteService.list(zId, cId);
        if (list == null || list.size() == 0) {
            isAdd = isAdd == 2 ? isAdd : 2;
            if (isAdd == 2) {
                siteService.insertTravelLineColumn(new TravelLineColumnDO(zId, cId));
                return JsonResultUtils.success("绑定成功!");
            }
        } else if (list != null && list.size() == 1) {
            isAdd = isAdd == 1 ? isAdd : 1;
            if (isAdd == 1) {
                siteService.deleteTravelLineColumn(list.get(0).getLcId());
                return JsonResultUtils.success("取消成功!");
            }
        } else if (list != null && list.size() > 1) {
            return JsonResultUtils.error("cId,zId错误!");
        }
        return JsonResultUtils.success("操作成功!");
    }

    @RequestMapping(value = "/site/index.htm")
    public ModelAndView siteindex(ModelAndView mav) {
        PaginationList<TravelSiteDO> list = siteService.listPagination(new TravelSiteQuery(), new DefaultIpageUrl());
        mav.addObject("siteList", list);
        mav.setViewName("manage/site/index");
        return mav;
    }

    @RequestMapping(value = "/site/siteedit.htm")
    public ModelAndView siteedit(ModelAndView mav, Long id) {
        if (id == null || id == 0) {
            return mav;
        }
        mav.addObject("site", siteService.getTravelSiteById(id));
        mav.setViewName("manage/site/siteadd");
        return mav;
    }

    @RequestMapping(value = "/site/siteadd.htm")
    public String siteadd() {
        return "/manage/site/siteadd";
    }

    @RequestMapping(value = "/site/sitedel.htm", produces = "application/json")
    @ResponseBody
    public JsonResult sitedel(Long id) {
        if (id == null) {
            return JsonResultUtils.error("id不能为空!");
        }
        siteService.deleteTravelSite(id);
        return JsonResultUtils.success("成功!");
    }

    @RequestMapping(value = "/site/savesite.htm", produces = "application/json")
    @ResponseBody
    public JsonResult savesite(@Valid
    TravelSiteDO site, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        ObjectUtils.trim(site);
        if (site.getsId() != null && site.getsId() > 0) {
            siteService.updateTravelSite(site);
            return JsonResultUtils.success("修改成功!");
        }
        if (StringUtils.isEmpty(site.getsName())) {
            return JsonResultUtils.error("站点名称不能为空!");
        }
        List<TravelSiteDO> siteList = siteService.list(new TravelSiteQuery(site.getsName(), site.getsProvince(),
                                                                           site.getsCity()));
        if (siteList != null && siteList.size() > 0) {
            return JsonResultUtils.error("站点名称已经存在!");
        }
        siteService.insertTravelSite(site);
        return JsonResultUtils.success("添加站点成功!");
    }

    /**
     * @param mav
     * @param id 站点ID
     * @return
     */
    @RequestMapping(value = "/site/chufaedit.htm")
    public ModelAndView chufaedit(ModelAndView mav, Long id) {
        if (id == null || id == 0) {
            return mav;
        }
        // 当前站点
        TravelSiteDO site = siteService.getTravelSiteById(id);
        if (site == null) {
            return mav;
        }
        // 出港地
        List<TravelSiteDO> chufaList = siteService.list(new TravelSiteQuery(id));
        List<ChufaVO> chufaVOList = new ArrayList<ChufaVO>();
        for (TravelSiteDO chufa : chufaList) {
            List<TravelColumnDO> column = siteService.listQuery(new TravelColumnQuery(id, chufa.getsId()));
            chufaVOList.add(new ChufaVO(chufa, column));
        }

        mav.addObject("chufaList", chufaVOList);
        mav.addObject("id", id);
        mav.setViewName("manage/site/chufaedit");
        return mav;
    }

    @RequestMapping(value = "/site/chufaUpdate.htm")
    public ModelAndView chufaUpdate(ModelAndView mav, Long id) {
        if (id == null || id == 0) {
            return mav;
        }
        TravelSiteDO chufa = siteService.getTravelSiteById(id);
        mav.addObject("chufa", chufa);
        mav.addObject("sToId", chufa.getsToid());
        mav.setViewName("manage/site/chufa");
        return mav;
    }

    @RequestMapping(value = "/site/chufa.htm")
    public ModelAndView chufa(ModelAndView mav, Long id) {
        mav.addObject("sToId", id);
        mav.setViewName("manage/site/chufa");
        return mav;
    }

    @RequestMapping(value = "/site/chufadel.htm", produces = "application/json")
    @ResponseBody
    public JsonResult chufadel(Long id) {
        if (id == null) {
            return JsonResultUtils.error("id不能为空!");
        }
        siteService.deleteTravelSite(id);
        return JsonResultUtils.success("成功!");
    }

    @RequestMapping(value = "/site/savechufa.htm", produces = "application/json")
    @ResponseBody
    public JsonResult savechufa(@Valid
    TravelSiteDO travelSiteDO, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        ObjectUtils.trim(travelSiteDO);
        if (travelSiteDO.getsToid() == null || travelSiteDO.getsToid() <= 0) {
            return JsonResultUtils.error("出港地上级ID不能为空!");
        }
        if (StringUtils.isEmpty(travelSiteDO.getsName())) {
            travelSiteDO.setsName(travelSiteDO.getsCity());
        }
        if (travelSiteDO.getsId() != null && travelSiteDO.getsId() > 0) {
            siteService.updateTravelSite(travelSiteDO);
            return JsonResultUtils.success("修改出港地成功!");
        }
        List<TravelSiteDO> siteList = siteService.list(new TravelSiteQuery(travelSiteDO.getsToid(),
                                                                           travelSiteDO.getsName()));
        if (siteList != null && siteList.size() > 0) {
            return JsonResultUtils.error("当前站点下出港地名称已经存在!");
        }
        siteService.insertTravelSite(travelSiteDO);
        return JsonResultUtils.success("添加出港地成功!");
    }

    /**
     * @param toId 出港地ID
     * @param zId 站点ID
     * @return
     */
    @RequestMapping(value = "/site/columnadd.htm")
    public ModelAndView columnadd(ModelAndView mav, Long toId, Long sId) {
        if (toId == null || sId == null) {
            return mav;
        }
        mav.addObject("sToId", toId);
        mav.addObject("sId", sId);
        mav.setViewName("manage/site/columnadd");
        return mav;
    }

    @RequestMapping(value = "/site/columnedit.htm")
    public ModelAndView columnedit(ModelAndView mav, Long zId) {
        if (zId == null) {
            return mav;
        }
        TravelColumnDO column = siteService.getTravelColumnById(zId);
        mav.addObject("column", column);
        mav.addObject("zId", zId);
        mav.addObject("sToId", column.getsToId());
        mav.addObject("sId", column.getsId());
        mav.setViewName("manage/site/columnadd");
        return mav;
    }

    @RequestMapping(value = "/site/columndel.htm", produces = "application/json")
    @ResponseBody
    public JsonResult columndel(Long id) {
        if (id == null) {
            return JsonResultUtils.error("id不能为空!");
        }
        siteService.deleteTravelLineColumn(id);
        return JsonResultUtils.success("成功!");
    }

    @RequestMapping(value = "/site/savecolumn.htm", produces = "application/json")
    @ResponseBody
    public JsonResult savecolumn(@Valid
    TravelColumnDO column, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        ObjectUtils.trim(column);
        if (StringUtils.isNotEmpty(column.getzPic()) && column.getzPic().contains("/tmp/")) {
            Result rusult = fileService.saveFileByPath(column.getzPic(), new IFileHandle() {

                @Override
                public String parse(String prefix, String suffix) {
                    return prefix + WebUserTools.getCid() + "/" + PhotoTypeEnum.SITE_COLUMN.getName() + "/" + suffix;
                }
            });
            column.setzPic((String) rusult.getData());
        }

        if (column.getzId() != null && column.getzId() > 0) {
            siteService.update(column);
            return JsonResultUtils.success("修改专线成功!");
        }
        List<TravelColumnDO> columnDOList = siteService.listQuery(new TravelColumnQuery(column.getsId(),
                                                                                        column.getsToId(),
                                                                                        column.getzName()));
        if (columnDOList != null && columnDOList.size() > 0) {
            return JsonResultUtils.error("当前站点当前出港地下专线名称已经存在!");
        }
        siteService.insert(column);
        return JsonResultUtils.success("添加成功!");
    }

    /* 黑名单反馈 */
    /* 列表 */
    @RequestMapping(value = "/blacklists.htm")
    public ModelAndView blacklists(ModelAndView mav, TravelBlackListQuery query, Integer page) {
        query.setPageSize(10);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        PaginationList<TravelBlackListThinDO> list = companyService.queryAllCompanyBlack(query, new DefaultIpageUrl());
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("blackList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/manage/blacklists/list");
        return mav;
    }

    /* list */
    @RequestMapping(value = "/black.htm")
    public String blacklistslist() {
        return "/manage/blacklists/index";
    }

    /* 查看详细 */
    @RequestMapping(value = "/blacklistsview.htm")
    public ModelAndView blacklistsview(ModelAndView mav, TravelBlackListQuery query, Integer page, Long id) {
        query.setPageSize(100);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setBeCId(id);
        PaginationList<TravelBlackListFullDO> list = companyService.listPagination(query, new DefaultIpageUrl());
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("blackList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/manage/blacklists/view");
        return mav;
    }

    /* 消除 */
    @RequestMapping(value = "/blacklistsconfirm.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult blacklistsconfirm(Long cId) {
        TravelCompanyDO companyDO = new TravelCompanyDO();
        companyDO.setcId(cId);
        companyDO.setcState(CompanyStateEnum.BLACK.getValue());
        boolean b = companyService.update(companyDO);
        if (b) {
            return JsonResultUtils.success(companyDO, "修改成功!");
        } else {
            return JsonResultUtils.error(companyDO, "修改失败!");
        }
    }
}
