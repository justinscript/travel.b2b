/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.tour;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.query.TravelMemberQuery;
import com.zb.app.common.authority.AuthorityHelper;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.security.EncryptBuilder;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelCompanyVO;
import com.zb.app.web.vo.TravelMemberVO;

/**
 * Tour 系统设置,公司信息,用户信息管理
 * 
 * @author zxc Jun 17, 2014 5:23:55 PM
 */
@Controller
@RequestMapping(value = "/tour")
public class TourController extends BaseController {

    /**
     * 查看公司信息
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value = "/company.htm")
    public ModelAndView company(ModelAndView mav) {
        TravelCompanyDO travelCompanyDO = companyService.getById(WebUserTools.getCid());
        TravelCompanyVO travelCompanyVO = new TravelCompanyVO();
        BeanUtils.copyProperties(travelCompanyVO, travelCompanyDO);

        mav.addObject("company", travelCompanyVO);
        mav.setViewName("/tour/customer/company");
        return mav;
    }

    /**
     * 公司信息更新
     * 
     * @param travelCompanyVO
     * @return
     */
    @RequestMapping(value = "/updateCompany.htm", produces = "application/json")
    @ResponseBody
    public JsonResult updateCompany(@RequestParam("file")
    MultipartFile file, TravelCompanyVO travelCompanyVO) {
        TravelCompanyDO travelCompanyDO = new TravelCompanyDO();
        BeanUtils.copyProperties(travelCompanyDO, travelCompanyVO);
        if (file != null) {
            String fileName = travelCompanyDO.getcId() + ".jpg";
            fileService.createFilePath(file, fileName);
            travelCompanyDO.setcLogo("/static/img/" + fileName);
        }
        Boolean bool = companyService.update(travelCompanyDO);
        if (bool) {
            return JsonResultUtils.success(travelCompanyDO, "更新成功!");
        } else {
            return JsonResultUtils.error(travelCompanyDO, "更新失败!");
        }
    }

    @RequestMapping(value = "/myinfo.htm")
    public ModelAndView myinfo(ModelAndView mav) {
        Long mId = WebUserTools.getMid();
        TravelMemberDO travelMemberDO = memberService.getById(mId);
        TravelMemberVO travelMemberVO = new TravelMemberVO();
        BeanUtils.copyProperties(travelMemberVO, travelMemberDO);
        mav.addObject("memberVO", travelMemberVO);
        mav.setViewName("/tour/customer/myinfo");
        return mav;
    }

    @RequestMapping(value = "/password.htm")
    public ModelAndView newsadd(ModelAndView mav, Long id) {
        mav.addObject("mId", id);
        mav.setViewName("/tour/customer/password");
        return mav;
    }

    @RequestMapping(value = "/updatePassword.htm", produces = "application/json")
    @ResponseBody
    public JsonResult updatePassword(TravelMemberDO travelMemberDO, String newpw) {
        TravelMemberQuery query = new TravelMemberQuery();
        query.setmId(travelMemberDO.getmId());
        query.setmPassword(EncryptBuilder.getInstance().encrypt(travelMemberDO.getmPassword()));
        List<TravelMemberDO> memberDOs = memberService.listQuery(query);
        if (memberDOs.size() == 0 || memberDOs == null) {
            return JsonResultUtils.error(travelMemberDO, "原密码错误!");
        }

        travelMemberDO.setmPassword(EncryptBuilder.getInstance().encrypt(newpw));
        Boolean bool = memberService.update(travelMemberDO);
        if (bool) {
            return JsonResultUtils.success(travelMemberDO, "密码修改成功!");
        } else {
            return JsonResultUtils.error(travelMemberDO, "密码修改失败!");
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
        if (StringUtils.isNotEmpty(travelMemberDO.getmPassword())) {
            travelMemberDO.setmPassword(EncryptBuilder.getInstance().encrypt(travelMemberDO.getmPassword()));
        }
        if (StringUtils.isNotEmpty(travelMemberVO.getmRole()) && StringUtils.contains(travelMemberVO.getmRole(), ",")) {
            String role = travelMemberVO.getmRole();
            role = AuthorityHelper.makeAuthority(role);
            travelMemberDO.setmRole(role);
        }
        Boolean bool = memberService.update(travelMemberDO);
        if (bool) {
            return JsonResultUtils.success(travelMemberDO, "更新成功!");
        } else {
            return JsonResultUtils.error(travelMemberDO, "更新失败!");
        }
    }

    @RequestMapping(value = "/userlist.htm")
    public ModelAndView user(ModelAndView mav, TravelMemberQuery query, Integer page) {
        query.setPageSize(20);
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
        mav.setViewName("/tour/customer/userlist");
        return mav;
    }

    @RequestMapping(value = "/user.htm")
    public String user() {
        return "/tour/customer/user";
    }

    @RequestMapping(value = "/useradd.htm")
    public String useradd() {
        return "/tour/customer/useradd";
    }

    @RequestMapping(value = "/addUser.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addUser(ModelAndView mav, TravelMemberVO travelMemberVO) {
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        BeanUtils.copyProperties(travelMemberDO, travelMemberVO);
        travelMemberDO.setmPassword(EncryptBuilder.getInstance().encrypt(travelMemberVO.getmPassword()));
        travelMemberDO.setmUserName(StringUtils.lowerCase(travelMemberVO.getmUserName()));
        travelMemberDO.setcId(WebUserTools.getCid());

        if (StringUtils.isNotEmpty(travelMemberVO.getmRole())) {
            String role = AuthorityHelper.makeAuthority(travelMemberVO.getmRole());
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
    @RequestMapping(value = "/ljEdit.htm")
    public ModelAndView ljUpdateUser(ModelAndView mav, Long id) {
        TravelMemberDO memberDO = memberService.getById(id);
        if (memberDO != null && StringUtils.isNotEmpty(memberDO.getmPassword())) {
            memberDO.setmPassword(EncryptBuilder.getInstance().decrypt(memberDO.getmPassword()));
        }
        if (StringUtils.isNotEmpty(memberDO.getmRole())) {
            String role = AuthorityHelper.createRightStr(memberDO.getmRole());
            memberDO.setmRole(role);
        }

        mav.addObject("member", memberDO);
        mav.addObject("type", "update");
        mav.setViewName("/tour/customer/useradd");
        return mav;
    }

    @RequestMapping(value = "/userEdit.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult userEdit(TravelMemberDO memberDO) {
        if (memberDO != null && StringUtils.isNotEmpty(memberDO.getmPassword())) {
            memberDO.setmPassword(EncryptBuilder.getInstance().encrypt(memberDO.getmPassword()));
        }
        if (StringUtils.isNotEmpty(memberDO.getmRole())) {
            String role = AuthorityHelper.makeAuthority(memberDO.getmRole());
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
}
