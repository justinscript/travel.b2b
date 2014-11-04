/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author ZhouZhong 2014-7-22 下午4:05:26
 */
public class TravelNewsDO implements Serializable {

    private static final long serialVersionUID = -5280607810615237920L;

    private Long              nId;                                     // 自动编号
    private Date              gmtCreate;                               // 创建时间
    private Date              gmtModified;                             // 修改时间

    private Long              cId;                                     // 公司ID
    @NotNull(message = "专线ID不能为空")
    private Long              zId;                                     // 专线ID
    @NotNull(message = "新闻类型不能为空")
    private Integer           nType;                                   // 新闻类型
    @NotEmpty(message = "新闻标题不能为空")
    private String            nTitle;                                  // 新闻标题
    private String            nPic;                                    // 新闻图片或广告图片
    @NotEmpty(message = "新闻内容不能为空")
    private String            nContent;                                // 新闻内容
    private Integer           nState;                                  // 状态(0=正常,1=停止)
    private Integer           nHotCount;                               // 浏览量

    public TravelNewsDO() {
        super();
    }

    public Long getnId() {
        return nId;
    }

    public void setnId(Long nId) {
        this.nId = nId;
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

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getzId() {
        return zId;
    }

    public void setzId(Long zId) {
        this.zId = zId;
    }

    public Integer getnType() {
        return nType;
    }

    public void setnType(Integer nType) {
        this.nType = nType;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnPic() {
        return nPic;
    }

    public void setnPic(String nPic) {
        this.nPic = nPic;
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    public Integer getnState() {
        return nState;
    }

    public void setnState(Integer nState) {
        this.nState = nState;
    }

    public Integer getnHotCount() {
        return nHotCount;
    }

    public void setnHotCount(Integer nHotCount) {
        this.nHotCount = nHotCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
