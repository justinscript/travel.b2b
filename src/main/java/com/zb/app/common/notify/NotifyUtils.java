/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.notify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ClassUtils;

import com.zb.app.common.cache.ConcurrentHashSet;
import com.zb.app.common.notify.event.Event;
import com.zb.app.common.notify.event.EventConfig;
import com.zb.app.common.notify.event.EventType;
import com.zb.app.common.result.Result;

/**
 * @author zxc Jul 24, 2014 4:51:32 PM
 */
public class NotifyUtils implements INotify {

    public static Result getListenedEvent(Map<EventType, Set<MethodDescriptor>> container, NotifyListener listener) {
        if (logger.isDebugEnabled()) {
            logger.debug("getListenedEvent for【 " + listener.getClass() + "】...");
        }
        try {
            Result result = Result.success();
            Method[] methods = listener.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(EventConfig.class)) {
                    parserAnnotation(result, listener, method, container);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.failed(e.getMessage());
        }
    }

    private static void parserAnnotation(Result result, Object obj, Method method,
                                         Map<EventType, Set<MethodDescriptor>> container) {
        EventConfig eventConfig = method.getAnnotation(EventConfig.class);
        EventType[] events = eventConfig.events();
        if (events == null || events.length == 0) {
            logger.warn("No eventConfig FOUND in 【" + method.getName() + "】");
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("found 【" + method.getName() + "】!");
        }
        for (int i = 0, j = events.length; i < j; i++) {
            Set<MethodDescriptor> methodSet = container.get(events[i]);
            if (methodSet == null) {
                methodSet = new ConcurrentHashSet<MethodDescriptor>();
                container.put(events[i], methodSet);
            }
            MethodDescriptor md = new MethodDescriptor(obj, method);
            methodSet.add(md);
            result.appendMessage(md.toString());
        }
    }

    @SuppressWarnings("rawtypes")
    public static class MethodDescriptor {

        private Object  obj;
        private Class[] paramTypes;
        private String  methodName;
        private Class   methodClass;
        private int     hashCode;

        public MethodDescriptor(Object obj, Method method) {
            if (obj == null) {
                throw new IllegalArgumentException("Class cannot be null");
            }
            if (method == null) {
                throw new IllegalArgumentException("Method Name cannot be null");
            }
            this.obj = obj;
            this.paramTypes = method.getParameterTypes();
            this.methodName = method.getName();
            this.methodClass = method.getClass();
            this.hashCode = methodName.length();

            if (method.getParameterTypes() == null || method.getParameterTypes().length != 1) {
                throw new IllegalArgumentException("Method Type Must be Event");
            }
            Class class1 = method.getParameterTypes()[0];
            if (!ClassUtils.isAssignable(class1, Event.class)) {
                throw new IllegalArgumentException(methodName + "'s paramster must be Event!");
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MethodDescriptor)) {
                return false;
            }
            MethodDescriptor md = (MethodDescriptor) obj;

            return (methodName.equals(md.methodName) && methodClass.equals(md.methodClass) && java.util.Arrays.equals(paramTypes,
                                                                                                                      md.paramTypes));
        }

        @Override
        public String toString() {
            return "{" + methodClass.getName() + " " + methodName + "}";
        }

        public int hashCode() {
            return hashCode;
        }

        public Object invoke(Object... args) throws NoSuchMethodException, IllegalAccessException,
                                            InvocationTargetException {
            if (logger.isWarnEnabled()) {
                logger.warn(this + " been invoked with " + printArgs(args));
            }
            return MethodUtils.invokeMethod(obj, methodName, args, paramTypes);
        }

        private static String printArgs(Object... args) {
            if (args == null || args.length == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0, j = args.length; i < j; i++) {
                sb.append(args[i]).append(" ");
            }
            return sb.toString();
        }
    }
}
