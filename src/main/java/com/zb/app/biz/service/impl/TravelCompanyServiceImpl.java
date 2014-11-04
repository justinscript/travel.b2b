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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.app.biz.dao.TravelBlackListDao;
import com.zb.app.biz.dao.TravelCompanyDao;
import com.zb.app.biz.dao.TravelServiceDao;
import com.zb.app.biz.dao.TravelServiceSiteDao;
import com.zb.app.biz.domain.TravelBlackListDO;
import com.zb.app.biz.domain.TravelBlackListFullDO;
import com.zb.app.biz.domain.TravelBlackListThinDO;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelServiceDO;
import com.zb.app.biz.domain.TravelServiceSiteDO;
import com.zb.app.biz.query.TravelBlackListQuery;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelServiceQuery;
import com.zb.app.biz.query.TravelServiceSiteQuery;
import com.zb.app.biz.service.event.CompanyEvent;
import com.zb.app.biz.service.interfaces.CompanyService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.ArrayUtils;
import com.zb.app.common.notify.NotifyListener;
import com.zb.app.common.notify.NotifyService;
import com.zb.app.common.notify.event.EventConfig;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.pagination.PaginationParser.IPageUrl;
import com.zb.app.common.result.Result;

/**
 * @author zxc Aug 20, 2014 5:11:53 PM
 */
@Service
@SuppressWarnings("unchecked")
public class TravelCompanyServiceImpl implements CompanyService {

    @Autowired
    private TravelCompanyDao                  travelCompanyDao;
    @Autowired
    private TravelServiceDao                  travelServiceDao;
    @Autowired
    private TravelServiceSiteDao              travelServiceSiteDao;
    @Autowired
    private TravelBlackListDao                travelBlackListDao;

    @Autowired
    private NotifyService                     notifyService;

    private static Map<Long, TravelCompanyDO> travelCompanyCacheMap = new ConcurrentHashMap<Long, TravelCompanyDO>();

    @PostConstruct
    public void cronCache() {
        Result companyRegist = notifyService.regist(new NotifyListener() {

            @EventConfig(events = { EventType.companyAdd, EventType.companyDelete, EventType.companyUpdate })
            public void companyEvent(CompanyEvent event) {
                handle(event);
            }
        });
        if (companyRegist.isFailed()) {
            logger.error("TravelCompanyServiceImpl init companyRegist 【failed!】");
        } else {
            logger.error("TravelCompanyServiceImpl init companyRegist 【success!】");
        }

        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
        newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    init();
                } catch (Throwable e) {
                    logger.error("init travelCompanyCacheMap error", e);
                }
            }

        }, 0, 12, TimeUnit.HOURS);
    }

    private void handle(CompanyEvent event) {
        EventType eventType = event.getEventType();
        switch (eventType) {
            case companyAdd:
                TravelCompanyDO travelCompany = event.getTravelCompany();
                if (travelCompany != null && travelCompany.getcId() != null) {
                    Long cid = travelCompany.getcId();
                    if (!travelCompanyCacheMap.containsKey(cid)) {
                        travelCompanyCacheMap.put(cid, travelCompany);
                    }
                }
                break;

            case companyDelete:
                Long cid = event.getcId();
                if (cid != null) {
                    if (travelCompanyCacheMap.containsKey(cid)) {
                        travelCompanyCacheMap.remove(cid);
                    }
                }
                break;

            case companyUpdate:
                Long _cid = event.getcId();
                TravelCompanyDO _travelCompany = event.getTravelCompany();
                if (_travelCompany != null && _cid != null) {
                    if (travelCompanyCacheMap.containsKey(_cid)) {
                        travelCompanyCacheMap.remove(_cid);
                        travelCompanyCacheMap.put(_cid, _travelCompany);
                    }
                }
                break;

            default:
                break;
        }
    }

    private void notifyCompanyEvent(CompanyEvent event) {
        notifyService.notify(event);
    }

    private void init() {
        logger.error("start init TravelCompany CacheMap!");
        travelCompanyCacheMap.clear();
        TravelCompanyQuery query = new TravelCompanyQuery();
        query.setPageSize(2000);
        // query.setcState(1);
        do {
            PaginationList<TravelCompanyDO> result = showCompanyPagination(query);
            if (result == null || result.isEmpty()) {
                break;
            }
            for (TravelCompanyDO company : result) {
                if (company != null && StringUtils.isNotBlank(company.getcName())) {
                    Long cid = company.getcId();
                    if (!travelCompanyCacheMap.containsKey(cid)) {
                        travelCompanyCacheMap.put(cid, company);
                    }
                }
            }
        } while (query.toNextPage());
        logger.error("init TravelCompany CacheMap finish!");
    }

    private TravelCompanyDO getTravelCompany(Long cid) {
        if (Argument.isNotPositive(cid)) {
            return null;
        }
        if (travelCompanyCacheMap.isEmpty()) {
            return null;
        }
        TravelCompanyDO company = travelCompanyCacheMap.get(cid);
        if (company == null) {
            company = _getById(cid);
            if (company != null) {
                travelCompanyCacheMap.put(cid, company);
            }
        }
        return company;
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 公司表 TravelCompanyDO
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    /**
     * 添加用户
     * 
     * @param travelMemberDO
     * @return
     */
    @Override
    public Integer insert(TravelCompanyDO travelCompanyDO) {
        if (travelCompanyDO == null) {
            return null;
        }
        Integer count = travelCompanyDao.insert(travelCompanyDO);
        if (count == null || count == 0) {
            return 0;
        }
        notifyCompanyEvent(new CompanyEvent(EventType.companyAdd, travelCompanyDO));
        logger.debug("add company event happen,cid={},time={}", travelCompanyDO.getcId(), new Date());
        return travelCompanyDO.getcId().intValue();
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        boolean isSuccess = travelCompanyDao.deleteById(id);
        if (isSuccess) {
            notifyCompanyEvent(new CompanyEvent(EventType.companyDelete, id.longValue(), null));
            logger.debug("delete company event happen,cid={},time={}", id, new Date());
        }
        return isSuccess;
    }

    /**
     * 分页查询
     */
    @SuppressWarnings("deprecation")
    @Override
    public List<TravelCompanyDO> listPage(TravelCompanyQuery query) {
        if (query == null) {
            return Collections.emptyList();
        }
        return travelCompanyDao.listPagination(query);
    }

    @Override
    public List<TravelCompanyDO> list() {
        if (!travelCompanyCacheMap.isEmpty()) {
            List<TravelCompanyDO> list = new ArrayList<TravelCompanyDO>();
            for (Entry<Long, TravelCompanyDO> entry : travelCompanyCacheMap.entrySet()) {
                list.add(entry.getValue());
            }
            return list;
        }
        return travelCompanyDao.list();
    }

    @Override
    public boolean update(TravelCompanyDO travelCompanyDO) {
        if (travelCompanyDO == null) {
            return false;
        }
        boolean isSuccess = travelCompanyDao.updateById(travelCompanyDO);
        if (isSuccess) {
            notifyCompanyEvent(new CompanyEvent(EventType.companyUpdate, travelCompanyDO.getcId(),
                                                travelCompanyDao.getById(travelCompanyDO.getcId())));
            logger.debug("update company event happen,cid={},time={}", travelCompanyDO.getcId(), new Date());
        }
        return isSuccess;
    }

    @Override
    public TravelCompanyDO getByName(TravelCompanyQuery query) {
        if (query == null) {
            return null;
        }
        return travelCompanyDao.getByName(query);
    }

    private TravelCompanyDO _getById(Long cId) {
        if (Argument.isNotPositive(cId)) {
            return null;
        }
        return travelCompanyDao.getById(cId);
    }

    @Override
    public TravelCompanyDO getById(Long cId) {
        if (Argument.isNotPositive(cId)) {
            return null;
        }
        return getTravelCompany(cId);
    }

    @Override
    public PaginationList<TravelCompanyDO> showCompanyPagination(TravelCompanyQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelCompanyDO>) Collections.<TravelCompanyDO> emptyList();
        }
        return (PaginationList<TravelCompanyDO>) travelCompanyDao.showCompanyPagination(query, iPages);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 黑名单表 TravelBlackListDO
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public TravelBlackListDO find(TravelBlackListQuery query) {
        if (query == null) {
            return null;
        }
        return travelBlackListDao.find(query);
    }

    @Override
    public List<TravelBlackListDO> list(TravelBlackListQuery query) {
        if (query == null) {
            return Collections.<TravelBlackListDO> emptyList();
        }
        return travelBlackListDao.list(query);
    }

    @Override
    public PaginationList<TravelBlackListFullDO> listPagination(TravelBlackListQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelBlackListFullDO>) Collections.<TravelBlackListFullDO> emptyList();
        }
        return (PaginationList<TravelBlackListFullDO>) travelBlackListDao.paginationList(query, iPages);
    }

    @Override
    public TravelBlackListDO getTravelBlackListById(Integer id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelBlackListDao.getById(id);
    }

    @Override
    public Integer addTravelBlackList(TravelBlackListDO... travels) {
        if (travels == null) {
            return 0;
        }
        return travelBlackListDao.insert(travels);
    }

    @Override
    public boolean updateTravelBlackList(TravelBlackListDO travel) {
        if (travel == null) {
            return false;
        }
        return travelBlackListDao.updateById(travel);
    }

    @Override
    public boolean deleteTravelBlackList(Integer id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelBlackListDao.deleteById(id);
    }

    @Override
    public Integer getBlackCount(TravelBlackListQuery travelBlackListQuery) {
        if (travelBlackListQuery == null) {
            return 0;
        }
        return travelBlackListDao.getBlackCount(travelBlackListQuery);
    }

    @Override
    public PaginationList<TravelBlackListThinDO> queryAllCompanyBlack(TravelBlackListQuery query, IPageUrl... iPages) {
        if (query == null) {
            return (PaginationList<TravelBlackListThinDO>) Collections.<TravelBlackListThinDO> emptyList();
        }
        return (PaginationList<TravelBlackListThinDO>) travelBlackListDao.queryAllCompanyBlack(query, iPages);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 客服表 TravelServiceDO
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Integer addService(TravelServiceDO... service) {
        if (service == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(service);
        if (Argument.isEmptyArray(service)) {
            return 0;
        }
        Integer count = travelServiceDao.insert(service);
        if (service.length == 1) {
            return service[0].getsId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public TravelServiceDO find(TravelServiceQuery query) {
        if (query == null) {
            return null;
        }
        return travelServiceDao.find(query);
    }

    @Override
    public List<TravelServiceDO> list(TravelServiceQuery query) {
        if (query == null) {
            return Collections.<TravelServiceDO> emptyList();
        }
        return travelServiceDao.list(query);
    }

    @Override
    public PaginationList<TravelServiceDO> listPagination(TravelServiceQuery query, IPageUrl... ipPages) {
        if (query == null) {
            return (PaginationList<TravelServiceDO>) Collections.<TravelServiceDO> emptyList();
        }
        return (PaginationList<TravelServiceDO>) travelServiceDao.paginationList(query, ipPages);
    }

    @Override
    public TravelServiceDO getServiceById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelServiceDao.getById(id);
    }

    @Override
    public boolean updateById(TravelServiceDO service) {
        if (service == null) {
            return false;
        }
        if (Argument.isNotPositive(service.getsId())) {
            return false;
        }
        return travelServiceDao.updateById(service);
    }

    @Override
    public boolean deleteServiceById(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return updateById(new TravelServiceDO());
    }

    @Override
    public boolean realDelService(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelServiceDao.deleteById(id);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// 客服站点表 TravelServiceSiteDO
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Integer addServiceSite(TravelServiceSiteDO... service) {
        if (service == null) {
            return 0;
        }
        ArrayUtils.removeNullElement(service);
        if (Argument.isEmptyArray(service)) {
            return 0;
        }
        Integer count = travelServiceSiteDao.insert(service);
        if (service.length == 1) {
            return service[0].getsId().intValue();
        }
        return count == 0 ? 0 : 1;
    }

    @Override
    public TravelServiceSiteDO find(TravelServiceSiteQuery query) {
        if (query == null) {
            return null;
        }
        return travelServiceSiteDao.find(query);
    }

    @Override
    public List<TravelServiceSiteDO> list(TravelServiceSiteQuery query) {
        if (query == null) {
            return Collections.<TravelServiceSiteDO> emptyList();
        }
        return travelServiceSiteDao.list(query);
    }

    @Override
    public PaginationList<TravelServiceSiteDO> listPagination(TravelServiceSiteQuery query, IPageUrl... ipPages) {
        if (query == null) {
            return (PaginationList<TravelServiceSiteDO>) Collections.<TravelServiceSiteDO> emptyList();
        }
        return (PaginationList<TravelServiceSiteDO>) travelServiceSiteDao.paginationList(query, ipPages);
    }

    @Override
    public TravelServiceSiteDO getServiceSiteById(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelServiceSiteDao.getById(id);
    }

    @Override
    public boolean updateById(TravelServiceSiteDO service) {
        if (service == null) {
            return false;
        }
        if (Argument.isNotPositive(service.getsId())) {
            return false;
        }
        return travelServiceSiteDao.updateById(service);
    }

    @Override
    public boolean deleteServiceSiteById(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return updateById(new TravelServiceSiteDO());
    }

    @Override
    public boolean realDelServiceSite(Long id) {
        if (Argument.isNotPositive(id)) {
            return false;
        }
        return travelServiceSiteDao.deleteById(id);
    }

    @Override
    public List<TravelServiceSiteDO> getServiceSiteBySId(Long id) {
        if (Argument.isNotPositive(id)) {
            return null;
        }
        return travelServiceSiteDao.getServiceSiteBySId(id);
    }

    @Override
    public Integer countByAccount(TravelCompanyQuery companyQuery) {
        return travelCompanyDao.countByAccount(companyQuery);
    }
}
