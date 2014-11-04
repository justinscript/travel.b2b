/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.pipeline;

/**
 * @author zxc Jul 15, 2014 4:15:22 PM
 */
public class PipelineValvesMapper {

    private String   pipelineName;
    private Pipeline pipeline;

    public PipelineValvesMapper(String pipelineName, Pipeline pipeline) {
        if (pipelineName == null || pipelineName.trim().length() == 0) {
            throw new PipelineResultException("pipeline name can not null");
        }
        if (pipeline == null) {
            throw new PipelineResultException("pipeline can not null");
        }
        this.pipelineName = pipelineName;
        this.pipeline = pipeline;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public class PipelineResultException extends RuntimeException {

        private static final long serialVersionUID = -608937022332488651L;

        public PipelineResultException(String messge) {
            super(messge);
        }
    }
}
