/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

/**
 * @author zxc Sep 7, 2014 11:42:00 PM
 */
public class SolrVersionVO {

    private String  gmtModified;
    private Integer version;
    private int     status;

    private boolean isInUse;

    public SolrVersionVO() {

    }

    public SolrVersionVO(String gmtModified, Integer version, int status) {
        this.gmtModified = gmtModified;
        this.version = version;
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isInUse() {
        return isInUse;
    }

    public void setInUse(boolean isInUse) {
        this.isInUse = isInUse;
    }
}
