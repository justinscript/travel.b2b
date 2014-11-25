/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.CompanyTypeEnum;
import com.zb.app.biz.cons.GuestAgeTypeEnum;
import com.zb.app.biz.cons.LogTableEnum;
import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelOperationLogDO;
import com.zb.app.biz.domain.TravelOrderCountDO;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderFullDO;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.domain.TravelOrderGuestFullDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.domain.TravelServiceDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.biz.query.TravelServiceQuery;
import com.zb.app.biz.service.combiz.ICallBack;
import com.zb.app.common.authority.AuthorityPolicy;
import com.zb.app.common.authority.Right;
import com.zb.app.common.component.annotation.FormBean;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.interceptor.annotation.ExportWordFile;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.common.util.PushSMSUtils;
import com.zb.app.common.velocity.CustomVelocityLayoutView;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;
import com.zb.app.web.vo.TravelLineVO;
import com.zb.app.web.vo.TravelOrderFullVO;

/**
 * 全局订单管理 控制层
 * 
 * @author zxc Jul 25, 2014 5:20:34 PM
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController implements ICallBack {

    // callback回调
    private static final int ADD_ORDER_CALLBACK    = 1;
    private static final int UPDATE_ORDER_CALLBACK = 2;
    private static final int DELETE_ORDER_CALLBACK = 3;

    /**
     * 送机名单
     * 
     * @param mav
     * @param id
     * @return
     */
    @RequestMapping(value = "/payment.htm")
    public String payment() {
        return "";
    }

    /**
     * 生成确认单
     * 
     * @param id
     * @return
     */
    @RequestMapping("/print/confirm.htm")
    public String printConfirm() {
        return "/print/confirm";
    }

    /**
     * 导出
     * 
     * @param id
     * @return
     */
    @RequestMapping("/print/line.htm")
    public String printLine() {
        return "/print/line";
    }

    /**
     * 查看游客名单
     * 
     * @param mav
     * @param id
     * @return
     */
    @RequestMapping(value = "/print/guest.htm")
    public ModelAndView showOrderGuest(ModelAndView mav, Long id) {
        // 设置路线Id,查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));
        TravelCompanyDO companyDO = companyService.getById(lineDO.getcId());
        List<TravelOrderGuestFullDO> guestDOFulls = orderService.getByLIdAndPrice(id);

        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("company", companyDO);
        mav.addObject("guestFullDOs", guestDOFulls);
        mav.setViewName("/print/guest");
        return mav;
    }

    /**
     * 送机名单
     * 
     * @param mav
     * @param id
     * @return
     */
    @RequestMapping(value = "/print/ticket.htm")
    public ModelAndView printTicket(ModelAndView mav, Long id) {
        // 设置路线Id,查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));
        TravelCompanyDO companyDO = companyService.getById(lineDO.getcId());
        List<TravelOrderGuestDO> guestDOs = orderService.getByLId(id);

        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("company", companyDO);
        mav.addObject("guestDOs", guestDOs);
        mav.setViewName("/print/ticket");
        return mav;
    }

    /**
     * 出票名单
     * 
     * @param mav
     * @param id
     * @return
     */
    @RequestMapping(value = "/print/goticket.htm")
    public ModelAndView printGoticket(ModelAndView mav, Long id) {
        // 设置路线Id,查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));
        TravelCompanyDO companyDO = companyService.getById(lineDO.getcId());
        List<TravelOrderGuestDO> guestDOs = orderService.getByLId(id);

        mav.addObject("company", companyDO);
        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("guestDOs", guestDOs);
        mav.setViewName("/print/goticket");
        return mav;
    }

    /**
     * 导出送机名单word文档
     * 
     * @param id
     * @return
     */
    @RequestMapping("/exportTicket/{id}.htm")
    @ExportWordFile(value = "送机名单")
    public ModelAndView exportTicket(@PathVariable("id")
    Long id, ModelAndView mav) {
        if (Argument.isNotPositive(id)) {
            return createErrorJsonMav("参数错误!", null);
        }
        // 设置路线Id,查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));
        TravelCompanyDO companyDO = companyService.getById(lineDO.getcId());
        List<TravelOrderGuestDO> guestDOs = orderService.getByLId(id);

        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("company", companyDO);
        mav.addObject("guestDOs", guestDOs);
        mav.setViewName("word/exportTicket");
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        return mav;
    }

    /**
     * 导出出票名单word文档
     * 
     * @param id
     * @return
     */
    @RequestMapping("/exportGoTicket/{id}.htm")
    @ExportWordFile(value = "出票名单")
    public ModelAndView exportGoTicket(@PathVariable("id")
    Long id, ModelAndView mav) {
        if (Argument.isNotPositive(id)) {
            return createErrorJsonMav("参数错误!", null);
        }
        // 设置路线Id,查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));
        TravelCompanyDO companyDO = companyService.getById(lineDO.getcId());
        List<TravelOrderGuestDO> guestDOs = orderService.getByLId(id);

        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("company", companyDO);
        mav.addObject("guestDOs", guestDOs);
        mav.setViewName("word/exportGoTicket");
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        return mav;
    }

    /**
     * 导出游客名单word文档
     * 
     * @param id
     * @return
     */
    @RequestMapping("/exportGuest/{id}.htm")
    @ExportWordFile(value = "游客名单")
    public ModelAndView exportGuest(@PathVariable("id")
    Long id, ModelAndView mav) {
        if (Argument.isNotPositive(id)) {
            return createErrorJsonMav("参数错误!", null);
        }
        // 设置路线Id,查询并转换对象
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(id));
        TravelCompanyDO companyDO = companyService.getById(lineDO.getcId());
        List<TravelOrderGuestFullDO> guestDOFulls = orderService.getByLIdAndPrice(id);

        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("company", companyDO);
        mav.addObject("guestFullDOs", guestDOFulls);
        mav.setViewName("word/exportGuest");
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        return mav;
    }

    /**
     * 查询剩余人数
     * 
     * @param mav
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryCount.htm", produces = "application/json")
    @ResponseBody
    public JsonResult queryCount(Long lId) {
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(lId));
        Integer shenyu = lineDO.getlRenCount() - lineDO.getlCrCount() - lineDO.getlXhCount();
        return JsonResultUtils.success(shenyu);
    }

    @RequestMapping(value = "/count_view.htm")
    public ModelAndView count_view(ModelAndView mav, Integer id) {
        mav.setViewName("order/count_view");
        if (id == null) {
            return mav;
        }
        TravelOrderQuery query = new TravelOrderQuery();
        query.setOrState(id);
        if (WebUserTools.getCompanyType().getValue() == CompanyTypeEnum.TOUR.getValue()) {
            query.setCustomCompanyId(WebUserTools.getCid());
        } else {
            query.setcId(WebUserTools.getCid());
        }
        List<TravelOrderFullDO> list = orderService.showOrderQuery(query);
        if (list == null || list.size() == 0) {
            return mav;
        }
        TravelOrderCountDO orderCountDO = new TravelOrderCountDO(0);
        for (TravelOrderFullDO orderFullDO : list) {
            orderCountDO.setOrAdultCount(orderFullDO.getOrAdultCount() + orderCountDO.getOrAdultCount());
            orderCountDO.setOrBabyCount(orderFullDO.getOrBabyCount() + orderCountDO.getOrBabyCount());
            orderCountDO.setOrChildCount(orderFullDO.getOrChildCount() + orderCountDO.getOrChildCount());
            orderCountDO.setOrPirceCount(orderFullDO.getOrPirceCount() + orderCountDO.getOrPirceCount());
        }
        orderCountDO.setAllCount(orderCountDO.getOrAdultCount() + orderCountDO.getOrBabyCount()
                                 + orderCountDO.getOrChildCount());
        mav.addObject("list", list);
        mav.addObject("orderCount", orderCountDO);
        return mav;
    }

    /**
     * 进入预定线路页面
     * 
     * @return
     */
    @RequestMapping(value = "/linecart.htm")
    public ModelAndView linecart(TravelLineVO trvo, ModelAndView mav) {
        if (trvo == null) {
            return createErrorJsonMav("error", null);
        }
        TravelLineDO lineDO = lineService.find(new TravelLineQuery(trvo.getlId()));
        Integer shenyu = lineDO.getlRenCount() - lineDO.getlCrCount() - lineDO.getlXhCount();
        Integer[] ren = trvo.getlPeople();
        Integer yudin = ren[0] + ren[1];
        if (yudin > shenyu) {
            return createErrorJsonMav("超出总人数", null);
        }

        mav.addObject("line", new TravelLineVO(lineDO));
        mav.addObject("people", trvo.getlPeople());
        mav.addObject("cType", WebUserTools.getCompanyType().getValue());
        mav.addObject("member", memberService.getById(WebUserTools.getMid()));
        mav.setViewName("line/linecart");
        return mav;
    }

    /**
     * 进入查看订单详细页面
     * 
     * @return
     */
    @RequestMapping(value = "/orderdetails.htm")
    public ModelAndView showOrderDetails(Long id, ModelAndView mav) {
        TravelOrderFullDO order = orderService.find(new TravelOrderQuery(id));
        TravelLineDO line = lineService.getTravelLineById(order.getlId());
        List<TravelOrderGuestDO> guestList = orderService.getByOrId(id);
        mav.addObject("cType", WebUserTools.getCompanyType().getValue());
        mav.addObject("guests", guestList);
        mav.addObject("line", new TravelLineVO(line));
        mav.addObject("order", new TravelOrderFullVO(order));
        mav.setViewName("order/orderdetails");
        return mav;
    }

    /**
     * 添加订单方法
     * 
     * @return
     */
    @RequestMapping(value = "/addOrder.htm", produces = "application/json")
    @ResponseBody
    public JsonResult addOrder(TravelOrderDO orderVO, @FormBean(value = "guestVOs")
    TravelOrderGuestDO[] guestVOs) {
        // 数据校验
        ArrayUtils.removeNullElement(guestVOs);
        if (Argument.isEmptyArray(guestVOs)) {
            return JsonResultUtils.error("订单游客人数不能为零!");
        }
        if (Argument.isNotPositive(orderVO.getlId())) {
            return JsonResultUtils.error("参数错误!数据格式错误!");
        }
        TravelLineDO line = lineService.find(new TravelLineQuery(orderVO.getlId()));
        if (line == null) {
            return JsonResultUtils.error("数据不存在!");
        }
        Integer shenyu = line.getlRenCount() - line.getlCrCount() - line.getlXhCount();
        Integer yudin = orderVO.getOrAdultCount() + orderVO.getOrChildCount();
        if (yudin > shenyu) {
            return JsonResultUtils.error("超出总人数!");
        }
        // 数据初始化
        if (WebUserTools.getCompanyType().getValue() == CompanyTypeEnum.TOUR.getValue()) {
            orderVO.setCustomId(WebUserTools.getMid());
            orderVO.setCustomCompanyId(WebUserTools.getCid());
        } else if (WebUserTools.getCompanyType().getValue() == CompanyTypeEnum.ACCOUNT.getValue()) {
            orderVO.setmId(WebUserTools.getMid());
        }
        orderVO.setOrPirceCount(orderVO.getOrAdultCount() * orderVO.getOrFirstJcrPrice() + orderVO.getOrChildCount()
                                * orderVO.getOrFirstJxhPrice() + orderVO.getOrBabyCount() * orderVO.getOrFirstJyPrice());
        orderVO.setOrState(OrderStateEnum.UN_CONFIRM.getValue());
        for (TravelOrderGuestDO guestVO : guestVOs) {
            guestVO.setgDangFangPrice(orderVO.getOrDangFangPrice());
            if (StringUtils.isEmpty(guestVO.getgName()) && !Argument.isPositive(orderVO.getOrState())) {
                orderVO.setOrState(OrderStateEnum.INCOMPLETE.getValue());
            }
        }
        TravelLineDO upLine = new TravelLineDO(orderVO.getlId());
        upLine.setlCrCount(line.getlCrCount() + orderVO.getOrAdultCount());
        upLine.setlXhCount(line.getlXhCount() + orderVO.getOrChildCount());
        upLine.setlYCount(line.getlYCount() + orderVO.getOrBabyCount());

        return (JsonResult) bizCommonService.transactionDoAction(ADD_ORDER_CALLBACK, this, orderVO, guestVOs, upLine);
    }

    /**
     * 修改订单方法
     * 
     * @return
     */
    @AuthorityPolicy(authorityTypes = { Right.MODIFY_ORDER })
    @RequestMapping(value = "/updateOrder.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateOrder(TravelOrderDO orderVO, @FormBean(value = "guestVOs")
    TravelOrderGuestDO[] guestVOs) {
        // 数据校验
        ArrayUtils.removeNullElement(guestVOs);
        if (Argument.isEmptyArray(guestVOs)) {
            return JsonResultUtils.error("订单游客人数不能为零!");
        }
        if (Argument.isNotPositive(orderVO.getOrId()) || Argument.isNotPositive(orderVO.getlId())) {
            return JsonResultUtils.error("参数错误!数据格式错误!");
        }
        TravelOrderDO order = orderService.getById(orderVO.getOrId());
        TravelLineDO line = lineService.getTravelLineById(orderVO.getlId());
        if (order == null || line == null) {
            return JsonResultUtils.error("数据不存在!");
        }
        // 数据初始化
        List<TravelOrderGuestDO> addList = new ArrayList<TravelOrderGuestDO>();
        List<TravelOrderGuestDO> upList = new ArrayList<TravelOrderGuestDO>();
        orderVO.setOrState(OrderStateEnum.UN_CONFIRM.getValue());
        orderVO.setOrAdultCount(order.getOrAdultCount());
        orderVO.setOrChildCount(order.getOrChildCount());
        orderVO.setOrBabyCount(order.getOrBabyCount());
        TravelLineDO upLine = new TravelLineDO(orderVO.getlId(), line.getlCrCount(), line.getlXhCount(),
                                               line.getlYCount());
        for (TravelOrderGuestDO guest : guestVOs) {
            if (StringUtils.isEmpty(guest.getgName()) && !Argument.isPositive(orderVO.getOrState())) {
                orderVO.setOrState(OrderStateEnum.INCOMPLETE.getValue());
            }
            if (!Argument.isNotPositive(guest.getgId())) {
                upList.add(guest);
                continue;
            }
            guest.setOrId(orderVO.getOrId());
            addList.add(guest);
            if (guest.getgType() == GuestAgeTypeEnum.ADULT.value) {
                orderVO.setOrAdultCount(orderVO.getOrAdultCount() + 1);
                upLine.setlCrCount(upLine.getlCrCount() + 1);
            } else if (guest.getgType() == GuestAgeTypeEnum.CHILDREN.value) {
                orderVO.setOrChildCount(orderVO.getOrChildCount() + 1);
                upLine.setlXhCount(upLine.getlXhCount() + 1);
            } else if (guest.getgType() == GuestAgeTypeEnum.BABY.value) {
                orderVO.setOrBabyCount(orderVO.getOrBabyCount() + 1);
                upLine.setlYCount(upLine.getlYCount() + 1);
            }
        }
        return (JsonResult) bizCommonService.transactionDoAction(UPDATE_ORDER_CALLBACK, this, orderVO, upLine, addList,
                                                                 upList);
    }

    /**
     * 删除订单游客方法
     * 
     * @return
     */
    @RequestMapping(value = "/deleteOrder.htm", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteOrder(Long id) {
        // 数据校验
        if (Argument.isNotPositive(id)) {
            return JsonResultUtils.error("参数错误!数据格式错误!");
        }
        TravelOrderGuestDO guest = orderService.getTravelOrderGuestById(id);
        if (guest == null || Argument.isNotPositive(guest.getOrId())) {
            return JsonResultUtils.error("数据不存在!");
        }
        TravelOrderDO order = orderService.getTravelOrderById(guest.getOrId());
        if (order == null || Argument.isNotPositive(order.getlId())) {
            return JsonResultUtils.error("数据不存在!");
        }
        TravelLineDO line = lineService.getTravelLineById(order.getlId());
        if (line == null) {
            return JsonResultUtils.error("数据不存在!");
        }
        // 数据初始化
        TravelOrderDO orderDO = new TravelOrderDO(order.getOrId());
        TravelLineDO lineDO = new TravelLineDO(line.getlId());
        if (guest.getgType() == 0) {
            orderDO.setOrAdultCount(order.getOrAdultCount() - 1);
            lineDO.setlCrCount(line.getlCrCount() - 1);
        } else if (guest.getgType() == 1) {
            orderDO.setOrChildCount(order.getOrChildCount() - 1);
            lineDO.setlXhCount(line.getlXhCount() - 1);
        } else if (guest.getgType() == 2) {
            orderDO.setOrBabyCount(order.getOrBabyCount() - 1);
            lineDO.setlYCount(line.getlYCount() - 1);
        }
        return (JsonResult) bizCommonService.transactionDoAction(DELETE_ORDER_CALLBACK, this, id, orderDO, lineDO);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object callback(int actionType, Object... params) {
        if (actionType == ADD_ORDER_CALLBACK) {
            TravelOrderDO order = (TravelOrderDO) params[0];
            TravelOrderGuestDO[] guestList = (TravelOrderGuestDO[]) params[1];
            TravelLineDO line = (TravelLineDO) params[2];

            orderService.addTravelOrder(order);

            for (TravelOrderGuestDO guestVO : guestList) {
                guestVO.setOrId(order.getOrId());
            }
            TravelOrderDO upOrder = new TravelOrderDO(order.getOrId(), order.getOrId());
            orderService.addTravelOrderGuest(guestList);
            orderService.updateTravelOrder(upOrder);
            lineService.updateTravelLine(line);
            List<TravelServiceDO> serviceDOs = companyService.list(new TravelServiceQuery(order.getcId()));
            List<String> mobiles = new ArrayList<String>();
            for (TravelServiceDO travelServiceDO : serviceDOs) {
				if(travelServiceDO.getsIsReceive() == 1){
					mobiles.add(travelServiceDO.getsMobile());
				}
			}
            logger.debug("发送信息");
            PushSMSUtils.getInstance().sendNewOrderSMS(upOrder.getOrOrderId(), mobiles.toArray(new String[mobiles.size()]));
            return JsonResultUtils.success("预定成功!");
        } else if (actionType == UPDATE_ORDER_CALLBACK) {
            TravelOrderDO orderDO = (TravelOrderDO) params[0];
            TravelLineDO lineDO = (TravelLineDO) params[1];
            List<TravelOrderGuestDO> addList = (List<TravelOrderGuestDO>) params[2];
            List<TravelOrderGuestDO> upList = (List<TravelOrderGuestDO>) params[3];

            if (Argument.isNotEmpty(addList)) {
                lineService.updateTravelLine(lineDO);
                orderService.addTravelOrderGuest(addList.toArray(new TravelOrderGuestDO[addList.size()]));
            }
            if (Argument.isNotEmpty(upList)) {
                orderService.updateTravelOrderGuest(upList.toArray(new TravelOrderGuestDO[upList.size()]));
            }
            orderService.updateTravelOrder(orderDO);

            // 查找新对象
            TravelOrderDO newOrder = orderService.getById(orderDO.getOrId());
            // 判断对象值得改变
            Map<String, String> map = BeanUtils.fieldEditable(orderDO, newOrder);
            TravelOperationLogDO operationLogDO = new TravelOperationLogDO(LogTableEnum.ORDERLOG.value,
                                                                           orderDO.getOrId(), orderDO.getOrOrderId(),
                                                                           map.get("oldString"), map.get("newString"),
                                                                           WebUserTools.getMid(), WebUserTools.getCid());
            // 添加日志
            operationLogService.insertTravelOperationLog(operationLogDO);
            if(orderDO.getOrMobile() != null){
            	logger.debug("发送信息");
            	PushSMSUtils.getInstance().sendOrderModifySMS(newOrder.getOrOrderId(), orderDO.getOrMobile());
            }
            return JsonResultUtils.success("更新成功!");
        } else if (actionType == DELETE_ORDER_CALLBACK) {
            Long id = (Long) params[0];
            TravelOrderDO order = (TravelOrderDO) params[1];
            TravelLineDO line = (TravelLineDO) params[2];

            orderService.deleteTravelOrderGuest(id);
            orderService.updateTravelOrder(order);
            lineService.updateTravelLine(line);
            return JsonResultUtils.success(id, "删除成功!");
        }
        return JsonResultUtils.success("成功!");
    }
    
    /***
     * 导出文档
     * 
     * @param id
     * @param mav
     * @return
     */
    @RequestMapping(value = "/printDocOrder/{id}.htm")
    @ExportWordFile(value = "OrderEnter")
    public ModelAndView ExportWord(@PathVariable
    Long id, ModelAndView mav) {
        // 订单
        TravelOrderDO order = orderService.getById(id);
        // 游客名单
        List<TravelOrderGuestDO> guestlist = orderService.getByOrId(id);
        // 线路
        TravelLineDO line = lineService.getTravelLineById(order.getlId());
        TravelLineVO lines = new TravelLineVO(line);
        // 查询行程
        TravelRouteQuery query = new TravelRouteQuery();
        query.setlId(lines.getlId());
        List<TravelRouteDO> routelist = lineService.list(query);
        lines.setRoutelist(routelist);
        // 公司
        TravelCompanyDO comp = companyService.getById(order.getcId());
        mav.addObject("comp", comp);
        mav.addObject("line", lines);
        mav.addObject("order", order);
        mav.addObject("list", guestlist);
        mav.getModel().put(CustomVelocityLayoutView.USE_LAYOUT, "false");
        mav.setViewName("word/OrderEnter");
        return mav;
    }
}
