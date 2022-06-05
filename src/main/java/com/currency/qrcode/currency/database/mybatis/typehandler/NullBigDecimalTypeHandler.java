/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.database.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库查询到的 null 转成 0.0.
 * @see org.apache.ibatis.type.BigDecimalTypeHandler
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/5 上午10:39
 * @since 1.0.0
 */
@MappedTypes(BigDecimal.class)
@MappedJdbcTypes(value = {JdbcType.REAL, JdbcType.DECIMAL, JdbcType.NUMERIC}, includeNullJdbcType = true)
public class NullBigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BigDecimal parameter, JdbcType jdbcType) throws
        SQLException {
        ps.setBigDecimal(i, parameter);
    }

    @Override
    public BigDecimal getNullableResult(ResultSet rs, String columnName) throws SQLException {
        BigDecimal result = rs.getBigDecimal(columnName);
        return result == null ? BigDecimal.valueOf(0) : result;
    }

    @Override
    public BigDecimal getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        BigDecimal result = rs.getBigDecimal(columnIndex);
        return result == null ? BigDecimal.valueOf(0) : result;
    }

    @Override
    public BigDecimal getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        BigDecimal result = cs.getBigDecimal(columnIndex);
        return result == null ? BigDecimal.valueOf(0) : result;
    }
}
