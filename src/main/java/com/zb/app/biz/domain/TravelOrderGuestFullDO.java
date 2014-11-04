/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the
 * confidential and proprietary information of ZuoBian.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

/**
 * 订单游客的完全类
 * @author ZhouZhong 2014-8-22 下午2:00:47
 */
public class TravelOrderGuestFullDO extends TravelOrderGuestDO{

    /**
     * 
     */
    private static final long serialVersionUID = -3732060378862345417L;
    private String cName;
    private String mName;
    private String cMobile;
    private String cTel;
    private Float lJCrPrice;
    private Float lJXhPrice;
    private Float lJYPrice;
    
    
    public Float getlJCrPrice() {
        return lJCrPrice;
    }

    
    public void setlJCrPrice(Float lJCrPrice) {
        this.lJCrPrice = lJCrPrice;
    }

    
    public Float getlJXhPrice() {
        return lJXhPrice;
    }

    
    public void setlJXhPrice(Float lJXhPrice) {
        this.lJXhPrice = lJXhPrice;
    }

    
    public Float getlJYPrice() {
        return lJYPrice;
    }

    
    public void setlJYPrice(Float lJYPrice) {
        this.lJYPrice = lJYPrice;
    }

    public String getcName() {
        return cName;
    }
    
    public void setcName(String cName) {
        this.cName = cName;
    }
    
    public String getmName() {
        return mName;
    }
    
    public void setmName(String mName) {
        this.mName = mName;
    }
    
    public String getcMobile() {
        return cMobile;
    }
    
    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }
    
    public String getcTel() {
        return cTel;
    }
    
    public void setcTel(String cTel) {
        this.cTel = cTel;
    }
    
}
