/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.database.mybatis.typehandler;

import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  数据库查询到的 null 转成 0.
 * @see IntegerTypeHandler
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/5 上午10:30
 * @since 1.0.0
 */
@MappedTypes(Integer.class)
@MappedJdbcTypes(value = {JdbcType.INTEGER}, includeNullJdbcType = true)
public class NullIntegerTypeHandler extends BaseTypeHandler<Integer> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws
        SQLException {
        ps.setInt(i, parameter);
    }

    @Override
    public Integer getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int result = rs.getInt(columnName);
        return result;
    }

    @Override
    public Integer getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int result = rs.getInt(columnIndex);
        return result;
    }

    @Override
    public Integer getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int result = cs.getInt(columnIndex);
        return result;
    }
}
