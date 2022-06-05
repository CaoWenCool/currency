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
 *  数据库查询到的 null 转成 0.0F.
 * @see FloatTypeHandler
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/5 上午10:30
 * @since 1.0.0
 */
@MappedTypes(Float.class)
@MappedJdbcTypes(value = {JdbcType.FLOAT}, includeNullJdbcType = true)
public class NullFloatTypeHandler extends BaseTypeHandler<Float> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Float parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setFloat(i, parameter);
    }

    @Override
    public Float getNullableResult(ResultSet rs, String columnName) throws SQLException {
        float result = rs.getFloat(columnName);
        return result;
    }

    @Override
    public Float getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        float result = rs.getFloat(columnIndex);
        return result;
    }

    @Override
    public Float getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        float result = cs.getFloat(columnIndex);
        return result;
    }
}
