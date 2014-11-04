/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.zb.app.biz.cons.MessageReadStateEnum;
import com.zb.app.biz.cons.MessageTypeEnum;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.core.lang.Wrapper;

/**
 * @author zxc Aug 19, 2014 10:45:17 AM
 */
public class MessageData {

    private int                                    msgTotal;

    private Map<String, CountMessageList<Message>> msgMap = new LinkedHashMap<String, CountMessageList<Message>>();

    public MessageData() {

    }

    public MessageData(int msgTotal, Map<String, CountMessageList<Message>> msgMap) {
        setMsgMap(msgMap);
        setMsgTotal(msgTotal);
    }

    public MessageData(TravelMessageDO... msg) {
        if (Argument.isEmptyArray(msg)) {
            this.msgTotal = 0;
            this.msgMap = Collections.emptyMap();
            return;
        }
        this.msgTotal = msg.length;
        Map<Integer, List<TravelMessageDO>> map = CollectionUtils.toIntegerListMap(Wrapper.collection(msg),
                                                                                   "messageType");
        if (map != null && map.size() > 0) {
            for (java.util.Map.Entry<Integer, List<TravelMessageDO>> entry : map.entrySet()) {
                List<TravelMessageDO> messageDOList = entry.getValue();
                CountMessageList<Message> messageList = new CountMessageList<MessageData.Message>(messageDOList.size());
                LinkedList<Message> list = messageList.getList();
                for (TravelMessageDO messageDO : messageDOList) {
                    if (list.size() >= 5) {
                        break;
                    }
                    list.add(new Message(messageDO));
                }
                msgMap.put(MessageTypeEnum.getAction(entry.getKey()).getName(), messageList);
            }
        }
    }

    public int getMsgTotal() {
        return msgTotal;
    }

    public void setMsgTotal(int msgTotal) {
        this.msgTotal = msgTotal;
    }

    public Map<String, CountMessageList<Message>> getMsgMap() {
        return msgMap;
    }

    public void setMsgMap(Map<String, CountMessageList<Message>> msgMap) {
        this.msgMap = msgMap;
    }

    public class CountMessageList<E> {

        private int           total;

        private LinkedList<E> list;

        public CountMessageList(int total) {
            this.total = total;
            this.list = new LinkedList<E>();
        }

        public LinkedList<E> getList() {
            return list;
        }

        public void setList(LinkedList<E> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public class Message {

        private Long   id;     // 自动编号
        private String title;  // 消息标题
        private String content; // 消息内容
        private String state;  // 消息状态(0=已读，1=未读,-1,已删除)

        public Message() {

        }

        public Message(TravelMessageDO messageDO) {
            setContent(messageDO.getContent());
            setId(messageDO.getMeId());
            setTitle(messageDO.getTitle());
            setState(MessageReadStateEnum.getAction(messageDO.getMessageState()).getName());
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
