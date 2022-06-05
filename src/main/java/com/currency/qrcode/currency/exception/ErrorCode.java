/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.exception;

/**
 * 公共错误码（Error Code）常量
 *
 * 详见http://gollum.baidu.com/BceApiSpec
 * 在以上BCE公共错误码的基础上，增加自定义错误码
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/30 5:26 下午
 * @since 1.0.0
 */
public final class ErrorCode {

    private ErrorCode() {
    }

    /****************
     * 公共错误码 *
     ****************/

    /**
     * 无权限访问资源
     */
    public static final String ACCESS_DENIED = "AccessDenied";

    /**
     * 请求的JSON格式正确，但语义不符合要求
     */
    public static final String INAPPROPRIATE_JSON = "InappropriateJSON";

    /**
     * Access Key ID不存在
     */
    public static final String INVALID_ACCESS_KEY_ID = "InvalidAccessKeyId";

    /**
     * Authorization头域格式错误
     */
    public static final String INVALID_HTTP_AUTH_HEADER = "InvalidHTTPAuthHeader";

    /**
     * HTTP body格式错误
     */
    public static final String INVALIED_HTTP_REQUEST = "InvalidHTTPRequest";

    /**
     * URI形式不正确
     */
    public static final String INVALIED_URL = "InvalidURI";

    /**
     * JSON格式不合法
     */
    public static final String MALFORMED_JSON = "MalformedJSON";

    /**
     * URI版本号不合法
     */
    public static final String INVALID_VERSION = "InvalidVersion";

    /**
     * 没有开通对应的服务
     */
    public static final String OPT_IN_REQUIRED = "OptInRequired";

    /**
     * ETAG不匹配
     */
    public static final String PRE_CONDITION_FAILED = "PreconditionFailed";

    /**
     * 请求超时
     */
    public static final String REQUEST_EXPIRED = "RequestExpired";

    /**
     * 幂等性参数不匹配
     */
    public static final String IDEMPOTENT_PARAMETER_MISMATCH = "IdempotentParameterMismatch";

    /**
     * 无效签名
     */
    public static final String SIGNATURE_DOES_NOT_MATCH = "SignatureDoesNotMatch";

    /**
     * 未知错误（对于有明确定义的错误不应该使用）
     */
    public static final String INTERNAL_ERROR = "InternalError";

    /**
     * 角色没找到
     */
    public static final String ROLE_NOT_FOUND = "RoleNotFound";

}
