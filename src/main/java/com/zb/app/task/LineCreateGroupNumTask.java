/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelLineThinDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.service.impl.TravelLineServiceImpl;
import com.zb.app.biz.service.interfaces.LineService;
import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.task.AbstractTask;
import com.zb.app.common.util.SerialNumGenerator;

/**
 * @author Administrator 2014-8-21 下午3:02:06
 */
@Component
public class LineCreateGroupNumTask extends AbstractTask {

    @Autowired
    private LineService lineService;

    public LineCreateGroupNumTask() {
        super();
    }

    @Override
    public void initTask() throws Exception {
        if (lineService == null) {
            lineService = (TravelLineServiceImpl) SpringContextAware.getBean("lineService");
        }
    }

    /**
     * 每隔十分钟执行一次
     */
    // @Scheduled(cron = "0 0/2 * * * ?")
    public void returnPointTask() {
        init();
    }

    @Override
    public void doTask() throws Exception {
        logger.debug("start do line create group num task!");
        TravelLineQuery query = new TravelLineQuery();
        query.setlDelState(1);
        query.setlTemplateState(LineTemplateEnum.Line.getValue());
        List<TravelLineDO> list = lineService.list(query);
        for (TravelLineThinDO dol : list) {
            lineService.updateTravelLine(new TravelLineDO(dol.getlId(),
                                                          SerialNumGenerator.createProductSerNo(dol.getlId())));
        }
    }
}
