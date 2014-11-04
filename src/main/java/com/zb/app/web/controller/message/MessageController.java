/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.message;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.MessageReadStateEnum;
import com.zb.app.biz.domain.MessageData;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.common.cons.ResultCode;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.webuser.ZuobianWebUser;
import com.zb.app.websocket.api.IMessageHandler;
import com.zb.app.websocket.api.MessageMapper;
import com.zb.app.websocket.server.WebSocketServerHandler;
import com.zb.app.websocket.server.wrapper.SessionWrapper;

/**
 * 消息推送 站内信 系统
 * 
 * @author zxc Jun 16, 2014 4:08:06 PM
 */
@Controller
@RequestMapping("/push")
public class MessageController extends BaseController {

    @RequestMapping(value = "/info.htm")
    public ModelAndView msg(ModelAndView mav) {
        mav.setViewName("msg/msg");

        Collection<SessionWrapper> sessionWrapperList = WebSocketServerHandler.getInstance().getAllSessionInfo();
        mav.addObject("onlineCount", sessionWrapperList.size());
        return mav;
    }

    @RequestMapping(value = "/opt/{id}.htm")
    public ModelAndView opt(@PathVariable("id")
    Long id, String returnurl) {
        if (Argument.isNotPositive(id)) {
            throw new RuntimeException("push msg opt exception,id is null");
        }
        ModelAndView mav = new ModelAndView("home");
        TravelMessageDO message = messageService.getTravelMessageById(id);
        if (message == null) {
            returnurl = "/index.htm";
        }
        message.setMessageState(MessageReadStateEnum.READ.value);
        messageService.updateTravelMessage(message);
        if (StringUtils.isNotEmpty(returnurl)) {
            mav.addObject("returnurl", returnurl);
        }
        return mav;
    }

    @RequestMapping(value = "/my.htm", produces = "application/json")
    @ResponseBody
    public JsonResult my() {
        if (!WebUserTools.hasLogin()) {
            return JsonResultUtils.success(null, "请登录!");
        }
        MessageData messageData = messageService.getMessageData(WebUserTools.getCid());
        if (messageData == null) {
            return JsonResultUtils.success(null, "暂无消息");
        }
        return JsonResultUtils.success(messageData, "推送成功");
    }

    @RequestMapping(value = "/read.htm", produces = "application/json")
    @ResponseBody
    public JsonResult read(Long id) {
        if (id == null) {
            return JsonResultUtils.error("已读失败");
        }
        TravelMessageDO message = messageService.getTravelMessageById(id);
        if (message == null) {
            return JsonResultUtils.error("已读失败");
        }
        message.setMessageState(MessageReadStateEnum.READ.value);
        messageService.updateTravelMessage(message);
        return JsonResultUtils.success("已读成功");
    }

    @RequestMapping(value = "/remove.htm", produces = "application/json")
    @ResponseBody
    public JsonResult remove(Long id) {
        if (id == null) {
            return JsonResultUtils.error("删除失败");
        }
        TravelMessageDO message = messageService.getTravelMessageById(id);
        if (message == null) {
            return JsonResultUtils.error("删除失败");
        }
        message.setMessageState(MessageReadStateEnum.DELETE.value);
        messageService.updateTravelMessage(message);
        return JsonResultUtils.success("删除成功");
    }

    @RequestMapping(value = "/save.htm", produces = "application/json")
    @ResponseBody
    public JsonResult saveMsg(String msg, Long cid) {
        if (StringUtils.isEmpty(msg) || cid == null) {
            return JsonResultUtils.error("失败");
        }
        ZuobianWebUser webUser = new ZuobianWebUser();
        webUser.setmId(cid);
        @SuppressWarnings("unused")
        String message = "{\"message\":\"非法访问\",\"result\":1,\"data\":\"\"}";
        notifyMsgEvent(webUser,
                         new MessageMapper<String, IMessageHandler>(
                                                                    JsonResultUtils.createJsonResult(ResultCode.SUCCESS,
                                                                                                     msg)));
        return JsonResultUtils.success("成功");
    }
}
