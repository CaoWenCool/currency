package com.currency.qrcode.currency.database.mybatis;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Delegating all methods of {@link StatementHandler} to {@code delegate}.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/18 下午3:35
 */
public class DelegatingStatementHandler implements StatementHandler {

    protected final StatementHandler delegate;

    public DelegatingStatementHandler(StatementHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public Statement prepare(Connection connection, Integer var2) throws SQLException {
        return delegate.prepare(connection, var2);
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        delegate.parameterize(statement);
    }

    @Override
    public void batch(Statement statement) throws SQLException {
        delegate.batch(statement);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        return delegate.update(statement);
    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        return delegate.query(statement, resultHandler);
    }

    @Override
    public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
        return delegate.queryCursor(statement);
    }

    @Override
    public BoundSql getBoundSql() {
        return delegate.getBoundSql();
    }

    @Override
    public ParameterHandler getParameterHandler() {
        return delegate.getParameterHandler();
    }
}
