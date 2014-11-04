/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.gson.Gson;
import com.zb.app.common.cons.ResultCode;
import com.zb.app.common.core.lang.Argument;

/**
 * 通用Ajax请求的JSON 结果
 * 
 * @author zxc Jun 10, 2014 10:58:33 PM
 */
public class JsonResultUtils {

    public static JsonResult build(ResultCode code, String message) {
        return buildJsonResult(null, code, null, message, true);
    }

    public static JsonResult success() {
        return success(null, null);
    }

    public static JsonResult success(Object data) {
        return success(data, null);
    }

    public static JsonResult success(Object data, boolean escape) {
        return success(data, null, escape);
    }

    public static JsonResult success(Object data, String message) {
        return buildJsonResult(ResultCode.SUCCESS, data, message);
    }

    public static JsonResult success(Object data, String message, boolean escape) {
        return buildJsonResult(null, ResultCode.SUCCESS, data, message, escape);
    }

    public static JsonResult needLoJsonResult() {
        return needLogin(null, null);
    }

    public static JsonResult needLogin(Object data) {
        return needLogin(data, null);
    }

    public static JsonResult needLogin(Object data, String message) {
        return buildJsonResult(ResultCode.NEED_LOGIN, data, message);
    }

    public static JsonResult error() {
        return error(null, null);
    }

    public static JsonResult error(String message) {
        return error(null, message);
    }

    public static JsonResult error(String message, boolean escape) {
        return error(null, message, escape);
    }

    public static JsonResult error(Map<String, ? extends Object> data) {
        return error(data, null);
    }

    public static JsonResult error(Map<String, ? extends Object> data, boolean escape) {
        return error(data, null);
    }

    public static JsonResult error(Object data, String message) {
        return buildJsonResult(ResultCode.ERROR, data, message);
    }

    public static JsonResult error(Object data, String message, boolean escape) {
        return buildJsonResult(null, ResultCode.ERROR, data, message, escape);
    }

    public static JsonResult submitted(Object data) {
        return buildJsonResult(ResultCode.SUBMITED, data, null);
    }

    public static JsonResult forbidden() {
        return buildJsonResult(ResultCode.FORBIDDEN, null, null);
    }

    public static JsonResult forbidden(Object data, String message) {
        return buildJsonResult(ResultCode.FORBIDDEN, data, message);
    }

    public static JsonResult forbidden(Object data, String message, boolean escape) {
        return buildJsonResult(null, ResultCode.FORBIDDEN, data, message, escape);
    }

    public static String getSubmitedJson(String data) {
        String submitedJson = StringUtils.EMPTY;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("result", ResultCode.SUBMITED.getValue());
        params.put("message", getMessage(ResultCode.SUBMITED, null));
        params.put("data", StringUtils.isNotEmpty(data) ? data : StringUtils.EMPTY);
        try {
            submitedJson = new Gson().toJson(params);
        } catch (Exception e) {

        }
        return submitedJson;
    }

    public static String getForbiddenJson() {
        String forbiddenJson = StringUtils.EMPTY;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("result", ResultCode.FORBIDDEN.getValue());
        params.put("message", getMessage(ResultCode.FORBIDDEN, null));
        params.put("data", "");
        try {
            forbiddenJson = new Gson().toJson(params);
        } catch (Exception e) {

        }
        return forbiddenJson;
    }

    public static String getNeedLoginJson() {
        String needLoginJson = StringUtils.EMPTY;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("result", ResultCode.NEED_LOGIN.getValue());// 本次请求是否成功
        params.put("message", getMessage(ResultCode.NEED_LOGIN, null));// 用户封装信息，典型的是检验出错信息
        params.put("data", "");// 本次请求需要返回的数据
        try {
            needLoginJson = new Gson().toJson(params);
        } catch (Exception e) {

        }
        return needLoginJson;
    }

    public static String createJsonResult(ResultCode code, Object data, String message) {
        return new Gson().toJson(new DefaultJsonResult(code.getValue(), getMessage(code, message),
                                                       data == null ? StringUtils.EMPTY : data));
    }

    public static String createJsonResult(ResultCode code, String message) {
        return createJsonResult(code, null, message);
    }

    private static JsonResult buildJsonResult(ResultCode code, Object data, String message) {
        return buildJsonResult(null, code, data, message, true);
    }

    private static JsonResult buildJsonResult(String callback, ResultCode code, Object data, String message,
                                              boolean escape) {
        DefaultJsonResult jsonResult = new DefaultJsonResult(code.getValue(), getMessage(code, message),
                                                             data == null ? StringUtils.EMPTY : data);
        JsonResult deferredResult = new JsonResult();
        deferredResult.setResult(jsonResult);
        return deferredResult;
    }

    public static class JsonResult extends DeferredResult<DefaultJsonResult> {

    }

    static class DefaultJsonResult {

        private int    code;
        private String message;
        private Object data;

        public DefaultJsonResult(int code, String message, Object data) {
            setCode(code);
            setMessage(message);
            setData(data);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    private static String getMessage(ResultCode code, String message) {
        if (Argument.isNotBlank(message)) {
            return message;
        }
        switch (code) {
            case SUCCESS:
                return "操作成功";
            case ERROR:
                return "操作失败";
            case NEED_LOGIN:
                return "需要登录";
            case SUBMITED:
                return "表单重复提交";
            case FORBIDDEN:
                return "权限不够";
            default:
                return code.name();
        }
    }

    public static <T> DeferredResult<IResult> deferredResult(GenericsResult<T> iResult) {
        DeferredResult<IResult> dr = new DeferredResult<IResult>();
        dr.setResult(iResult);
        return dr;
    }
}
