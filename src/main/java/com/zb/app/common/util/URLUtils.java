/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc Jun 16, 2014 12:40:39 AM
 */
public class URLUtils {

    private static final String split = "&";

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParams(URL url) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String query = url.getQuery();
        if (query == null) {
            return paramMap;
        }
        String[] params = query.split(split);
        for (String param : params) {
            if (StringUtils.isEmpty(param)) {
                continue;
            }
            String name = param.substring(0, param.indexOf("="));
            String value = param.substring(param.indexOf("=") + 1, param.length());
            if (paramMap.containsKey(name)) {
                Object object = paramMap.get(name);
                List<String> values = null;
                if (object instanceof List) {
                    values = (List<String>) object;
                } else {
                    values = new ArrayList<String>();
                    values.add(object.toString());
                }
                values.add(value);
                paramMap.put(name, values);
            } else {
                paramMap.put(name, value);
            }
        }
        return paramMap;
    }

    public static void main(String[] args) throws MalformedURLException {
        String subway_url = "http://detail.tmall.com/item.htm?spm=a1z10.4.w5003-3705396631.2.aiPsIi&id=22490775015&&mt=&&scene=taobao_shop";
        Map<String, Object> params = getParams(new URL(subway_url));
        System.out.println(params);
        System.out.println(params.get("token"));
    }

}
