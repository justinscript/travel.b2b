/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.CLOB;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author zxc Jun 25, 2014 1:04:23 PM
 */
public class OracleClobTypeHandler implements TypeHandler<Object> {

    public Object valueOf(String param) {
        return null;
    }

    @Override
    public Object getResult(ResultSet arg0, String arg1) throws SQLException {
        CLOB clob = (CLOB) arg0.getClob(arg1);
        return (clob == null || clob.length() == 0) ? null : clob.getSubString((long) 1, (int) clob.length());
    }

    @Override
    public Object getResult(ResultSet arg0, int arg1) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setParameter(PreparedStatement arg0, int arg1, Object arg2, JdbcType arg3) throws SQLException {
        CLOB clob = CLOB.empty_lob();
        clob.setString(1, (String) arg2);
        arg0.setClob(arg1, clob);
    }
}
