/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.external.lucene.solr.exception;

/**
 * @author zxc Sep 2, 2014 1:42:08 PM
 */
public class SolrServerUnAvailableException extends RuntimeException {

    private static final long serialVersionUID = -2476119679281400135L;

    public SolrServerUnAvailableException() {
        super();
    }

    public SolrServerUnAvailableException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SolrServerUnAvailableException(String arg0) {
        super(arg0);
    }

    public SolrServerUnAvailableException(Throwable arg0) {
        super(arg0);
    }
}
