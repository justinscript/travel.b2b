/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author Administrator 2014-7-9 下午3:34:33
 */
public class TravelTrafficDO implements Serializable {

    private static final long serialVersionUID = 9108143290402607596L;

    private Long              tId;                                    // 自动编号
    private Date              gmtCreate;                              // 创建时间
    private Date              gmtModified;                            // 修改时间

    private Integer           tType;                                  // 交通类型
    private Integer           tCat;                                   // 往返类型 0:往 1：返
    private String            tTraffic;                               // 交通备注
    private Long              cId;                                    // 公司id

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public Integer gettCat() {
        return tCat;
    }

    public void settCat(Integer tCat) {
        this.tCat = tCat;
    }

    public String gettTraffic() {
        return tTraffic;
    }

    public void settTraffic(String tTraffic) {
        if (tTraffic.length() > 8) {
            this.tTraffic = tTraffic.substring(0, 7).equals(",,,,,,,") ? tTraffic.replaceAll(",", "").replace("无", "") : tTraffic;
        } else {
            this.tTraffic = tTraffic;
        }
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
