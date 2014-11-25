/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;

import org.apache.velocity.VelocityContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.app.biz.BaseTestCase;
import com.zb.app.biz.cons.LineTemplateEnum;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelLineDO;
import com.zb.app.biz.domain.TravelRouteDO;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.query.TravelLineQuery;
import com.zb.app.biz.query.TravelRouteQuery;
import com.zb.app.biz.service.interfaces.CompanyService;
import com.zb.app.biz.service.interfaces.LineService;
import com.zb.app.web.tools.ExportTools;
import com.zb.app.web.vo.TravelLineVO;

/**
 * @author zxc Jun 25, 2014 4:26:06 PM
 */
public class TravalLineServiceTest extends BaseTestCase {

    @Autowired
    private LineService    lineService;
    @Autowired
    private CompanyService service;

    @Override
    public void onSetUp() throws Exception {
        if (service == null) {
            service = (CompanyService) getBean("travelCompanyServiceImpl");
        }
        if (lineService == null) {
            lineService = (LineService) getBean("travelLineServiceImpl");
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void listTravelLine() {
        List<TravelLineDO> list = lineService.list(new TravelLineQuery());
        assertNotNullOrEmpty(list);
    }

    @Test
    public void listPaginationTravelLine() {

    }

    @Test
    public void getTravelLineById() {

    }

    @Test
    public void addTravelLine() {
        String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        // SQL数据库引擎
        String connectDB = "jdbc:sqlserver://202.91.242.116:1314;DatabaseName=TravelDB";
        // 数据源 ！！！！注意若出现加载或者连接数据库失败一般是这里出现问题
        // 我将在下面详述
        try {
            // 加载数据库引擎，返回给定字符串名的类
            Class.forName(JDriver);
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
            System.out.println("加载数据库引擎失败");
            System.exit(0);
        }
        System.out.println("数据库驱动成功");

        try {
            String user = "sa";
            // 这里只要注意用户名密码不要写错即可
            String password = "zhangwenjin@123";
            Connection con = DriverManager.getConnection(connectDB, user, password);
            // 连接数据库对象
            System.out.println("连接数据库成功");
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            Statement stmt3 = con.createStatement();
            // 读取数据
            System.out.println("开始读取数据");
            ResultSet rs = stmt.executeQuery("SELECT * FROM DBO.TRAVEL_LINE WHERE Z_ID IS NULL");// 返回SQL语句查询结果集(集合)
            // 循环输出每一条记录
            while (rs.next()) {
                // 生成线路
                TravelLineDO linedo = new TravelLineDO();
                linedo.setlTile(rs.getString("L_TITLE"));
                linedo.setlType(rs.getInt("L_TYPE"));
                linedo.setlProvince(rs.getString("L_Province"));
                linedo.setlCity(rs.getString("L_City"));
                linedo.setlArea(rs.getString("L_Area"));
                linedo.setlArrivalProvince(rs.getString("L_ArrivalProvince"));
                linedo.setlArrivalCity(rs.getString("L_ArrivalCity"));
                linedo.setlArrivalArea(rs.getString("L_ArrivalArea"));
                linedo.setlDay(rs.getInt("L_Day"));
                linedo.setlJhd(rs.getString("L_JHD"));
                linedo.setlJhdTime(rs.getString("L_JHDTime"));
                linedo.setlIco(rs.getString("L_Ico"));
                linedo.setlDipei(rs.getString("L_DiPei"));
                linedo.setlGroupTel(rs.getString("L_GroupTel"));
                linedo.setlMode(rs.getString("L_Mode"));
                linedo.setlNMode(rs.getString("L_NMode"));
                linedo.setlYesItem(rs.getString("L_YesItem"));
                linedo.setlNoItem(rs.getString("L_NoItem"));
                linedo.setlChildren(rs.getString("L_Children"));
                linedo.setlShop(rs.getString("L_Shop"));
                linedo.setlExpenseItem(rs.getString("L_ExpenseItem"));
                linedo.setlPreseItem(rs.getString("L_PreseItem"));
                linedo.setlAttention(rs.getString("L_Attention"));
                linedo.setlOther(rs.getString("L_Other"));
                linedo.setlReminder(rs.getString("L_Reminder"));
                linedo.setlTemplateState(LineTemplateEnum.Template.getValue());
                linedo.setlTourContent(rs.getString("L_TourContent"));
                // 获取线路对应的公司原名称
                ResultSet cid = stmt3.executeQuery("SELECT C_NAME,C_TYPE FROM TRAVEL_COMPANY WHERE C_ID="
                                                   + rs.getInt("C_ID"));
                while (cid.next()) {
                    TravelCompanyQuery query = new TravelCompanyQuery();
                    query.setcName(cid.getString("C_NAME"));
                    if (cid.getInt("C_TYPE") == 0) {
                        query.setcType(3);
                    } else {
                        query.setcType(cid.getInt("C_TYPE"));
                    }
                    TravelCompanyDO travelCompanyDO = service.getByName(query);
                    linedo.setcId(travelCompanyDO.getcId());
                }
                lineService.addTravelLine(linedo);

                // 添加行程
                ResultSet rsMember = stmt2.executeQuery("select * from dbo.Travel_Route where L_Id="
                                                        + rs.getInt("L_Id"));
                TravelRouteDO routedo = new TravelRouteDO();
                while (rsMember.next()) {
                    routedo.setlId(linedo.getlId());
                    routedo.setrContent(rsMember.getString("R_Content"));
                    routedo.setrZhu(rsMember.getString("R_Zhu"));
                    routedo.setrCan(rsMember.getString("R_Can"));
                    routedo.setrCar(rsMember.getString("R_Car"));
                    lineService.addTravelRoute(routedo);
                }
            }
            // 关闭连接
            stmt.close();// 关闭命令对象连接
            stmt2.close();
            stmt3.close();
            con.close();// 关闭数据库连接
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getErrorCode());
            // System.out.println("数据库连接错误");
            System.exit(0);
        }
    }

    @Test
    public void updateTravelLine() {

    }

    @Test
    public void deleteTravelLine() {

    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ////
    // ////
    // ////
    // /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void listTravelRoute() {
        List<TravelRouteDO> list = lineService.list(new TravelRouteQuery());
        assertNotNullOrEmpty(list);
    }

    @Test
    public void listPaginationTravelRoute() {

    }

    @Test
    public void getTravelRouteById() {
        TravelLineDO trdo = lineService.getTravelLineById(Long.parseLong(202 + ""));
        TravelLineVO line = new TravelLineVO(trdo);
        VelocityContext context = new VelocityContext();
        context.put("line", line);
        boolean bool = ExportTools.exportDoc(context, "word.vm");
        System.err.println(bool);
    }

    @Test
    public void addTravelRoute() {
        TravelRouteDO TravelRoute = new TravelRouteDO();
        TravelRoute.setlId(100l);
        TravelRoute.setrZhu("get");

        Integer id = lineService.addTravelRoute(TravelRoute);
        Assert.assertNotNull(id);
    }

    @Test
    public void updateTravelRoute() {

    }

    @Test
    public void deleteTravelRoute() {

    }
}
