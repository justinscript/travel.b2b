/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.api;

import java.io.Serializable;

import com.zb.app.common.core.lang.Argument;

/**
 * @author zxc Jul 24, 2014 4:25:13 PM
 */
public class MessageMapper<DATA extends Object & Serializable, IH extends IMessageHandler> implements Serializable {

    private static final long serialVersionUID = -710655444590971920L;

    private DATA              data;

    private IH[]               ihandler;

    public MessageMapper() {

    }

    @SuppressWarnings("unchecked")
    public MessageMapper(DATA data, IH... ihandler) {
        this.data = data;
        if (Argument.isNotEmptyArray(ihandler)) {
            this.ihandler = ihandler;
        }
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    public IMessageHandler[] getIhandler() {
        return ihandler;
    }

    public void setIhandler(IH[] ihandler) {
        this.ihandler = ihandler;
    }
}
