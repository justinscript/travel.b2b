/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装接口的工具类
 * 
 * @author zxc Jul 23, 2014 2:26:54 PM
 */
public class CoreUtilities {

    private static final Logger logger    = LoggerFactory.getLogger(CoreUtilities.class);
    // 记录IP地址
    private static String       ipAddress = null;
    // 记录HostName
    private static String       hostName  = null;

    /**
     * 获取主机名
     * 
     * @return
     */
    public static String getHostName() {
        if (hostName != null) {
            return hostName;
        } else {
            try {
                String cmd = isWindowsOS() ? "hostname" : "/bin/hostname";
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                InputStream in = process.getInputStream();
                InputStreamReader inr = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(inr);
                String wg = br.readLine();
                if (wg != null) {
                    hostName = wg.trim();
                } else {
                    hostName = "unknown hostname";
                }
            } catch (Exception e) {
                logger.error("Exception", e);
                hostName = "unknown hostname";
            }
        }
        return hostName;
    }

    /**
     * 返回IP地址，先尝试getHostAddress()取，不行从Linux的命令行取
     */
    public static String getIPAddress() {
        if (ipAddress != null && ipAddress.length() > 0) {
            return ipAddress;
        }
        String hostIp = null;
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("UnknownHostException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        if (hostIp != null) {
            String tmpIp = hostIp.toLowerCase();
            if (tmpIp.startsWith("localhost")) {
                hostIp = null;
            } else if (tmpIp.startsWith("127")) {
                hostIp = null;
            }
        }
        if (hostIp != null) {
            ipAddress = hostIp;
            return ipAddress;
        }
        try {
            ipAddress = getIfconigByCurl();
            return ipAddress;
        } catch (Exception e) {
            logger.error("getIfconigByCurl Error", e);
            ipAddress = "unknownIp";
        }

        try {
            ipAddress = getIfconig();
        } catch (Exception e) {
            logger.error("getIfconig Error", e);
            ipAddress = "unknownIp";
        }
        return ipAddress;
    }

    /**
     * 根据Linux命令行取IP
     * 
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private static String getIfconigByCurl() throws IOException, InterruptedException {
        String cmd = "/usr/bin/curl ifconfig.me";
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        InputStream in = process.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inr);
        String wg = "";
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            wg = line.substring(0, line.length());
        }
        return wg.trim();
    }

    /**
     * 根据Linux命令行取IP
     * 
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private static String getIfconig() throws IOException, InterruptedException {
        String cmd = "/sbin/ifconfig";
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        InputStream in = process.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inr);
        String wg = "";
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            if (line.indexOf("inet addr") != -1 || line.indexOf("inet 地址") != -1) {
                wg = line.substring(line.indexOf(":") + 1, line.length());
                wg = wg.trim();
                int index = wg.indexOf("Bcast");
                if (index == -1) {
                    index = wg.indexOf("广播");
                }
                if (index != -1) {
                    wg = wg.substring(0, index);
                    wg = wg.trim();
                    break;
                } else {
                    if (wg.length() > 14) {
                        wg = wg.substring(0, 14);
                    }
                }
            }
        }
        return wg.trim();
    }

    /**
     * 返回异常堆栈
     * 
     * @param e
     * @return
     */
    public static String getExceptionText(Throwable e) {
        if (e != null) {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] st = e.getStackTrace();
            for (StackTraceElement s : st) {
                sb.append(s.toString()).append("\r\n");
            }
            return sb.toString();
        }
        return null;
    }

    public static boolean isWindowsOS() {
        String p = System.getProperties().getProperty("os.name");
        if (p != null && p.toLowerCase().indexOf("windows") != -1) {
            return true;
        }
        return false;
    }
}
