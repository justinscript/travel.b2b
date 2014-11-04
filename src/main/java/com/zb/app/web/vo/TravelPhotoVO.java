/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import com.zb.app.biz.domain.TravelPhotoDO;

/**
 * @author Administrator 2014-8-14 下午5:43:59
 */
public class TravelPhotoVO extends TravelPhotoDO {

    private static final long serialVersionUID = -2138840126874162654L;

    private String[]          pTitles;                                 // varchar2(50); --图片标题
    private String[]          pPaths;                                  // varchar2(100); --图片地址
    private String[]          pRemarks;                                // varchar2(300) --图片备注

    public String[] getpTitles() {
        return pTitles;
    }

    public void setpTitles(String[] pTitles) {
        this.pTitles = pTitles;
    }

    public String[] getpPaths() {
        return pPaths;
    }

    public void setpPaths(String[] pPaths) {
        this.pPaths = pPaths;
    }

    public String[] getpRemarks() {
        return pRemarks;
    }

    public void setpRemarks(String[] pRemarks) {
        this.pRemarks = pRemarks;
    }
}
