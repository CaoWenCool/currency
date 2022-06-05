package com.currency.qrcode.currency.database.mybatis;


import com.currency.qrcode.currency.database.SqlEscape;

/**
 * Bean version of {@link SqlEscape}, which can be instantiated and used in mybatis SQLs.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/14 下午8:48
 */
public class SqlEscapeBean {
    public static final SqlEscapeBean INSTANCE = new SqlEscapeBean();

    public String likePattern(String pattern) {
        return SqlEscape.escapeLikePattern(pattern);
    }
}
