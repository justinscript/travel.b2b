/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.util.DateViewTools;

/**
 * @author ZhouZhong 2014-7-7 下午5:20:30
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TravelOrderFullVO extends TravelOrderFullDO {

    private static final long serialVersionUID = -2190781966650960457L;

    private String orGoGroupTimeString;
    
    private String gmtCreateString;                     // 订单创建时间

    private String gmtModifiedString;
    
    public TravelOrderFullVO() {

    }

    public TravelOrderFullVO(TravelOrderFullDO travelOrderFullDO) {
        BeanUtils.copyProperties(this, travelOrderFullDO);
        this.init();
    }

    public String getOrGoGroupTimeString() {
        return orGoGroupTimeString;
    }

    public void setOrGoGroupTimeString(String orGoGroupTimeString) {
        this.orGoGroupTimeString = orGoGroupTimeString;
    }

    public String getGmtModifiedString() {
        return gmtModifiedString;
    }

    public void setGmtModifiedString(String gmtModifiedString) {
        this.gmtModifiedString = gmtModifiedString;
    }
    
    public String getGmtCreateString() {
        return gmtCreateString;
    }
    
    public void setGmtCreateString(String gmtCreateString) {
        this.gmtCreateString = gmtCreateString;
    }
    
    public void init() {
        // 出团日期转换
        String date = DateViewTools.format(super.getOrGoGroupTime(), "yyyy-MM-dd");
        this.orGoGroupTimeString = date.replaceAll("星期", "");
        // 操作日期转换
        String date1 = DateViewTools.format(super.getGmtModified(), "yyyy-MM-dd HH:mm");
        this.gmtModifiedString = date1;
        //创建日期转换
        String date2 = DateViewTools.format(super.getGmtCreate(), "yyyy-MM-dd HH:mm");
        this.gmtCreateString = date2;
    }
}
