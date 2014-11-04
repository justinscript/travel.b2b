/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.combiz;

import org.springframework.stereotype.Service;

/**
 * @author zxc Jun 17, 2014 2:07:29 PM
 */
@Service
public class BizCommonService {

    /**
     * <pre>
     * 这是一个神奇方法。 担负着特殊的使命。主要处理不通Service之间的事务问题。
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
     * 使用方法有两种：
     * 第一种方法是Controler继承Callback接口。个人比较推荐这种方法。
     * 
     * public class XXXController implements ICallBack
     * {
     *      //接口实现
     *      public Object callback(int actionType,Object... params)
     *      {
     *          if (actionType==0)
     *          {
     *              //处理Service方法
     *          }
     *      }
     *      //业务逻辑Controller方法
     *      public void xxxxMethod()
     *      {
     *          BizCommonService bizCommonService = BizCommonServiceLocator.getBizCommonService();
     *          bizCommonService.transactionDoAction(0,this,params);
     *      }
     * }
     *    
     * 第二种方法是匿名内部类实现。虽然代码比较优雅，但是每次执行方法时New一个匿名类。不是很推荐使用
     * 
     * public class XXXController
     * {
     *      
     *      //业务逻辑Controller方法
     *      public void xxxxMethod()
     *      {
     *          BizCommonService bizCommonService = BizCommonServiceLocator.getBizCommonService();
     *          bizCommonService.transactionDoAction(0,new CallBack(params){
     *              //接口实现
     *              public Object callback(int actionType,Object... params)
     *              {
     *                  //处理Service方法
     *              }
     *          });
     *      }
     * }
     * 
     * 
     * </pre>
     * 
     * @param actionType 区分业务层Action类型
     * @param back
     */
    public Object transactionDoAction(int actionType, ICallBack back, Object... params) {
        return back.callback(actionType, params);
    }
}
