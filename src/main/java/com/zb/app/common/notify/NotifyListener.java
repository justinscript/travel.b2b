/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.notify;

import com.zb.app.common.notify.event.EventConfig;

/**
 * 具体事件配置接口,这个接口没有方法，是通过Annotation做的，{@link EventConfig}
 * 
 * <pre>
 * // 注册监听器（一般情况下在类的Static块中注册时间，注意捕获异常啊！）
 * NotifyService.regist(new NotifyListener() {
 * 
 *     // 方法上配置出您需要关顾的事件。多个事件用逗号连接。
 *     &#064;EventConfig(events = { EventType.orderAdd, EventType.orderDelete, EventType.orderUpdate })
 *     public void orderEvent(OrderEvent orderEvent) {
 *         Integer userId = orderEvent.getUserId();// 时间的userId
 *         EventType eventType = orderEvent.getEventType();// 事件类型
 *         List&lt;Long&gt; productId = orderEvent.getData();// productId 列表。
 * 
 *         // 这里去写您的业务代码就好了。
 * 
 *     }
 * 
 * });
 * 
 * </pre>
 * 
 * @author zxc Jul 24, 2014 4:52:38 PM
 */
public interface NotifyListener {

}
