/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.tools;

import org.apache.commons.lang.StringUtils;

import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.MemberTypeEnum;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.util.DateViewTools;
import com.zb.app.web.webuser.ZuobianWebUser;

/**
 * @author zxc Jul 8, 2014 11:17:48 AM
 */
public class WebUserTools {

    public static ZuobianWebUser current() {
        return ZuobianWebUser.getCurrentUser();
    }

    public static String getNick() {
        return current() == null ? null : current().getUserName();
    }

    public static String getCompanyName() {
        return current() == null ? null : current().getCompanyName();
    }

    public static CompanyTypeEnum getCompanyType() {
        return current() == null ? null : current().getType();
    }
    
    public static MemberTypeEnum getMemberType() {
        return current() == null ? null : current().getmType();
    }

    public static Long getCid() {
        return current() == null ? null : current().getcId();
    }

    public static Long getMid() {
        return current() == null ? null : current().getmId();
    }

    public static Long getSiteId() {
        return current() == null ? null : current().getSiteId();
    }

    public static String getSiteName() {
        return current() == null ? StringUtils.EMPTY : current().getSiteName();
    }

    public static Long getChugangId() {
        return current() == null ? null : current().getChugangId();
    }

    public static String getChugangName() {
        return current() == null ? StringUtils.EMPTY : current().getChugangName();
    }

    public static String getLastLogin() {
        return current() == null ? null : DateViewTools.format(current().getLastLogin(), "yyyy-MM-dd HH:mm");
    }

    public static boolean hasLogin() {
        return current() == null ? false : current().hasLogin();
    }

    public static boolean hasPermission(String right) {
        if (StringUtils.isEmpty(right)) {
            return false;
        }
        if (current() == null) {
            return false;
        }
        if (MemberTypeEnum.isSupper(current().getmType())) {
            return true;
        }
        return current().getRightSet().contains(Right.getAction(right));
    }

    public static boolean hasPermission(Integer right) {
        if (Argument.isNotPositive(right)) {
            return false;
        }
        if (current() == null) {
            return false;
        }
        if (MemberTypeEnum.isSupper(current().getmType())) {
            return true;
        }
        return current().getRightSet().contains(Right.getAction(right));
    }

    public static Object getPermissionValue(String value) {
        return null;
    }

    @Override
    public String toString() {
        return current() == null ? StringUtils.EMPTY : current().toString();
    }
}
