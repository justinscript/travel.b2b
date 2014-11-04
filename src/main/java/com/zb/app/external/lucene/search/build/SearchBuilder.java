/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;

import com.zb.app.common.core.SpringContextAware;
import com.zb.app.common.pagination.PaginationList;
import com.zb.app.common.result.Result;
import com.zb.app.common.util.DateViewTools;
import com.zb.app.external.lucene.search.build.base.DataFetcher;
import com.zb.app.external.lucene.search.cons.SearchTypeEnum;
import com.zb.app.external.lucene.search.service.AppBaseSearch;
import com.zb.app.external.lucene.solr.exception.SolrUnSupportException;
import com.zb.app.external.lucene.solr.service.SearchServiceConfig;
import com.zb.app.external.lucene.solr.service.VersionableSearch;

/**
 * @author zxc Sep 2, 2014 1:56:39 PM
 */
public class SearchBuilder implements SearchServiceConfig {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Result build(SearchTypeEnum searchType, Integer version, boolean isForceBuild, String info,
                               DataFetcher fetcher) {
        VersionableSearch<?, ?> search = getSearch(searchType);
        String coreName = ((AppBaseSearch) search).getCoreName(version);
        Counter counter = newCountIfHasPermission(isForceBuild, coreName, info);
        if (counter == null) {
            return Result.failed("Other Builder is Running...");
        }
        try {
            PaginationList data = fetcher.fetch(null);
            search.delAll(version);
            while (data != null && data.size() > 0 && counter.checkVersion()) {
                search.indexWithOutDel(version, data);
                counter.increment(data.size());
                data = fetcher.fetch(data.getQuery());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failed(ExceptionUtils.getFullStackTrace(e));
        } finally {
            // 如果版本号是对的就保存最好的记过
            if (counter.checkVersion()) {
                counter.stop();
            }
        }
        return Result.success(counter.sumarry());
    }

    private static Counter newCountIfHasPermission(boolean isForceBuild, String corename, String info) {
        boolean force = isForceBuild;
        Counter current = Counter.getCurrent(corename);
        boolean isOtherRunning = current != null && current.end == null;
        if (force || !isOtherRunning) {
            return Counter.newCounter(corename, info).addHistory(current);
        } else {
            return null;
        }
    }

    public static class Counter implements Serializable, Comparable<Counter> {

        private static final long serialVersionUID = 1L;
        private String            info;
        private String            key;
        private Date              start;
        public Date               end;
        private int               count            = 0;
        private int               incrementSize    = 0;
        private Long              version;
        public List<Counter>      history          = new ArrayList<Counter>();

        public static Counter getCurrent(String key) {
            Serializable serializable = null;
            return (Counter) serializable;
        }

        public Counter addHistory(Counter counter) {
            if (counter == null) {
                return this;
            }
            // add
            if (counter.history.size() > 0) {
                this.history.addAll(counter.history);
            }
            if (counter.end == null) {
                counter.end = new Date();
            }
            this.history.add(counter);
            // sort
            Collections.<Counter> sort(this.history);
            while (this.history.size() > 1) {
                history.remove(history.size() - 1);
            }
            return this;
        }

        public Counter increment(int size) {
            count += size;
            incrementSize += size;
            if (incrementSize > 100) {
                save();
                incrementSize = 0;
            }
            return this;
        }

        private void save() {
            // client.put(CacheType.build_search, this.key, new UdasObj(this));
        }

        public static Counter newCounter(String key, String source) {
            Counter c = new Counter();
            c.count = 0;
            c.start = new Date();
            c.key = key;
            c.version = System.nanoTime();
            c.info = source + ":" + c.version;
            c.save();
            return c;
        }

        public Counter stop() {
            this.end = new Date();
            // (但是如果是版本不对而退出的，就不用保存了）
            if (checkVersion()) {
                this.save();
                if (StringUtils.contains(key, "fbiz.search.word") && end != null) {
                    logger.error(String.format("word search bulid done!current time %s;key=%s",
                                               DateViewTools.formatFullDate(end), key));
                }
            }
            return this;
        }

        public String sumarry() {
            Date _end = end == null ? new Date() : end;
            long seconds = (_end.getTime() - start.getTime()) / DateUtils.MILLIS_PER_SECOND;
            return String.format("%s【%s】,Start【%s】 ,Cost【%d】seconds 。indexed 【 %d 】Status:【%s】 ", key, info,
                                 DateViewTools.formatFullDate(start), seconds, count,
                                 end != null ? "Done@" + DateViewTools.formatFullDate(end) : "working");
        }

        public boolean checkVersion() {
            Counter current = getCurrent(key);
            if (current == null || current.version == null) {
                return true;
            }
            return current.version.equals(version);

        }

        @Override
        public int compareTo(Counter arg0) {
            int bigger = 1, smaller = -1;
            if (arg0 == null || arg0.start == null) {
                return smaller;
            }
            if (this.start == null) {
                return bigger;
            }
            return this.start.compareTo(arg0.start) > 0 ? smaller : bigger;
        }
    }

    public static VersionableSearch<?, ?> getSearch(SearchTypeEnum searchType) {
        try {
            @SuppressWarnings("rawtypes")
            Map beansOfType = SpringContextAware.getBeansOfType(searchType.getType());
            for (Object object : beansOfType.values()) {
                // return first;
                return (VersionableSearch<?, ?>) object;
            }
        } catch (Exception e) {
            throw new SolrUnSupportException(String.format("Not Suppert SearchType 【%s】", searchType.getDesc()));
        }
        // can't happend
        return null;
    }
}
