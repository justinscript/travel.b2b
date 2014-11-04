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

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.zb.app.biz.BaseTestCase;
import com.zb.app.biz.domain.TravelCompanyDO;
import com.zb.app.biz.domain.TravelMemberDO;
import com.zb.app.biz.query.TravelCompanyQuery;
import com.zb.app.biz.service.interfaces.CompanyService;
import com.zb.app.biz.service.interfaces.MemberService;
import com.zb.app.common.security.EncryptBuilder;
import com.zb.app.common.util.PinyinParser;

/**
 * 类TravelCompanyServiceTest.java的实现描述：TODO 类实现描述
 * 
 * @author ZhouZhong 2014-6-26 上午11:04:15
 */
public class TravelCompanyServiceTest extends BaseTestCase {

    private CompanyService service;
    private MemberService memberService;

    @Override
    public void onSetUp() throws Exception {
        if (service == null) {
            service = (CompanyService) getBean("travelCompanyServiceImpl");
        }
        if (memberService == null){
        	memberService = (MemberService) getBean("travelMemberServiceImpl");
        }
    }

    @Test
    public void testAdd() {
	      String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//SQL数据库引擎
	      String connectDB="jdbc:sqlserver://202.91.242.116:1314;DatabaseName=TravelDB";
	//数据源  ！！！！注意若出现加载或者连接数据库失败一般是这里出现问题
	     // 我将在下面详述
	      try {
	    //加载数据库引擎，返回给定字符串名的类
	          Class.forName(JDriver);
	      }catch(ClassNotFoundException e)
	      {
	       //e.printStackTrace();
	          System.out.println("加载数据库引擎失败");
	          System.exit(0);
	      }     
	      System.out.println("数据库驱动成功");
	      
	      try {
	          String user="sa";                                    
	   //这里只要注意用户名密码不要写错即可
	          String password="zhangwenjin@123";
	          Connection con=DriverManager.getConnection(connectDB,user,password);
	//连接数据库对象
	          System.out.println("连接数据库成功");
	          Statement stmt=con.createStatement();
	          Statement stmt2=con.createStatement();
	//创建SQL命令对象
	       
	       //创建表
	           //System.out.println("开始创建表");
	          //创建表SQL语句
//	           String query= "create table TABLE1(ID NCHAR(2),NAME NCHAR(10))";
//	           stmt.executeUpdate(query);//执行SQL命令对象
//	           System.out.println("表创建成功");
//	              
//	           //输入数据
//	           System.out.println("开始插入数据");
//	           String a1="INSERT INTO TABLE1 VALUES('1','旭哥')";
//	                //插入数据SQL语句
//	           String a2="INSERT INTO TABLE1 VALUES('2','伟哥')";
//	           String a3="INSERT INTO TABLE1 VALUES('3','张哥')";
//	           stmt.executeUpdate(a1);//执行SQL命令对象
//	           stmt.executeUpdate(a2);   
//	           stmt.executeUpdate(a3);
//	           System.out.println("插入数据成功");
	           
	           //读取数据
	           System.out.println("开始读取数据");
	           ResultSet rs=stmt.executeQuery("SELECT * FROM TRAVEL_COMPANY");//返回SQL语句查询结果集(集合)
	       //循环输出每一条记录
			while (rs.next()) {
				// 输出每个字段
				System.out.println(rs.getString("C_ID") + "\t"
						+ rs.getString("C_NAME"));
				TravelCompanyDO travelCompanyDO = new TravelCompanyDO();
				if(rs.getInt("C_TYPE") == 0){
					travelCompanyDO.setcType(3);
				}else{
					travelCompanyDO.setcType(rs.getInt("C_TYPE"));
				}
				travelCompanyDO.setcName(rs.getString("C_NAME"));
				travelCompanyDO.setcProvince(rs.getString("C_Province"));
				travelCompanyDO.setcCity(rs.getString("C_City"));
				travelCompanyDO.setcCounty(rs.getString("C_County"));
				travelCompanyDO.setcCustomname(rs.getString("C_CustomName"));
				travelCompanyDO.setcLogo(rs.getString("C_Logo"));
				travelCompanyDO.setcQQ(rs.getString("C_QQ"));
				travelCompanyDO.setcEmail(rs.getString("C_Email"));
				travelCompanyDO.setcTel(rs.getString("C_Tel"));
				travelCompanyDO.setcFax(rs.getString("C_Fax"));
				travelCompanyDO.setcMobile(rs.getString("C_Mobile"));
				travelCompanyDO.setcAddress(rs.getString("C_Address"));
				travelCompanyDO.setcAboutus(rs.getString("C_AboutUs"));
				travelCompanyDO.setcContact(rs.getString("C_Contact"));
				travelCompanyDO.setcDefaultCity(rs.getString("C_DefaultCity"));
				travelCompanyDO.setcCityTop(rs.getString("C_CityTop"));
				travelCompanyDO.setcCityList(rs.getString("C_CityList"));
				travelCompanyDO.setcBank(rs.getString("C_Bank"));
				travelCompanyDO.setcCorporation(rs.getString("C_Referrer"));
				travelCompanyDO.setcRecommend(rs.getString("C_Corporation"));
				travelCompanyDO.setcLoginTime(rs.getDate("C_LoginTime"));
				travelCompanyDO.setcState(1);
				travelCompanyDO.setcSpell(PinyinParser
						.converterToFirstSpell(travelCompanyDO.getcName()));
				service.insert(travelCompanyDO);
				ResultSet rsMember = stmt2
						.executeQuery("SELECT * FROM TRAVEL_MEMBER WHERE C_ID = "
								+ rs.getInt("C_ID"));
				TravelMemberDO travelMemberDO = new TravelMemberDO();
				while (rsMember.next()) {
					System.out.println(rsMember.getString("M_ID") + "\t"
							+ rsMember.getString("M_Password"));
					travelMemberDO.setcId(travelCompanyDO.getcId());
					travelMemberDO.setmUserName(StringUtils.lowerCase(rsMember
							.getString("M_UserName")));
					travelMemberDO.setmPassword(EncryptBuilder.getInstance()
							.encrypt(rsMember.getString("M_Password")));
					travelMemberDO.setmName(rsMember.getString("M_Name"));
					travelMemberDO.setmSex(rsMember.getInt("M_Sex"));
					travelMemberDO.setmMobile(rsMember.getString("M_Mobile"));
					travelMemberDO.setmTel(rsMember.getString("M_Tel"));
					travelMemberDO.setmEmail(rsMember.getString("M_Email"));
					travelMemberDO.setmFax(rsMember.getString("M_Fax"));
					travelMemberDO.setmQQ(rsMember.getString("M_QQ"));
					travelMemberDO.setmType(rsMember.getInt("M_Type"));
					//travelMemberDO.setmRole(rsMember.getString("M_Role"));
					travelMemberDO.setmState(rsMember.getInt("M_State"));
					memberService.insert(travelMemberDO);
				}
			}
	       //关闭连接
	       stmt.close();//关闭命令对象连接
	       stmt2.close();;
	       con.close();//关闭数据库连接
	      }catch(SQLException e){
	       e.printStackTrace();
	       System.out.print(e.getErrorCode());
	       //System.out.println("数据库连接错误");
	       System.exit(0);
	      }
    }

    @Test
    public void testQuery() {
        System.out.println("QQQQQQQQQQQQQQQQ");
        List<TravelCompanyDO> list = service.list();
        for (TravelCompanyDO travelCompanyDO : list) {
            System.out.println(travelCompanyDO.getcId());
        }
    }

    @Test
    public void testUpdate() {
        TravelCompanyDO travelCompanyDO = new TravelCompanyDO();
        travelCompanyDO.setcId(14L);
        travelCompanyDO.setcName("WOSHIXXX");
        service.update(travelCompanyDO);
    }

    @Test
    public void testDelete() {
        service.delete(14);
    }

    @Test
    public void testGetName() {
        // "WOSHIXXX"
        TravelCompanyDO travelCompanyDO = service.getByName(new TravelCompanyQuery());
        if (travelCompanyDO != null) {
            System.out.println(travelCompanyDO.getGmtCreate());
        } else {
            System.out.println("没找到");
        }

    }
}
