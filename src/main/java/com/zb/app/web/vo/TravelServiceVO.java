/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 客服VO类
 * 
 * @author ZhouZhong 2014-8-29 下午5:07:10
 */
public class TravelServiceVO {

    private Long       sId;        // 自动编号
    private Date       gmtCreate;  // 记录创建时间
    private Date       gmtModified; // 记录最后修改时间

    private Long       cId;        // 公司ID
    // @NotNull(message = "专线ID不能为空")
    private List<Long> zId;        // 专线ID
    @NotEmpty(message = "客服名称不能为空")
    private String     sName;      // 客服名称
    @NotEmpty(message = "客服QQ不能为空")
    private String     sQQ;        // 客服QQ
    private Integer    sSort;      // 排序(大号排前)

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
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

    public List<Long> getzId() {
        return zId;
    }

    public void setzId(List<Long> zId) {
        this.zId = zId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsQQ() {
        return sQQ;
    }

    public void setsQQ(String sQQ) {
        this.sQQ = sQQ;
    }

    public Integer getsSort() {
        return sSort;
    }

    public void setsSort(Integer sSort) {
        this.sSort = sSort;
    }
}
