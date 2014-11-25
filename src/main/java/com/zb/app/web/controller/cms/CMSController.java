/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.cms;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.ArticlesSourceEnum;
import com.zb.app.biz.cons.CompanyStateEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.IntegralSourceEnum;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.domain.TravelArticlesDO;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelGiftClassDO;
import com.zb.app.biz.domain.TravelGiftDO;
import com.zb.app.biz.domain.TravelGiftOrderDO;
import com.zb.app.biz.domain.TravelIntegralDO;
import com.zb.app.biz.domain.TravelLineColumnDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelNewsDO;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelGiftClassQuery;
import com.zb.app.biz.query.TravelGiftQuery;
import com.zb.app.biz.query.TravelIntegralQuery;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelNewsQuery;
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
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelLineVO;

/**
 * 全局内容管理系统 帮助中心(关于我们,登录注册,常见问题,服务热线)
 * 
 * @author zxc Aug 15, 2014 4:16:18 PM
 */
@Controller
public class CMSController extends BaseController {

    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT     = 20;

    /**
     * 商家大全
     * 
     * @return
     */
    @RequestMapping(value = "/company.htm")
    public ModelAndView company(ModelAndView mav, TravelCompanyQuery query, Integer page, Integer pagesize) {
        mav.setViewName("cms/1409/company");

        query.setPageSize(pagesize = Argument.isNotPositive(pagesize) ? 20 : pagesize);
        query.setNowPageIndex(page = Argument.isNotPositive(page) ? 0 : page - 1);
        query.setcType(CompanyTypeEnum.ACCOUNT.getValue());
        query.setcState(CompanyStateEnum.NORMAL.getValue());

        PaginationList<TravelCompanyDO> list = companyService.showCompanyPagination(query);
        PagesPagination pagination = PaginationParser.getPaginationList(page, pagesize, query.getAllRecordNum(),
                                                                        new IPageUrl() {

                                                                            @Override
                                                                            public String parsePageUrl(Object... objs) {
                                                                                return "/company.htm?page="
                                                                                       + (Integer) objs[1];
                                                                            }
                                                                        });

        mav.addObject("list", list);
        mav.addObject("pagination", pagination);
        return mav;
    }

    @RequestMapping(value = "/company/column/{id}.htm")
    public ModelAndView companyColumn(@PathVariable("id")
    Long id, ModelAndView mav) {
        mav.setViewName("cms/1409/company");
        List<TravelLineColumnDO> columnList = siteService.list(id, null);
        if (columnList == null || columnList.size() == 0) {
            return mav;
        }
        List<Long> cIdList = CollectionUtils.getLongValues(columnList, "cId");
        if (cIdList == null || cIdList.size() == 0) {
            return mav;
        }
        PaginationList<TravelCompanyDO> list = companyService.showCompanyPagination(new TravelCompanyQuery(
                                                                                                           cIdList.toArray(new Long[cIdList.size()])),
                                                                                    new DefaultIpageUrl());
        mav.addObject("list", list);
        mav.addObject("pagination", list.getQuery());
        return mav;
    }

    @RequestMapping(value = "/company_view/{id}.htm")
    public ModelAndView company_view(@PathVariable("id")
    Long id, ModelAndView mav) {
        mav.setViewName("cms/1409/company_view");
        if (Argument.isNotPositive(id)) {
            return mav;
        }

        TravelCompanyDO company = companyService.getById(id);
        mav.addObject("company", company);
        mav.addObject("nav", "公司介绍");
        return mav;
    }

    @RequestMapping(value = "/companyDetail/{id}.htm")
    public ModelAndView companyDetail(@PathVariable("id")
    Long id, ModelAndView mav) {
        mav.setViewName("cms/companyDetail");
        if (Argument.isNotPositive(id)) {
            return mav;
        }

        TravelCompanyDO company = companyService.getById(id);
        mav.addObject("company", company);
        return mav;
    }

    @RequestMapping(value = "/company_contact/{id}.htm")
    public ModelAndView company_contact(@PathVariable("id")
    Long id, ModelAndView mav) {
        mav.setViewName("cms/company_view");
        if (Argument.isNotPositive(id)) {
            return mav;
        }

        TravelCompanyDO company = companyService.getById(id);
        mav.addObject("company", company);
        mav.addObject("nav", "联系方式");
        return mav;
    }

    @RequestMapping(value = "/news.htm")
    public ModelAndView news(ModelAndView mav, TravelNewsQuery query, Integer page) {
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        query.setPageSize(20);
        PaginationList<TravelNewsDO> list = cmsService.showNewsPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("newsList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("cms/1409/news");
        return mav;
    }

    @RequestMapping(value = "/news_view.htm")
    public ModelAndView news_view(ModelAndView mav, Long id) {
        TravelNewsDO newsDO = cmsService.getById(id);
        mav.addObject("news", newsDO);
        mav.setViewName("cms/1409/news_view");
        return mav;
    }

    @RequestMapping(value = "/company_line/{id}.htm")
    public ModelAndView line(@PathVariable("id")
    Long id, ModelAndView mav, Integer page, Integer pagesize, TravelLineQuery query) {
        TravelLineQuery.parse(query, null, null, page, pagesize, LineTemplateEnum.Line.getValue());
        query.setcId(id);

        PaginationList<TravelLineDO> list = lineService.listPagination(query, new DefaultIpageUrl());
        List<TravelLineVO> lists = BeanUtils.convert(TravelLineVO.class, list);

        mav.addObject("list", lists);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("cms/1409/line");
        return mav;
    }

    /**
     * 积分商城
     * 
     * @return
     */
    @RequestMapping(value = "/shop.htm")
    public ModelAndView shop(ModelAndView mav, TravelGiftQuery query, Integer page) {
        query.setPageSize(20);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);
        PaginationList<TravelGiftDO> list = integralService.listPagination(query, new IPageUrl() {

            @Override
            public String parsePageUrl(Object... objs) {
                return "/shop.htm?page=" + (Integer) objs[1];
            }
        });
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("giftList", list);
        mav.addObject("pagination", list.getQuery());

        List<TravelGiftClassDO> classDOs = integralService.list(new TravelGiftClassQuery());
        mav.addObject("gClassList", classDOs);
        mav.setViewName("cms/1409/shop");
        return mav;
    }

    @RequestMapping(value = "/shop_view.htm")
    public ModelAndView shop_view(ModelAndView mav, Long id) {
        TravelGiftDO giftDO = integralService.getTravelGiftById(id.intValue());
        mav.addObject("gift", giftDO);
        mav.setViewName("cms/1409/shop_view");
        List<TravelGiftClassDO> classDOs = integralService.list(new TravelGiftClassQuery());
        mav.addObject("gClassList", classDOs);
        return mav;
    }

    @RequestMapping(value = "/shop_cart.htm")
    public ModelAndView shop_cart(ModelAndView mav, Long id) {
        TravelGiftDO giftDO = integralService.getTravelGiftById(id.intValue());
        mav.addObject("gift", giftDO);
        TravelIntegralQuery integralQuery = new TravelIntegralQuery();
        integralQuery.setcId(WebUserTools.getCid());
        integralQuery.setmId(WebUserTools.getMid());

        TravelIntegralDO integralDO = integralService.queryBala(integralQuery);
        mav.addObject("integralDO", integralDO);

        List<TravelGiftClassDO> classDOs = integralService.list(new TravelGiftClassQuery());
        mav.addObject("gClassList", classDOs);
        mav.setViewName("cms/1409/shop_cart");
        return mav;
    }

    @RequestMapping(value = "/addGiftOrder.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addGiftOrder(ModelAndView mav, final TravelGiftOrderDO giftOrderDO) {
        TravelIntegralQuery integralQuery = new TravelIntegralQuery();
        integralQuery.setcId(WebUserTools.getCid());
        integralQuery.setmId(WebUserTools.getMid());
        TravelIntegralDO integralDO = integralService.queryBala(integralQuery);
        if(integralDO == null){
        	return JsonResultUtils.error(giftOrderDO, "你暂时还没有积分，赶快去消费吧！");
        }
        mav.addObject("integralDO", integralDO);
        if (giftOrderDO.getGoIntegralCount() > integralDO.getiBalance()) return JsonResultUtils.error(giftOrderDO,
                                                                                                      "积分不够，请改变兑换数量！");

        giftOrderDO.setcId(WebUserTools.getCid());
        giftOrderDO.setmId(WebUserTools.getMid());
        giftOrderDO.setGoState(0);
        Integer i = integralService.addTravelGiftOrder(giftOrderDO);
        if(i == 0){
        	return JsonResultUtils.error(giftOrderDO, "下单失败!");
        }else{
        	TravelIntegralDO newACC = new TravelIntegralDO();
	        newACC.setcId(WebUserTools.getCid());
	        newACC.setmId(WebUserTools.getMid());
	        newACC.setiSource(IntegralSourceEnum.consumer.value);
	        newACC.setiAddintegral(0 - giftOrderDO.getGoIntegralCount());
	        newACC.setiBalance(integralDO.getiBalance() + newACC.getiAddintegral());
	        newACC.setiFrozen(integralDO.getiFrozen());
	        newACC.setiAltogether(newACC.getiBalance() + newACC.getiFrozen());
	        newACC.setiRemark("兑换积分产品");
	        integralService.addTravelIntegral(newACC);
	        return JsonResultUtils.success(giftOrderDO, "下单成功!");
        }
    }

    /**
     * help 页面
     * 
     * @return
     */
    @RequestMapping(value = "/help/{id}.htm")
    public ModelAndView help(@PathVariable("id")
    Long id, ModelAndView mv) {
        mv.setViewName("cms/1409/help");
        mv.addObject("source", ArticlesSourceEnum.HELP_CENTER.getName());
        if (Argument.isNotPositive(id)) {
            return mv;
        }

        List<TravelArticlesDO> advertisementList = cmsService.list(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.HELP_CENTER.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return mv;
        }
        TravelArticlesDO articlesDO = new TravelArticlesDO();
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesDO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
            if (articles.getaId() == id) {
                articlesDO = articles;
            }
        }

        mv.addObject("nav", navMap);
        mv.addObject("articles", articlesDO);
        return mv;
    }

    /**
     * about 页面
     * 
     * @return
     */
    @RequestMapping(value = "/about/{id}.htm")
    public ModelAndView about(@PathVariable("id")
    Long id, ModelAndView mv) {
        mv.setViewName("cms/1409/about");
        mv.addObject("source", ArticlesSourceEnum.ABOUT_ZUOBIAN.getName());
        if (Argument.isNotPositive(id)) {
            return mv;
        }

        List<TravelArticlesDO> advertisementList = cmsService.list(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.ABOUT_ZUOBIAN.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return mv;
        }
        TravelArticlesDO articlesDO = new TravelArticlesDO();
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesDO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
            if (articles.getaId() == id) {
                articlesDO = articles;
            }
        }

        mv.addObject("nav", navMap);
        mv.addObject("articles", articlesDO);
        return mv;
    }
    /**
     * tourIssue 页面
     * 
     * @return
     */
    @RequestMapping(value = "/tourIssue/{id}.htm")
    public ModelAndView tourIssue(@PathVariable("id")
    Long id, ModelAndView mv) {
        mv.setViewName("cms/1409/tourIssue");
        mv.addObject("source", ArticlesSourceEnum.TOUR_ISSUE.getName());
        if (Argument.isNotPositive(id)) {
            return mv;
        }

        List<TravelArticlesDO> advertisementList = cmsService.list(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.TOUR_ISSUE.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return mv;
        }
        TravelArticlesDO articlesDO = new TravelArticlesDO();
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesDO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
            if (articles.getaId() == id) {
                articlesDO = articles;
            }
        }

        mv.addObject("nav", navMap);
        mv.addObject("articles", articlesDO);
        return mv;
    }
    /**
     * accountIssue 页面
     * 
     * @return
     */
    @RequestMapping(value = "/accountIssue/{id}.htm")
    public ModelAndView accountIssue(@PathVariable("id")
    Long id, ModelAndView mv) {
        mv.setViewName("cms/1409/accountIssue");
        mv.addObject("source", ArticlesSourceEnum.ACCOUNT_ISSUE.getName());
        if (Argument.isNotPositive(id)) {
            return mv;
        }

        List<TravelArticlesDO> advertisementList = cmsService.list(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.ACCOUNT_ISSUE.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return mv;
        }
        TravelArticlesDO articlesDO = new TravelArticlesDO();
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesDO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
            if (articles.getaId() == id) {
                articlesDO = articles;
            }
        }

        mv.addObject("nav", navMap);
        mv.addObject("articles", articlesDO);
        return mv;
    }
    /**
     * orderGuide 页面
     * 
     * @return
     */
    @RequestMapping(value = "/orderGuide/{id}.htm")
    public ModelAndView orderGuide(@PathVariable("id")
    Long id, ModelAndView mv) {
        mv.setViewName("cms/1409/orderGuide");
        mv.addObject("source", ArticlesSourceEnum.ORDER_GUIDE.getName());
        if (Argument.isNotPositive(id)) {
            return mv;
        }

        List<TravelArticlesDO> advertisementList = cmsService.list(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.ORDER_GUIDE.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return mv;
        }
        TravelArticlesDO articlesDO = new TravelArticlesDO();
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesDO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
            if (articles.getaId() == id) {
                articlesDO = articles;
            }
        }

        mv.addObject("nav", navMap);
        mv.addObject("articles", articlesDO);
        return mv;
    }

    /**
     * detail 页面(系统告示,新闻资讯)
     * 
     * @return
     */
    @RequestMapping(value = "/detail/{id}.htm")
    public ModelAndView detail(@PathVariable("id")
    String id, ModelAndView mv) {
        mv.setViewName("cms/about");
        if (StringUtils.isNotEmpty(id)) {
            return mv;
        }
        // cmsService.getById(id);
        return mv;
    }

    /**
     * 根据条件查询公司
     * 
     * @return
     */
    @RequestMapping(value = "/queryCompanyByConditions.htm", produces = "application/json")
    @ResponseBody
    public JsonResult queryCompanyByConditions(TravelCompanyQuery query, Integer limit) {
        List<TravelCompanyDO> list = companyService.listQuery(query);

        List<Map<String, ?>> mapList = CollectionUtils.toMapList(list, "cId", "cName", "cSpell");
        // StringBuilder sb = new StringBuilder();
        String cond = query.getQ() == null ? StringUtils.EMPTY : query.getQ();
        cond = cond.toLowerCase();
        // String temp;
        int maxSize = getLimit(limit);
        int size = 0;
        List<Map<String, ?>> result = new LinkedList<Map<String, ?>>();
        String property = cond.matches("[a-zA-Z]+") ? "cSpell" : "cName";
        for (Map<String, ?> map : mapList) {
            Object cName = null;
            for (Entry<String, ?> entry : map.entrySet()) {
                if (StringUtils.equals(entry.getKey(), property)) {
                    cName = entry.getValue();
                }
            }
            if (cond.matches("[a-zA-Z]+") ? StringUtils.startsWith((String) cName, cond) : StringUtils.containsIgnoreCase((String) cName,
                                                                                                                          cond)) {
                result.add(map);
                size++;
                if (size > maxSize) {
                    break;
                }
            }
        }
        return JsonResultUtils.success(result);
    }

    /**
     * 得到条目个数
     * 
     * @param limit
     * @return
     */
    private int getLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return DEFAULT_LIMIT;
        }
        return limit < MAX_LIMIT ? limit : MAX_LIMIT;
    }
}
