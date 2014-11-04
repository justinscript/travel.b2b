/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.solr.exception;

/**
 * @author zxc Sep 2, 2014 2:36:45 PM
 */
public class SolrUnSupportException extends RuntimeException {

    private static final long serialVersionUID = 957102044387061473L;

    public SolrUnSupportException(String msg) {
        super(msg);
    }

    public SolrUnSupportException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
