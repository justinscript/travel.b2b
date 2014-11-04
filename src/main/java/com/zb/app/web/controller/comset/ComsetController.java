/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.controller.comset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zb.app.comset.info.UnitInfo;
import com.zb.app.comset.manager.ResourceHelper;
import com.zb.app.web.controller.BaseController;

/**
 * COMSET系统调用时间分析(DB层,CACHE层,Service层)
 * 
 * @author zxc Jul 23, 2014 11:59:20 AM
 */
@Controller
@RequestMapping("/comset")
public class ComsetController extends BaseController {

    @RequestMapping(value = "/show.htm")
    public ModelAndView show(ModelAndView model, final String sortcount, final String clear) throws Exception {

        if ("true".equals(clear)) {
            ResourceHelper.clear();
        }
        List<UnitInfo> list = ResourceHelper.getRecordUnitInfo();

        Collections.sort(list, new Comparator<UnitInfo>() {

            public int compare(UnitInfo o1, UnitInfo o2) {
                long v1 = o1.getCount();
                long v2 = o2.getCount();
                if ("false".equals(sortcount)) {
                    v1 = o1.getPeriod() / o1.getCount();
                    v2 = o2.getPeriod() / o2.getCount();
                }
                if (v1 < v2) {
                    return 1;
                } else if (v1 > v2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        if (list.size() > 100) {
            List<UnitInfo> newlist = new ArrayList<UnitInfo>(100);
            for (int i = 0; i < 100; i++) {
                newlist.add(list.get(i));
            }
            model.addObject("comsetList", newlist);
        } else {
            model.addObject("comsetList", list);
        }
        model.addObject("sort_type", "false".equals(sortcount) ? 0 : 1);
        model.setViewName("show");
        return model;
    }
}
