/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.tour;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.ColumnCatEnum;
import com.zb.app.biz.cons.LineStateEnum;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelSiteFullDO;
import com.zb.app.biz.query.TravelColumnQuery;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.SiteCacheTools;
import com.zb.app.web.vo.TravelLineVO;

/**
 * Tour 线路管理
 * 
 * @author zxc Jun 17, 2014 5:23:55 PM
 */
@Controller
@RequestMapping(value = "/tour")
public class TourLineController extends BaseController {

    /**
     * 进入线路展示页面
     * 
     * @return
     */
    @RequestMapping(value = "/line/{column}.htm")
    public String lineindex(@PathVariable String column, Model model) {
        model.addAttribute("url", "/tour/line/show" + column + ".htm");
        model.addAttribute("type", ColumnCatEnum.getAction(column).getValue());
        return "/tour/line/index";
    }

    /**
     * 查看线路 分页查询
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value = "/line/show{column}.htm")
    public ModelAndView line(@PathVariable String column, ModelAndView mav, String lGoGroupTime,
                             String lGoGroupEndTime, TravelLineQuery query, Integer page, Integer pagesize) {
        ColumnCatEnum cat = ColumnCatEnum.getAction(column);
        if (cat == null) {
            return mav;
        }
        List<TravelColumnDO> columnList = siteService.listQuery(new TravelColumnQuery(cat));
        Long[] zIds = CollectionUtils.getLongValueArrays(columnList, "zId");

        SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
        List<TravelSiteFullDO> siteFullList = siteCacheTools.getTourSiteList();

        List<Long> map = CollectionUtils.getLongValues(siteFullList, "zId");
        Set<Long> zIdSet = new HashSet<Long>();
        for (Long zId : zIds) {
            if (map.contains(zId)) {
                zIdSet.add(zId);
            }
        }

        TravelLineQuery.parse(query, lGoGroupTime, lGoGroupEndTime, page, pagesize, LineTemplateEnum.Line.getValue());
        query.setlState(LineStateEnum.NORMAL.getValue());
        if (query.getzId() != null) {
            query.setzIds(query.getzId());
        } else {
            query.setzIds(zIdSet.toArray(new Long[zIdSet.size()]));
        }

        PaginationList<TravelLineDO> list = lineService.listGroup(query, new DefaultIpageUrl());
        List<TravelLineVO> trvolist = BeanUtils.convert(TravelLineVO.class, list);
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("list", trvolist);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("tour/line/linelist");
        return mav;
    }
}
