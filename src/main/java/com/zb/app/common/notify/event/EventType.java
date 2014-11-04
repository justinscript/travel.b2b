/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.notify.event;

/**
 * 所有的事件
 * 
 * @author zxc Jul 24, 2014 4:53:59 PM
 */
public enum EventType {

    // 新增订单
    orderAdd,
    // 删除订单
    orderDelete,
    // 订单更新
    orderUpdate,

    // 新增专线
    siteAdd,
    // 删除专线
    siteDelete,
    // 更新专线
    siteUpdate,

    // 绑定专线
    bindColumn,
    // 取消绑定专线
    unBindColumn,

    // 新增旅游产品
    lineAdd,

    companyAdd, companyDelete, companyUpdate,

    newClientMessage,

    newClientConnect,

    // notify时间
    notifyItem, notifyTrade, notifyRefund,

    // INVALID_SESSION
    invalidSession;

    public static boolean isOrderAdd(EventType eventType) {
        return eventType != null && eventType == EventType.orderAdd;
    }

    public static boolean isOrderDelete(EventType eventType) {
        return eventType != null && eventType == EventType.orderDelete;
    }

    public boolean isOrderAdd() {
        return this == orderAdd;
    }

    public boolean isOrderDelete() {
        return this == orderDelete;
    }

    public boolean isOrderUpdate() {
        return this == orderUpdate;
    }

    public static boolean isOrderUpdate(EventType eventType) {
        return eventType != null && eventType == EventType.orderUpdate;
    }
}
