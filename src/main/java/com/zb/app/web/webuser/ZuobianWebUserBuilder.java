/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.webuser;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.common.cookie.CookieKeyEnum;
import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.cookie.parser.CookieIdBuilder;

/**
 * @author zxc Jul 1, 2014 4:23:34 PM
 */
public class ZuobianWebUserBuilder {

    /**
     * 构建webUser对象
     * 
     * @param cookieManager
     * @return
     */
    public static ZuobianWebUser create(CookieManager cookieManager) {
        ZuobianWebUser webUser = new ZuobianWebUser();

        String userName = cookieManager.get(CookieKeyEnum.member_nickname);
        Long mId = NumberUtils.toLong(cookieManager.get(CookieKeyEnum.member_id));
        Long cId = NumberUtils.toLong(cookieManager.get(CookieKeyEnum.member_company_id));
        Integer type = NumberUtils.toInt(cookieManager.get(CookieKeyEnum.member_type));
        Date lastLogin = new Date(NumberUtils.toLong(cookieManager.get(CookieKeyEnum.last_login_time)));

        webUser.setUserName(userName);
        webUser.setmId(mId);
        webUser.setcId(cId);
        webUser.setType(CompanyTypeEnum.getEnum(type));
        webUser.setLastLogin(lastLogin);

        boolean hasLogin = StringUtils.isNotBlank(userName) && mId != null && mId != 0l && cId != null && cId != 0l
                           && type != null && type != 0;
        webUser.setHasLogin(hasLogin);

        String cookieId = CookieIdBuilder.getCookieId(cookieManager);
        if (cookieId == null) {
            webUser.setFirstAccess(true);
            cookieId = CookieIdBuilder.createCookieId(cookieManager);
        } else {
            webUser.setFirstAccess(false);
        }
        webUser.setCookieId(cookieId);

        ZuobianWebUser.setCurrentUser(webUser);
        return ZuobianWebUser.getCurrentUser();
    }

    /**
     * 登陆成功设置cookie.(会先登出，然后重新设置Cookie）
     * 
     * @param cookieManager
     * @param date
     * @param userName
     */
    public static void loginSuccess(CookieManager cookieManager, String userName, Long mid, Long cid, Integer type) {
        loginOut(cookieManager);
        cookieManager.set(CookieKeyEnum.member_nickname, userName);
        cookieManager.set(CookieKeyEnum.member_id, StringUtils.EMPTY + mid);
        cookieManager.set(CookieKeyEnum.member_company_id, StringUtils.EMPTY + cid);
        cookieManager.set(CookieKeyEnum.member_type, StringUtils.EMPTY + type);
        cookieManager.set(CookieKeyEnum.last_login_time, StringUtils.EMPTY + System.currentTimeMillis());
    }

    /**
     * 登出
     */
    public static void loginOut(CookieManager cookieManager) {
        _loginOut(cookieManager, CookieKeyEnum.member_nickname, CookieKeyEnum.member_id,
                  CookieKeyEnum.member_company_id, CookieKeyEnum.member_type, CookieKeyEnum.last_login_time);
    }

    private static void _loginOut(CookieManager cookieManager, CookieKeyEnum... keys) {
        for (CookieKeyEnum key : keys) {
            cookieManager.set(key, StringUtils.EMPTY);
        }
    }
}
