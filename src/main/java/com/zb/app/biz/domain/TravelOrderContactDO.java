/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;

/**
 * @author zxc Aug 27, 2014 6:54:44 PM
 */
public class TravelOrderContactDO implements Serializable {

    private static final long serialVersionUID = 7395789025240950557L;

    private Long              cId;                                    // 组团社公司id

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }
}
