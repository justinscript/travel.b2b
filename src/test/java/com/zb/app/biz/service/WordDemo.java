package com.zb.app.biz.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.zb.app.web.vo.TravelLineVO;

public class WordDemo {

    public static void createDoc(VelocityContext vc, String templetePath) throws Exception {
        Properties ps = new Properties();
        ps.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, "./src/");// 这是模板所在路径
        VelocityEngine ve = new VelocityEngine();
        ve.init(ps);
        ve.init();
        Template template = ve.getTemplate(templetePath, "utf-8");
        File srcFile = new File("E:/Test.doc");// 输出路径
        FileOutputStream fos = new FileOutputStream(srcFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
        template.merge(vc, writer);
        writer.flush();
        writer.close();
        fos.close();
    }

    public static void main(String[] args) {
        VelocityContext velocityContext = new VelocityContext();
        TravelLineVO line = new TravelLineVO();
        line.setlTile("阳澄湖一日游");
        line.setlProvince("湖南");
        line.setlCity("株洲");
        velocityContext.put("line", line);
        try {
            WordDemo.createDoc(velocityContext, "word.vm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
