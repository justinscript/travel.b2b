/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.site;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.app.biz.cons.ColumnCatEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.domain.CompanyColumnDO;
import com.zb.app.common.cookie.CookieKeyEnum;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.SiteCacheTools;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.SiteCoreVO;
import com.zb.app.web.vo.SiteFullVO;

/**
 * 全局站点管理
 * 
 * @author zxc Jul 25, 2014 5:30:34 PM
 */
@Controller
public class SiteController extends BaseController {

    /**
     * 修改默认站点
     * 
     * @param siteId
     * @return
     */
    @RequestMapping(value = "/modifySite.htm", produces = "application/json")
    @ResponseBody
    public JsonResult modifySite(Long siteId) {
        if (Argument.isNotPositive(siteId)) {
            return JsonResultUtils.error("传入参数错误!");
        }
        cookieManager.set(CookieKeyEnum.site_id, siteId + StringUtils.EMPTY);
        cookieManager.set(CookieKeyEnum.chugang_id, StringUtils.EMPTY);
        return JsonResultUtils.success();
    }

    /**
     * 修改默认出港地
     * 
     * @param chugangId
     * @return
     */
    @RequestMapping(value = "/modifyChugang.htm", produces = "application/json")
    @ResponseBody
    public JsonResult modifyChugang(Long chugangId) {
        if (Argument.isNotPositive(chugangId)) {
            return JsonResultUtils.error("传入参数错误!");
        }
        cookieManager.set(CookieKeyEnum.chugang_id, chugangId + StringUtils.EMPTY);
        return JsonResultUtils.success();
    }

    /**
     * 查询当前专线下所有已绑定的公司
     * 
     * @param zId
     * @return
     */
    @RequestMapping(value = "/getCompany.htm", produces = "application/json")
    @ResponseBody
    public JsonResult getCompanyByzId(Long zId) {
        if (Argument.isNotPositive(zId)) {
            return JsonResultUtils.error("传入参数错误!");
        }
        List<CompanyColumnDO> companyColumnList = siteService.getCompanyByzId(zId);
        if (companyColumnList == null || companyColumnList.size() == 0) {
            return JsonResultUtils.error("没有查询到数据!");
        }
        return JsonResultUtils.success(companyColumnList);
    }

    /**
     * 查询所有站点及其出港点
     * 
     * @return
     */
    @RequestMapping(value = "/allSite.htm", produces = "application/json")
    @ResponseBody
    public JsonResult allSite() {
        SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
        List<SiteCoreVO> siteCorevoList = siteCacheTools.getSiteCoreList();
        if (siteCorevoList == null || siteCorevoList.size() == 0) {
            return JsonResultUtils.error("没有查询到数据!");
        }
        return JsonResultUtils.success(siteCorevoList);
    }

    @RequestMapping(value = "/fullSite.htm", produces = "application/json")
    @ResponseBody
    public JsonResult fullSite() {
        SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
        List<SiteFullVO> siteCorevoList = siteCacheTools.getAllSite();
        if (siteCorevoList == null || siteCorevoList.size() == 0) {
            return JsonResultUtils.error("没有查询到数据!");
        }
        return JsonResultUtils.success(siteCorevoList);
    }

    /**
     * 当前账号，公司的站点，出港地，专线
     * 
     * @return
     */
    @RequestMapping(value = "/currentSite.htm", produces = "application/json")
    @ResponseBody
    public JsonResult currentSite(Integer cat) {
        SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
        List<SiteFullVO> siteFullvoList = null;
        if (WebUserTools.getCompanyType() != null
            && WebUserTools.getCompanyType().getValue() == CompanyTypeEnum.MANAGE.getValue()) {
            if (cat == null) {
                siteFullvoList = siteCacheTools.getAllSite();
            } else {
                ColumnCatEnum catEnum = ColumnCatEnum.getAction(cat);
                if (catEnum == null) {
                    siteFullvoList = siteCacheTools.getAllSite();
                } else {
                    siteFullvoList = siteCacheTools.getAllSite4Cat(cat);
                }
            }
        } else {
            siteFullvoList = siteCacheTools.getCurrentSite(WebUserTools.getCid());
        }
        if (siteFullvoList == null || siteFullvoList.size() == 0) {
            return JsonResultUtils.error("没有查询到数据!");
        }
        return JsonResultUtils.success(siteFullvoList);
    }
}
