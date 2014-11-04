/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.manage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.ColumnCatEnum;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.query.TravelColumnQuery;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.vo.TravelLineVO;
import com.zb.app.web.vo.TravelRouteVO;

/**
 * 线路管理 控制层
 * 
 * @author zxc Jun 16, 2014 3:45:54 PM
 */
@Controller
@RequestMapping("/zbmanlogin")
public class ManageLineController extends BaseController {

    /**
     * 专线类别
     * 
     * @param columnCat
     * @param mv
     * @return
     */
    @RequestMapping("/line/{columnCat}.htm")
    public String manline(@PathVariable("columnCat")
    String columnCat, Model mv) {
        mv.addAttribute("url", "/zbmanlogin/showlist/" + columnCat + ".htm");
        mv.addAttribute("type", ColumnCatEnum.getAction(columnCat).getValue());
        return "/manage/line/index";
    }

    /**
     * 线路列表
     * 
     * @param columnCat
     * @param mv
     * @param query
     * @param lGoGroupTime
     * @param lGoGroupEndTime
     * @param page
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "/showlist/{columnCat}.htm")
    public ModelAndView line(@PathVariable("columnCat")
    String columnCat, ModelAndView mv, TravelLineQuery query, String lGoGroupTime, String lGoGroupEndTime,
                             Integer page, Integer pagesize) {
        mv.setViewName("/manage/line/linelist");
        ColumnCatEnum cat = ColumnCatEnum.getAction(columnCat);
        if (cat == null) {
            return mv;
        }
        List<TravelColumnDO> columnList = siteService.listQuery(new TravelColumnQuery(cat));
        Long[] zIds = CollectionUtils.getLongValueArrays(columnList, "zId");

        TravelLineQuery.parse(query, lGoGroupTime, lGoGroupEndTime, page, pagesize, LineTemplateEnum.Line.getValue());
        if (query.getzId() != null) {
            query.setzIds(query.getzId());
        } else {
            query.setzIds(zIds);
        }

        PaginationList<TravelLineDO> list = lineService.listPagination(query, new DefaultIpageUrl());
        List<TravelLineVO> lists = BeanUtils.convert(TravelLineVO.class, list);

        mv.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mv.addObject("list", lists);
        mv.addObject("pagination", list.getQuery());
        return mv;
    }
    
    
    /*模板列表*/
    @RequestMapping(value = "/line/template.htm")
    public String template() {
        return "/manage/line/template";
    }
  
    
    /**
     * 模板显示list
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
        query.setPageSize(Argument.isNotPositive(pagesize) ? 15 : pagesize);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        // 查询线路模板
        PaginationList<TravelLineDO> list = lineService.listPagination(query, new DefaultIpageUrl());
        List<TravelLineVO> lists = BeanUtils.convert(TravelLineVO.class, list);
        mv.addObject("pagination", list.getQuery());
        mv.addObject("template", lists);
        mv.setViewName("/manage/line/templatepage");
        return mv;
    }
    
    /**
     * 进入模板修改页面
     * 
     * @param id
     * @param mv
     * @return
     */
    @RequestMapping(value = "/line/templateupdate.htm")
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
        mv.setViewName("/manage/line/templateadd");
        return mv;
    }
    
    @RequestMapping(value = "/line/updatetemplate.htm")
    @ResponseBody
    public JsonResult updateTemplate(TravelLineVO trDo) {
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
    
}
