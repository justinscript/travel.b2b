/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zb.app.common.file.ExcelUtils;
import com.zb.app.common.interceptor.annotation.ExportExcelFile;
import com.zb.app.common.interceptor.annotation.ExportWordFile;

/**
 * @author zxc Aug 7, 2014 11:26:48 PM
 */
public class ExportFileAnnotationInterceptor extends HandlerInterceptorAdapter {

    public static Logger logger = LoggerFactory.getLogger(ExportFileAnnotationInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (handler == null || response == null) {
            return;
        }

        HandlerMethod hm = (HandlerMethod) handler;

        ExportWordFile exportWordFile = hm.getMethodAnnotation(ExportWordFile.class);
        // 没有导出word文件声明,放行
        if (exportWordFile != null) {
            String wordName = exportWordFile.value();
            if (StringUtils.isEmpty(wordName)) {
                return;
            }
            wordName = new String(wordName.getBytes(), "ISO8859-1");

            String contentDis = "attachment;filename=" + wordName + ".doc";
            response.setHeader("content-disposition", contentDis);
            response.setContentType("application/msword;");
            response.setCharacterEncoding("UTF-8");
        }

        ExportExcelFile exportExcelFile = hm.getMethodAnnotation(ExportExcelFile.class);
        // 没有导出excel文件声明,放行
        if (exportExcelFile != null) {
            String xlsName = exportExcelFile.value();
            if (StringUtils.isEmpty(xlsName)) {
                return;
            }
            xlsName = new String(xlsName.getBytes(), "UTF-8");

            List<?> list = (List<?>) modelAndView.getModel().get("list");
            String[] head = (String[]) modelAndView.getModel().get("head");
            modelAndView.clear();

            HSSFWorkbook workbook = ExcelUtils.defBuildExcel(list, xlsName, head);

            if (workbook == null) {
                try {
                    response.getOutputStream().print("Not conform to the requirements of data");
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                return;
            }

            response.setHeader("content-disposition", "attachment;filename=" + xlsName + ".xls");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            // 写入到 客户端response
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        }
    }
}
