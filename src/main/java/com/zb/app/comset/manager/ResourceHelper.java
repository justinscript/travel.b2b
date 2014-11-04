/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.comset.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.zb.app.comset.cons.RecordEnum;
import com.zb.app.comset.info.LeafInfo;
import com.zb.app.comset.info.UnitInfo;

/**
 * @author zxc Jul 23, 2014 11:38:56 AM
 */
public class ResourceHelper {

    private static ConcurrentHashMap<Long, LeafInfo>   threadIDMap = new ConcurrentHashMap<Long, LeafInfo>();
    private static ConcurrentHashMap<String, UnitInfo> urlMap      = new ConcurrentHashMap<String, UnitInfo>();
    private static Boolean                             trace;

    public static List<UnitInfo> getRecordUnitInfo() {
        List<UnitInfo> list = new ArrayList<UnitInfo>();
        if (urlMap != null) {
            Iterator<String> ir = urlMap.keySet().iterator();
            while (ir.hasNext()) {
                String key = ir.next();
                UnitInfo value = urlMap.get(key);
                if (value != null) {
                    list.add(value);
                }
            }
        }
        return list;
    }

    public static LeafInfo getRootLeafInfo(Long threadId) {
        LeafInfo urlInfo = threadIDMap.get(threadId);
        if (urlInfo == null) {
            urlInfo = new LeafInfo();
            threadIDMap.put(threadId, urlInfo);
        }
        return urlInfo;
    }

    public static UnitInfo getRootUnitInfo(String url) {
        if (url == null) {
            return null;
        }
        UnitInfo urlInfo = urlMap.get(url);
        if (urlInfo == null) {
            urlInfo = new UnitInfo();
            urlInfo.setName(url);
            urlMap.put(url, urlInfo);
        }
        return urlInfo;
    }

    public static void clear() {
        if (urlMap.size() > 0) {
            urlMap.clear();
        }
        if (threadIDMap.size() > 0) {
            threadIDMap.clear();
        }

    }

    public static void recordRunTime(RecordEnum type, String name, long period) {
        if (!isTrace()) {
            return;
        }
        LeafInfo leaf = new LeafInfo(type, name);
        leaf.addRunTime(1, period);
        addLeafInfo(type, leaf);
    }

    private static void addLeafInfo(RecordEnum type, LeafInfo leafUnitInfo) {
        LeafInfo rootInfo = (LeafInfo) ComsetThreadContextCache.get(type);
        if (rootInfo == null) {
            rootInfo = new UnitInfo(type, leafUnitInfo.getName());
            rootInfo.addRunTime(leafUnitInfo.getCount(), leafUnitInfo.getPeriod());
            ComsetThreadContextCache.put(type, rootInfo);
        }
        rootInfo.addLeaf(leafUnitInfo);
    }

    public static void complete(LeafInfo rootInfo) {
        UnitInfo urlRootInfo = getRootUnitInfo(rootInfo.getName());
        if (urlRootInfo == null) {
            return;
        }
        urlRootInfo.addRunTime(1, rootInfo.getAvg());
        RecordEnum vs[] = RecordEnum.values();
        for (RecordEnum v : vs) {
            LeafInfo tagLeaf = (LeafInfo) ComsetThreadContextCache.get(v);
            if (tagLeaf != null) {
                List<LeafInfo> sonList = tagLeaf.getLeafList();
                if (sonList != null) {
                    for (LeafInfo son : sonList) {
                        urlRootInfo.addTypeLeaf(son);
                    }
                }
            }
        }
    }

    /**
     * 设置是否需要设置记录URL访问次数的标记
     * 
     * @param flag
     */
    public static void setTrace(boolean flag) {
        trace = new Boolean(flag);
    }

    /**
     * 是否需要记录URL的访问次数和时间
     * 
     * @return
     */
    public static boolean isTrace() {
        if (trace == null) {
            try {
                trace = new Boolean(System.getProperty("comset.reoced.urltime.flag", "false"));
            } catch (Exception e) {
                trace = new Boolean(false);
            }
        }
        return trace;
    }
}
