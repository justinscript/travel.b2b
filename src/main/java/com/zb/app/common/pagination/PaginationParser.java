/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pagination;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zb.app.common.cons.SearchCons;

/**
 * 提供一些分页的方法,主要是针对使用缓存时的分页
 * 
 * @author zxc Jul 7, 2014 4:47:53 PM
 */
public class PaginationParser {

    public interface IPageUrl {

        /**
         * 不同实例的分页页面需要重写此方法
         * 
         * @param clazz
         * @return
         */
        public String parsePageUrl(Object... objs);
    }

    public static class DefaultIpageUrl implements IPageUrl {

        @Override
        public String parsePageUrl(Object... objs) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * @param page
     * @param pageSize
     * @param totalNum
     * @param iPageUrl URL策略
     * @return
     */
    public static PagesPagination getPaginationList(Integer page, int pageSize, int totalNum, IPageUrl iPageUrl) {
        PagesPagination pagesPagination = getPagination(page, pageSize, totalNum);
        initPages(pagesPagination, iPageUrl);
        return pagesPagination;
    }

    /**
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param totalNum 总记录数
     * @return
     */
    public static PagesPagination getPagination(Integer page, int pageSize, int totalNum) {
        return new PagesPagination(pageSize, page, totalNum);
    }

    // /**
    // * 初始化分页的页码URL
    // *
    // * @param pagination
    // * @param url
    // */
    // public static void initPages(PagesPagination pagination, IPageUrl url) {
    // initPages(-1, pagination, url);
    // }
    //
    // /**
    // * 初始化分页的页码URL
    // *
    // * @param pagination
    // * @param url
    // * @param data
    // */
    // public static void initPages(PagesPagination pagination, IPageUrl url, Object... data) {
    // initPages(-1, pagination, url, data);
    // }

    /**
     * 生成分页信息,implements IPageUrl
     * 
     * @param pagination
     */
    public static void initPages(PagesPagination pagination, IPageUrl iPageUrl, Object... objs) {

        int index = pagination.getFirstPageIndex();
        PageInfo firstpage = new PageInfo(SearchCons.FIRSTPAGE, index + 1, pagination.isFirstPage(),
                                          iPageUrl.parsePageUrl(objs, index + 1));
        pagination.setFirstPage(firstpage);
        // 上一页
        index = pagination.getPrevPageIndex();
        PageInfo prevpage = new PageInfo(SearchCons.PREPAGE, index + 1, false, iPageUrl.parsePageUrl(objs, index + 1));
        pagination.setPrevPage(prevpage);
        // 下一页
        index = pagination.getNextPageIndex();
        PageInfo nextpage = new PageInfo(SearchCons.NEXTPAGE, index + 1, false, iPageUrl.parsePageUrl(objs, index + 1));
        pagination.setNextPage(nextpage);
        // 尾页
        index = pagination.getLastPageIndex();
        PageInfo lastpage = new PageInfo(SearchCons.LASTPAGE, index + 1, pagination.isLastPage(),
                                         iPageUrl.parsePageUrl(objs, index + 1));
        pagination.setLastPage(lastpage);
        //
        int nowPageIndex = pagination.getNowPageIndex();
        List<PageInfo> pages = new ArrayList<PageInfo>();
        List<Integer> skipPageIndexs = pagination.getSkipPageIndex();
        for (Integer integer : skipPageIndexs) {
            pages.add(new PageInfo(StringUtils.EMPTY + (integer + 1), integer + 1, nowPageIndex == integer,
                                   iPageUrl.parsePageUrl(objs, integer + 1)));
        }
        pagination.setPages(pages);
    }

    /**
     * 对所有数据进行分页,每页默认10个
     * 
     * @param <T>
     * @param all 所有数据
     * @param nowPageIndex 第几页(1,2,3,4......)
     * @return
     */
    public static <T> PaginationList<T> getPaginationList(List<T> all, int nowPageIndex) {
        return getPaginationList(all, nowPageIndex, Pagination.DEFAULT_PAGESIZE);
    }

    /**
     * 对所有数据进行分页
     * 
     * @param <T>
     * @param all 所有数据
     * @param nowPageIndex 第几页(1,2,3,4......)
     * @param pageSize 每页数量
     * @return
     */
    public static <T> PaginationList<T> getPaginationList(List<T> all, int nowPageIndex, int pageSize) {
        return getPaginationList(all, new Pagination(), nowPageIndex, pageSize);
    }

    /**
     * 对所有数据进行分页
     * 
     * @param <T>
     * @param all 所有数据
     * @param nowPageIndex 第几页(1,2,3,4......)
     * @param pageSize 每页数量
     * @return
     */
    public static <T> PaginationList<T> getPaginationList(List<T> all, Pagination pagination, int nowPageIndex,
                                                          int pageSize) {
        if (all == null || pagination == null) {
            return null;
        }
        int size = all.size();
        nowPageIndex = nowPageIndex >= 1 ? nowPageIndex - 1 : 0;
        pageSize = pageSize > 0 ? pageSize : Pagination.DEFAULT_PAGESIZE;
        pagination.setNowPageIndex(nowPageIndex);
        pagination.setPageSize(pageSize);
        pagination.init(size);
        int start = pagination.getStartRecordIndex();
        int end = pagination.getEndRecordIndex();
        end = end > size ? size : end;
        start = start - 1 >= 0 ? start - 1 : 0;
        end = end - 1;
        PaginationList<T> page = new PaginationList<T>(pagination);
        for (int i = start; i <= end; i++) {
            page.add(all.get(i));
        }
        return page;
    }
}
