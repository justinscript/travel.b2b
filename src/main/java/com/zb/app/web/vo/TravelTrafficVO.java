/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zb.app.biz.cons.TrafficBackTypeEnum;
import com.zb.app.biz.domain.TravelTrafficDO;
import com.zb.app.common.core.lang.BeanUtils;

/**
 * @author Administrator 2014-7-11 上午11:21:31
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TravelTrafficVO extends TravelTrafficDO {

    private static final long serialVersionUID = 5612029674500183673L;

    private String            tTypeString;                            // 类型
    private String[]          tTrafficStrings;                        // 飞机数组

    public TravelTrafficVO() {

    }

    public TravelTrafficVO(TravelTrafficDO trdo) {
        BeanUtils.copyProperties(this, trdo);
    }

    public String[] gettTrafficStrings() {
        return tTrafficStrings;
    }

    public void settTrafficStrings(String[] tTrafficStrings) {
        this.tTrafficStrings = tTrafficStrings;
    }

    public String gettTypeString() {
        return tTypeString;
    }

    public void settTypeString(String tType) {
        // this.tTypeString = TrafficBackTypeEnum.getEnum(super.gettType()).getDesc();
        this.tTypeString = tType;
    }

    public void init() {
        // 类型
        this.tTypeString = TrafficBackTypeEnum.getEnum(super.gettType()).getDesc();
        // 备注
        this.tTrafficStrings = super.gettTraffic().split(",");
    }
}
