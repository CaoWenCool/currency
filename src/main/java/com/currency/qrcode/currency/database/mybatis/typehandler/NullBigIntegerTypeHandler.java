/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.database.mybatis.typehandler;

import org.apache.ibatis.type.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库查询到的 null 转成 0.
 * @see BigIntegerTypeHandler
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/5 上午10:39
 * @since 1.0.0
 */
@MappedTypes(BigInteger.class)
@MappedJdbcTypes(value = {}, includeNullJdbcType = true)
public class NullBigIntegerTypeHandler extends BaseTypeHandler<BigInteger> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BigInteger parameter, JdbcType jdbcType) throws
        SQLException {
        ps.setBigDecimal(i, new BigDecimal(parameter));
    }

    @Override
    public BigInteger getNullableResult(ResultSet rs, String columnName) throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnName);
        return bigDecimal == null ? BigInteger.valueOf(0) : bigDecimal.toBigInteger();
    }

    @Override
    public BigInteger getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(columnIndex);
        return bigDecimal == null ? BigInteger.valueOf(0) : bigDecimal.toBigInteger();
    }

    @Override
    public BigInteger getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        BigDecimal bigDecimal = cs.getBigDecimal(columnIndex);
        return bigDecimal == null ? BigInteger.valueOf(0) : bigDecimal.toBigInteger();
    }
}
