/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.app.biz.cons.LineStateEnum;
import com.zb.app.biz.dao.TravelLineDao;
import com.zb.app.biz.dao.TravelRouteDao;
import com.zb.app.biz.dao.TravelTrafficDao;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelLineThinDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.domain.TravelSiteFullDO;
import com.zb.app.biz.domain.TravelTrafficDO;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.biz.query.TravelTrafficQuery;
import com.zb.app.biz.service.event.LineEvent;
import com.zb.app.biz.service.interfaces.LineService;
import com.zb.app.biz.service.interfaces.SiteService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.notify.NotifyListener;
import com.zb.app.common.notify.NotifyService;
import com.zb.app.common.notify.event.EventConfig;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.result.Result;
import com.zb.app.common.util.NumberParser;
import com.zb.app.common.util.SerialNumGenerator;
import com.zb.app.external.lucene.search.pojo.ProductSearchField;
import com.zb.app.web.vo.ColumnThinVO;

/**
 * @author zxc Jun 25, 2014 12:15:41 PM
 */
@Service
public class TravelLineServiceImpl implements LineService {

    @Autowired
    private TravelLineDao                  travelLineDao;
    @Autowired
    private TravelRouteDao                 travelRouteDao;
    @Autowired
    private TravelTrafficDao               trafficDao;
    @Autowired
    private SiteService                    siteService;
    @Autowired
    private NotifyService                  notifyService;

    private static Map<Long, List<String>> siteDestinationCache = new ConcurrentHashMap<Long, List<String>>();

    @PostConstruct
    public void init() {
        Result lineRegist = notifyService.regist(new NotifyListener() {

            @EventConfig(events = { EventType.lineAdd })
            public void lineEvent(LineEvent event) {
                handle(event);
            }
        });
        if (lineRegist.isFailed()) {
            logger.error("TravelLineServiceImpl init lineRegist 【failed!】");
        } else {
            logger.error("TravelLineServiceImpl init lineRegist 【success!】");
        }

        // ///////////////////////////////////////////////////////////////////////////////
        synchronized (TravelLineServiceImpl.class) {
            logger.error("start init siteDestination localCache!");
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    try {
                        doInitSiteDestinationCache();
                    } catch (Throwable e) {
                        logger.error("TravelLineServiceImpl init error:", e);
                    }
                }

            }, 0, 1 * 60, TimeUnit.MINUTES);
            logger.error("init siteDestination localCache finish!");
        }
    }

    protected void doInitSiteDestinationCache() {
        siteDestinationCache.clear();

        List<TravelSiteFullDO> siteCoreList = siteService.getSiteFullCore4All();
        // 出港点集合
        List<Long> cids = new ArrayList<Long>();
        for (TravelSiteFullDO sitefull : siteCoreList) {
            if (!cids.contains(sitefull.getcId()) && sitefull.getcId() != null) {
                cids.add(sitefull.getcId());
            }
        }
        // Map<出港点ID,专线集合>
        Map<Long, List<ColumnThinVO>> sids = new HashMap<Long, List<ColumnThinVO>>();
        for (Long cid : cids) {
            if (cid == null) {
                continue;
            }
            List<ColumnThinVO> list = new ArrayList<ColumnThinVO>();
            for (TravelSiteFullDO core : siteCoreList) {
                if (NumberParser.isEqual(cid, core.getcId()) && core.getzId() != null) {
                    ColumnThinVO col = new ColumnThinVO();
                    BeanUtils.copyProperties(col, core);
                    list.add(col);
                }
            }
            sids.put(cid, list);
        }
        // line
        for (Long cid : sids.keySet()) {
            List<String> citys = new ArrayList<String>();
            TravelLineQuery query = new TravelLineQuery();
            query.setlState(LineStateEnum.NORMAL.getValue());
            query.setzIds(CollectionUtils.getLongValueArrays(sids.get(cid), "zId"));
            List<TravelLineDO> list = travelLineDao.paginationList(query);
            for (TravelLineDO travelLineDO : list) {
                if (!citys.contains(travelLineDO.getlArrivalCity())) {
                    citys.add(travelLineDO.getlArrivalCity());
                }
            }
            siteDestinationCache.put(cid, citys);
        }
    }

    protected void handle(LineEvent event) {
        EventType eventType = event.getEventType();
        switch (eventType) {
            case lineAdd:
                if (Argument.isNotPositive(event.getlId())) {
                    logger.error("TravelLineServiceImpl Handle lineRegist 【failed!】,event.getlId() is null!");
                    break;
                }
                travelLineDao.updateById(new TravelLineDO(event.getlId(),
                                                          SerialNumGenerator.createProductSerNo(event.getlId())));
                logger.error("TravelLineServiceImpl Handle lineRegist 【success!】,event.getlId()={}!", event.getlId());

            default:
                break;
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelLineDO find(TravelLineQuery query) {
        if (query == null) {
            return null;
        }
        return travelLineDao.find(query);
    }

    @Override
    public List<TravelLineDO> list(TravelLineQuery query) {
        if (query == null) {
            return Collections.<TravelLineDO> emptyList();
        }
        return travelLineDao.list(query);
    }

    @Override
    public List<String> getCityByCid(Long cid) {
        return siteDestinationCache.get(cid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginationList<TravelLineDO> listPagination(TravelLineQuery query, IPageUrl... iPageUrls) {
        if (query == null) {
            return (PaginationList<TravelLineDO>) Collections.<TravelLineDO> emptyList();
        }
        return (PaginationList<TravelLineDO>) travelLineDao.paginationList(query, iPageUrls);
    }

    @Override
    public TravelLineDO getTravelLineById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelLineDao.getById(id);
    }

    @Override
    public Integer addTravelLine(TravelLineDO... travelLines) {
        if (travelLines == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(travelLines);
        if (Argument.isEmptyArray(travelLines)) {
            return 0;
        }
        Integer count = travelLineDao.insert(travelLines);
        if (travelLines.length == 1) {
            Long lId = travelLines[0].getlId();
            if (travelLines[0].getlTemplateState() == 0) {
                travelLineDao.updateById(new TravelLineDO(
                                                          travelLines[0].getlId(),
                                                          SerialNumGenerator.createProductSerNo(travelLines[0].getlId())));
            }
            return lId.intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public Integer count(TravelLineQuery query) {
        return travelLineDao.count(query);
    }

    @Override
    public boolean updateTravelLine(TravelLineDO travelLine) {
        if (travelLine == null) {
            return false;
        }
        if (travelLine.getlId() == null) {
            return false;
        }
        return travelLineDao.updateById(travelLine);
    }

    @Override
    public boolean deleteTravelLine(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelLineDao.deleteById(id);
    }

    @Override
    public PaginationList<TravelLineDO> listGroup(TravelLineQuery query, IPageUrl... iPageUrls) {
        return (PaginationList<TravelLineDO>) travelLineDao.listGroup(query, iPageUrls);
    }

    @Override
    public Integer updateLines(Long[] lids, TravelLineThinDO trdo) {
        if (lids == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lIds", lids);
        map.put("trdo", trdo);
        return travelLineDao.updateLines(map);
    }

    @Override
    public Integer countByGroup(TravelLineQuery query) {
        return travelLineDao.countByGroup(query);
    }

    @Override
    public PaginationList<ProductSearchField> listProductSearch(TravelLineQuery query) {
        PaginationList<ProductSearchField> fieldlist = travelLineDao.listProductSearch(query);
        for (ProductSearchField productSearchField : fieldlist) {
            List<TravelRouteDO> routelist = travelRouteDao.list(new TravelRouteQuery(productSearchField.getlId()));
            String rCar = "", rContent = "";
            int i = 1;
            for (TravelRouteDO travelRouteDO : routelist) {
                rCar += "第" + i + "天" + travelRouteDO.getrCar();
                rContent += "第" + i + "天" + travelRouteDO.getrContent();
                i++;
            }
            productSearchField.setrCar(rCar);
            productSearchField.setrContent(rContent);
        }
        return fieldlist;
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelRouteDO find(TravelRouteQuery query) {
        if (query == null) {
            return null;
        }
        return travelRouteDao.find(query);
    }

    @Override
    public List<TravelRouteDO> list(TravelRouteQuery query) {
        if (query == null) {
            return Collections.<TravelRouteDO> emptyList();
        }
        return travelRouteDao.list(query);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public PaginationList<TravelRouteDO> listPagination(TravelRouteQuery query) {
        if (query == null) {
            return (PaginationList<TravelRouteDO>) Collections.<TravelRouteDO> emptyList();
        }
        return (PaginationList<TravelRouteDO>) travelRouteDao.paginationList(query);
    }

    @Override
    public TravelRouteDO getTravelRouteById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelRouteDao.getById(id);
    }

    @Override
    public Integer addTravelRoute(TravelRouteDO... travelRoutes) {
        if (travelRoutes == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(travelRoutes);
        if (Argument.isEmptyArray(travelRoutes)) {
            return 0;
        }
        Integer count = travelRouteDao.insert(travelRoutes);
        if (travelRoutes.length == 1) {
            return travelRoutes[0].getrId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public boolean updateTravelRoute(TravelRouteDO travelRoute) {
        if (travelRoute == null) {
            return false;
        }
        if (travelRoute.getrId() == null) {
            return false;
        }
        return travelRouteDao.updateById(travelRoute);
    }

    @Override
    public boolean deleteTravelRoute(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelRouteDao.deleteById(id);
    }

    @Override
    public boolean deleteTravelRouteByLineid(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelRouteDao.deleteByLineId(id);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<TravelTrafficDO> listTravelTraffic() {
        return trafficDao.list();
    }

    @Override
    public TravelTrafficDO getTravelTrafficById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return trafficDao.getById(id);
    }

    @Override
    public Integer insertTravelTraffic(TravelTrafficDO... ts) {
        if (ts == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(ts);
        if (Argument.isEmptyArray(ts)) {
            return 0;
        }
        Integer count = trafficDao.insert(ts);
        if (ts.length == 1) {
            return ts[0].gettId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public boolean updateTravelTraffic(TravelTrafficDO ts) {
        if (ts == null) {
            return false;
        }
        return trafficDao.updateById(ts);
    }

    @Override
    public boolean deleteTravelTraffic(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return trafficDao.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginationList<TravelTrafficDO> listPagination(TravelTrafficQuery query, IPageUrl... pageUrls) {
        if (query == null) {
            return (PaginationList<TravelTrafficDO>) Collections.<TravelTrafficDO> emptyList();
        }
        return trafficDao.paginationList(query, pageUrls);
    }
}
