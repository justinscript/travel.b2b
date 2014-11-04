/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.exception;

import java.io.PrintStream;
import java.util.Locale;

/**
 * controller error
 * 
 * @author zxc Jun 10, 2014 11:34:24 PM
 */
public class ControllerFault extends RuntimeException {

    private static final long  serialVersionUID         = -3778504091462043977L;

    public final static String DEFUALT_LANGUAGE         = "en_US";

    public static final int    SYSTEM_ERROR             = 1;
    public static final int    INVALID_TOKEN            = 2;
    public static final int    INVALID_APPID            = 3;
    public static final int    INVALID_APPSIG           = 4;
    public static final int    OPTIMISTIC_LOCKING_ERROR = 5;

    private int                errorCode;
    private Locale             locale;

    public int getErrorCode() {
        return this.errorCode;
    }

    protected void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getKey() {
        return "ec_" + getErrorCode();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getMessage() {
        return "";
    }

    public String getMessage(Locale locale) {
        return "";
    }

    public ControllerFault(int errorCode) {
        this.setErrorCode(errorCode);
    }

    public ControllerFault(int errorCode, Throwable cause) {
        super(cause);
        this.setErrorCode(errorCode);
    }

    public StackTraceElement[] getStackTrace() {
        StackTraceElement[] srcs = super.getStackTrace();
        int length = srcs.length < 5 ? srcs.length : 5;
        StackTraceElement[] dest = new StackTraceElement[length];
        System.arraycopy(srcs, 0, dest, 0, length);
        return dest;
    }

    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            s.println(this);
            StackTraceElement[] trace = super.getStackTrace();
            for (int i = 0; i < trace.length && i < 5; i++)
                s.println("\tat " + trace[i]);
        }
    }
}
