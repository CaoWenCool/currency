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
 *  数据库查询到的 null 转成 0.0D.
 * @see DoubleTypeHandler
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/5 上午10:30
 * @since 1.0.0
 */
@MappedTypes(Double.class)
@MappedJdbcTypes(value = {JdbcType.DOUBLE}, includeNullJdbcType = true)
public class NullDoubleTypeHandler extends BaseTypeHandler<Double> {

    public void setNonNullParameter(PreparedStatement ps, int i, Double parameter, JdbcType jdbcType) throws
        SQLException {
        ps.setDouble(i, parameter);
    }

    public Double getNullableResult(ResultSet rs, String columnName) throws SQLException {
        double result = rs.getDouble(columnName);
        return result;
    }

    public Double getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        double result = rs.getDouble(columnIndex);
        return result;
    }

    public Double getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        double result = cs.getDouble(columnIndex);
        return result;
    }
}
