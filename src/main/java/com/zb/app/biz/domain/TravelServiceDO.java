/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.zb.app.common.core.CustomToStringStyle;

/**
 * @author zxc Aug 20, 2014 5:21:26 PM
 */
public class TravelServiceDO implements Serializable {

    private static final long serialVersionUID = -285324344476429245L;

    private Long              sId;                                    // 自动编号
    private Date              gmtCreate;                              // 记录创建时间
    private Date              gmtModified;                            // 记录最后修改时间

    private Long              cId;                                    // 公司ID
    @NotEmpty(message = "客服名称不能为空")
    private String            sName;                                  // 客服名称
    @NotEmpty(message = "客服QQ不能为空")
    private String            sQQ;                                    // 客服QQ
    private Integer           sSort;                                  // 排序(大号排前)
    private String            sMobile;                                // 客服手机
    private Integer           sIsReceive;                             // 是否接受信息   0=否 ，1=是
    
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

    public String getsMobile() {
		return sMobile;
	}

	public void setsMobile(String sMobile) {
		this.sMobile = sMobile;
	}

	public Integer getsIsReceive() {
		return sIsReceive;
	}

	public void setsIsReceive(Integer sIsReceive) {
		this.sIsReceive = sIsReceive;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
