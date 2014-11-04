/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.app.biz.dao.TravelColumnDao;
import com.zb.app.biz.dao.TravelCompanyDao;
import com.zb.app.biz.dao.TravelLineColumnDao;
import com.zb.app.biz.dao.TravelSiteDao;
import com.zb.app.biz.domain.CompanyColumnDO;
import com.zb.app.biz.domain.TravelColumnDO;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelLineColumnDO;
import com.zb.app.biz.domain.TravelSiteCoreDO;
import com.zb.app.biz.domain.TravelSiteDO;
import com.zb.app.biz.domain.TravelSiteFullDO;
import com.zb.app.biz.query.TravelColumnQuery;
import com.zb.app.biz.query.TravelSiteQuery;
import com.zb.app.biz.service.event.SiteEvent;
import com.zb.app.biz.service.interfaces.SiteService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.notify.NotifyListener;
import com.zb.app.common.notify.NotifyService;
import com.zb.app.common.notify.event.EventConfig;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.result.Result;

/**
 * @author zxc Jul 8, 2014 4:52:57 PM
 */
@SuppressWarnings("unchecked")
@Service
public class TravelSiteServiceImpl implements SiteService {

    @Autowired
    private NotifyService                    notifyService;

    @Autowired
    private TravelCompanyDao                 travelCompanyDao;

    @Autowired
    private TravelLineColumnDao              travelLineColumnDao;
    @Autowired
    private TravelSiteDao                    travelSiteDao;
    @Autowired
    private TravelColumnDao                  travelColumnDao;

    private static AtomicBoolean             initialized        = new AtomicBoolean(false);
    private static AtomicBoolean             initsuccess        = new AtomicBoolean(false);

    // cId,TravelSiteFullDO
    static Map<Long, List<TravelSiteFullDO>> siteLocalMapCache  = new ConcurrentHashMap<Long, List<TravelSiteFullDO>>();
    static List<TravelSiteFullDO>            siteLocalListCache = java.util.Collections.synchronizedList(new ArrayList<TravelSiteFullDO>());

    @PostConstruct
    public void init() {
        if (initialized.compareAndSet(false, true)) {
            Result siteRegist = notifyService.regist(new NotifyListener() {

                @EventConfig(events = { EventType.siteAdd, EventType.siteDelete, EventType.siteUpdate,
                        EventType.bindColumn, EventType.unBindColumn })
                public void siteEvent(SiteEvent event) {
                    handle(event);
                }
            });
            if (siteRegist.isFailed()) {
                logger.error("TravelSiteServiceImpl init siteRegist 【failed!】");
            } else {
                logger.error("TravelSiteServiceImpl init siteRegist 【success!】");
            }

            synchronized (TravelSiteServiceImpl.class) {
                logger.error("start init sitefull localCache!");
                ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
                ses.scheduleAtFixedRate(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            doInitTravelSiteFullCache();
                        } catch (Throwable e) {
                            logger.error("TravelSiteServiceImpl init error:", e);
                        }
                    }

                }, 0, 1 * 60, TimeUnit.MINUTES);
                logger.error("init sitefull localCache finish!");
            }
        }
        initsuccess.set(true);
    }

    /**
     * 初始化TravelSiteFull本地缓存
     */
    private void doInitTravelSiteFullCache() {
        siteLocalMapCache.clear();
        siteLocalListCache.clear();
        // 初始化siteLocalListCache缓存
        List<TravelSiteFullDO> siteFulllist = travelSiteDao.getSiteFullCore4All();
        if (siteFulllist == null || siteFulllist.size() == 0) {
            return;
        }
        siteLocalListCache.addAll(siteFulllist);

        // 初始化siteLocalMapCache缓存
        List<TravelCompanyDO> companyList = travelCompanyDao.list();
        if (companyList == null || companyList.size() == 0) {
            return;
        }
        for (TravelCompanyDO company : companyList) {
            if (company == null || company.getcId() == null) {
                continue;
            }
            Long cId = company.getcId();
            List<TravelSiteFullDO> list = travelSiteDao.getSiteFullBycId(cId);
            if (list == null || list.size() == 0) {
                continue;
            }
            siteLocalMapCache.put(cId, list);
        }
        logger.debug("TravelSiteServiceImpl doInitTravelSiteFullCache: do initTravelSiteFullCache");
    }

    /**
     * 根据公司ID查询TravelSiteFullDO信息,关联4张表
     */
    @Override
    public List<TravelSiteFullDO> getSiteFull(Long cId) {
        if (Argument.isNotPositive(cId)) {
            return Collections.<TravelSiteFullDO> emptyList();
        }
        if (!siteLocalMapCache.isEmpty() && siteLocalMapCache.containsKey(cId)) {
            return siteLocalMapCache.get(cId);
        }
        List<TravelSiteFullDO> list = travelSiteDao.getSiteFullBycId(cId);
        return list == null ? Collections.<TravelSiteFullDO> emptyList() : list;
    }

    @Override
    public List<TravelSiteFullDO> getSiteFullCore4All() {
        if (!siteLocalListCache.isEmpty()) {
            return siteLocalListCache;
        }
        List<TravelSiteFullDO> list = travelSiteDao.getSiteFullCore4All();
        return list == null ? Collections.<TravelSiteFullDO> emptyList() : list;
    }

    /**
     * 根据公司ID查询TravelSiteFullDO信息,关联4张表
     */
    @Override
    public List<TravelSiteCoreDO> getSiteCoreAll() {
        List<TravelSiteCoreDO> list = travelSiteDao.getSiteCore4All();
        return list == null ? Collections.<TravelSiteCoreDO> emptyList() : list;
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////站点管理表(TravelSiteDO)
    // ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<TravelSiteDO> list(TravelSiteQuery query) {
        if (query == null) {
            return Collections.<TravelSiteDO> emptyList();
        }
        return travelSiteDao.list(query);
    }

    @Override
    public List<TravelSiteDO> listTravelSite() {
        List<TravelSiteDO> list = travelSiteDao.list();
        return list == null ? Collections.<TravelSiteDO> emptyList() : list;
    }

    @Override
    public PaginationList<TravelSiteDO> listPagination(TravelSiteQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelSiteDO>) Collections.<TravelSiteDO> emptyList();
        }
        return (PaginationList<TravelSiteDO>) travelSiteDao.paginationList(query, iPages);
    }

    @Override
    public TravelSiteDO getTravelSiteById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelSiteDao.getById(id);
    }

    // 添加站点
    @Override
    public Integer insertTravelSite(TravelSiteDO ts) {
        if (ts.getsState() == null) {
            ts.setsState(0);
        }
        if (ts.getsToid() == null) {
            ts.setsToid(0l);
        }
        Integer count = travelSiteDao.insert(ts);
        if (count == null || count == 0) {
            return 0;
        }
        notifySiteEvent(new SiteEvent(EventType.siteAdd, ts.getsId()));
        logger.debug("add site event happen,sid={},time={}", ts.getsId(), new Date());
        return ts.getsId().intValue();
    }

    // 添加出港点
    @Override
    public Integer insertTravelSite(TravelSiteDO ts, Long sToId) {
        if (ts == null) {
            return null;
        }
        if (ts.getsState() == null) {
            ts.setsState(0);
        }
        ts.setsToid(sToId == null ? 0l : sToId);
        Integer count = travelSiteDao.insert(ts);
        if (count == null || count == 0) {
            return 0;
        }
        notifySiteEvent(new SiteEvent(EventType.siteAdd, ts.getsToid()));
        logger.debug("add chugang event happen,chuid={},time={}", ts.getsToid(), new Date());
        return ts.getsId().intValue();
    }

    @Override
    public boolean updateTravelSite(TravelSiteDO ts) {
        if (ts == null) {
            return false;
        }
        boolean isSuccess = travelSiteDao.updateById(ts);
        if (isSuccess) {
            notifySiteEvent(new SiteEvent(EventType.siteUpdate, ts.getsId()));
            logger.debug("update site event happen,chuid={},time={}", ts.getsId(), new Date());
        }
        return isSuccess;
    }

    @Override
    public boolean deleteTravelSite(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        boolean isSuccess = travelSiteDao.deleteById(id);
        if (isSuccess) {
            notifySiteEvent(new SiteEvent(EventType.siteDelete, id));
            logger.debug("delete site event happen,chuid={},time={}", id, new Date());
        }
        return isSuccess;
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////专线与公司关联表，公司所属专线(TravelLineColumnDO)
    // ////////////////////////////////////////////////////////////////////////////////////////

    public List<TravelLineColumnDO> list(Long zId, Long cId) {
        if (Argument.isNotPositive(cId) && Argument.isNotPositive(zId)) {
            return Collections.<TravelLineColumnDO> emptyList();
        }
        return travelLineColumnDao.getByCidAndZid(cId, zId);
    }

    @Override
    public List<TravelLineColumnDO> getTravelLineColumnByCid(Long cId) {
        if (Argument.isNotPositive(cId)) {
            return Collections.<TravelLineColumnDO> emptyList();
        }
        return travelLineColumnDao.getByCid(cId);
    }

    @Override
    public List<TravelLineColumnDO> listTravelLineColumn() {
        return travelLineColumnDao.list();
    }

    @Override
    public TravelLineColumnDO getTravelLineColumnById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelLineColumnDao.getById(id);
    }

    @Override
    public Integer insertTravelLineColumn(TravelLineColumnDO ts) {
        if (ts == null) {
            return 0;
        }
        Integer count = travelLineColumnDao.insert(ts);
        if (count == null || count == 0) {
            return 0;
        }
        notifySiteEvent(new SiteEvent(EventType.bindColumn, null, ts.getzId(), ts.getcId()));
        logger.debug("bind column event happen,cId={},zId={},time={}", ts.getcId(), ts.getzId(), new Date());
        return ts.getLcId().intValue();
    }

    @Override
    public boolean updateTravelLineColumn(TravelLineColumnDO ts) {
        if (ts == null) {
            return false;
        }
        return travelLineColumnDao.updateById(ts);
    }

    @Override
    public boolean deleteTravelLineColumn(Long id) {
        if (id == null) {
            return false;
        }
        boolean isSuccess = travelLineColumnDao.deleteById(id);
        if (isSuccess) {
            notifySiteEvent(new SiteEvent(EventType.unBindColumn, null, null, null));
            logger.debug("unBind column event happen,LCId={},time={}", id, new Date());
        }
        return isSuccess;
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    // //////////站点下专线表，专线分类
    // ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Integer insert(TravelColumnDO travelColumnDO) {
        if (travelColumnDO == null) {
            return null;
        }
        if (travelColumnDO.getzState() == null) {
            travelColumnDO.setzState(0);
        }
        if (travelColumnDO.getzDesc() == null || travelColumnDO.getzDesc() == 0) {
            travelColumnDO.setzDesc(0);
        }
        Integer count = travelColumnDao.insert(travelColumnDO);
        if (count == null || count == 0) {
            return 0;
        }
        notifySiteEvent(new SiteEvent(EventType.siteAdd, null, travelColumnDO.getzId()));
        logger.debug("add zhuanxian event happen,zId={},time={}", travelColumnDO.getzId(), new Date());
        return travelColumnDO.getzId().intValue();
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        boolean isSuccess = travelColumnDao.deleteById(id);
        if (isSuccess) {
            notifySiteEvent(new SiteEvent(EventType.siteDelete, null, id));
            logger.debug("delete zhuanxian event happen,zId={},time={}", id, new Date());
        }
        return isSuccess;
    }

    @Override
    public List<TravelColumnDO> listQuery(TravelColumnQuery query) {
        if (query == null) {
            return Collections.<TravelColumnDO> emptyList();
        }
        return travelColumnDao.list(query);
    }

    @Override
    public List<TravelColumnDO> list() {
        return travelColumnDao.list();
    }

    @Override
    public TravelColumnDO getTravelColumnById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelColumnDao.getById(id);
    }

    @Override
    public boolean update(TravelColumnDO travelColumnDO) {
        if (travelColumnDO == null) {
            return false;
        }
        boolean isSuccess = travelColumnDao.updateById(travelColumnDO);
        if (isSuccess) {
            notifySiteEvent(new SiteEvent(EventType.siteUpdate, null, travelColumnDO.getzId()));
            logger.debug("update zhuanxian event happen,zId={},time={}", travelColumnDO.getzId(), new Date());
        }
        return isSuccess;
    }

    private void notifySiteEvent(SiteEvent siteEvent) {
        notifyService.notify(siteEvent);
    }

    private void handle(SiteEvent event) {
        EventType eventType = event.getEventType();
        switch (eventType) {
            case siteAdd:
                doInitTravelSiteFullCache();
                break;

            case siteDelete:
                doInitTravelSiteFullCache();
                break;

            case siteUpdate:
                break;

            case bindColumn:
                Long cId = event.getcId();
                if (cId == null) {
                    break;
                }
                List<TravelSiteFullDO> cacheList = siteLocalMapCache.get(cId);
                List<TravelSiteFullDO> list = travelSiteDao.getSiteFullBycId(cId);
                if (cacheList != null && cacheList.size() > 0) {
                    siteLocalMapCache.remove(cId);
                }
                siteLocalMapCache.put(cId, list);
                break;

            case unBindColumn:
                doInitTravelSiteFullCache();
                break;

            default:
                break;
        }
    }

    @Override
    public List<CompanyColumnDO> getCompanyByzId(Long zId) {
        if (Argument.isNotPositive(zId)) {
            return Collections.<CompanyColumnDO> emptyList();
        }
        return travelSiteDao.getCompanyByzId(zId);
    }
}
