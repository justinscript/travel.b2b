/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.websocket.server.wrapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.TextMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.util.JsonUtils;
import com.zb.app.websocket.cons.ActionEnum;
import com.zb.app.websocket.exception.WebSocketDataFormatException;
import com.zb.app.websocket.exception.WebSocketException;

/**
 * @author zxc Jul 28, 2014 10:39:11 AM
 */
@SuppressWarnings("unchecked")
public class SocketMessage<M extends Object> implements Serializable {

    private static final long   serialVersionUID = 249607316078998216L;

    public static String        ACTION           = "action";
    public static String        TYPE             = "type";
    public static String        DATA             = "data";

    private ActionEnum          action;
    private ClientWrapper       clientInfo;
    private String              remark;
    private String              sendIp;

    private TextMessage         clienMessage;
    private boolean             isErrorMsg;

    private TextMessage         serverMessage;

    private M[]                 data;

    private Map<String, Object> paramMap         = new HashMap<String, Object>();

    public SocketMessage(ClientWrapper clientInfo) {
        if (clientInfo == null) {
            throw new WebSocketException("The SocketMessage is Error! clientInfo is null or data is null !");
        }
        this.clientInfo = clientInfo;
    }

    /**
     * 解析客户端消息
     * 
     * @param action
     * @param clientInfo
     * @param clientMsg
     */
    public SocketMessage(ClientWrapper clientInfo, TextMessage clientMsg) {
        if (clientInfo == null) {
            throw new WebSocketException("The SocketMessage is Error! clientInfo is null!");
        }
        String clientData = (String) clientMsg.getPayload();
        boolean isNotJson = JsonUtils.isBadJson(clientData);
        if (isNotJson) {
            this.action = ActionEnum.ERROR_FORMAT_MESSAGE;
            this.isErrorMsg = true;
        } else {
            try {
                this.paramMap = parseData(clientData);
                this.action = ActionEnum.getAction((Integer) paramMap.get(ACTION));
                this.isErrorMsg = false;
            } catch (Exception e) {
                throw new WebSocketDataFormatException("The SocketMessage is Error! The message format is incorrect!");
            }
        }
        this.clienMessage = clientMsg;
        this.clientInfo = clientInfo;
    }

    public SocketMessage(ActionEnum action, ClientWrapper clientInfo, M... data) {
        if (clientInfo == null) {
            throw new WebSocketException("The SocketMessage is Error! clientInfo is null!");
        }
        if (action == null) {
            action = ActionEnum.SERVER_SEND_MESSAGE;
        }
        this.action = action;
        this.clientInfo = clientInfo;
        this.data = data;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public ClientWrapper getClientInfo() {
        return clientInfo;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public TextMessage getClienMessage() {
        return clienMessage;
    }

    public void setClienMessage(TextMessage clienMessage) {
        this.clienMessage = clienMessage;
    }

    public boolean isErrorMsg() {
        return isErrorMsg;
    }

    public void setErrorMsg(boolean isErrorMsg) {
        this.isErrorMsg = isErrorMsg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendIp() {
        return sendIp;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp;
    }

    public void putKV(String key, Serializable value) {
        paramMap.put(key, value);
    }

    public M[] getData() {
        return data;
    }

    public void setData(M... data) {
        this.data = data;
    }

    public void setClientInfo(ClientWrapper clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getKV(String key, String defaultValue) {
        Object value = paramMap.get(key);
        return value == null ? defaultValue : (String) value;
    }

    public int getKV(String key, int defaultValue) {
        Object value = paramMap.get(key);
        return value == null ? defaultValue : (Integer) value;
    }

    public TextMessage getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(TextMessage serverMessage) {
        this.serverMessage = serverMessage;
    }

    public float getKV(String key, float defaultValue) {
        Object value = paramMap.get(key);
        return value == null ? defaultValue : (Float) value;
    }

    private static Map<String, Object> parseData(String data) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        ClientData clientData = g.fromJson(data, new TypeToken<ClientData>() {
        }.getType());
        Map<String, Object> map = BeanUtils.beanToMap(clientData);
        return map;
    }

    // {"action":"5","type":"client_heartbeat","data":"$zuobian$"}
    public static class ClientData implements Serializable {

        private static final long serialVersionUID = -8734218351960285744L;

        private int               action;
        private String            type;
        private String            data;

        public ClientData(int action, String type, String data) {
            setAction(action);
            setType(type);
            setData(data);
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
