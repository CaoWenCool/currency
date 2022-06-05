package com.currency.qrcode.currency.database;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Utils for preventing SQL injection (SQLi).
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/14 下午7:20
 */
public class SqliPrevention {
    public static final String IDENTIFIER_REGEX = "[0-9a-zA-Z_]*";

    // 兼容  name和k.name两种格式的colume
    public static final String IDENTIFIER_WITHPRE_REGEX = "([0-9a-zA-Z_]+\\.)?[0-9a-zA-Z_]+";

    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";

    public static final Pattern IDENTIFIER_PATTERN = Pattern.compile(IDENTIFIER_REGEX);
    public static final Pattern IDENTIFIER_WITHPRE_PATTERN = Pattern.compile(IDENTIFIER_WITHPRE_REGEX);

  public static void checkTable(String table) {
        if (!IDENTIFIER_PATTERN.matcher(table).matches()) {
            throw new IllegalArgumentException("Invalid table name: " + table);
        }
    }

    public static void checkColumn(String column) {
        if (!IDENTIFIER_PATTERN.matcher(column).matches()) {
            throw new IllegalArgumentException("Invalid column name: " + column);
        }
    }

    /**
     * // 兼容  name和k.name两种格式的colume
     * @param column 列名
     */
    public static void checkColumnWithPrefix(String column) {
        if (!IDENTIFIER_WITHPRE_PATTERN.matcher(column).matches()) {
            throw new IllegalArgumentException("Invalid column name: " + column);
        }
    }

    public static void checkOrder(String order) {
        if (!StringUtils.equalsIgnoreCase(order, ORDER_ASC) && !StringUtils.equalsIgnoreCase(order, ORDER_DESC)) {
            throw new IllegalArgumentException("Invalid order: " + order);
        }
    }
}
