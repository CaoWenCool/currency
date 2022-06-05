/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging;

/**
 *  生态云常用常量值.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/4 上午11:58
 */
public final class XCloudConstant {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATETIME_OPENSTACK_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.FFF'Z'";
    // FFF microsecond, SSS millisecond
    public static final String DATETIME_LOCAL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // public static final String DATETIME_VOLUME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.FFF";
    // public static final String DATETIME_DAY_FORMAT = "yyyyMMdd";

    public static final String X_AUTH_TOKEN = "X-Auth-Token";

    // public static final String X_BCE_REQUEST_ID = "x-bce-request-id";
    // public static final String X_BCE_DATE = "x-bce-date";
    // public static final String X_BCE_SECURITY_TOKEN = "x-bce-security-token";
    // public static final String X_BCE_PREFIX = "x-bce-";
    // public static final String X_IDG_REQUEST_ID = "x-apollo-request-id";
    // public static final String X_IDG_DATE = "x-apollo-date";
    // public static final String X_IDG_SECURITY_TOKEN = "x-apollo-security-token";
    // public static final String X_IDG_CONSOLE_RPC_ID = "x-apollo-console-rpc-id";
    // public static final String X_IDG_PREFIX = "x-apollo-";
    public static final String X_IDG_REQUEST_ID = "x-bce-request-id";
    public static final String X_IDG_DATE = "x-bce-date";
    public static final String X_IDG_SECURITY_TOKEN = "x-bce-security-token";
    public static final String X_IDG_CONSOLE_RPC_ID = "x-bce-console-rpc-id";
    public static final String X_IDG_PREFIX = "x-bce-";

    public static final String HOST = "Host";
    public static final String AUTHORIZATION = "Authorization";

    public static final String ESCAPE_SPLIT_STRING = "\\|";
}
