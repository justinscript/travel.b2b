/*
 * Copyright 2014-2017 ZuoBian.com Alimport java.util.ArrayList; import java.util.List; and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pagination;

import java.util.List;

/**
 * @author zxc Jun 15, 2014 11:19:10 PM
 */
public class PagesPagination extends Pagination {

    private List<PageInfo> pages;
    private PageInfo       firstPage;
    private PageInfo       lastPage;
    private PageInfo       prevPage;
    private PageInfo       nextPage;

    public PagesPagination() {

    }

    public PagesPagination(int pageSize, int nowPageIndex) {
        setPageSize(pageSize);
        setNowPageIndex(nowPageIndex);
    }

    public PagesPagination(int pageSize, int nowPageIndex, int totalNum) {
        setPageSize(pageSize);
        setNowPageIndex(nowPageIndex);
        init(totalNum);
    }

    public List<PageInfo> getPages() {
        return pages;
    }

    public void setPages(List<PageInfo> pages) {
        this.pages = pages;
    }

    public PageInfo getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(PageInfo firstPage) {
        this.firstPage = firstPage;
    }

    public PageInfo getLastPage() {
        return lastPage;
    }

    public void setLastPage(PageInfo lastPage) {
        this.lastPage = lastPage;
    }

    public PageInfo getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(PageInfo prevPage) {
        this.prevPage = prevPage;
    }

    public PageInfo getNextPage() {
        return nextPage;
    }

    public void setNextPage(PageInfo nextPage) {
        this.nextPage = nextPage;
    }
}
