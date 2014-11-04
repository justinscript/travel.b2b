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
 * 文章管理
 * 
 * @author zxc Aug 19, 2014 3:56:25 PM
 */
public class TravelArticlesDO implements Serializable {

    private static final long serialVersionUID = 6522819821240432206L;

    private Long              aId;                                    // 文章编号主键
    private Date              gmtCreate;                              // 创建时间
    private Date              gmtModified;                            // 修改时间

    @NotNull(message = "文章类别来源不能为空")
    private Integer           source;                                 // 文章类别来源
    @NotEmpty(message = "文章标题不能为空")
    private String            title;                                  // 文章标题
    private String            content;                                // 文章内容
    private Integer           sort;                                   // 文章排序
    private Integer           state;                                  // 文章状态

    public TravelArticlesDO() {

    }

    public TravelArticlesDO(Integer state) {
        setState(state);
    }

    public TravelArticlesDO(Long aId) {
        setaId(aId);
    }

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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
