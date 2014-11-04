/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author zxc Jul 11, 2014 3:00:33 PM
 */
public class ColumnThinVO {

    // 专线信息
    private Long    zId;  // 专线ID
    private String  zName; // 专线名称
    private Integer zCat; // 专线分类(0=短线，1=长线，2=国际线)
    private Integer zDesc; // 专线排序(大号排前)
    private Integer zHot; // 状态(1=热门，2=推荐)
    private String  zPic; // 专线图片

    public ColumnThinVO() {

    }

    public ColumnThinVO(Long zId, String zName, Integer zCat, Integer zDesc, Integer zHot, String zPic) {
        setzId(zId);
        setzName(zName);
        setzCat(zCat);
        setzDesc(zDesc);
        setzHot(zHot);
        setzPic(zPic);
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
