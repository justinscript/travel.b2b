/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import com.zb.app.biz.domain.TravelAdvertisementDO;

/**
 * @author zxc Aug 20, 2014 11:49:16 AM
 */
public class ADLinkVO extends TravelAdvertisementDO {

    private static final long serialVersionUID = -5220310819150777187L;

    private Long              chugangId;

    private String            province;

    private String            city;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getChugangId() {
        return chugangId;
    }

    public void setChugangId(Long chugangId) {
        this.chugangId = chugangId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
