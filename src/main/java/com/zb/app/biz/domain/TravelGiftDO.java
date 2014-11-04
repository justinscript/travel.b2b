/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author zxc Jul 25, 2014 10:04:09 AM
 */
public class TravelGiftDO implements Serializable {

    private static final long serialVersionUID = -6095045164190525323L;

    private Long              gId;                                     // 自动编号
    private Date              gmtCreate;                               // 记录创建时间
    private Date              gmtModified;                             // 记录最后修改时间

    private String            gTitle;                                  // 积分产品标题
    private Integer           gcId;                                    // 积分产品分类
    private Integer           gRedemption;                             // 兑换积分
    private Double            gPrice;                                  // 市场价格
    private String            gContent;                                // 内容/备注
    private String            gPic;                                    // 产品图片
    private String            gState;                                  // 状态(0=正常,1=停止)
    private String            gcName;                                    // 积分产品分类
    
    public String getGcName() {
        return gcName;
    }
    
    public void setGcName(String gcName) {
        this.gcName = gcName;
    }
    
    public Long getgId() {
        return gId;
    }

    private TravelGiftDO() {
        super();
    }

    public void setgId(Long gId) {
        this.gId = gId;
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

    public String getgTitle() {
        return gTitle;
    }

    public void setgTitle(String gTitle) {
        this.gTitle = gTitle;
    }

    public Integer getGcId() {
        return gcId;
    }

    public void setGcId(Integer gcId) {
        this.gcId = gcId;
    }

    public Integer getgRedemption() {
        return gRedemption;
    }

    public void setgRedemption(Integer gRedemption) {
        this.gRedemption = gRedemption;
    }

    public Double getgPrice() {
        return gPrice;
    }

    public void setgPrice(Double gPrice) {
        this.gPrice = gPrice;
    }

    public String getgContent() {
        return gContent;
    }

    public void setgContent(String gContent) {
        this.gContent = gContent;
    }

    public String getgPic() {
        return gPic;
    }

    public void setgPic(String gPic) {
        this.gPic = gPic;
    }

    public String getgState() {
        return gState;
    }

    public void setgState(String gState) {
        this.gState = gState;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
