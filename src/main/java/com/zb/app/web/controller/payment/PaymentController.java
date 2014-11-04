/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.payment;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.web.controller.BaseController;

/**
 * 支付 API
 * 
 * @author zxc Jun 30, 2014 4:50:39 PM
 */
@Controller
public class PaymentController extends BaseController {

    /**
     * 邮政储蓄银行(PSBC)的通知接口
     */
    @RequestMapping(value = "/pay.htm")
    @ResponseBody
    public JsonResult psbcNotify(Map<String, Object> model) {

        return JsonResultUtils.success();
    }

}
