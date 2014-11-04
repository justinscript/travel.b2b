/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.cons;

/**
 * 积分来源(0=消费产品,1=返还积分,2=转入,3=充值,4,=发布线路,5=冻结归还)
 * 
 * @author zxc Aug 14, 2014 5:44:24 PM
 */
public enum IntegralSourceEnum {

    consumer(0), return_point(1), transfer(2),recharge(3),release(4),frozen_return(5); 

    public int value;

    private IntegralSourceEnum(int value) {
        this.value = value;
    }
}
