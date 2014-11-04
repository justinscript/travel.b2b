/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.combiz;

/**
 * <pre>
 * 这是一个神奇的接口。 担负着特殊的使命。
 * 
 * 有这么一种场景：当我们在处理业务逻辑时（在Web层），需要调用两个Service的更新操作，两个操作需要在同一个事务中，
 * 同时两个Service之间又没有引用关系，怎么办呢？
 * 
 * 举个例子：
 * 我们在下订单过程中，既要调用OrderService的Update操作，又要调用库存Service的更新库存操作，
 * 此时两个操作需要是在同一个事务中执行，又不想在两个Service之间建立引用依赖关系，怎么办呢？
 * 我们的Service方法会调用事务机制（Service方法名必须是insert、add、update、transaction等开头）
 * 这时我们必须新写一个Service，叫做BizCommonService，然后在BizCommonService中的transactionDoAction()方法中
 * 调用OrderService的Update操作和库存Service的更新库存操作，此时这两个操作就在同一事务中了。
 * 
 * 现在方法已经找到了，BizCommonService也写了，为了通用性，以及在BizCommonService中不倾入业务代码，
 * 我们就出现这个神奇的CallBack接口。采用接口回调的方法解决。
 * 
 * 具体使用方法见com.zb.app.common.combiz.BizCommonService#transactionDoAction(ICallBack)注释
 * </pre>
 * 
 * @see com.zb.app.biz.service.combiz.BizCommonService#transactionDoAction(ICallBack)
 * @author zxc Jun 17, 2014 2:06:39 PM
 */
public interface ICallBack {

    /**
     * 外部业务逻辑层（web层）中的接口回调方法。处理事务中的一些业务逻辑。
     * 
     * @param actionType 用于区分是那一个Action
     * @param params 业务层需要的参数
     * @return Object 返回需要的结果
     * @see com.zb.app.biz.service.combiz.BizCommonService#transactionDoAction(ICallBack)
     */
    Object callback(int actionType, Object... params);
}
