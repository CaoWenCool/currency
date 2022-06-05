package com.currency.qrcode.currency.database;

import org.apache.commons.lang3.StringUtils;

/**
 * Utils for escaping expressions within SQLs.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/14 下午8:48
 */
public class SqlEscape {
    public static String escapeLikePattern(String pattern) {
        // "\" is the escape character.
        pattern = StringUtils.replace(pattern, "\\", "\\\\");
        // "%" matches any number of characters, even zero characters.
        pattern = StringUtils.replace(pattern, "%", "\\%");
        // "_" matches exactly one character.
        pattern = StringUtils.replace(pattern, "_", "\\_");

        return pattern;
    }
}
