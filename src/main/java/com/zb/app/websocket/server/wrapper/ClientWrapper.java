/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.server.wrapper;

import java.io.Serializable;
import java.net.InetAddress;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.web.socket.WebSocketSession;

import com.zb.app.common.cookie.CookieKeyEnum;
import com.zb.app.common.cookie.manager.CookieManager;
import com.zb.app.common.cookie.manager.WebSocketCookieManager;
import com.zb.app.common.core.lang.Assert;
import com.zb.app.common.util.NumberParser;
import com.zb.app.web.webuser.ZuobianWebUser;
import com.zb.app.web.webuser.ZuobianWebUserBuilder;

/**
 * 用于记录注册的客户端的IP、WebUser
 * 
 * @author zxc Jul 25, 2014 2:29:55 PM
 */
public class ClientWrapper implements Serializable {

    private static final long serialVersionUID     = -1291257527806358194L;

    private static final long MAX_LAST_ACCESS_TIME = 1000 * 3600;

    private Long              mId;

    private ZuobianWebUser    webUser;

    private String            ip;

    private InetAddress       address;

    private CookieManager     cookieManager;

    public ClientWrapper(Long mId) {
        this.mId = mId;
    }

    public ClientWrapper(WebSocketSession socketSession) {
        this.address = socketSession.getRemoteAddress().getAddress();
        this.ip = address.getHostAddress();

        this.cookieManager = new WebSocketCookieManager(socketSession.getHandshakeHeaders());
        // 操作大于1小时就自动退出
        long lastLoginTime = NumberParser.parseLong(cookieManager.get(CookieKeyEnum.last_login_time), 0);
        if (lastLoginTime <= 0 || System.currentTimeMillis() - lastLoginTime > MAX_LAST_ACCESS_TIME) {
            ZuobianWebUserBuilder.loginOut(cookieManager);
        } else {
            this.webUser = ZuobianWebUserBuilder.create(cookieManager);
            this.mId = webUser.getmId();
        }
    }

    public ClientWrapper(ZuobianWebUser webUser, WebSocketSession socketSession) {
        setWebUser(webUser);
    }

    public void setWebUser(ZuobianWebUser webUser) {
        this.webUser = webUser;
    }

    public ZuobianWebUser getWebUser() {
        return webUser;
    }

    public String getIp() {
        return ip;
    }

    public CookieManager getCookieManager() {
        return cookieManager;
    }

    public void setCookieManager(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClientWrapper other = (ClientWrapper) obj;
        if (!NumberParser.isEqual(this.mId, other.mId)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return true;
    }

    @Override
    public int hashCode() {
        Assert.assertNotNull(this.mId);
        return HashCodeBuilder.reflectionHashCode(this.mId);
    }
}
