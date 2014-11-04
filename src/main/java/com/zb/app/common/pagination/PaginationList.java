/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pagination;

import java.util.ArrayList;

/**
 * @author zxc Jun 15, 2014 11:18:10 PM
 */
public class PaginationList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 3198909484620898963L;
    private Pagination        query;

    public PaginationList(Pagination query) {
        super();
        this.query = query;
    }

    public Pagination getQuery() {
        return query;
    }

    public void setQuery(Pagination query) {
        this.query = query;
    }
}
