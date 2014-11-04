/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.search.build.base;

import com.zb.app.common.pagination.PaginationList;

/**
 * @author zxc Sep 2, 2014 1:55:39 PM
 */
public interface DataFetcher<T> {

    /**
     * 检索数据
     */
    PaginationList<?> fetch(T p);
}
