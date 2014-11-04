/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.webuser;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.MemberTypeEnum;
import com.zb.app.common.authority.AuthorityHelper;
import com.zb.app.common.authority.Right;
import com.zb.app.common.core.CustomToStringStyle;
import com.zb.app.common.pipeline.value.BaseWebUser;

/**
 * @author zxc Jul 1, 2014 4:25:17 PM
 */
public class ZuobianWebUser extends BaseWebUser {

    private static final long                  serialVersionUID = -2486506270980794759L;

    private Long                               cId;
    private CompanyTypeEnum                    type;
    private String                             companyName;

    private Long                               mId;
    private MemberTypeEnum                     mType;
    private String                             userName;
    private String                             role;
    private Integer                            state;

    private Long                               siteId;                                              // 站点ID
    private String                             siteName;                                            // 站点名称
    private Long                               chugangId;                                           // 站点ID
    private String                             chugangName;                                         // 站点名称

    private boolean                            isFirstAccess;
    private Date                               lastLogin;

    private Set<Right>                         rightSet         = new HashSet<Right>();

    private static ThreadLocal<ZuobianWebUser> userHolder       = new ThreadLocal<ZuobianWebUser>();

    public ZuobianWebUser() {

    }

    public ZuobianWebUser(Long cId, Long mId) {
        setcId(cId);
        setmId(mId);
    }

    public static void setCurrentUser(ZuobianWebUser webUser) {
        userHolder.set(webUser);
    }

    public static ZuobianWebUser getCurrentUser() {
        return userHolder.get();
    }

    public Long getcId() {
        return cId;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setRole(String role) {
        Collection<Right> list = AuthorityHelper.createRightList(role);
        if (!list.isEmpty()) {
            this.rightSet = new HashSet<Right>(list);
        }
        this.role = role;
    }

    public Integer getState() {
        return state;
    }

    public Set<Right> getRightSet() {
        return rightSet;
    }

    public void setRightSet(Set<Right> rightSet) {
        this.rightSet = rightSet;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getChugangId() {
        return chugangId;
    }

    public void setChugangId(Long chugangId) {
        this.chugangId = chugangId;
    }

    public String getChugangName() {
        return chugangName;
    }

    public void setChugangName(String chugangName) {
        this.chugangName = chugangName;
    }

    public CompanyTypeEnum getType() {
        return type;
    }

    public void setType(CompanyTypeEnum type) {
        this.type = type;
    }

    public MemberTypeEnum getmType() {
        return mType;
    }

    public void setmType(MemberTypeEnum mType) {
        this.mType = mType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isFirstAccess() {
        return isFirstAccess;
    }

    public void setFirstAccess(boolean isFirstAccess) {
        this.isFirstAccess = isFirstAccess;
    }

    public static ThreadLocal<ZuobianWebUser> getUserHolder() {
        return userHolder;
    }

    public static void setUserHolder(ThreadLocal<ZuobianWebUser> userHolder) {
        ZuobianWebUser.userHolder = userHolder;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new CustomToStringStyle());
    }
}
