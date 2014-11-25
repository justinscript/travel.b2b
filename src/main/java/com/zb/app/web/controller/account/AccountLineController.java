/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.IntegralSourceEnum;
import com.zb.app.biz.cons.LineStateEnum;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.cons.LogTableEnum;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelLineThinDO;
import com.zb.app.biz.domain.TravelOperationLogDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelPhotoDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.domain.TravelSiteFullDO;
import com.zb.app.biz.domain.TravelTrafficDO;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.biz.query.TravelPhotoQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.biz.query.TravelTrafficQuery;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.pagination.PagesPagination;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.util.DateViewTools;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;
import com.zb.app.external.lucene.search.query.ProductSearchQuery;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelLineVO;
import com.zb.app.web.vo.TravelRouteVO;
import com.zb.app.web.vo.TravelTrafficVO;

/**
 * Account 产品管理,预定管理,创建线路
 * 
 * @author zxc Fun 16, 2014 5:44:33 PM
 */
@Controller
@RequestMapping("/account")
public class AccountLineController extends BaseController {

    /**
     * 添加线路
     * 
     * @return
     * @throws Exception
     */
    @AuthorityPolicy(authorityTypes = { Right.CREATE_PRODUCT })
    @RequestMapping(value = "/addLine.htm", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addLine(@Valid TravelLineVO line, BindingResult result) throws Exception {
        if (line.getlCrPrice() == null || line.getlXhPrice() == null || line.getlYPrice() == null
            || line.getlJCrPrice() == null || line.getlJXhPrice() == null || line.getlJYPrice() == null
            || line.getlFangPrice() == null) {
            return JsonResultUtils.error("价格不能为空!");
        }
        TravelIntegralDO integralDO = null;
        int integral = 0;
        // 未通过验证
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        Calendar date = Calendar.getInstance();
        String[] groupTimes = line.getlGoGroupTimeString().split(",");
        // 验证积分
        if (line.getlIsIntegral() == 1) {
            if (line.getlAdultIntegral() == null || line.getlChildrenIntegral() == null) {
                return JsonResultUtils.error("积分不能为空!");
            }
            // 获取公司积分
            TravelIntegralQuery integralQuery = new TravelIntegralQuery();
            integralQuery.setcId(WebUserTools.getCid());
            integralDO = integralService.queryBala(integralQuery);
            if (integralDO == null) {
                return JsonResultUtils.error("积分不足");
            }
            Integer cintegral = integralDO.getiBalance();
            integral = line.getlAdultIntegral() > line.getlChildrenIntegral() ? line.getlRenCount() * groupTimes.length
                                                                                * line.getlAdultIntegral() : line.getlRenCount()
                                                                                                             * groupTimes.length
                                                                                                             * line.getlChildrenIntegral();
            if (cintegral < integral) {
                return JsonResultUtils.error("积分不足");
            }
        }
        int tlid = 0, roid = 0;
        String rom = getSerialNumber();
        TravelLineDO tr = null;
        for (String groupTime : groupTimes) {
            if (org.apache.commons.lang.StringUtils.isEmpty(groupTime)) {
                continue;
            }
            if (groupTimes.length == 1) {
                return JsonResultUtils.success(null, "出团日期不能为空!");
            }
            // 出团日期
            Date gogrouptime = null;
            try {
                gogrouptime = DateViewTools.parseSimple(groupTime);
            } catch (Exception e) {
                return JsonResultUtils.success(null, "日期格式不正确");
            }
            line.setlGoGroupTime(gogrouptime);
            // 截止日期
            date.setTime(gogrouptime);
            date.add(Calendar.DAY_OF_MONTH, -line.getlEndTimeInteger());
            line.setlEndTime(date.getTime());
            // 属性拷贝
            tr = new TravelLineDO();
            BeanUtils.copyProperties(tr, line);
            // 设置类型为线路
            tr.setlTemplateState(LineTemplateEnum.Line.getValue());
            // 设置线路分组
            tr.setlProduct(rom);
            // 设置公司ID
            tr.setcId(WebUserTools.getCid());
            // 设置添加线路的用户id
            tr.setmId(WebUserTools.getMid());
            // 添加线路
            tlid = lineService.addTravelLine(tr);
            // 添加行程
            if (line.getRoutelist() != null || line.getRoutelist().size() != 0) {
                int nullroute = line.getlDay() - line.getRoutelist().size();
                for (TravelRouteDO route : line.getRoutelist()) {
                    route.setlId(tr.getlId());
                    roid = lineService.addTravelRoute(route);
                }
                if (nullroute != 0) {
                    for (int i = 0; i < nullroute; i++) {
                        TravelRouteDO route = new TravelRouteDO();
                        route.setlId(tr.getlId());
                        roid = lineService.addTravelRoute(route);
                    }
                }
            }
        }
        boolean bool = tlid != 0 && roid != 0;
        if (bool) {
            // 增量更新索引
            PaginationList<ProductSearchField> list = lineService.listProductSearch(new TravelLineQuery(rom));
            solrClient.addBeans("zuobian", list);
            // 冻结积分
            if (line.getlIsIntegral() == 1) {
                TravelIntegralDO integralDJ = new TravelIntegralDO();
                integralDJ.setiSource(IntegralSourceEnum.release.value);
                integralDJ.setcId(WebUserTools.getCid());
                integralDJ.setiBalance(integralDO.getiBalance() - integral);
                integralDJ.setiFrozen(integralDO.getiFrozen() + integral);
                integralDJ.setiAltogether(integralDJ.getiBalance() + integralDJ.getiFrozen());
                integralService.addTravelIntegral(integralDJ);
            }
        }

        return bool ? JsonResultUtils.success(lineService.getTravelLineById(tr.getlId()).getlGroupNumber(), "添加成功!") : JsonResultUtils.error("添加失败!");
    }

    /**
     * 批量修改线路
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "updateLines.htm", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateLines(String id, Integer state) {
        if (id == null || id.equals("")) {
            return JsonResultUtils.error("请选择数据!");
        }
        // 字符串转Long数组
        String[] ids = id.substring(1, id.length()).split(",");
        Long[] lids = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            lids[i] = Long.parseLong(ids[i]);
        }
        // 设置条件
        TravelLineThinDO trdo = new TravelLineThinDO();
        trdo.setlState(state);
        // 设置最后修改人ID
        trdo.setlEditUserId(WebUserTools.getMid().intValue());
        // 获取修改前线路
        List<TravelLineDO> oldlineDOs = new ArrayList<TravelLineDO>();
        for (Long lid : lids) {
            TravelLineDO oldLine = lineService.getTravelLineById(lid);
            oldlineDOs.add(oldLine);
        }
        // 批量修改
        int result = lineService.updateLines(lids, trdo);

        if (result == 0) {
            return JsonResultUtils.error("修改失败!");
        } else {
            // 修改成功则添加日志
            List<TravelLineDO> newlineDOs = new ArrayList<TravelLineDO>();
            for (Long lid : lids) {
                TravelLineDO oldLine = lineService.getTravelLineById(lid);
                newlineDOs.add(oldLine);
                //更新索引
                updateSolr(oldLine);
            }
            // 判断对象值得改变
            List<Map<String, String>> lst = BeanUtils.fieldEditableList(oldlineDOs, newlineDOs);
            for (int i = 0; i < lst.size(); i++) {
                TravelOperationLogDO operationLogDO = new TravelOperationLogDO(LogTableEnum.LINELOG.value,
                                                                               newlineDOs.get(i).getlId(),
                                                                               newlineDOs.get(i).getlGroupNumber(),
                                                                               lst.get(i).get("oldString"),
                                                                               lst.get(i).get("newString"),
                                                                               WebUserTools.getMid(),
                                                                               WebUserTools.getCid());
                // 添加日志
                operationLogService.insertTravelOperationLog(operationLogDO);
            }
            return JsonResultUtils.success(null, "修改成功!");
        }
    }

    /**
     * 修改路线传值
     * 
     * @param mav
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_PRODUCT })
    @RequestMapping(value = "/linegetvalue.htm")
    public ModelAndView lineUpdateGetValue(Long lId, ModelAndView mav) {
        // 查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(lId));
        TravelLineVO lineVO = new TravelLineVO();
        BeanUtils.copyProperties(lineVO, lineDO);
        // 查询线路所有行程
        TravelRouteQuery routq = new TravelRouteQuery();
        routq.setlId(lineVO.getlId());
        List<TravelRouteDO> rlistp = lineService.list(routq);
        List<TravelRouteVO> rlist = BeanUtils.convert(TravelRouteVO.class, rlistp);
        // 添加进模型
        mav.addObject("line", lineVO);
        mav.addObject("route", rlist);
        mav.addObject("type", "update");
        mav.setViewName("account/line/lineadd");
        return mav;
    }

    /**
     * 线路另存传值
     * 
     * @param mav
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.SAVE_PRODUCT })
    @RequestMapping(value = "/linecopyvalue.htm")
    public ModelAndView lineCopyGetValue(Long lId, ModelAndView mav) {
        // 查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(lId));
        TravelLineVO lineVO = new TravelLineVO();
        BeanUtils.copyProperties(lineVO, lineDO);
        // 查询线路所有行程
        TravelRouteQuery routq = new TravelRouteQuery();
        routq.setlId(lineVO.getlId());
        List<TravelRouteDO> rlistp = lineService.list(routq);
        List<TravelRouteVO> rlist = BeanUtils.convert(TravelRouteVO.class, rlistp);
        // 添加进模型
        mav.addObject("line", lineVO);
        mav.addObject("route", rlist);
        mav.addObject("type", "copy");
        mav.setViewName("account/line/lineadd");
        return mav;
    }

    /**
     * 线路修改
     * 
     * @param line
     * @param result
     * @return
     * @throws Exception
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_PRODUCT })
    @RequestMapping(value = "/lineupdate.htm", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult lineUpdate(TravelLineVO line) throws Exception {
        if (line.getlCrPrice() == null || line.getlXhPrice() == null || line.getlYPrice() == null
            || line.getlJCrPrice() == null || line.getlJXhPrice() == null || line.getlJYPrice() == null
            || line.getlFangPrice() == null) {
            return JsonResultUtils.error("价格不能为空!");
        }
        // 截止日期计算
        Calendar date = Calendar.getInstance();
        date.setTime(line.getlGoGroupTime());
        date.add(Calendar.DAY_OF_MONTH, -line.getlEndTimeInteger());
        line.setlEndTime(date.getTime());
        // 删除行程
        lineService.deleteTravelRouteByLineid(line.getlId());
        // 最后修改人ID
        line.setlEditUserId(WebUserTools.getMid().intValue());
        // 添加行程
        int roid = 0;
        if (line.getRoutelist() != null || line.getRoutelist().size() != 0) {
            for (TravelRouteDO route : line.getRoutelist()) {
                route.setlId(line.getlId());
                roid = lineService.addTravelRoute(route);
            }
        }
        // 查找老对象
        TravelLineDO oldLine = lineService.getTravelLineById(line.getlId());
        boolean bool = lineService.updateTravelLine(line) && roid != 0;
        if (bool) {
            // *****************修改成功添加日志
            // 查找新对象
            TravelLineDO newLine = lineService.getTravelLineById(line.getlId());
            // 判断对象值得改变
            Map<String, String> map = BeanUtils.fieldEditable(oldLine, newLine);
            TravelOperationLogDO operationLogDO = new TravelOperationLogDO(LogTableEnum.LINELOG.value, line.getlId(),
                                                                           line.getlGroupNumber(),
                                                                           map.get("oldString"), map.get("newString"),
                                                                           WebUserTools.getMid(), WebUserTools.getCid());
            // 添加日志
            operationLogService.insertTravelOperationLog(operationLogDO);
            // *****************修改成功便更新索引
            updateSolr(newLine);
            return JsonResultUtils.success(null, "修改成功!");
        } else {
            return JsonResultUtils.error("修改失败!");
        }
    }

    /**
     * 查看线路 分页查询
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value = "/showlist.htm", method = RequestMethod.POST)
    public ModelAndView line(ModelAndView mav, TravelLineQuery query, String lGoGroupTime, String lGoGroupEndTime,
                             Integer page, Integer pagesize) {
        List<TravelSiteFullDO> sitelist = siteService.getSiteFull(WebUserTools.getCid());
        Long[] zids = CollectionUtils.getLongValueArrays(sitelist, "zId");
        if (query.getzId() != null) {
            query.setzIds(query.getzId());
        } else {
            query.setzIds(zids);
        }
        TravelLineQuery.parse(query, lGoGroupTime, lGoGroupEndTime, page, pagesize, LineTemplateEnum.Line.getValue());
        query.setcId(WebUserTools.getCid());

        PaginationList<TravelLineDO> list = lineService.listPagination(query, new DefaultIpageUrl());
        List<TravelLineVO> lists = BeanUtils.convert(TravelLineVO.class, list);

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("list", lists);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("account/line/linelist");
        return mav;
    }

    /**
     * 进入线路展示页面
     * 
     * @return
     */
    @RequestMapping(value = "/line.htm")
    public String showline() {
        return "account/line/index";
    }

    /**
     * 进入线路添加页面
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.CREATE_PRODUCT })
    @RequestMapping(value = "/lineadd.htm")
    public String lineadd() {
        return "account/line/lineadd";
    }

    /***
     * 删除线路
     * 
     * @param id
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.REMOVE_PRODUCT })
    @RequestMapping("/deleteline.htm")
    @ResponseBody
    public JsonResult deleteline(Long id) {
        // 判断该线路是否有订单
        TravelOrderQuery query = new TravelOrderQuery();
        query.setlId(id);
        List<TravelOrderDO> list = orderService.list(query);
        if (Argument.isNotEmpty(list)) {
            return JsonResultUtils.error("线路已产生订单,无法删除!");
        }
        TravelLineDO tr = lineService.getTravelLineById(id);
        tr.setlDelState(1);
        lineService.deleteTravelRouteByLineid(tr.getlId());
        boolean bool = lineService.updateTravelLine(tr);
        // 删除成功便删除索引
        if (bool) {
            updateSolr(tr);
        }
        return bool ? JsonResultUtils.success(null, "删除成功!") : JsonResultUtils.error("删除失败!");
    }

    /**
     * 新增线路中弹出交通模板窗口
     * 
     * @return
     */
    @RequestMapping(value = "/trafficlist.htm")
    public ModelAndView traffic(ModelAndView mav, TravelTrafficQuery query, String type, Integer page, Integer pagesize) {
        query.setcId(WebUserTools.getCid());
        query.setPageSize(pagesize = Argument.isNotPositive(pagesize) ? 8 : pagesize);
        query.setNowPageIndex(page = Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelTrafficDO> trafficlist = lineService.listPagination(query, new DefaultIpageUrl());
        PagesPagination pagination = PaginationParser.getPaginationList(page, pagesize, query.getAllRecordNum(),
                                                                        new IPageUrl() {

                                                                            @Override
                                                                            public String parsePageUrl(Object... objs) {
                                                                                return "/account/trafficlist.htm?page="
                                                                                       + (Integer) objs[1];
                                                                            }

                                                                        });
        List<TravelTrafficVO> trafficlistvo = BeanUtils.convert(TravelTrafficVO.class, trafficlist);

        mav.addObject("trafficlist", trafficlistvo);
        mav.addObject("pagination", pagination);
        mav.addObject("type", type);
        mav.setViewName("account/line/trafficlist");
        return mav;
    }

    /**
     * 新增线路中弹出线路模板窗口
     * 
     * @return
     */
    @RequestMapping(value = "/templatelist.htm")
    public ModelAndView template(ModelAndView mv, TravelLineQuery query, Integer page, Integer pagesize) {
        query.setcId(WebUserTools.getCid());
        query.setlTemplateState(LineTemplateEnum.Template.getValue());
        query.setPageSize(pagesize = Argument.isNotPositive(pagesize) ? 8 : pagesize);
        query.setNowPageIndex(page = Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelLineDO> list = lineService.listPagination(query, new DefaultIpageUrl());
        PagesPagination pagination = PaginationParser.getPaginationList(page, pagesize, query.getAllRecordNum(),
                                                                        new IPageUrl() {

                                                                            @Override
                                                                            public String parsePageUrl(Object... objs) {
                                                                                return "/account/templatelist.htm?page="
                                                                                       + (Integer) objs[1];
                                                                            }

                                                                        });
        List<TravelLineVO> lists = BeanUtils.convert(TravelLineVO.class, list);

        mv.addObject("pagination", pagination);
        mv.addObject("template", lists);
        mv.setViewName("account/line/templatelist");
        return mv;
    }

    /**
     * 获取交通模板对象
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/getTraffic.htm")
    @ResponseBody
    public JsonResult getTraffic(Long id) {
        TravelTrafficDO traf = lineService.getTravelTrafficById(id);
        return traf != null ? JsonResultUtils.success(traf) : JsonResultUtils.error("未找到!");
    }

    /**
     * 获取线路模板对象
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/getTemplate.htm")
    @ResponseBody
    public JsonResult getTemplate(Long id) {
        TravelLineDO tral = lineService.getTravelLineById(id);
        if (tral == null) {
            return JsonResultUtils.error("发生错误!");
        }
        TravelLineVO trvo = new TravelLineVO(tral);
        TravelRouteQuery query = new TravelRouteQuery();
        query.setlId(trvo.getlId());
        List<TravelRouteDO> listrout = lineService.list(query);
        trvo.setRoutelist(listrout);
        return trvo != null ? JsonResultUtils.success(trvo) : JsonResultUtils.error("未找到!");
    }

    @RequestMapping(value = "/road.htm")
    public String Road() {
        return "account/line/road";
    }

    @RequestMapping(value = "/roadadd.htm")
    public String roadadd() {
        return "account/line/roadadd";
    }

    /***
     * 弹出图片选择框
     * 
     * @param name
     * @return
     */
    @RequestMapping(value = "/resourcelist.htm")
    public String resourcelist(String name) {
        return "account/line/resourcelist";
    }

    /***
     * 显示图片LIST
     * 
     * @param query
     * @param pagesize
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/photodiv.htm")
    public ModelAndView photolist(TravelPhotoQuery query, Integer pagesize, Integer page, ModelAndView model) {
        query.setPageSize(pagesize = Argument.isNotPositive(pagesize) ? 8 : pagesize);
        query.setNowPageIndex(page = Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcId(WebUserTools.getCid());

        PaginationList<TravelPhotoDO> list = photoService.listPagination(query, new DefaultIpageUrl());
        PagesPagination pagination = PaginationParser.getPaginationList(page, pagesize, query.getAllRecordNum(),
                                                                        new IPageUrl() {

                                                                            @Override
                                                                            public String parsePageUrl(Object... objs) {
                                                                                return "/account/photodiv.htm?page="
                                                                                       + (Integer) objs[1];
                                                                            }

                                                                        });
        model.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        model.addObject("list", list);
        model.addObject("type", "div");
        model.addObject("pagination", pagination);
        model.setViewName("account/photo/list");
        return model;
    }

    @RequestMapping(value = "/order.htm")
    public ModelAndView showOrder(Long id, ModelAndView mav) {
        mav.setViewName("account/order/order");
        if (Argument.isNotPositive(id)) {
            return mav;
        }
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));

        mav.addObject("line", new TravelLineVO(lineDO));
        return mav;
    }

    /**
     * 生成流水号
     * 
     * @return
     */
    private String getSerialNumber() {
        String now = DateViewTools.format(new Date(), "yyyyMMddHHmmssSSS");
        int num = new Random().nextInt(50000) + 10000;
        return now + num;
    }

    /***
     * 更新索引
     * 
     * @param line
     */
    private void updateSolr(TravelLineDO line) {
        ProductSearchQuery query = new ProductSearchQuery(null, line.getlProduct());
        solrClient.del("zuobian", query.toSolrQuery());
        TravelLineQuery queryline = new TravelLineQuery();
        queryline.setlTemplateState(LineTemplateEnum.Line.getValue());
        queryline.setlState(LineStateEnum.NORMAL.getValue());
        queryline.setlProduct(line.getlProduct());
        PaginationList<ProductSearchField> field = lineService.listProductSearch(queryline);
        if (Argument.isNotEmpty(field)) {
            solrClient.addBeans("zuobian", field);
        }
    }
}
