/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.query;

import com.zb.app.biz.cons.CompanyStateEnum;
import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.pagination.Pagination;

/**
 * @author ZhouZhong 2014-6-24 下午4:31:35
 */
public class TravelCompanyQuery extends Pagination {

    private Long    cId;
    private Long[]  cIds;
    private Integer cType;
    private String  cName;
    private String  q;
    private String  cRecommend;
    private String  cProvince;
    private String  cCity;
    private String  cCounty;
    private String  companyType;
    private String  companyValue;
    private Integer cState;      // 状态（0=未审核，1=正常，2=停止）
    private String  cSpell;
    private String  cNameLike;

    public TravelCompanyQuery() {

    }

    public TravelCompanyQuery(Long... cIds) {
        ArrayUtils.removeNullElement(cIds);
        if (Argument.isEmptyArray(cIds)) {
            return;
        }
        setcIds(cIds);
    }

    public TravelCompanyQuery(CompanyTypeEnum companyType, Long... cIds) {
        setcType(companyType.getValue());
        ArrayUtils.removeNullElement(cIds);
        if (Argument.isEmptyArray(cIds)) {
            return;
        }
        setcIds(cIds);
    }

    public TravelCompanyQuery(CompanyTypeEnum companyType, String cNameLike) {
        setcType(companyType.getValue());
        setcState(CompanyStateEnum.NORMAL.getValue());
        setcNameLike(cNameLike);
    }

    public Long getcId() {
        return cId;
    }

    public Integer getcState() {
        return cState;
    }

    public void setcState(Integer cState) {
        this.cState = cState;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public String getcNameLike() {
        return cNameLike;
    }

    public void setcNameLike(String cNameLike) {
        this.cNameLike = cNameLike;
    }

    public String getcName() {
        return cName;
    }

    public Long[] getcIds() {
        return cIds;
    }

    public void setcIds(Long[] cIds) {
        this.cIds = cIds;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcRecommend() {
        return cRecommend;
    }

    public void setcRecommend(String cRecommend) {
        this.cRecommend = cRecommend;
    }

    public String getcProvince() {
        return cProvince;
    }

    public void setcProvince(String cProvince) {
        this.cProvince = cProvince;
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public String getcCounty() {
        return cCounty;
    }

    public void setcCounty(String cCounty) {
        this.cCounty = cCounty;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyValue() {
        return companyValue;
    }

    public void setCompanyValue(String companyValue) {
        this.companyValue = companyValue;
    }

    public String getcSpell() {
        return cSpell;
    }

    public void setcSpell(String cSpell) {
        this.cSpell = cSpell;
    }

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
