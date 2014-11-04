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
import com.zb.app.biz.domain.TravelArticlesDO;
import com.zb.app.biz.query.TravelArticlesQuery;
import com.zb.app.biz.service.interfaces.CmsService;

/**
 * @author ZhouZhong
 */
public class ArticleCacheTools {

    @Autowired
    protected CmsService cmsService;

    public Map<Long, String> getAboutZuobian() {
        List<TravelArticlesDO> advertisementList = cmsService.list(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.ABOUT_ZUOBIAN.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return null;
        }
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesDO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
        }
        return navMap;
    }

    public Map<Long, String> getHelpCenter() {
        List<TravelArticlesDO> advertisementList = cmsService.list(new TravelArticlesQuery(
                                                                                           ArticlesSourceEnum.HELP_CENTER.getValue()));
        if (advertisementList == null || advertisementList.size() == 0) {
            return null;
        }
        Map<Long, String> navMap = new LinkedHashMap<Long, String>();
        for (TravelArticlesDO articles : advertisementList) {
            navMap.put(articles.getaId(), articles.getTitle());
        }
        return navMap;
    }
}
