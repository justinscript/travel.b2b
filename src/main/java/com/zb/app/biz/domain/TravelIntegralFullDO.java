/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.util.Date;

/**
 * 积分详细类
 * 
 * @author ZhouZhong 2014-8-15 下午12:15:57
 */
public class TravelIntegralFullDO extends TravelIntegralDO {

    private static final long serialVersionUID = -8581574159518290967L;

    private Date              lGoGroupTime;
    private String            lGroupNumber;
    private String            lTitle;
    private String            cName;
    private String            mName;
    private String            mUserName;

    public Date getlGoGroupTime() {
        return lGoGroupTime;
    }

    public void setlGoGroupTime(Date lGoGroupTime) {
        this.lGoGroupTime = lGoGroupTime;
    }

    public String getlGroupNumber() {
        return lGroupNumber;
    }

    public void setlGroupNumber(String lGroupNumber) {
        this.lGroupNumber = lGroupNumber;
    }

    public String getlTitle() {
        return lTitle;
    }

    public void setlTitle(String lTitle) {
        this.lTitle = lTitle;
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

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }
}
