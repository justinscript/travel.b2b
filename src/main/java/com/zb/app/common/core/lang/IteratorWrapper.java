/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页数据处理公用类
 * 
 * @author zxc Jun 16, 2014 12:10:11 AM
 */
public class IteratorWrapper<T> {

    private List<T> data;
    private int     pageSize;

    public static int getPageCount(int size, int pageSize) {
        int pageCount = size / pageSize;
        if (size % pageSize != 0) {
            pageCount++;
        }
        return pageCount;
    }

    /**
     * pageSize为每页的大小，data为待分页数据List
     * 
     * @param data
     * @param pageSize
     * @return
     */
    public static <T extends Object> IteratorWrapper<T> pagination(Collection<T> data, int pageSize) {
        IteratorWrapper<T> it = new IteratorWrapper<T>();
        if (data == null) {
            return it;
        }
        if (data instanceof List) {
            it.data = (List<T>) data;
        } else {
            it.data = new ArrayList<T>(data);
        }
        it.pageSize = pageSize;
        return it;
    }

    /**
     * pageSize为每页的大小，data为待分页数据数组
     * 
     * @param data
     * @param pageSize
     * @return
     */
    public static <T extends Object> IteratorWrapper<T> pagination(T[] data, int pageSize) {
        IteratorWrapper<T> it = new IteratorWrapper<T>();
        ArrayUtils.removeNullElement(data);
        if (Argument.isEmptyArray(data)) {
            return it;
        }
        return pagination(Arrays.asList(data), pageSize);
    }

    /**
     * 具体的迭代接口，params为传入的参数
     * 
     * @param handler
     * @param params
     */
    public void iterator(IteratorHandler<T> handler, Object... params) {
        if (data == null || data.isEmpty()) {
            return;
        }
        int total = data.size(), pageCount = getPageCount(total, pageSize);
        for (int pageNum = 0; pageNum < pageCount; pageNum++) {
            int start = Math.min(pageNum * pageSize, total), end = Math.min((pageNum + 1) * pageSize, total);
            if (start >= end) {
                break;
            }
            List<T> subList = null;
            try {
                subList = data.subList(start, end);
                if (!handler.handle(pageNum, subList, params)) {
                    return;
                }
            } catch (Exception e) {
                if (handler.onException(e, pageNum, data, subList, params) == false) {
                    return;
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    public static abstract class IteratorHandler<T> {

        private static Logger logger = LoggerFactory.getLogger(IteratorHandler.class);

        /**
         * return false 则迭代不再继续
         * 
         * @param pageNum
         * @param subData
         * @param params
         * @return
         */
        public abstract boolean handle(int pageNum, List<T> subData, Object... params);

        /**
         * 执行某次迭代发生异常
         * 
         * @param e
         * @param pageNum
         * @param subData
         * @param params
         * @return true则迭代继续，否则迭代退出
         */
        public boolean onException(Throwable e, int pageNum, List<T> data, Collection<T> subData, Object... params) {
            logger.error("data.size:" + data.size() + ";" + e.getMessage(), e);
            e.printStackTrace();
            return true;
        }
    }

    public static void main(String[] args) {
        Integer[] data = new Integer[] { 1, 2, 3, 4, 5, 6 };
        IteratorHandler<Integer> handler = new IteratorHandler<Integer>() {

            @Override
            public boolean handle(int pageNum, List<Integer> subData, Object... params) {
                System.out.println("pageNum" + pageNum + "subData" + subData);
                System.out.println("第" + pageNum + "页" + subData);
                return true;
            }
        };
        IteratorWrapper.pagination(data, 2).iterator(handler);
        Set<Integer> data2 = new HashSet<Integer>(Arrays.asList(data));
        IteratorWrapper.pagination(data2, 2).iterator(handler);
    }
}
