/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.tools;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.zb.app.biz.cons.ArticlesSourceEnum;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.biz.service.interfaces.CmsService;
import com.zb.app.web.vo.TravelArticlesVO;

/**
 * @author ZhouZhong
 */
public class ArticleCacheTools {

    @Autowired
    protected CmsService cmsService;
    
    //关于左边
    public Map<Long, String> getAboutZuobian() {
        List<TravelArticlesVO> advertisementList = cmsService.listQueryVO(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.ABOUT_ZUOBIAN.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return null;
        }
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesVO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
        }
        return navMap;
    }
    
    //帮助中心
    public Map<Long, String> getHelpCenter() {
        List<TravelArticlesVO> advertisementList = cmsService.listQueryVO(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.HELP_CENTER.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return null;
        }
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesVO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
        }
        return navMap;
    }
    
    //组团社问题
    public Map<Long, String> getTourIssue() {
        List<TravelArticlesVO> advertisementList = cmsService.listQueryVO(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.TOUR_ISSUE.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return null;
        }
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesVO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
        }
        return navMap;
    }
    
    //批发商问题
    public Map<Long, String> getAccountIssue() {
        List<TravelArticlesVO> advertisementList = cmsService.listQueryVO(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.ACCOUNT_ISSUE.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return null;
        }
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesVO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
        }
        return navMap;
    }
    
    //订购指南
    public Map<Long, String> getOrderGuide() {
        List<TravelArticlesVO> advertisementList = cmsService.listQueryVO(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.ORDER_GUIDE.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return null;
        }
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesVO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
        }
        return navMap;
    }
}
