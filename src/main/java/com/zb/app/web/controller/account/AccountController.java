/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.account;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.biz.cons.MessageTypeEnum;
import com.zb.app.biz.cons.OrderStateEnum;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.domain.TravelMessageDO;
import com.zb.app.biz.domain.TravelOrderCountByStateDO;
import com.zb.app.biz.query.TravelMessageQuery;
import com.zb.app.biz.query.TravelOrderQuery;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.core.lang.CollectionUtils.Grep;
import com.zb.app.common.result.JsonResultUtils;
import com.zb.app.common.result.JsonResultUtils.JsonResult;
import com.zb.app.web.controller.BaseController;
import com.zb.app.web.tools.WebUserTools;

/**
 * Account 系统信息,登录,权限控制
 * 
 * @author zxc Jun 17, 2014 6:02:47 PM
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT     = 20;

    @RequestMapping(value = "/home.htm")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("account/index");
        TravelOrderQuery orderQuery = new TravelOrderQuery();
        orderQuery.setcId(WebUserTools.getCid());
        List<TravelOrderCountByStateDO> zongList = orderService.countByOrState(orderQuery);
        Map<Integer, Integer> zongMap = new HashMap<Integer, Integer>();
        for (TravelOrderCountByStateDO travelOrderCountByStateDO : zongList) {
        	zongMap.put(travelOrderCountByStateDO.getOrState(), travelOrderCountByStateDO.getOrStateCount());
		}

        List<TravelMessageDO> list = messageService.list(new TravelMessageQuery(MessageTypeEnum.NEW_ORDER_UNREAD,
                                                                                WebUserTools.getCid()));

        TravelMemberDO member = memberService.getById(WebUserTools.getMid());
        TravelMemberDO memberDO = new TravelMemberDO();
        memberDO.setmId(member.getmId());
        memberDO.setmLastLoginTime(new Date());
        memberService.update(memberDO);
        mav.addObject("confirm", zongMap.get(OrderStateEnum.CONFIRM.getValue()));
        mav.addObject("unConfirm", zongMap.get(OrderStateEnum.UN_CONFIRM.getValue()));
        mav.addObject("newOrder", list.size());
        mav.addObject("mLastLoginTime", member.getmLastLoginTime());
        mav.addObject("mPic", member.getmPic());
        return mav;
    }

    /**
     * 自动提示
     * 
     * @param q
     * @param limit
     * @return
     */
    @RequestMapping(value = "/autocompleteCompany.htm", produces = "application/json")
    @ResponseBody
    public JsonResult autocompleteCompany(String q, Integer limit) {
        List<TravelCompanyDO> list = companyService.list();
        final List<Long> cIdList = orderService.getTourCompany(new TravelOrderQuery(WebUserTools.getCid(),
                                                                                    OrderStateEnum.CONFIRM));
        if (cIdList == null || cIdList.size() == 0) {
            return JsonResultUtils.success("暂无数据!");
        }
        CollectionUtils.remove(list, new Grep<TravelCompanyDO>() {

            @Override
            public boolean grep(TravelCompanyDO comapny) {
                return !cIdList.contains(comapny.getcId());
            }

        });

        List<Map<String, ?>> mapList = CollectionUtils.toMapList(list, "cId", "cName", "cSpell");

        // StringBuilder sb = new StringBuilder();
        String cond = q == null ? StringUtils.EMPTY : q;
        cond = cond.toLowerCase();
        // String temp;
        int maxSize = getLimit(limit);
        int size = 0;

        List<Map<String, ?>> result = new LinkedList<Map<String, ?>>();
        String property = cond.matches("[a-zA-Z]+") ? "cSpell" : "cName";
        for (Map<String, ?> map : mapList) {
            Object cName = null;
            for (Entry<String, ?> entry : map.entrySet()) {
                if (StringUtils.equals(entry.getKey(), property)) {
                    cName = entry.getValue();
                }
            }
            if (cond.matches("[a-zA-Z]+") ? StringUtils.startsWith((String) cName, cond) : StringUtils.containsIgnoreCase((String) cName,
                                                                                                                          cond)) {
                result.add(map);
                size++;
                if (size > maxSize) {
                    break;
                }
            }
        }
        return JsonResultUtils.success(result);
    }

    /**
     * 得到条目个数
     * 
     * @param limit
     * @return
     */
    private int getLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return DEFAULT_LIMIT;
        }
        return limit < MAX_LIMIT ? limit : MAX_LIMIT;
    }
}
