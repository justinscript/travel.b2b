/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
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
 * 广告管理
 * 
 * @author zxc Aug 19, 2014 3:55:44 PM
 */
public class TravelAdvertisementDO implements Serializable {

    private static final long serialVersionUID = 8947099833704544741L;

    private Long              adId;                                   // 广告编号主键
    private Date              gmtCreate;                              // 创建时间
    private Date              gmtModified;                            // 修改时间

    @NotNull(message = "广告位置不能为空")
    private Integer           location;                               // 广告位置
    @NotEmpty(message = "广告站点不能为空")
    private String            site;                                   // 广告站点
    @NotNull(message = "广告站点ID不能为空")
    private Long              siteId;                                 // 广告站点ID
    @NotEmpty(message = "广告标题不能为空")
    private String            title;                                  // 广告标题
    private String            content;                                // 广告内容
    private String            pic;                                    // 广告图片
    private String            link;                                   // 广告链接
    private Integer           sort;                                   // 广告排序
    private Integer           state;                                  // 广告状态

    public TravelAdvertisementDO() {

    }

    public TravelAdvertisementDO(Long adId) {
        setAdId(adId);
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
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

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
