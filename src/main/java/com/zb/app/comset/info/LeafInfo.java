/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.comset.info;

import java.util.ArrayList;
import java.util.List;

import com.zb.app.comset.cons.RecordEnum;

/**
 * @author zxc Jul 23, 2014 11:39:45 AM
 */
public class LeafInfo {

    private String         name;
    private RecordEnum     type;

    private long           count;   // 记录访问次数
    private long           period;  // 访问时间

    private List<LeafInfo> leafList;

    public LeafInfo() {
    }

    public LeafInfo(RecordEnum type, String name) {
        this.type = type;
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRunTime(long tmpCount, long tmpPeriod) {
        count += tmpCount;
        period += tmpPeriod;
    }

    public RecordEnum getType() {
        return type;
    }

    public long getAvg() {
        return period / count;
    }

    public List<LeafInfo> getLeafList() {
        return leafList;
    }

    public void addLeaf(LeafInfo leaf) {
        if (leaf != null) {
            if (leafList == null) {
                leafList = new ArrayList<LeafInfo>();
            }
            leafList.add(leaf);
        }
    }

    public void dispose() {
        if (leafList != null) {
            leafList.clear();
        }
    }
}
