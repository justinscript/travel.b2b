/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.CompanyStateEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.LineStateEnum;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelLineThinDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.domain.TravelServiceDO;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.biz.query.TravelServiceQuery;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.interceptor.annotation.ExportWordFile;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.util.NumberParser;
import com.zb.app.common.util.StringFormatter;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.SiteCacheTools;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.ChufaFullVO;
import com.zb.app.web.vo.ColumnThinVO;
import com.zb.app.web.vo.TravelLineSimpleVO;
import com.zb.app.web.vo.TravelLineVO;
import com.zb.app.web.vo.TravelRouteVO;

/**
 * 全局线路管理
 * 
 * @author zxc Aug 4, 2014 2:39:49 PM
 */
@Controller
public class LineController extends BaseController {

    /**
     * 进入线路详细页面
     * 
     * @return
     */
    @RequestMapping(value = "/line/{id}.htm")
    public ModelAndView lineshow(@PathVariable("id") String id, ModelAndView mv) {
        mv.setViewName("line/line");
        TravelLineQuery query = new TravelLineQuery();
        if (StringUtils.isEmpty(id) || !NumberParser.isNumber(id)) {
            return mv;
        }

        if (id.length() == 8) {
            query.setlGroupNumber(id);
        } else {
            query.setlId(Long.parseLong(id));
        }
        // 查询线路
        TravelLineDO trline = lineService.find(query);
        if (trline == null) {
            mv.addObject("exception", "页面未找到!");
            mv.setViewName("error");
            return mv;
        }
        TravelLineVO trDo = new TravelLineVO(trline);
        // 查询线路所有行程
        List<TravelRouteDO> rlistp = lineService.list(new TravelRouteQuery(trDo.getlId()));
        List<TravelRouteVO> rlist = BeanUtils.convert(TravelRouteVO.class, rlistp);
        // 查询线路发布公司
        if (trline.getcId() != null) {
            TravelCompanyDO comdo = companyService.getById(trline.getcId());
            mv.addObject("company", comdo);
        }
        // 查询客服
        TravelServiceQuery servicequery = new TravelServiceQuery(trline.getcId(), null);
        List<TravelServiceDO> servicelist = companyService.list(servicequery);
        if (!WebUserTools.hasLogin()
            || !(CompanyTypeEnum.isAccount(WebUserTools.getCompanyType()) || CompanyTypeEnum.isManage(WebUserTools.getCompanyType()))) {
            // 浏览量增加
            TravelLineDO lineup = new TravelLineDO(trline.getlId());
            lineup.setlViews((trline.getlViews() == null ? 0 : trline.getlViews()) + 1);
            lineService.updateTravelLine(lineup);
        }
        TravelLineQuery queryv = new TravelLineQuery();
        queryv.setColumnType(1);
        queryv.setlProduct(trline.getlProduct());
        trDo.setlViews(lineService.countByGroup(queryv));
        // 添加进模型
        mv.addObject("servicelist", servicelist);
        mv.addObject("line", trDo);
        mv.addObject("routelist", rlist);
        return mv;
    }

    /**
     * 导出word文档
     * 
     * @param id
     * @return
     */
    @RequestMapping("/printdoc/{id}.htm")
    @ExportWordFile(value = "行程单")
    public ModelAndView exportDoc(@PathVariable("id") Long id, ModelAndView mav) {
        if (Argument.isNotPositive(id)) {
            return createErrorJsonMav("参数错误!", null);
        }
        TravelLineDO trdo = lineService.getTravelLineById(id);
        if (trdo == null) {
            return createErrorJsonMav("未找到对象!", null);
        }
        TravelLineVO line = new TravelLineVO(trdo);
        // 查询行程
        TravelRouteQuery query = new TravelRouteQuery();
        query.setlId(line.getlId());
        List<TravelRouteDO> routelist = lineService.list(query);
        line.setRoutelist(routelist);
        // 查询线路发布公司
        TravelCompanyDO company = companyService.getById(WebUserTools.getCid());
        mav.addObject("line", StringFormatter.objectFieldEscape(line));
        mav.addObject("comp", company);
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.setViewName("word/lineword");
        return mav;
    }

    /**
     * 查询分组日期
     * 
     * @param groupid
     * @return
     */
    @RequestMapping(value = "/line/getGroup.htm", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getGrouplist(String id) {
        // 查询线路分组日期
        TravelLineQuery queryg = new TravelLineQuery();
        queryg.setlState(0);
        queryg.setlProduct(id);
        List<TravelLineDO> datelist = lineService.list(queryg);
        for (TravelLineThinDO travelLineDO : datelist) {
            travelLineDO.setlSurplusCount(travelLineDO.getlRenCount() - travelLineDO.getlCrCount()
                                          - travelLineDO.getlXhCount());
        }
        List<TravelLineSimpleVO> simplelist = BeanUtils.convert(TravelLineSimpleVO.class, datelist);
        return JsonResultUtils.success(simplelist);
    }

    /**
     * 日期控件
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/line/linedate.htm")
    public ModelAndView linedate(String id, ModelAndView model) {
        TravelLineQuery query = new TravelLineQuery();
        query.setlGroupNumber(id);
        TravelLineDO trdo = lineService.find(query);
        model.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        model.addObject("line", trdo);
        model.setViewName("/line/linedate");
        return model;
    }

    /**
     * 获取线路总数
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/line/getLineCount.htm")
    @ResponseBody
    public JsonResult getLineCount(TravelLineQuery query) {
        query.setlTemplateState(LineTemplateEnum.Line.getValue());
        int count = lineService.count(query);
        return JsonResultUtils.success(count);
    }

    /***
     * 线路列表展示
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/line/showline.htm")
    public ModelAndView showLine(ModelAndView model, final TravelLineQuery query, Integer page, Integer pagesize) {
        // 设置条件
        // 获取站点下专线Zids
        if (query.getzId() == null) {
            SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
            ChufaFullVO chugang = siteCacheTools.getChugangByChugangId(WebUserTools.getChugangId());
            Map<Integer, List<ColumnThinVO>> column = chugang.getColumnMap();
            List<ColumnThinVO> columnlist = new ArrayList<ColumnThinVO>();
            for (List<ColumnThinVO> list : column.values()) {
                columnlist.addAll(list);
            }
            Long[] zIds = CollectionUtils.getLongValueArrays(columnlist, "zId");
            query.setzIds(zIds);
        } else {
            query.setzIds(query.getzId());
        }
        TravelLineQuery.parse(query, null, null, page, pagesize, LineTemplateEnum.Line.getValue());
        query.setlState(LineStateEnum.NORMAL.getValue());
        // 查询
        PaginationList<TravelLineDO> list = lineService.listGroup(query, new IPageUrl() {

            @Override
            public String parsePageUrl(Object... objs) {
                String str = "/line/showline.htm?page=" + (Integer) objs[1];
                if (query.getlArrivalCity() != null) {
                    str += "&lArrivalCity=" + query.getlArrivalCity();
                }
                if (query.getlDay() != null) {
                    str += "&lDay=" + query.getlDay();
                }
                if (query.getlType() != null) {
                    str += "&lType=" + query.getlType();
                }
                return str;
            }

        });

        model.addObject("list", BeanUtils.convert(TravelLineVO.class, list));
        // 线路总数
        TravelLineQuery queryline = new TravelLineQuery();
        queryline.setlState(LineStateEnum.NORMAL.getValue());
        queryline.setlTemplateState(LineTemplateEnum.Line.getValue());
        model.addObject("linecount", lineService.countByGroup(queryline));
        model.addObject("pagination", list.getQuery());
        model.addObject("searchcount", lineService.countByGroup(query));
        // 已定人数
        model.addObject("orderGuestCount", orderService.countByOrderGuest());
        // 商家推荐
        TravelCompanyQuery companyQuery = new TravelCompanyQuery();
        companyQuery.setNowPageIndex(0);
        companyQuery.setPageSize(6);
        companyQuery.setcType(CompanyTypeEnum.ACCOUNT.getValue());
        companyQuery.setcState(CompanyStateEnum.NORMAL.getValue());
        PaginationList<TravelCompanyDO> companyDOs = companyService.showCompanyPagination(companyQuery,
                                                                                          new DefaultIpageUrl());
        model.addObject("companyDOs", companyDOs);
        // 传递参数
        model.addObject("lDay", query.getlDay());
        model.addObject("lType", query.getlType());
        model.addObject("city", query.getlArrivalCity());
        // 获取所有抵达城市
        model.addObject("citylists", lineService.getCityByCid(WebUserTools.getChugangId()));

        model.setViewName("/cms/1409/line");
        return model;
    }
}
