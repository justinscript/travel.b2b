/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.web.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author Administrator 2014-8-7 下午12:08:41
 */
public class ExportTools {

    public static boolean exportDoc(VelocityContext vc, String templetePath) {
        Properties ps = new Properties();
        ps.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "./src/");// 这是模板所在路径
        VelocityEngine ve = new VelocityEngine();
        ve.init(ps);
        ve.init();
        Template template = ve.getTemplate(templetePath, "utf-8");
        File srcFile = new File("E:/Test.doc");// 输出路径
        try {
            FileOutputStream fos = new FileOutputStream(srcFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            template.merge(vc, writer);
            writer.flush();
            writer.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
