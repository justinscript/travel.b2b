/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import com.zb.app.biz.domain.TravelRouteDO;

/**
 * @author Administrator 2014-7-7 下午1:22:57
 */
public class TravelRouteVO extends TravelRouteDO {

    private static final long serialVersionUID = -2244305375749833721L;

    private String[]          rPicpathStrings;                         // 图片地址数组
    private String[]          rCanStrings;                             // 餐饮数组

    public String[] getrPicpathStrings() {
        return rPicpathStrings;
    }

    public void setrPicpathStrings(String[] rPicpathStrings) {
        this.rPicpathStrings = rPicpathStrings;
    }

    public String[] getrCanStrings() {
        return rCanStrings;
    }

    public void setrCanStrings(String[] rCanStrings) {
        this.rCanStrings = rCanStrings;
    }

    /**
     * 转换VO
     */
    public void init() {
        if (super.getrPicpath()!=null) {
            this.rPicpathStrings = super.getrPicpath().split(",");
        }
        if (super.getrCan()!=null) {
            this.rCanStrings = super.getrCan().split(",");
        }
    }
}
