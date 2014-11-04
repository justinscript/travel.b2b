/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelSiteCoreDO;
import com.zb.app.biz.domain.TravelSiteFullDO;
import com.zb.app.biz.service.interfaces.CompanyService;
import com.zb.app.biz.service.interfaces.SiteService;
import com.zb.app.common.core.lang.Argument;
import com.zb.app.common.core.lang.BeanUtils;
import com.zb.app.common.core.lang.CollectionUtils;
import com.zb.app.common.core.lang.CollectionUtils.Grep;
import com.zb.app.common.util.JsonUtils;
import com.zb.app.common.util.NumberParser;
import com.zb.app.web.vo.ChufaCoreVO;
import com.zb.app.web.vo.ChufaFullVO;
import com.zb.app.web.vo.ChufaThinVO;
import com.zb.app.web.vo.ColumnThinVO;
import com.zb.app.web.vo.SiteCoreVO;
import com.zb.app.web.vo.SiteFullVO;
import com.zb.app.web.vo.SiteOnlyVO;

/**
 * @author zxc Aug 5, 2014 4:57:26 PM
 */
@Component
public class SiteCacheTools {

    @Autowired
    private SiteService    siteService;

    @Autowired
    private CompanyService companyService;

    public ChufaFullVO getChugangByChugangId(Long chugangId) {
        if (Argument.isNotPositive(chugangId)) {
            return null;
        }
        List<TravelSiteFullDO> siteall = siteService.getSiteFullCore4All();
        if (Argument.isEmpty(siteall)) {
            return null;
        }
        List<ChufaFullVO> chufaFullList = parseChufaFullvoList(siteall);
        for (ChufaFullVO chufaFullVO : chufaFullList) {
            if (NumberParser.isEqual(chufaFullVO.getcId(), chugangId)) {
                return chufaFullVO;
            }
        }
        return null;
    }

    public ChufaFullVO getSiteAndChugang(Long siteId, Long chugangId) {
        if (Argument.isNotPositive(siteId) || Argument.isNotPositive(chugangId)) {
            return null;
        }
        List<ChufaFullVO> chufaFullList = this.getSiteBysId(siteId);
        for (ChufaFullVO chufaFull : chufaFullList) {
            if (NumberParser.isEqual(chufaFull.getcId(), chugangId)) {
                return chufaFull;
            }
        }
        return null;
    }

    public List<ChufaFullVO> getSiteBysId(Long siteId) {
        if (Argument.isNotPositive(siteId)) {
            return Collections.<ChufaFullVO> emptyList();
        }
        List<TravelSiteFullDO> siteall = siteService.getSiteFullCore4All();
        Map<Long, List<TravelSiteFullDO>> map = CollectionUtils.toLongListMap(siteall, "sId");
        if (map != null && map.size() > 0) {
            siteall = map.get(siteId);
        }
        return parseChufaFullvoList(siteall);
    }

    private List<ChufaFullVO> parseChufaFullvoList(List<TravelSiteFullDO> siteCoreList) {
        if (siteCoreList == null || siteCoreList.size() == 0) {
            return Collections.<ChufaFullVO> emptyList();
        }
        CollectionUtils.remove(siteCoreList, new Grep<TravelSiteFullDO>() {

            @Override
            public boolean grep(TravelSiteFullDO site) {
                return site == null || Argument.isNotPositive(site.getcId());
            }
        });
        Set<Long> siteSet = new HashSet<Long>();
        List<ChufaFullVO> siteCorevoList = new LinkedList<ChufaFullVO>();
        for (TravelSiteFullDO siteFull : siteCoreList) {
            if (siteFull.getcId() != null && !siteSet.contains(siteFull.getcId())) {
                ChufaFullVO siteFullvo = new ChufaFullVO();
                BeanUtils.copyProperties(siteFullvo, siteFull);
                siteFullvo.setColumnMap(new LinkedHashMap<Integer, List<ColumnThinVO>>());
                siteCorevoList.add(siteFullvo);
                siteSet.add(siteFull.getcId());
            }

            Map<Long, ChufaFullVO> siteFullMap = CollectionUtils.toLongMap(siteCorevoList, "cId");
            if (siteFullMap.get(siteFull.getcId()) != null) {
                Map<Integer, List<ColumnThinVO>> chufaMap = siteFullMap.get(siteFull.getcId()).getColumnMap();

                ColumnThinVO columnThin = new ColumnThinVO();
                BeanUtils.copyProperties(columnThin, siteFull);
                if (!chufaMap.containsKey(columnThin.getzCat())) {
                    List<ColumnThinVO> chufaList = new LinkedList<ColumnThinVO>();
                    chufaList.add(columnThin);
                    chufaMap.put(columnThin.getzCat(), chufaList);
                }
                List<ColumnThinVO> chufaList = chufaMap.get(siteFull.getzCat());
                if (chufaList == null) {
                    chufaList = new LinkedList<ColumnThinVO>();
                }
                if (siteFull.getcId() != null && !chufaList.contains(columnThin)) {
                    chufaList.add(columnThin);
                }
            }
        }
        return siteCorevoList;
    }

    public List<SiteFullVO> getAllSite() {
        List<TravelSiteFullDO> siteCoreList = siteService.getSiteFullCore4All();
        return parseSiteFullvoList(siteCoreList);
    }

    public List<SiteCoreVO> getSiteCoreList() {
        List<TravelSiteCoreDO> siteCoreList = siteService.getSiteCoreAll();
        List<SiteCoreVO> siteCorevoList = parseSiteCorevoList(siteCoreList);
        return siteCorevoList;
    }

    public List<ChufaCoreVO> getSiteCoreCityList() {
        List<TravelSiteCoreDO> siteCoreList = siteService.getSiteCoreAll();
        if (Argument.isEmpty(siteCoreList)) {
            return Collections.<ChufaCoreVO> emptyList();
        }
        CollectionUtils.remove(siteCoreList, new Grep<TravelSiteCoreDO>() {

            @Override
            public boolean grep(TravelSiteCoreDO siteCore) {
                return siteCore == null || Argument.isNotPositive(siteCore.getcId());
            }
        });
        Map<Long, TravelSiteCoreDO> chufaCoreMap = CollectionUtils.toLongMap(siteCoreList, "cId");
        List<ChufaCoreVO> chufaCoreList = new ArrayList<ChufaCoreVO>();
        for (TravelSiteCoreDO siteCoreDO : chufaCoreMap.values()) {
            chufaCoreList.add(new ChufaCoreVO(siteCoreDO.getcId(), siteCoreDO.getcName(), siteCoreDO.getcSort()));
        }
        return chufaCoreList;
    }

    public Map<Long, ChufaCoreVO> getOnlyChugangMap(Long siteId) {
        if (Argument.isNotPositive(siteId)) {
            return Collections.emptyMap();
        }
        List<TravelSiteFullDO> siteCoreList = siteService.getSiteFullCore4All();
        Map<Long, ChufaCoreVO> resultMap = new LinkedHashMap<Long, ChufaCoreVO>();
        for (TravelSiteFullDO siteFull : siteCoreList) {
            if (resultMap.containsKey(siteFull.getcId())) {
                continue;
            }
            if (!NumberParser.isEqual(siteFull.getsId(), siteId)) {
                continue;
            }
            ChufaCoreVO chugangOnly = new ChufaCoreVO();
            BeanUtils.copyProperties(chugangOnly, siteFull);
            resultMap.put(siteFull.getcId(), chugangOnly);
        }
        return resultMap;
    }

    public Map<Long, ChufaCoreVO> getOnlyChufaMap() {
        List<TravelSiteFullDO> siteCoreList = siteService.getSiteFullCore4All();
        List<SiteFullVO> onlySiteList = parseSiteFullvoList(siteCoreList);
        Map<Long, ChufaCoreVO> resultMap = new LinkedHashMap<Long, ChufaCoreVO>();
        for (SiteFullVO siteFull : onlySiteList) {
            if (Argument.isEmpty(siteFull.getChufaList())) {
                continue;
            }
            for (ChufaThinVO chufa : siteFull.getChufaList()) {
                if (resultMap.containsKey(chufa.getcId())) {
                    continue;
                }
                resultMap.put(chufa.getcId(), new ChufaCoreVO(chufa.getcId(), chufa.getcName(), chufa.getcSort()));
            }
        }
        return resultMap;
    }

    public Map<Long, SiteOnlyVO> getOnlySiteMap() {
        List<TravelSiteFullDO> siteCoreList = siteService.getSiteFullCore4All();
        List<SiteFullVO> onlySiteList = parseSiteFullvoList(siteCoreList);
        Map<Long, SiteOnlyVO> resultMap = new LinkedHashMap<Long, SiteOnlyVO>();
        for (SiteFullVO siteFull : onlySiteList) {
            if (resultMap.containsKey(siteFull.getsId())) {
                continue;
            }
            SiteOnlyVO siteOnly = new SiteOnlyVO();
            BeanUtils.copyProperties(siteOnly, siteFull);
            resultMap.put(siteFull.getsId(), siteOnly);
        }
        return resultMap;
    }

    public List<SiteOnlyVO> getOnlySite() {
        List<TravelSiteFullDO> siteCoreList = siteService.getSiteFullCore4All();
        List<SiteFullVO> onlySiteList = parseSiteFullvoList(siteCoreList);
        List<SiteOnlyVO> resultList = new ArrayList<SiteOnlyVO>();
        for (SiteFullVO siteFull : onlySiteList) {
            SiteOnlyVO siteOnly = new SiteOnlyVO();
            BeanUtils.copyProperties(siteOnly, siteFull);
            resultList.add(siteOnly);
        }
        return resultList;
    }

    public List<SiteFullVO> getCurrentSite(Long cId) {
        if (Argument.isNotPositive(cId)) {
            return Collections.<SiteFullVO> emptyList();
        }
        List<TravelSiteFullDO> siteall = siteService.getSiteFull(cId);
        return parseSiteFullvoList(siteall);
    }

    public List<SiteFullVO> getAllSite4Cat(Integer cat) {
        if (cat == null) {
            return Collections.<SiteFullVO> emptyList();
        }
        List<TravelSiteFullDO> siteall = siteService.getSiteFullCore4All();
        Map<Long, List<TravelSiteFullDO>> map = CollectionUtils.toLongListMap(siteall, "zCat");
        if (map != null && map.size() > 0) {
            siteall = map.get(cat.longValue());
        }
        return parseSiteFullvoList(siteall);
    }

    public List<TravelSiteFullDO> getTourSiteList() {
        TravelCompanyDO comp = companyService.getById(WebUserTools.getCid());
        List<TravelSiteFullDO> sitefullList = siteService.getSiteFullCore4All();
        if (comp == null || sitefullList == null || sitefullList.size() == 0) {
            return Collections.<TravelSiteFullDO> emptyList();
        }

        String json = comp.getcCityList();
        if (StringUtils.isEmpty(json) || JsonUtils.isBadJson(json)) {
            return Collections.<TravelSiteFullDO> emptyList();
        }

        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, new TypeToken<Object>() {
        }.getType());
        if (list == null || list.size() == 0) {
            return Collections.<TravelSiteFullDO> emptyList();
        }

        Long[] cIdArrays = parseCid(new HashSet<Long>(), list);
        if (Argument.isEmptyArray(cIdArrays)) {
            return Collections.<TravelSiteFullDO> emptyList();
        }

        final Set<Long> chufaIdSet = new HashSet<Long>(Arrays.asList(cIdArrays));
        CollectionUtils.remove(sitefullList, new Grep<TravelSiteFullDO>() {

            @Override
            public boolean grep(TravelSiteFullDO siteFull) {
                return !chufaIdSet.contains(siteFull.getcId());
            }
        });
        return sitefullList;
    }

    public Map<Long, SiteFullVO> getTourSiteMap() {
        TravelCompanyDO comp = companyService.getById(WebUserTools.getCid());
        List<TravelSiteFullDO> sitefullList = siteService.getSiteFullCore4All();
        if (comp == null || sitefullList == null || sitefullList.size() == 0) {
            return Collections.emptyMap();
        }

        String json = comp.getcCityList();
        if (StringUtils.isEmpty(json) || JsonUtils.isBadJson(json)) {
            return Collections.emptyMap();
        }

        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, new TypeToken<Object>() {
        }.getType());
        if (list == null || list.size() == 0) {
            return Collections.emptyMap();
        }

        Long[] cIdArrays = parseCid(new HashSet<Long>(), list);
        if (Argument.isEmptyArray(cIdArrays)) {
            return Collections.emptyMap();
        }

        final Set<Long> chufaIdSet = new HashSet<Long>(Arrays.asList(cIdArrays));
        CollectionUtils.remove(sitefullList, new Grep<TravelSiteFullDO>() {

            @Override
            public boolean grep(TravelSiteFullDO siteFull) {
                return !chufaIdSet.contains(siteFull.getcId());
            }
        });
        List<SiteFullVO> resultList = parseSiteFullvoList(sitefullList);
        return CollectionUtils.toLongMap(resultList, "sId");
    }

    private List<SiteCoreVO> parseSiteCorevoList(List<TravelSiteCoreDO> siteCoreList) {
        Set<Long> siteSet = new HashSet<Long>();
        List<SiteCoreVO> siteCorevoList = new LinkedList<SiteCoreVO>();
        for (TravelSiteCoreDO siteCore : siteCoreList) {
            if (siteCore.getsId() != null && !siteSet.contains(siteCore.getsId())) {
                SiteCoreVO siteCorevo = new SiteCoreVO();
                BeanUtils.copyProperties(siteCorevo, siteCore);
                siteCorevo.setChufaList(new LinkedList<ChufaCoreVO>());
                siteCorevoList.add(siteCorevo);
                siteSet.add(siteCore.getsId());
            }

            Map<Long, SiteCoreVO> siteFullMap = CollectionUtils.toLongMap(siteCorevoList, "sId");
            List<ChufaCoreVO> chufaThinList = siteFullMap.get(siteCore.getsId()).getChufaList();
            Map<Long, ChufaCoreVO> chufaCoreMap = CollectionUtils.toLongMap(chufaThinList, "cId");

            if (siteCore.getcId() != null && !chufaCoreMap.keySet().contains(siteCore.getcId())) {
                chufaThinList.add(new ChufaCoreVO(siteCore.getcId(), siteCore.getcName(), siteCore.getcSort()));
            }
        }
        return siteCorevoList;
    }

    private Long[] parseCid(Set<Long> cIdSet, List<?> list) {
        for (Object obj : list) {
            if (obj == null) {
                continue;
            }
            if (obj instanceof Map<?, ?>) {
                for (Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                    if (entry == null || entry.getKey() == null || entry.getValue() == null) {
                        continue;
                    }
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (key instanceof String && StringUtils.equalsIgnoreCase("City", (String) key) && value != null
                        && value instanceof List<?>) {
                        for (Object val : (List<?>) value) {
                            if (val instanceof Map<?, ?>) {
                                String cId = (String) ((Map<?, ?>) val).get("CId");
                                if (StringUtils.isNotEmpty(cId)) {
                                    cIdSet.add(Long.valueOf(cId));
                                }
                            }
                        }
                    }
                }
            }
        }
        return cIdSet.toArray(new Long[cIdSet.size()]);
    }

    @SuppressWarnings("unused")
    private Long[] parseCid(Set<Long> cIdSet, Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            if (entry == null || entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Number) {
                if (StringUtils.equalsIgnoreCase("cId", (String) key)) {
                    cIdSet.add((Long) entry.getValue());
                }
            }
            if (value instanceof String) {
                if (StringUtils.equalsIgnoreCase("cId", (String) key)) {
                    cIdSet.add(Long.valueOf((String) entry.getValue()));
                }
            }
            if (value instanceof Map<?, ?>) {
                cIdSet.addAll(Arrays.asList(parseCid(cIdSet, (Map<?, ?>) value)));
            }
        }
        return cIdSet.toArray(new Long[cIdSet.size()]);
    }

    private List<SiteFullVO> parseSiteFullvoList(List<TravelSiteFullDO> siteFullList) {
        Set<Long> siteSet = new HashSet<Long>();
        List<SiteFullVO> siteFullvoList = new LinkedList<SiteFullVO>();
        for (TravelSiteFullDO siteFull : siteFullList) {
            if (siteFull.getsId() != null && !siteSet.contains(siteFull.getsId())) {
                SiteFullVO siteFullvo = new SiteFullVO();
                BeanUtils.copyProperties(siteFullvo, siteFull);
                siteFullvo.setChufaList(new LinkedList<ChufaThinVO>());
                siteFullvoList.add(siteFullvo);
                siteSet.add(siteFull.getsId());
            }

            Map<Long, SiteFullVO> siteFullMap = CollectionUtils.toLongMap(siteFullvoList, "sId");

            List<ChufaThinVO> chufaThinList = siteFullMap.get(siteFull.getsId()).getChufaList();
            Map<Long, ChufaThinVO> chufaMap = CollectionUtils.toLongMap(chufaThinList, "cId");

            if (siteFull.getcId() != null && !chufaMap.keySet().contains(siteFull.getcId())) {
                ChufaThinVO chufaThinVO = new ChufaThinVO(siteFull.getcId(), siteFull.getcName(), siteFull.getcSort(),
                                                          new LinkedList<ColumnThinVO>());
                chufaThinList.add(chufaThinVO);
            }

            Map<Long, ChufaThinVO> chufaThinMap = CollectionUtils.toLongMap(chufaThinList, "cId");
            if (siteFull.getcId() == null) {
                continue;
            }
            List<ColumnThinVO> columnThinList = chufaThinMap.get(siteFull.getcId()).getColumnList();

            Map<Long, ColumnThinVO> columnThinMap = CollectionUtils.toLongMap(columnThinList, "zId");

            if (siteFull.getzId() != null && !columnThinMap.keySet().contains(siteFull.getzId())) {
                ColumnThinVO columnThinVO = new ColumnThinVO();
                BeanUtils.copyProperties(columnThinVO, siteFull);
                columnThinList.add(columnThinVO);
            }
        }
        return siteFullvoList;
    }
}
