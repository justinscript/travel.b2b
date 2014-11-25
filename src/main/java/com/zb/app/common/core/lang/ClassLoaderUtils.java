/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载制定目录下的类(加载classpath下的类。支持跨jar加载)
 * 
 * <pre>
 * List&lt;?&gt; load = load(&quot;com/zb/app/biz/service/builder&quot;, new ClassNameFilter(false, &quot;Abstract&quot;));
 * for (Object obj : load) {
 *     System.err.println(obj);
 * }
 * 
 * </pre>
 * 
 * @author zxc Jul 25, 2014 1:34:22 PM
 */
public class ClassLoaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtils.class);

    public static List<String> getClassNamesInPackage(String jarName, String packageName) throws IOException {
        JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
        packageName = packageName.replace(".", "/");
        List<String> classes = new ArrayList<String>();

        try {
            for (JarEntry jarEntry; (jarEntry = jarFile.getNextJarEntry()) != null;) {
                if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class"))) {
                    classes.add(jarEntry.getName().replace("/", ".").replaceAll(".class", ""));
                }
            }
        } finally {
            jarFile.close();
        }
        return classes;
    }

    public static List<?> load(String classpath, ClassFilter filter) throws Exception {
        List<Object> objs = new ArrayList<Object>();
        URL resource = ClassLoaderUtils.class.getClassLoader().getResource(classpath);
        logger.debug("Search from {} ...", resource.getPath());
        List<String> classnameArray;
        if ("jar".equalsIgnoreCase(resource.getProtocol())) {
            String file = resource.getFile();
            String jarName = file.substring(file.indexOf("/"), (file.lastIndexOf("jar") + 3));
            classnameArray = getClassNamesInPackage(jarName, classpath);
        } else {
            Collection<File> listFiles = FileUtils.listFiles(new File(resource.getPath()), null, false);
            String classNamePrefix = classpath.replaceAll("/", ".");
            classnameArray = new ArrayList<String>();
            for (File file : listFiles) {
                String name = file.getName();
                if (name.endsWith(".class") == false) {
                    continue;
                }
                if (StringUtils.contains(name, '$')) {
                    logger.warn("NOT SUPPORT INNERT CLASS" + file.getAbsolutePath());
                    continue;
                }
                String classname = classNamePrefix + "." + StringUtils.remove(name, ".class");
                classnameArray.add(classname);
            }
        }

        for (String classname : classnameArray) {
            try {
                Class<?> loadClass = ClassLoaderUtils.class.getClassLoader().loadClass(classname);
                if (filter != null && !filter.filter(loadClass)) {
                    logger.error("{} 被 {} 过滤掉了", classname, filter);
                    continue;
                }
                // if (ClassLoaderUtil.class.isAssignableFrom(loadClass) == false) {
                // logger.error("{} 包访问权限受限！", classname);
                // continue;
                // }
                Object newInstance = loadClass.newInstance();
                objs.add(newInstance);
                logger.debug("load {}/{}.class success", resource.getPath(), classname);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("load " + resource.getPath() + "/" + classname + ".class failed", e);
            }
        }
        return objs;
    }

    public static void main(String[] args) throws Exception {
        List<?> load = load("com/zb/app/biz/item/title/builder", new ClassNameFilter(false, "Abstract"));
        for (Object obj : load) {
            System.err.println(obj);
        }
        System.exit(0);
    }

    public static interface ClassFilter {

        boolean filter(Class<?> classType);
    }

    public static class ClassNameFilter implements ClassFilter {

        private boolean  include;
        private String[] classnames;

        public ClassNameFilter(boolean include, String... classnames) {
            this.include = include;
            this.classnames = classnames;
        }

        @Override
        public boolean filter(Class<?> classType) {
            String className = classType.getName();
            if (logger.isDebugEnabled()) {
                logger.debug("filter className...  " + className);
            }
            boolean found = false;
            for (String name : classnames) {
                if (StringUtils.isNotBlank(name) && className.contains(name)) {
                    found = true;
                    break;
                }
            }
            return found && include || (!found && !include);
        }

        @Override
        public String toString() {
            return (include ? "必须包含" : "不能包含") + Arrays.toString(classnames);
        }
    }
}
