/*
 * Copyright 2014-2017 ZuoBian.com Alimport java.util.ArrayList; import java.util.List; and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页组件
 * 
 * @author zxc Jun 15, 2014 11:18:31 PM
 */
public class Pagination {

    // 最多显示多少条页面按钮，推荐定义为<strong>奇数</strong>，偶数也可以
    public final static int DEFAULT_PAGE_LENGTH = 9;
    // 定义一个最初时的一个page，表示没有值的情况
    public final static int NO_PAGE             = -1;

    // 定义一个第一次访问时的page页
    public final static int INIT_PAGE           = 0;

    // 定义一个第一次访问的每页下标
    public final static int INIT_INDEX          = 0;

    // 定义默认每页显示记录数
    public final static int DEFAULT_PAGESIZE    = 30;

    // 所有的记录数
    private int             allRecordNum;

    // 所有的页面数量
    private int             allPageNum;

    // 当前页面处于第几页，页面从0开始计数，在页面上显示则从1开始
    private int             nowPageIndex        = 0;
    // 每页的记录数
    private int             pageSize            = DEFAULT_PAGESIZE;

    // 当前页的第一条记录在所有记录中的下标，从1开始
    private int             startRecordIndex    = 1;

    // 当前页的最后一条记录在所有记录中的下标
    private int             endRecordIndex      = Integer.MAX_VALUE;

    // 定义从当前所处页面开始能够跳转到的<strong>最开始</strong>页面
    private int             firstPageIndex;

    // 定义从当前所处页面开始能够跳转到的<strong>最末尾</strong>页面
    private int             lastPageIndex;

    private List<Integer>   skipPageIndex       = new ArrayList<Integer>(DEFAULT_PAGE_LENGTH + 1);

    private int             pageLength          = DEFAULT_PAGE_LENGTH;                             ;

    /**
     * 返回数据库分页的起始位置。Oracle从1开始，Mysql从0开始
     * 
     * @return
     */
    public int getOffset() {
        return 1;
    }

    /**
     * 根据所有的记录数计算处总共有多少页
     * 
     * @param allRecordNum
     */
    public void init(int allRecordNum) {
        this.allRecordNum = allRecordNum;
        allPageNum = (allRecordNum + pageSize - 1) / pageSize;
        startRecordIndex = nowPageIndex * pageSize + getOffset();
        endRecordIndex = startRecordIndex + pageSize - 1;
        firstPageIndex = nowPageIndex - pageLength / 2;
        if (firstPageIndex < 0) {
            firstPageIndex = 0;
        }
        lastPageIndex = nowPageIndex + (pageLength - 1) / 2;
        if (lastPageIndex >= allPageNum) {
            lastPageIndex = allPageNum - 1;
        }
        if (nowPageIndex - firstPageIndex < pageLength / 2) {
            while (lastPageIndex + 1 < allPageNum && lastPageIndex + 1 - firstPageIndex <= pageLength - 1) {
                lastPageIndex++;
            }
        }
        if (lastPageIndex - nowPageIndex < (pageLength - 1) / 2) {
            while (firstPageIndex - 1 >= 0 && lastPageIndex - (firstPageIndex - 1) <= pageLength - 1) {
                firstPageIndex--;
            }
        }
        int i;
        for (i = firstPageIndex; i <= lastPageIndex; i++) {
            skipPageIndex.add(i);
        }
    }

    /**
     * 返回所有的记录数
     * 
     * @return
     */
    public int getAllRecordNum() {
        return allRecordNum;
    }

    /**
     * 返回所有的页码数
     * 
     * @return
     */
    public int getAllPageNum() {
        return allPageNum;
    }

    /**
     * 返回当前页码
     * 
     * @return
     */
    public int getNowPageIndex() {
        return nowPageIndex;
    }

    /**
     * 首页页码
     * 
     * @return
     */
    public int getFirstPageIndex() {
        return 0;
    }

    /**
     * 尾页页码
     * 
     * @return
     */
    public int getLastPageIndex() {
        return allPageNum - 1;
    }

    /**
     * 返回上一页页码，若当前页已是首页则返回首页
     * 
     * @return
     */
    public int getPrevPageIndex() {
        return nowPageIndex - 1 > 0 ? nowPageIndex - 1 : 0;
    }

    /**
     * 返回下一页页码，若当前页已是首页则返回首页
     * 
     * @return
     */
    public int getNextPageIndex() {
        return nowPageIndex == allPageNum - 1 ? allPageNum - 1 : nowPageIndex + 1;
    }

    /**
     * 当前页是否是首页
     * 
     * @return
     */
    public boolean isFirstPage() {
        return nowPageIndex == 0;
    }

    /**
     * 当前页是否是尾页
     * 
     * @return
     */
    public boolean isLastPage() {
        return nowPageIndex == allPageNum - 1;
    }

    /**
     * 设置当前页码(oracle分页默认从0开始,mysql默认从1开始)
     * 
     * @param nowPageIndex
     */
    public void setNowPageIndex(int nowPageIndex) {
        this.nowPageIndex = nowPageIndex;
    }

    /**
     * 返回起始记录
     * 
     * @return
     */
    public int getStartRecordIndex() {
        return startRecordIndex;
    }

    /**
     * 返回起始记录
     * 
     * @return
     */
    public int getMySqlStartRecordIndex() {
        return startRecordIndex - 1 >= 0 ? startRecordIndex - 1 : 0;
    }

    /**
     * 返回结束记录
     * 
     * @return
     */
    public int getEndRecordIndex() {
        return endRecordIndex;
    }

    /**
     * 到前一页，如果不能再前进的时候，不动
     */
    public boolean toPrePage() {
        if (nowPageIndex == 0) {
            return false;
        }
        nowPageIndex--;
        startRecordIndex -= pageSize;
        return true;
    }

    /**
     * 到后一页，如果不能再前进，则不动
     * 
     * @return
     */
    public boolean toNextPage() {
        if (nowPageIndex == allPageNum - 1) {
            return false;
        }
        nowPageIndex++;
        startRecordIndex += pageSize;
        return true;
    }

    /**
     * 直接跳转到nextPage页
     */
    public boolean toSpecialPage(int nextPage) {
        if (nextPage < 0 || nextPage >= allPageNum || nextPage == nowPageIndex) {
            return false;
        }
        startRecordIndex += pageSize * (nextPage - nowPageIndex);
        nowPageIndex = nextPage;
        return true;
    }

    /**
     * 对总数量显示做一个转变，比如1023233434，显示成1,023,233,434
     */
    public String formatAllRecordNumber() {
        return formatNumber(allRecordNum);
    }

    public static String formatNumber(int number) {
        if (number < 1000) {
            return String.valueOf(number);
        }
        StringBuffer str = new StringBuffer();
        int temNum = number;
        int k = 0;
        while (temNum != 0) {
            str.append(temNum % 10);
            temNum /= 10;
            k++;
            if (k == 3) {
                str.append(',');
                k = 0;
            }
        }
        return str.reverse().toString();
    }

    /**
     * 返回最多显示多少条页面按钮
     * 
     * @return
     */
    public int getPageLength() {
        return pageLength;
    }

    /**
     * @param pageLength the pageLength to set
     */
    public void setPageLength(int pageLength) {
        if (pageLength <= 0) {
            return;
        }
        this.pageLength = pageLength;
    }

    /**
     * 设置每页的记录数
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 返回每页记录数
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 定义从当前所处页面开始能够跳转到的<strong>最开始</strong>页面
     * 
     * @return
     */
    public int getFirstGotoPageIndex() {
        return firstPageIndex;
    }

    /**
     * 定义从当前所处页面开始能够跳转到的<strong>最开始</strong>页面
     * 
     * @param firstPageIndex
     */
    public void setFirstGotoPageIndex(int firstPageIndex) {
        this.firstPageIndex = firstPageIndex;
    }

    /**
     * 定义从当前所处页面开始能够跳转到的<strong>最末尾</strong>页面
     * 
     * @return
     */
    public int getLastGotoPageIndex() {
        return lastPageIndex;
    }

    public List<Integer> getSkipPageIndex() {
        return skipPageIndex;
    }

    /**
     * 定义从当前所处页面开始能够跳转到的<strong>最末尾</strong>页面
     * 
     * @param lastPageIndex
     */
    public void setLastGotoPageIndex(int lastPageIndex) {
        this.lastPageIndex = lastPageIndex;
    }

    /**
     * IBatis使用，返回该页面的起始记录数
     * 
     * @return
     */
    public Integer getPageStart() {
        return startRecordIndex;
    }

    /**
     * myBatis使用，返回该页面的结束记录数
     * 
     * @return
     */
    public Integer getPageEnd() {
        return endRecordIndex;
    }

    public void setStartRecordIndex(int startRecordIndex) {
        this.startRecordIndex = startRecordIndex;
    }

    public void setEndRecordIndex(int endRecordIndex) {
        this.endRecordIndex = endRecordIndex;
    }
}
