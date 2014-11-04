/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.authority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc Jul 14, 2014 10:48:20 PM
 */
public class AuthorityHelper {

    /**
     * 250个0
     */
    final public static String _RAW = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

    // final public static String _RAW_ON =
    // "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

    /**
     * 判断是否有权限
     * 
     * @param akey
     * @param session
     * @return
     */
    public static boolean hasAuthority(int akey, String rc) {
        if (rc == null || StringUtils.EMPTY.equals(rc)) {
            return false;
        }
        if (rc.length() <= akey) {
            return false;
        }
        char value = rc.charAt(akey);
        if (value == '1') {
            return true;
        }
        return false;
    }

    /**
     * @param role权限字符串, 比如0101001001000000000
     * @return List<Right>
     */
    public static Collection<Right> createRightList(String role) {
        if (StringUtils.isEmpty(role)) {
            return Collections.<Right> emptyList();
        }
        List<Right> rightList = new ArrayList<Right>();
        for (Right right : Right.values()) {
            boolean has = hasAuthority(right.getIndex(), role);
            if (has) {
                rightList.add(right);
            }
        }
        return rightList;
    }

    /**
     * @param role权限字符串, 比如0101001001000000000
     * @return 有权限的项,比如 1,3,6,11,20
     */
    public static String createRightStr(String role) {
        if (StringUtils.isEmpty(role)) {
            return StringUtils.EMPTY;
        }
        StringBuffer sb = new StringBuffer();
        List<Right> rightList = (List<Right>) createRightList(role);
        if (rightList == null || rightList.size() == 0) {
            return StringUtils.EMPTY;
        }
        if (rightList.size() == 1) {
            sb.append(rightList.get(0).getIndex());
            return sb.toString();
        }
        if (rightList.size() == 2) {
            sb.append(rightList.get(0).getIndex());
            sb.append(",");
            sb.append(rightList.get(1).getIndex());
            return sb.toString();
        }
        for (Right right : rightList.subList(0, rightList.size() - 2)) {
            sb.append(right.getIndex());
            sb.append(",");
        }
        sb.append(rightList.get(rightList.size() - 1).getIndex());
        return sb.toString();
    }

    /**
     * 创建权限字符串
     * 
     * @param akeys 有权限的项,比如 1,3,6,11,20
     * @return 权限字符串, 比如0101001001000000000
     */
    public static String makeAuthority(String akeys) {
        if (StringUtils.isEmpty(akeys)) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(_RAW);
        String[] akeys_s = akeys.split(",");
        for (String akey : akeys_s) {
            if (null == akey || StringUtils.EMPTY.equals(akey)) {
                continue;
            }
            int ak = Integer.parseInt(akey);
            sb.setCharAt(ak, '1');
        }
        return sb.toString();
    }
}
