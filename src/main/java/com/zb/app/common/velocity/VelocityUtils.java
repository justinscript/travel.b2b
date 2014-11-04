/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.velocity;

import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.zb.app.common.core.SpringContextAware;

/**
 * @author zxc Aug 7, 2014 2:15:00 PM
 */
@Component
public class VelocityUtils {

    private static final Logger logger                   = LoggerFactory.getLogger(VelocityUtils.class);

    public static final String  DEFAULT_LAYOUT_DIRECTORY = "/layout";
    public static final String  DEFAULT_VIEW_DIRECTORY   = "/view";
    public static final String  DEFAULT_WIDGET_DIRECTORY = "/widget";

    private VelocityEngine      velocityEngine;

    @PostConstruct
    public void init() {

        // @SuppressWarnings("unused")
        // ViewResolver viewResolver = (ViewResolver)
        // SpringContextAware.getBean(DispatcherServlet.VIEW_RESOLVER_BEAN_NAME);
    }

    public static VelocityUtils getInstance() {
        return (VelocityUtils) SpringContextAware.getBean("velocityUtils");
    }

    /**
     * 渲染$WORKSPACE/resources下的模板
     * 
     * @param templateName 模板的名字，注意是相对于$WORKSPACE/resources的路径名称。例如/user/join_success.vm
     * @param model 数据对象
     * @return
     */
    @SuppressWarnings("deprecation")
    public StringWriter mergetTemplate(String templateName, Map<String, Object> model) {
        StringWriter sw = new StringWriter();
        try {
            VelocityEngineUtils.mergeTemplate(velocityEngine, getTemplatePath(templateName), model, sw);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return sw;
    }

    private String getTemplatePath(String templateName) {
        return DEFAULT_WIDGET_DIRECTORY + templateName;
    }
}
