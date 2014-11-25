/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.manage;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.PhotoTypeEnum;
import com.zb.app.biz.cons.TravelNewsTypeEnum;
import com.zb.app.biz.domain.TravelAdvertisementDO;
import com.zb.app.biz.domain.TravelArticlesDO;
import com.zb.app.biz.domain.TravelLabelCategoryDO;
import com.zb.app.biz.domain.TravelNewsDO;
import com.zb.app.biz.query.TravelAdvertisementQuery;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.biz.query.TravelLabelCategoryQuery;
import com.zb.app.biz.query.TravelNewsQuery;
import com.zb.app.biz.service.impl.FileServiceImpl.IFileHandle;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.DefaultIpageUrl;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.result.Result;
import com.zb.app.common.util.ObjectUtils;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.ADLinkVO;

/**
 * 公司后台系统 新闻管理 CMS管理
 * 
 * @author zxc Jun 16, 2014 3:46:35 PM
 */
@Controller
@RequestMapping("/zbmanlogin")
public class ManageCmsController extends BaseController {

    // /////
    //
    // ####################################################Manage后台--旅游资讯,网站公告,用户资讯管理###################################################
    //
    // /////

    // 旅游资讯,网站公告,用户资讯
    @RequestMapping(value = "/info/{newsType}.htm")
    public ModelAndView news(ModelAndView mav, @PathVariable("newsType")
    String newsType) {
        mav.setViewName("/manage/news/index");
        if(newsType == null){
        	return mav;
        }
        TravelNewsTypeEnum typeEnum = TravelNewsTypeEnum.getAction(newsType);
        if (typeEnum == null) {
            return mav;
        }
        PaginationList<TravelNewsDO> list = cmsService.showNewsPagination(new TravelNewsQuery(typeEnum), new DefaultIpageUrl());
        mav.addObject("list", list);
        return mav;
    }

    // 新闻添加页面
    @RequestMapping(value = "/newsadd.htm")
    public String newadd() {
        return "/manage/news/add";
    }

    // 新闻添加
    @RequestMapping(value = "/saveNews.htm", produces = "application/json")
    @ResponseBody
    public JsonResult saveNews(@Valid
    TravelNewsDO news, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        ObjectUtils.trim(news);
        news.setnType(TravelNewsTypeEnum.WEB_NEWS.value);
        if (news.getnId() != null && news.getnId() > 0) {
            cmsService.updateById(news);
            return JsonResultUtils.success("修改成功!");
        }
        PaginationList<TravelNewsDO> list = cmsService.showNewsPagination(new TravelNewsQuery(
                                                                                              news.getnTitle(),
                                                                                              TravelNewsTypeEnum.WEB_NEWS),
                                                                          new DefaultIpageUrl());
        if (list != null && list.size() > 0) {
            return JsonResultUtils.error("新闻标题已经存在!");
        }
        cmsService.addTravelNews(news);
        return JsonResultUtils.success();
    }

    // 新闻修改
    @RequestMapping(value = "/newsedit.htm")
    public ModelAndView newsedit(ModelAndView mav, Long id) {
        mav.setViewName("/manage/news/add");
        if (id == null || id == 0) {
            return mav;
        }
        TravelNewsDO newsDO = cmsService.getById(id);
        mav.addObject("news", newsDO);
        return mav;
    }

    /**
     * 新闻删除
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/delNews.htm", produces = "application/json")
    @ResponseBody
    public JsonResult newsdel(Long id) {
        if (id == null || id == 0) {
            return JsonResultUtils.error("id不能为空!");
        }
        boolean isDel = cmsService.deleteById(id);
        return isDel ? JsonResultUtils.success("删除成功!") : JsonResultUtils.error("删除失败!");
    }

    // /////
    //
    // ####################################################Manage后台--文章管理###################################################
    //
    // /////

    // 栏目查询
    @RequestMapping(value = "/page.htm")
    public ModelAndView page(ModelAndView mav) {
        mav.setViewName("/manage/page/index");
        List<TravelArticlesDO> list = cmsService.list(new TravelArticlesQuery());

        mav.addObject("list", list);
        return mav;
    }

    // 栏目添加
    @RequestMapping(value = "/pageadd.htm")
    public String pageadd() {
        return "/manage/page/add";
    }

    // 栏目修改
    @RequestMapping(value = "/pageedit.htm")
    public ModelAndView pageedit(ModelAndView mav, Long id) {
        mav.setViewName("/manage/page/add");
        if (id == null || id == 0) {
            return mav;
        }
        TravelArticlesDO page = cmsService.getArticlesById(id);
        mav.addObject("page", page);
        return mav;
    }

    @RequestMapping(value = "/savePage.htm", produces = "application/json")
    @ResponseBody
    public JsonResult savePage(@Valid
    TravelArticlesDO page, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        ObjectUtils.trim(page);
        if (page.getaId() != null && page.getaId() > 0) {
            cmsService.updateById(page);
            return JsonResultUtils.success("修改成功!");
        }
        List<TravelArticlesDO> list = cmsService.list(new TravelArticlesQuery(page.getTitle()));
        if (list != null && list.size() > 0) {
            return JsonResultUtils.error("文章标题已经存在!");
        }
        cmsService.addArticles(page);
        return JsonResultUtils.success("增加成功");
    }

    @RequestMapping(value = "/delPage.htm", produces = "application/json")
    @ResponseBody
    public JsonResult delPage(Long id) {
        if (id == null || id == 0) {
            return JsonResultUtils.error("id不能为空!");
        }
        boolean isDel = cmsService.realDelArticles(id);
        return isDel ? JsonResultUtils.success("删除成功!") : JsonResultUtils.error("删除失败!");
    }

    // /////
    //
    // ####################################################Manage后台--广告管理###################################################
    //
    // /////

    // 广告管理
    @RequestMapping(value = "/link.htm")
    public ModelAndView link(ModelAndView mav) {
        mav.setViewName("/manage/page/link");
        List<TravelAdvertisementDO> list = cmsService.list(new TravelAdvertisementQuery());

        mav.addObject("list", list);
        return mav;
    }

    // 广告添加
    @RequestMapping(value = "/linkadd.htm")
    public String linkadd() {
        return "/manage/page/linkadd";
    }

    // 广告修改
    @RequestMapping(value = "/linkedit.htm")
    public ModelAndView linkedit(ModelAndView mav, Long id) {
        mav.setViewName("/manage/page/linkadd");
        if (id == null || id == 0) {
            return mav;
        }
        TravelAdvertisementDO link = cmsService.getAdvertisementById(id);
        if (link == null) {
            return mav;
        }
        ADLinkVO adLink = new ADLinkVO();
        BeanUtils.copyProperties(adLink, link);
        if (StringUtils.isNotEmpty(link.getSite()) && link.getSite().contains("|")) {
            String[] tmp = StringUtils.split(link.getSite(), "|");
            if (tmp != null && tmp.length == 2) {
                adLink.setProvince(tmp[0]);
                adLink.setCity(tmp[1]);
            }
        }
        mav.addObject("link", adLink);
        return mav;
    }

    @RequestMapping(value = "/saveLink.htm", produces = "application/json")
    @ResponseBody
    public JsonResult saveLink(@Valid
    ADLinkVO link, BindingResult result) {
        Result rs = showErrors(result);
        if (rs.isFailed()) {
            return JsonResultUtils.error(rs.getMessage());
        }
        ObjectUtils.trim(link);
        if (Argument.isNotPositive(link.getChugangId())) {
            return JsonResultUtils.error("站点ID不能为空!");
        }

        TravelAdvertisementDO advertisement = new TravelAdvertisementDO();
        BeanUtils.copyProperties(advertisement, link);
        advertisement.setSiteId(link.getChugangId());

        if (StringUtils.isNotEmpty(link.getPic()) && link.getPic().contains("/tmp/")) {
            Result rusult = fileService.saveFileByPath(link.getPic(), new IFileHandle() {

                @Override
                public String parse(String prefix, String suffix) {
                    return prefix + WebUserTools.getCid() + "/" + PhotoTypeEnum.ADVERTISEMENT.getName() + "/" + suffix;
                }
            });
            advertisement.setPic((String) rusult.getData());
        }

        if (link.getAdId() != null && link.getAdId() > 0) {
            cmsService.updateById(advertisement);
            return JsonResultUtils.success("修改成功!");
        }
        List<TravelAdvertisementDO> list = cmsService.list(new TravelAdvertisementQuery(link.getTitle()));
        if (list != null && list.size() > 0) {
            return JsonResultUtils.error("广告标题已经存在!");
        }
        cmsService.addTravelAdvertisement(advertisement);
        return JsonResultUtils.success("增加成功");
    }

    @RequestMapping(value = "/delLink.htm", produces = "application/json")
    @ResponseBody
    public JsonResult delLink(Long id) {
        if (id == null || id == 0) {
            return JsonResultUtils.error("id不能为空!");
        }
        TravelAdvertisementDO advertisement = cmsService.getAdvertisementById(id);
        if (advertisement == null) {
            return JsonResultUtils.error("id参数错误!");
        }
        if (StringUtils.isNotEmpty(advertisement.getPic()) && StringUtils.contains(advertisement.getPic(), "/static/")) {
            fileService.delFileByPath(advertisement.getPic());
        }
        boolean isDel = cmsService.realDelAdvertisement(id);
        return isDel ? JsonResultUtils.success("删除成功!") : JsonResultUtils.error("删除失败!");
    }

    // 图片墙管理
    @RequestMapping(value = "/photo.htm")
    public String photo() {
        return "/manage/page/photo";
    }

    // 图片墙添加
    @RequestMapping(value = "/photoadd.htm")
    public String photoadd() {
        return "/manage/page/photoadd";
    }

    // /////
    //
    // ####################################################Manage后台--标签管理###################################################
    //
    // /////

    // 标签添加
    @RequestMapping(value = "/site/tagadd.htm")
    public String tagadd() {
        return "/manage/site/tagadd";
    }

    @RequestMapping(value = "/site/savetag.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult savetag(TravelLabelCategoryDO categoryDO, Integer madd) {
        if (categoryDO.getLcId() != null && categoryDO.getLcId() > 0) {
            Boolean b = cmsService.updateTravelLabelCategory(categoryDO);
            return b ? JsonResultUtils.success(categoryDO, "修改成功!") : JsonResultUtils.error(categoryDO, "修改失败!");
        }
        if (madd == null || madd != 0) {
            categoryDO.setParentId(0L);
        }
        Integer i = cmsService.insertTravelLabelCategory(categoryDO);
        return i == 0 ? JsonResultUtils.error(categoryDO, "添加失败!") : JsonResultUtils.success(categoryDO, "添加成功!");
    }

    @RequestMapping(value = "/site/tag.htm")
    public ModelAndView tag(ModelAndView mav, TravelLabelCategoryQuery query, Integer page) {
        query.setPageSize(1000);
        query.setNowPageIndex(Argument.isNotPositive(page) ? 0 : page - 1);

        PaginationList<TravelLabelCategoryDO> list = cmsService.listPagination(query, new DefaultIpageUrl());

        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.addObject("labelList", list);
        mav.addObject("pagination", list.getQuery());
        mav.setViewName("/manage/site/tag");
        return mav;
    }

    @RequestMapping(value = "/site/tagmadd.htm")
    public ModelAndView tagmadd(ModelAndView mav, Long id) {
        TravelLabelCategoryDO categoryDO = cmsService.getTravelLabelCategoryById(id);
        mav.addObject("categoryDO", categoryDO);
        mav.setViewName("/manage/site/tagmadd");
        return mav;
    }

    @RequestMapping(value = "/site/ljUpdate.htm")
    public ModelAndView ljUpdate(ModelAndView mav, Long id) {
        TravelLabelCategoryDO categoryDO = cmsService.getTravelLabelCategoryById(id);
        mav.addObject("categoryDO", categoryDO);
        mav.addObject("type", "update");
        mav.setViewName("/manage/site/tagadd");
        return mav;
    }

    @RequestMapping(value = "/site/deletetag.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deletetag(Long id) {
        Boolean b = cmsService.deleteTravelLabelCategory(id);
        return b ? JsonResultUtils.success(id, "删除成功!") : JsonResultUtils.error(id, "删除失败!");
    }
}
