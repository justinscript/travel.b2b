/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.webuser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.MemberTypeEnum;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.service.interfaces.CompanyService;
import com.zb.app.biz.service.interfaces.MemberService;
import com.zb.app.common.cookie.CookieKeyEnum;
import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.utilities.CoreUtilities;
import com.zb.app.common.pipeline.PipelineResult;
import com.zb.app.common.pipeline.value.BaseWebUserBuilderValve;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.Result;
import com.zb.app.common.util.HttpUtil;
import com.zb.app.common.util.IPTools;
import com.zb.app.common.util.NumberParser;
import com.zb.app.web.tools.InvokeTypeTools;
import com.zb.app.web.tools.SiteCacheTools;
import com.zb.app.web.vo.ChufaCoreVO;

/**
 * www.zuobian.com 构建webuser的valve
 * 
 * @author zxc Jul 8, 2014 10:37:50 AM
 */
public class ZuobianWebUserBuilderValve extends BaseWebUserBuilderValve<ZuobianWebUser> {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private MemberService  memberService;

    @Override
    protected ZuobianWebUser createWebUser(HttpServletRequest request, CookieManager cookieManager) {
        ZuobianWebUser webUser = ZuobianWebUserBuilder.create(cookieManager);
        if (webUser != null && webUser.hasLogin() && webUser.getcId() != null
            && StringUtils.isEmpty(webUser.getCompanyName())) {
            TravelCompanyDO tc = companyService.getById(webUser.getcId());
            if (tc != null) {
                webUser.setCompanyName(tc.getcName());
            }
        }
        if (webUser != null && webUser.hasLogin() && webUser.getmId() != null && StringUtils.isEmpty(webUser.getRole())) {
            TravelMemberDO tm = memberService.getById(webUser.getmId());
            if (tm != null) {
                webUser.setRole(tm.getmRole());
                webUser.setmType(MemberTypeEnum.getEnum(tm.getmType()));
            }
        }

        try {
            parseSiteAndIP(request, cookieManager, webUser);
        } catch (Exception e) {

        }
        return webUser;
    }

    @Override
    protected PipelineResult getToLogin(HttpServletRequest request, HttpServletResponse response, String uri)
                                                                                                             throws UnsupportedEncodingException,
                                                                                                             IOException {
        String url = loginUrl;
        if (InvokeTypeTools.isAjax(request)) {
            String needLogin = JsonResultUtils.getNeedLoginJson();
            response.getOutputStream().write(needLogin.getBytes("utf-8"));
            return PipelineResult.gotoAfterCompletion("gotoLogin", null);
        }
        if (this.pathMatcher.match("/account/**", uri)) {
            url = homeUrl + "?returnurl=" + loginUrl;
        }
        if (this.pathMatcher.match("/zbmanlogin/**", uri)) {
            url = homeUrl + "?returnurl=" + "/zbmanlogin/login.htm";
        }
        if (this.pathMatcher.match("/tour/**", uri)) {
            url = homeUrl + "?returnurl=" + "/index.htm";
        }
        return PipelineResult.gotoAfterCompletion("gotoLogin", url);
    }

    @Override
    protected Result judgePermission(HttpServletRequest request, HttpServletResponse response, String uri,
                                     ZuobianWebUser webUser) throws Exception, IOException {
        CompanyTypeEnum type = webUser.getType();
        String[] authUrls = type.getAuthUrl();
        for (String authUrl : authUrls) {
            if (this.pathMatcher.match(authUrl, uri)) {
                return Result.success();
            }
        }
        if (InvokeTypeTools.isAjax(request)) {
            String needLogin = JsonResultUtils.getNeedLoginJson();
            response.getOutputStream().write(needLogin.getBytes("utf-8"));
            return Result.failed(null);
        }
        return Result.failed(StringUtils.EMPTY, homeUrl + "?returnurl=" + type.getLoginUrl());
    }

    @Override
    protected boolean judgeAccessTime(CookieManager cookieManager) {
        long maxLastAccessTime = MAX_LAST_ACCESS_TIME;
        long lastLoginTime = NumberParser.parseLong(cookieManager.get(CookieKeyEnum.last_login_time), 0);
        // 操作大于1小时就自动退出
        if (lastLoginTime <= 0 || System.currentTimeMillis() - lastLoginTime > maxLastAccessTime) {
            ZuobianWebUserBuilder.loginOut(cookieManager);
            return false;
        }
        // 更新用户的登录时间
        cookieManager.set(CookieKeyEnum.last_login_time, StringUtils.EMPTY + System.currentTimeMillis());
        return true;
    }

    private void parseSiteAndIP(HttpServletRequest request, CookieManager cookieManager, ZuobianWebUser webUser) {
        SiteCacheTools siteCacheTools = (SiteCacheTools) SpringContextAware.getBean("siteCacheTools");
        List<ChufaCoreVO> siteList = siteCacheTools.getSiteCoreCityList();
        // Long siteId = NumberUtils.toLong(cookieManager.get(CookieKeyEnum.site_id));
        Long chugangId = NumberUtils.toLong(cookieManager.get(CookieKeyEnum.chugang_id));
        if (Argument.isNotPositive(chugangId)) {
            String ip = HttpUtil.getExternalIP(request);
            if (HttpUtil.isIntranetIp(ip)) {
                ip = CoreUtilities.getIPAddress();
            }
            Set<String> address = null;
            try {
                address = IPTools.getInstance().getCity(ip);
            } catch (Exception e) {
                logger.error("parse IP error,remote ip:{}", ip);
            }
            if (Argument.isNotEmpty(address)) {
                logger.debug("remote ip:{},address:{}", ip, address);
                for (ChufaCoreVO siteOnly : siteList) {
                    if (address.contains(siteOnly.getcName())) {
                        chugangId = siteOnly.getcId();
                        break;
                    }
                }
            }
            if (Argument.isNotPositive(chugangId) && !siteList.isEmpty()) {
                Collections.sort(siteList, new Comparator<ChufaCoreVO>() {

                    @Override
                    public int compare(ChufaCoreVO o1, ChufaCoreVO o2) {
                        if (o1.getcSort() == null) {
                            return -1;
                        }
                        if (o2.getcSort() == null) {
                            return 1;
                        }
                        return o1.getcSort() - o1.getcSort();
                    }
                });
                chugangId = siteList.get(0).getcId();
            }
            if (Argument.isNotPositive(chugangId)) {
                chugangId = 0l;
            }
            cookieManager.set(CookieKeyEnum.chugang_id, chugangId + StringUtils.EMPTY);
        }
        // if (Argument.isNotPositive(chugangId)) {
        // SiteCoreVO siteCoreVO = new SiteCoreVO();
        // for (SiteCoreVO siteOnly : siteList) {
        // if (NumberParser.isEqual(siteOnly.getsId(), siteId)) {
        // siteCoreVO = siteOnly;
        // }
        // }
        // List<ChufaCoreVO> chufaList = siteCoreVO.getChufaList();
        // if (chufaList == null || chufaList.size() == 0) {
        // logger.error("chufa is null,site ID:{}", siteId);
        // return;
        // }
        // Collections.sort(chufaList, new Comparator<ChufaCoreVO>() {
        //
        // @Override
        // public int compare(ChufaCoreVO o1, ChufaCoreVO o2) {
        // if (o1.getcSort() == null) {
        // return -1;
        // }
        // if (o2.getcSort() == null) {
        // return 1;
        // }
        // return o1.getcSort() - o1.getcSort();
        // }
        // });
        // chugangId = chufaList.get(0).getcId();
        // cookieManager.set(CookieKeyEnum.chugang_id, chugangId + StringUtils.EMPTY);
        // }
        webUser.setSiteId(0l);
        webUser.setChugangId(chugangId);
    }
}
