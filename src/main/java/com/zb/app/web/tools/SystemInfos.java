/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.tools;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.core.utilities.CoreUtilities;

/**
 * 记录一些系统的属性
 * 
 * @author zxc Jul 23, 2014 2:24:51 PM
 */
public class SystemInfos {

    // 记录IP地址
    private static final String ipAddress;
    // 记录HostName
    private static final String hostName;

    private static final String dbInfo;
    private static final String user;

    static {
        hostName = CoreUtilities.getHostName();
        ipAddress = CoreUtilities.getIPAddress();
        BasicDataSource dataSource = (BasicDataSource) SpringContextAware.getBean("dataSource");
        String dbUrl = dataSource.getUrl();
        dbInfo = getDBIP(dbUrl);
        user = dataSource.getUsername();
    }

    private static String getDBIP(String dburl) {
        if (dburl == null || dburl.length() == 0) {
            return StringUtils.EMPTY;
        }
        int index = dburl.indexOf('@');
        if (index < 0) {
            return StringUtils.EMPTY;
        }
        String t = dburl.substring(index + 1);
        if (t == null || t.length() == 0) {
            return StringUtils.EMPTY;
        }
        index = t.indexOf(':');
        return t.substring(0, index);
    }

    public static String getIpaddress() {
        return ipAddress;
    }

    public static String getHostname() {
        return hostName;
    }

    public static String getDbinfo() {
        return dbInfo;
    }

    public static String getUser() {
        return user;
    }
}
