/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.velocity;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

/**
 * @author zxc Jul 14, 2014 11:55:45 AM
 */
public class CustomVelocityLayoutView extends VelocityLayoutView {

    private static final Logger        logger                   = LoggerFactory.getLogger(CustomVelocityLayoutView.class);

    public static final String         DEFAULT_LAYOUT_DIRECTORY = "/layout";
    public static final String         DEFAULT_WIDGET_DIRECTORY = "/widget";
    public static final String         DEFAULT_VIEW_DIRECTORY   = "/view";
    private static final String        DEFAULT_LAYOUT_VM        = "default.vm";

    public static final String         USE_LAYOUT               = "_$_velocity_user_layout_$_";                           // 是否要使用layout模版的key

    private String                     screenContentKey         = DEFAULT_SCREEN_CONTENT_KEY;
    private String                     contextPath;

    private String                     suffix;
    private static Map<String, Object> viewTools;
    private ApplicationContext         applicationContext;

    public CustomVelocityLayoutView() {

    }

    protected void initApplicationContext(ApplicationContext applicationContext) {
        super.initApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

    /**
     * 创建velocity的Context后注如一些工具类和widget
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Context createVelocityContext(Map model, HttpServletRequest request, HttpServletResponse response)
                                                                                                                throws Exception {
        Context context = super.createVelocityContext(model, request, response);
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }
        context.put("base", contextPath);

        if (viewTools == null) {
            initViewTools();
        }
        if (viewTools.size() != 0) {
            for (String name : viewTools.keySet()) {
                context.put(name, viewTools.get(name));
                logger.debug("the view tool named " + name + " is added to velocity context");
            }
        }

        context.put("widget", new Widget(applicationContext, request, response));
        return context;
    }

    protected void doRender(Context context, HttpServletResponse response) throws Exception {
        Object o = context.get(Widget.IS_WIDGET);
        Object layout = context.get(USE_LAYOUT);
        // widget时不需要布局
        if (o != null && "true".equals(o.toString())) {
            renderScreenContent(context, response, getUrl());
        }
        // 手工设置不需要布局时，不需要布局
        else if (layout != null && "false".equals(layout.toString())) {
            renderScreenContent(context, response, buildViewName());
        } else {
            mergeScreenContent(context);
            mergeTemplate(getLayoutTemplate(buildViewName()), context, response);
        }
    }

    /**
     * 覆盖父类中的方法，无需检查模版是否存在，<br>
     * 在不同的命名空间中模版是不同的<br>
     * 但必须保证在一个命名空间中必须存在/namespace/layout/default.vm模版
     * 
     * @see org.springframework.web.servlet.view.velocity.VelocityLayoutView#checkTemplate()
     */
    @Override
    public boolean checkResource(Locale locale) throws ApplicationContextException {
        return true;
    }

    /**
     * 得到布局的vm
     * 
     * @param viewname
     * @return
     */
    private Template getLayoutTemplate(String viewname) throws Exception {
        /**
         * 传入的是视图的名字,需要处理为布局的名字<br>
         * 视图名格式应为<br>
         * "/namespace/view/zzz.vm"<br>
         * "/namespace/view/yyy/zzz.vm"<br>
         * "/namespace/view/xxx/yyy/zzz.vm"<br>
         */
        Object obj = getVelocityEngine().getProperty("file.resource.loader.path");
        String resourcePath = "";
        if (obj != null && obj instanceof String) {
            resourcePath = (String) obj;
        }
        // 去掉后面的后缀
        int index = viewname.indexOf(".");
        if (index != -1) {
            viewname = viewname.substring(0, index);
        }
        viewname += suffix;
        // 如果开头不是"/"加上
        if (!viewname.startsWith("/")) {
            viewname = "/" + viewname;
        }
        int base = viewname.indexOf(DEFAULT_VIEW_DIRECTORY);
        base = base + 1 + DEFAULT_LAYOUT_DIRECTORY.length();
        // 尝试取同名的模版
        viewname = viewname.replaceFirst(DEFAULT_VIEW_DIRECTORY, DEFAULT_LAYOUT_DIRECTORY);
        String defaultLayout = viewname.substring(0, base) + DEFAULT_LAYOUT_VM;
        String layoutUrlToUse = viewname;
        if (layOutTemplateExists(resourcePath + layoutUrlToUse)) {
            return getTemplate(layoutUrlToUse);
        }
        // 尝试取同层默认模版
        // /namespace/layout/xxx/yyy/zzz.vm ---> /namespace/layout/xxx/yyy/default.vm
        // /namespace/layout/zzz.vm ---> /namespace/layout/default.vm
        layoutUrlToUse = layoutUrlToUse.substring(0, layoutUrlToUse.lastIndexOf("/") + 1).concat(DEFAULT_LAYOUT_VM);
        if (layOutTemplateExists(resourcePath + layoutUrlToUse)) {
            return getTemplate(layoutUrlToUse);
        }
        // 此处需递归查找上一层
        // 尝试取上一层模版
        while (true) {
            // 去掉最后一层/default.vm
            index = layoutUrlToUse.lastIndexOf("/");
            if (index == -1) {
                break;
            }
            layoutUrlToUse = layoutUrlToUse.substring(0, index);
            // 去掉上一层
            index = layoutUrlToUse.lastIndexOf("/");
            if (index == -1) {
                break;
            }
            layoutUrlToUse = layoutUrlToUse.substring(0, index + 1).concat(DEFAULT_LAYOUT_VM);
            // 检查下路径最小长度不可能小于默认布局长度
            if (layoutUrlToUse.length() < defaultLayout.length()) {
                break;
            }
            if (layOutTemplateExists(resourcePath + layoutUrlToUse)) {
                return getTemplate(layoutUrlToUse);
            }
        }
        return getTemplate(defaultLayout);
    }

    private boolean layOutTemplateExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    private void renderScreenContent(Context context, HttpServletResponse response, String name) throws Exception {
        Template screenContentTemplate = getTemplate(name);
        screenContentTemplate.merge(context, response.getWriter());
    }

    private void mergeScreenContent(Context velocityContext) throws Exception {
        StringWriter sw = new StringWriter();
        Template screenContentTemplate = getTemplate(buildViewName());
        screenContentTemplate.merge(velocityContext, sw);
        // Put rendered content into Velocity context.
        velocityContext.put(this.screenContentKey, new ViewContent(sw.toString()));
    }

    public void setScreenContentKey(String screenContentKey) {
        super.setScreenContentKey(screenContentKey);
        this.screenContentKey = screenContentKey;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    private String buildViewName() {
        String url = getUrl();
        if (StringUtils.startsWith(url, "/")) {
            url = url.substring(1, url.length());
        }
        return DEFAULT_VIEW_DIRECTORY + "/" + url;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void initViewTools() {
        viewTools = new HashMap<String, Object>();
        Map matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ViewToolsFactory.class,
                                                                           true, false);
        Collection<ViewToolsFactory> values = matchingBeans.values();
        for (ViewToolsFactory factory : values) {
            Map<String, Object> vt = factory.getViewTools();
            if (vt != null) {
                viewTools.putAll(vt);
            }
        }
    }

    public class ViewContent {

        private String content;

        public ViewContent(String content) {
            this.content = content;
        }

        public String toString() {
            if (this.content == null) return StringUtils.EMPTY;
            return this.content;
        }
    }
}
