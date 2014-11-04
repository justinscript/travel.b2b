/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc Jul 8, 2014 10:28:47 AM
 */
public class HttpUtil {

    private static final String[] INTRANET_IPS_PREFIX = { "192.168.1.", "192.168.0." };
    private static final String[] INTRANET_IPS        = { "127.0.0.1", "localhost" };

    /**
     * 返回用户的IP地址
     * 
     * @param request
     * @return
     */
    public static String toIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取访问者的ip<br>
     * 此ip是从请求头中取出（x-forwarded-for），如果没取到则取remoteAddr
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        // X-Real-IP
        // Proxy-Client-IP
        // WL-Proxy-Client-IP
        return request.getRemoteAddr();
    }

    /**
     * 检查此请求是否是内网请求的ip
     * 
     * @param request
     * @return
     */
    public static boolean isIntranetIp(HttpServletRequest request) {
        return isIntranetIp(getIpAddr(request));
    }

    /**
     * 检查此ip是否是内网ip
     * 
     * @param ip
     * @return
     */
    public static boolean isIntranetIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return true;
        }
        for (String temp : INTRANET_IPS) {
            if (temp.equals(ip)) {
                return true;
            }
        }
        for (String temp : INTRANET_IPS_PREFIX) {
            if (ip.startsWith(temp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取外网的IP<br/>
     * IP格式:101.226.52.82, 192.168.1.102
     * 
     * @return
     */
    public static String getExternalIP(HttpServletRequest request) {
        String ipAddr = getIpAddr(request);
        return _getExternalIP(ipAddr);
    }

    /**
     * @param ipAddr
     * @return
     */
    private static String _getExternalIP(String ipAddr) {
        if (StringUtils.isEmpty(ipAddr)) {
            return null;
        }
        int index = ipAddr.indexOf(',');
        if (index == -1) {
            return ipAddr;
        }
        ipAddr = ipAddr.substring(0, index);
        if (isIntranetIp(ipAddr)) {
            return null;
        }
        return ipAddr;
    }

    public static void main(String[] args) {
        System.out.println(_getExternalIP("101.226.52.82, 192.168.1.102"));
        System.out.println(_getExternalIP("192.168.1.105, 192.168.1.102"));
    }
}
