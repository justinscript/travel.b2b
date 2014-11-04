/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.domain.TravelTrafficDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.biz.query.TravelTrafficQuery;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelLineVO;
import com.zb.app.web.vo.TravelRouteVO;
import com.zb.app.web.vo.TravelTrafficVO;

/**
 * Account 线路模板,交通模板
 * 
 * @author zxc Jul 30, 2014 11:47:06 PM
 */
@Controller
@RequestMapping("/account")
public class AccountTemplateController extends BaseController {

    // /////
    //
    // ####################################################Account线路模板管理###################################################
    //
    // /////

    /**
     * 进入线路显示页面
     * 
     * @return
     */
    @RequestMapping(value = "/template.htm")
    public String template() {
        return "account/line/template";
    }

    /**
     * 模板显示list
     * 
     * @param mv
     * @param query
     * @param page
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "/templatepage.htm")
    public ModelAndView template(ModelAndView mv, TravelLineQuery query, Integer page, Integer pagesize) {
        if (query.getzId() != null) {
            query.setzIds(query.getzId());
        }
        query.setlTemplateState(1);
        query.setcId(WebUserTools.getCid());
        query.setPageSize(Argument.isNotPositive(pagesize) ? 15 : pagesize);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        // 查询线路模板
        PaginationList<TravelLineDO> list = lineService.listPagination(query, new DefaultIpageUrl());
        List<TravelLineVO> lists = BeanUtils.convert(TravelLineVO.class, list);
        mv.addObject("pagination", list.getQuery());
        mv.addObject("template", lists);
        mv.setViewName("account/line/templatepage");
        return mv;
    }

    /**
     * 进入线路模板添加页面
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CREATE_LINE_TEMPLATE })
    @RequestMapping(value = "/templateadd.htm")
    public String templateadd() {
        return "account/line/templateadd";
    }

    /**
     * 进入模板修改页面
     * 
     * @param id
     * @param mv
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_LINE_TEMPLATE })
    @RequestMapping(value = "/templateupdate.htm")
    public ModelAndView templateupdate(Long id, ModelAndView mv) {
        // 查询线路模板
        TravelLineDO tr = lineService.find(new TravelLineQuery(id));
        // 查询线路所有行程
        TravelRouteQuery routq = new TravelRouteQuery();
        routq.setlId(tr.getlId());
        List<TravelRouteDO> rlistp = lineService.list(routq);
        List<TravelRouteVO> rlist = BeanUtils.convert(TravelRouteVO.class, rlistp);
        mv.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mv.addObject("type", "update");
        mv.addObject("route", rlist);
        mv.addObject("temp", tr);
        mv.setViewName("account/line/templateadd");
        return mv;
    }

    /**
     * 进入模板复制页面
     * 
     * @param id
     * @param mv
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.COPY_LINE_TEMPLATE })
    @RequestMapping(value = "/templatecopy.htm")
    public ModelAndView templatecopy(Long id, ModelAndView mv) {
        // 查询线路模板
        TravelLineDO tr = lineService.find(new TravelLineQuery(id));
        // 查询线路所有行程
        TravelRouteQuery routq = new TravelRouteQuery();
        routq.setlId(tr.getlId());
        List<TravelRouteDO> rlistp = lineService.list(routq);
        List<TravelRouteVO> rlist = BeanUtils.convert(TravelRouteVO.class, rlistp);
        // 添加进模型
        tr.setcId(null);
        mv.addObject("type", "copy");
        mv.addObject("route", rlist);
        mv.addObject("temp", tr);
        mv.setViewName("account/line/templateadd");
        return mv;
    }

    /**
     * 修改线路模板
     * 
     * @param trDo
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_LINE_TEMPLATE })
    @RequestMapping(value = "/updatetemplate.htm")
    @ResponseBody
    public JsonResult updateTemplate(TravelLineVO trDo) {
        //设置最后修改人ID
        trDo.setlEditUserId(WebUserTools.getMid().intValue());
        boolean bool = lineService.updateTravelLine(trDo);
        // 删除行程
        lineService.deleteTravelRouteByLineid(trDo.getlId());
        // 添加行程
        boolean boo = true;
        if (trDo.getRoutelist() != null || trDo.getRoutelist().size() != 0) {
            for (TravelRouteDO route : trDo.getRoutelist()) {
                route.setlId(trDo.getlId());
                int roid = lineService.addTravelRoute(route);
                if (roid == 0) {
                    boo = false;
                }
            }
        }
        return bool && boo ? JsonResultUtils.success(null, "修改成功!") : JsonResultUtils.error("修改失败!");
    }

    /**
     * 添加线路模板
     * 
     * @param travelLineDO
     * @param mv
     * @param result
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CREATE_LINE_TEMPLATE })
    @RequestMapping(value = "/addtemplate.htm")
    @ResponseBody
    public JsonResult addtemplat(TravelLineVO travelLineVO, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        // 添加线路模板
        travelLineVO.setlTemplateState(LineTemplateEnum.Template.getValue());
        TravelLineDO travelLineDO = new TravelLineDO();
        BeanUtils.copyProperties(travelLineDO, travelLineVO);
        // 设置添加线路的用户id
        travelLineDO.setmId(WebUserTools.getMid());
        travelLineDO.setcId(WebUserTools.getCid());
        int linebo = lineService.addTravelLine(travelLineDO);
        int routebo = 0;
        // 添加行程
        if (travelLineVO.getRoutelist() != null || travelLineVO.getRoutelist().size() != 0) {
            for (TravelRouteDO route : travelLineVO.getRoutelist()) {
                route.setlId(travelLineDO.getlId());
                routebo = lineService.addTravelRoute(route);
            }
        }
        boolean bool = linebo != 0 && routebo != 0;
        return bool ? JsonResultUtils.success("添加成功!") : JsonResultUtils.error(null, "添加失败!");
    }

    /**
     * 删除线路模板
     * 
     * @param id
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.DELETE_LINE_TEMPLATE })
    @RequestMapping(value = "/deletetemplate.htm")
    @ResponseBody
    public JsonResult delete(Long id) {
        boolean bool = lineService.deleteTravelLine(id);
        return JsonResultUtils.success(null, bool ? "删除成功!" : "删除失败!");
    }

    // /////
    //
    // ####################################################Account交通模板管理###################################################
    //
    // /////

    /**
     * 进入交通模板页面
     * 
     * @return
     */
    @RequestMapping(value = "/traffic.htm")
    public String traffic() {
        return "account/line/traffic";
    }

    /***
     * 交通列表
     * 
     * @param mav
     * @param query
     * @param page
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "/trafficpage.htm")
    public ModelAndView traffic(ModelAndView mav, TravelTrafficQuery query, Integer page, Integer pagesize) {
        query.setcId(WebUserTools.getCid());
        query.setPageSize(Argument.isNotPositive(pagesize) ? 15 : pagesize);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        // 分页查询
        PaginationList<TravelTrafficDO> trafficlist = lineService.listPagination(query, new DefaultIpageUrl());
        // 转成VOList
        List<TravelTrafficVO> trafficlistvo = BeanUtils.convert(TravelTrafficVO.class, trafficlist);
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("traffic", trafficlistvo);
        mav.addObject("pagination", trafficlist.getQuery());
        mav.setViewName("account/line/trafficpage");
        return mav;
    }

    /**
     * 进入添加交通模板页面
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CREATE_TRAFFIC_TEMPLATE })
    @RequestMapping(value = "/trafficadd.htm")
    public String trafficadd() {
        return "account/line/trafficadd";
    }

    /**
     * 添加交通模板页面
     * 
     * @param traffic
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CREATE_TRAFFIC_TEMPLATE })
    @RequestMapping(value = "/addtraffic.htm", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addTraffic(TravelTrafficDO traffic) {
        traffic.setcId(WebUserTools.getCid());
        int i = lineService.insertTravelTraffic(traffic);
        return JsonResultUtils.success(null, i != 0 ? "添加成功!" : "添加失败!");
    }

    /**
     * 进入修改交通模板页面
     * 
     * @param id
     * @param mav
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_TRAFFIC_TEMPLATE })
    @RequestMapping(value = "/trafficupdate.htm", method = RequestMethod.GET)
    public ModelAndView trafficUpdate(Long id, ModelAndView mav) {
        TravelTrafficDO trafficdo = lineService.getTravelTrafficById(id);
        TravelTrafficVO vo = new TravelTrafficVO(trafficdo);
        BeanUtils.copyProperties(vo, trafficdo);
        mav.addObject("traffic", vo);
        mav.addObject("type", "update");
        mav.setViewName("account/line/trafficadd");
        return mav;
    }

    /**
     * 修改交通模板
     * 
     * @param traffic
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_TRAFFIC_TEMPLATE })
    @RequestMapping(value = "/updatetraffic.htm")
    @ResponseBody
    public JsonResult updateTraffic(TravelTrafficDO traffic) {
        return JsonResultUtils.success(null, lineService.updateTravelTraffic(traffic) ? "修改成功!" : "修改失败!");
    }

    /**
     * 删除交通模板
     * 
     * @param id
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.DELETE_TRAFFIC_TEMPLATE })
    @RequestMapping(value = "/deletetraffic.htm", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteTraffic(Long id) {
        return JsonResultUtils.success(null, lineService.deleteTravelTraffic(id) ? "删除成功!" : "删除失败!");
    }
}
