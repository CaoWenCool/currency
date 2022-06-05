package com.currency.qrcode.currency.database.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Plugin for removing whitespaces in SQL.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/18 下午3:32
 */
@Intercepts(
    {@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
    )
    })
public class WhitespaceRemoverPlugin extends TypedInterceptor<StatementHandler> {

    private static final Logger log = LoggerFactory.getLogger(WhitespaceRemoverPlugin.class);

    public static final WhitespaceRemoverPlugin INSTANCE = new WhitespaceRemoverPlugin();

    private WhitespaceRemoverPlugin() {
        super(StatementHandler.class);
    }

    @Override
    protected StatementHandler wrap(StatementHandler target) {
        return new StatementHandlerWrapper(target);
    }

    private class StatementHandlerWrapper extends DelegatingStatementHandler {

        public StatementHandlerWrapper(StatementHandler delegate) {
            super(delegate);
        }

        @Override
        public Statement prepare(Connection connection, Integer var2) throws SQLException {
            String sql = delegate.getBoundSql().getSql();
            sql = removeWhitespaces(sql);
            log.debug("Whitespaces of SQL are removed. sql={}", sql);
            try {
                FieldUtils.writeField(delegate.getBoundSql(), "sql", sql, true);
            } catch (Exception e) {
                log.error("Failed to replace SQL. sql={}", sql, e);
            }
            return super.prepare(connection, var2);
        }

        private String removeWhitespaces(String sql) {
            if (StringUtils.isEmpty(sql)) {
                return sql;
            }

            String[] lines = StringUtils.split(sql, "\r\n");
            List<String> trimed = new ArrayList<>();
            for (String line : lines) {
                line = StringUtils.trim(line);
                if (StringUtils.isNotEmpty(line)) {
                    trimed.add(line);
                }
            }

            return StringUtils.join(trimed, " ");
        }

    }
}
