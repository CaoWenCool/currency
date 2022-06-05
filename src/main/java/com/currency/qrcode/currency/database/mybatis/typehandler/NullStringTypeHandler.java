/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.database.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库查询到的 null 转成 ""
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/4 下午8:36
 * @since 1.0.0
 */
@MappedTypes(String.class)
@MappedJdbcTypes(value = {JdbcType.CHAR, JdbcType.CLOB, JdbcType.VARCHAR, JdbcType.LONGVARCHAR, JdbcType.NVARCHAR,
    JdbcType.NCHAR, JdbcType.NCLOB}, includeNullJdbcType = true)
public class NullStringTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName) == null ? "" : rs.getString(columnName);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex) == null ? "" : rs.getString(columnIndex);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex) == null ? "" : cs.getString(columnIndex);
    }
}
