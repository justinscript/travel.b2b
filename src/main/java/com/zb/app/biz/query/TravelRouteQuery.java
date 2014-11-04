/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.common.pagination.Pagination;

/**
 * @author zxc Jun 24, 2014 5:13:37 PM
 */
public class TravelRouteQuery extends Pagination {

    private Long   rId;
    /**
     * 线路ID
     */
    private Long   lId;
    /**
     * 内容
     */
    private String rContent;
    /**
     * 住宿
     */
    private String rZhu;
    /**
     * 餐（0=早，1=中，2=晚）
     */
    private String rCan;
    /**
     * 图片地址（每个路径用英文逗号隔开）
     */
    private String rPicpath;
    /**
     * 交通计划
     */
    private String rCar;
    /**
     * 资源ID（每个 ID用英文逗号隔开）
     */
    private String rResource;

    public TravelRouteQuery() {

    }

    public TravelRouteQuery(Long lId) {
        setlId(lId);
    }

    public Long getrId() {
        return rId;
    }

    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public String getrZhu() {
        return rZhu;
    }

    public void setrZhu(String rZhu) {
        this.rZhu = rZhu;
    }

    public String getrCan() {
        return rCan;
    }

    public void setrCan(String rCan) {
        this.rCan = rCan;
    }

    public String getrPicpath() {
        return rPicpath;
    }

    public void setrPicpath(String rPicpath) {
        this.rPicpath = rPicpath;
    }

    public String getrCar() {
        return rCar;
    }

    public void setrCar(String rCar) {
        this.rCar = rCar;
    }

    public String getrResource() {
        return rResource;
    }

    public void setrResource(String rResource) {
        this.rResource = rResource;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }
}
