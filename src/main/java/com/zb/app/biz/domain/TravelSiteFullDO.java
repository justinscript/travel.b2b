/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author zxc Jul 11, 2014 11:24:23 AM
 */
public class TravelSiteFullDO implements Serializable {

    private static final long serialVersionUID = 5905922234548054538L;

    // 站点信息
    private Long              sId;                                    // 站点ID
    private String            sName;                                  // 站点名称
    private String            sProvince;                              // 站点省
    private String            sCity;                                  // 站点市
    private Integer           sSort;                                  // 排序

    // 出港点信息
    private Long              cId;                                    // 出港点ID
    private String            cName;                                  // 出港点名称
    private Integer           cSort;                                  // 排序

    // 专线信息
    private Long              zId;                                    // 专线ID
    private String            zName;                                  // 专线名称
    private Integer           zCat;                                   // 专线分类(0=短线，1=长线，2=国际线)
    private Integer           zDesc;                                  // 专线排序(大号排前)
    private Integer           zHot;                                   // 状态(1=热门，2=推荐)
    private String            zPic;                                   // 专线图片

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsProvince() {
        return sProvince;
    }

    public void setsProvince(String sProvince) {
        this.sProvince = sProvince;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public Integer getsSort() {
        return sSort;
    }

    public void setsSort(Integer sSort) {
        this.sSort = sSort;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcSort() {
        return cSort;
    }

    public void setcSort(Integer cSort) {
        this.cSort = cSort;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public String getzName() {
        return zName;
    }

    public void setzName(String zName) {
        this.zName = zName;
    }

    public Integer getzCat() {
        return zCat;
    }

    public void setzCat(Integer zCat) {
        this.zCat = zCat;
    }

    public Integer getzDesc() {
        return zDesc;
    }

    public void setzDesc(Integer zDesc) {
        this.zDesc = zDesc;
    }

    public Integer getzHot() {
        return zHot;
    }

    public void setzHot(Integer zHot) {
        this.zHot = zHot;
    }

    public String getzPic() {
        return zPic;
    }

    public void setzPic(String zPic) {
        this.zPic = zPic;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
