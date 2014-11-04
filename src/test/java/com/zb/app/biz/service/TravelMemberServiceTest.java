/*
 * Copyright 2011-2016 import org.junit.Before; import org.junit.Test; import com.zb.app.biz.domain.TravelMemberDO;
 * import com.zb.app.biz.service.impl.TravelMemberService; l not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service;

import java.util.List;

import org.junit.Test;

import com.zb.app.biz.BaseTestCase;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.service.interfaces.MemberService;

/**
 * @author zxc Jun 18, 2014 3:49:00 PM
 */
public class TravelMemberServiceTest extends BaseTestCase {

    private MemberService service;

    @Override
    public void onSetUp() throws Exception {
        if (service == null) {
            service = (MemberService) getBean("travelMemberServiceImpl");
        }
    }

    @Test
    public void testAdd() {
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        travelMemberDO.setmName("周忠");
        travelMemberDO.setcId(15L);
        travelMemberDO.setmState(0);
        service.insert(travelMemberDO);
    }

    @Test
    public void testQuery() {
        List<TravelMemberDO> list = service.list();
        for (TravelMemberDO travelMemberDO : list) {
            System.out.println(travelMemberDO.getmId());
        }
    }

    @Test
    public void testUpdate() {
        TravelMemberDO travelMemberDO = new TravelMemberDO();
        travelMemberDO.setmId(14L);
        travelMemberDO.setmName("WOSHIXXX");
        service.update(travelMemberDO);
    }

    @Test
    public void testDelete() {
        service.delete(14L);
    }

    @Test
    public void testGet() {
        // TravelMemberDO travelMemberDO = service.getById(13);
        // System.out.println(travelMemberDO.getmId());
    }
}
