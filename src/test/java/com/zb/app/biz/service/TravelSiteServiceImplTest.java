package com.zb.app.biz.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.app.biz.BaseTestCase;
import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.domain.TravelLineColumnDO;
import com.zb.app.biz.domain.TravelSiteDO;
import com.zb.app.biz.service.interfaces.SiteService;

public class TravelSiteServiceImplTest extends BaseTestCase {

    @Autowired
    private SiteService service;

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////站点管理表(TravelSiteDO)
    // ////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testListTravelSite() {
        List<TravelSiteDO> list = service.listTravelSite();
        for (TravelSiteDO travelSiteDO : list) {
            logger.debug(travelSiteDO.toString());
        }
    }

    @Test
    public void testGetTravelSiteById() {
        logger.debug(service.getTravelSiteById(3l).toString());
    }

    @Test
    public void testInsertTravelSite() {
        TravelSiteDO ts = new TravelSiteDO();
        logger.debug(service.insertTravelSite(ts) + "");
    }

    @Test
    public void testUpdateTravelSite() {
        TravelSiteDO ts = new TravelSiteDO();
        ts.setsId(Long.parseLong("3"));
        ts.setsName("肖伟");
        logger.debug(service.updateTravelSite(ts) + "");
    }

    @Test
    public void testDeleteTravelSite() {
        logger.debug(service.deleteTravelSite(3l) + "");
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////专线与公司关联表，公司所属专线(TravelLineColumnDO)
    // ////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testListTravelLineColumn() {
        List<TravelLineColumnDO> list = service.listTravelLineColumn();
        for (TravelLineColumnDO travelLineColumnDO : list) {
            logger.debug(travelLineColumnDO.toString());
        }
    }

    @Test
    public void testGetTravelLineColumnById() {
        logger.debug(service.getTravelLineColumnById(1l).toString());
    }

    @Test
    public void testInsertTravelLineColumn() {
        TravelLineColumnDO tl = new TravelLineColumnDO();
        logger.debug(service.insertTravelLineColumn(tl) + "");
    }

    @Test
    public void testUpdateTravelLineColumn() {
        TravelLineColumnDO tl = new TravelLineColumnDO();
        tl.setLcId(Long.parseLong("1"));
        tl.setcId(Long.parseLong("2"));
        logger.debug(service.updateTravelLineColumn(tl) + "");
    }

    @Test
    public void testDeleteTravelLineColumn() {
        logger.debug(service.deleteTravelLineColumn(1l) + "");
    }

    @Override
    public void onSetUp() throws Exception {
        if (service == null) {
            service = (SiteService) getBean("travelSiteServiceImpl");
        }
    }

    @Test
    public void testAdd() {
        TravelColumnDO travelColumnDO = new TravelColumnDO();
        travelColumnDO.setzName("上海");
        travelColumnDO.setzState(0);
        service.insert(travelColumnDO);
    }

    @Test
    public void testQuery() {
        List<TravelColumnDO> list = service.list();
        for (TravelColumnDO travelColumnDO : list) {
            System.out.println(travelColumnDO.getzId());
        }
    }

    @Test
    public void testUpdate() {
        TravelColumnDO travelColumnDO = new TravelColumnDO();
        travelColumnDO.setzId(1L);
        travelColumnDO.setzName("WOSHIXXX");
        service.update(travelColumnDO);
    }

    @Test
    public void testDelete() {
        service.delete(1l);
    }
}
