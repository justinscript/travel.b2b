/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pipeline;

/**
 * @author zxc Jul 15, 2014 3:57:40 PM
 */
public class PipelineResult {

    private PipelineType type;
    private String       name;
    private String       redirectUrl;

    private PipelineResult(PipelineType type) {
        this(type, null, null);
    }

    private PipelineResult(PipelineType type, String name, String redirectUrl) {
        this.type = type;
        this.name = name;
        this.redirectUrl = redirectUrl;
    }

    public static PipelineResult gotoPreHandle(String name) {
        return new PipelineResult(PipelineType.PIPELINE_PREHANDLE, name, null);
    }

    public static PipelineResult gotoFinally(String name) {
        return gotoAfterCompletion(name, null);
    }

    public static PipelineResult gotoAfterCompletion(String name, String redirectUrl) {
        return new PipelineResult(PipelineType.PIPELINE_AFTERCOMPLETION, name, redirectUrl);
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public PipelineType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
