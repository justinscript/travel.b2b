package com.zb.app.biz.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.app.biz.BaseTestCase;
import com.zb.app.biz.domain.TravelOrderDO;
import com.zb.app.biz.domain.TravelOrderGuestDO;
import com.zb.app.biz.service.interfaces.OrderService;

public class TravelOrderServiceTest extends BaseTestCase {

    @Autowired
    private OrderService service;

    // /////////////////////////////////////////////////////////////////////////////////////
    // /////订单测试
    // /////////////////////////////////////////////////////////////////////////////////////
    /***
     * 查询方法：OK
     */
    @Test
    public void testListTravelOrder() {
        List<TravelOrderDO> list = service.listTravelOrder();
        for (TravelOrderDO travelOrderDO : list) {
            logger.debug(travelOrderDO.toString());
        }
    }

    /***
     * 根据编号查询：
     */
    @Test
    public void testGetByTravelOrderById() {
        TravelOrderDO toDo = service.getTravelOrderById(21L);
        logger.debug(toDo.toString());
    }

    /***
     * 添加方法：OK
     */
    @Test
    public void testAddTravelOrder() {
        TravelOrderDO tr = new TravelOrderDO();
        int s = service.addTravelOrder(tr);
        logger.debug(s + "");
    }

    /***
     * 修改方法：OK
     */
    @Test
    public void testUpdateTravelOrder() {
        TravelOrderDO tr = new TravelOrderDO();
        tr.setOrId(Long.parseLong("999999"));
        tr.setOrName("肖伟");
        boolean b = service.updateTravelOrder(tr);
        logger.debug(b + "");
    }

    /***
     * 删除方法：OK
     */
    @Test
    public void testDeleteTravelOrder() {
        logger.debug("" + service.deleteTravelOrder(999999L));
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // /////订单游客测试
    // /////////////////////////////////////////////////////////////////////////////////////

    /***
     * 添加方法：OK
     */
    @Test
    public void testAddTravelOrderGuest() {
        TravelOrderGuestDO tg = new TravelOrderGuestDO();
        int s = service.addTravelOrderGuest(tg);
        logger.debug(s + "");
    }

    /***
     * 查询方法：OK
     */
    @Test
    public void testlistTravelOrderGuest() {
        List<TravelOrderGuestDO> list = service.listTravelOrderGuest();
        for (TravelOrderGuestDO travelOrderGuestDO : list) {
            logger.debug(travelOrderGuestDO.toString());
        }
    }

    /***
     * 根据编号查询：OK
     */
    @Test
    public void testGetByTravelorderGuestById() {
        TravelOrderGuestDO do1 = service.getTravelOrderGuestById(9L);
        logger.debug(do1.toString());
    }

    /***
     * 修改方法:OK
     */
    @Test
    public void testUpdateTravelOrderGuest() {
        TravelOrderGuestDO tDo = new TravelOrderGuestDO();
        tDo.setgId(Long.parseLong("9"));
        tDo.setgMobile("18274298902");
        boolean a = service.updateTravelOrderGuest(tDo);
        logger.debug("" + a);
    }

    /***
     * 删除方法：OK
     */
    @Test
    public void testDeleteTravelOrderGuest() {
        logger.debug("" + service.deleteTravelOrderGuest(23L));
    }

    @Override
    public void onSetUp() throws Exception {
        if (service == null) {
            service = (OrderService) getBean("travelOrderServiceImpl");
        }
    }
}
