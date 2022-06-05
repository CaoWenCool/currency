/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.exception;

import com.currency.qrcode.currency.i18n.I18nMessageUtils;
import org.apache.http.HttpStatus;

/**
 * 公共异常类.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/5 下午4:35
 */
public class XCloudCommonExceptions {

    /**
     * 请求参数无效
     * httpStatus: 400
     */
    public static class RequestInvalidException extends XCloudException {
        public RequestInvalidException() {
            super(I18nMessageUtils.message("RequestInvalidException"), HttpStatus.SC_BAD_REQUEST, "BadRequest");
            
        }

        public RequestInvalidException(String message) {
            super(message, HttpStatus.SC_BAD_REQUEST, "BadRequest");
            
        }
    }


    /**
     * 不可操作（重复名字、重建正在进行中等）
     * httpStatus: 409
     */
    public static class ResourceInTaskException extends XCloudException {
        public ResourceInTaskException() {
            super(I18nMessageUtils.message("ResourceInTaskException"), HttpStatus.SC_CONFLICT, "OperationDenied");
            
        }

        public ResourceInTaskException(String message) {
            super(message, HttpStatus.SC_CONFLICT, "OperationDenied");
            
        }
    }

    /**
     * 支付失败
     * httpStatus: 403
     */
    public static class PaymentFailedException extends XCloudException {
        public PaymentFailedException() {
            super(I18nMessageUtils.message("PaymentFailedException"), HttpStatus.SC_FORBIDDEN, "PaymentFailed");
            
        }
    }

    /**
     * 支付失败，余额不足 403
     */
    public static class InsufficientBalanceException extends XCloudException {
        public InsufficientBalanceException() {
            super(I18nMessageUtils.message("InsufficientBalanceException"), HttpStatus.SC_FORBIDDEN,
                    "InsufficientBalance");
            
        }
    }

    /**
     * 资源不存在
     * httpStatus: 404
     */
    public static class ResourceNotExistException extends XCloudException {
        public ResourceNotExistException() {
            super(I18nMessageUtils.message("ResourceNotExistException"),
                    HttpStatus.SC_NOT_FOUND, "NoSuchObject");
            
        }

        public ResourceNotExistException(String msg) {
            super(I18nMessageUtils.message("ResourceNotExistException2", msg),
                    HttpStatus.SC_NOT_FOUND, "NoSuchObject");
            
        }
    }


    // region  跟ErrorCode对应，公共异常

    /**
     * 权限错误，无权限访问对应的资源
     * httpStatus: 403
     */
    public static class AccessDeniedException extends XCloudException {
        public AccessDeniedException() {
            super(I18nMessageUtils.message("AccessDeniedException"), HttpStatus.SC_FORBIDDEN, ErrorCode.ACCESS_DENIED);
            
        }

        public AccessDeniedException(String message) {
            super(message, HttpStatus.SC_FORBIDDEN, ErrorCode.ACCESS_DENIED);
            
        }
    }

    /**
     * 请求中的JSON格式正确，但语义上不符合要求。如缺少某个必需项，或者值类型不匹配等。出于兼容性考虑，对于所有无法识别的项应直接忽略，
     * 不应该返回这个错误
     * httpStatus: 400
     */
    public static class InappropriateJSONException extends XCloudException {
        public InappropriateJSONException() {
            super(I18nMessageUtils.message("InappropriateJSONException"),
                    HttpStatus.SC_BAD_REQUEST, ErrorCode.INAPPROPRIATE_JSON);
            setHttpStatus(HttpStatus.SC_BAD_REQUEST);
            
        }
    }

    /**
     * Access Key ID不存在
     * httpStatus: 403
     */
    public static class InvalidAccessKeyIdException extends XCloudException {
        public InvalidAccessKeyIdException() {
            super(I18nMessageUtils.message("InvalidAccessKeyIdException"), HttpStatus.SC_FORBIDDEN,
                    ErrorCode.INVALID_ACCESS_KEY_ID);
            
        }
    }

    /**
     * Authorization头域格式错误
     * httpStatus: 400
     */
    public static class InvalidHTTPAuthHeaderException extends XCloudException {
        public InvalidHTTPAuthHeaderException() {
            super(I18nMessageUtils.message("InvalidHTTPAuthHeaderException"),
                    HttpStatus.SC_BAD_REQUEST,
                    ErrorCode.INVALID_HTTP_AUTH_HEADER);
            
        }
    }

    /**
     * HTTP body格式错误。例如不符合指定的Encoding等
     * httpStatus: 400
     */
    public static class InvalidHTTPRequestException extends XCloudException {
        public InvalidHTTPRequestException() {
            super(I18nMessageUtils.message("InvalidHTTPRequestException"), HttpStatus.SC_BAD_REQUEST,
                    ErrorCode.INVALIED_HTTP_REQUEST);
            
        }

        public InvalidHTTPRequestException(String message) {
            super(message, HttpStatus.SC_BAD_REQUEST, ErrorCode.INVALIED_HTTP_REQUEST);
            
        }
    }

    /**
     * URI形式不正确。例如一些服务定义的关键词不匹配等。对于ID不匹配等问题，应定义更加具体的错误码，例如NoSuchKey。
     * httpStatus: 400
     */
    public static class InvalidURIException extends XCloudException {
        public InvalidURIException() {
            super(I18nMessageUtils.message("InvalidURIException"), HttpStatus.SC_BAD_REQUEST, ErrorCode.INVALIED_URL);
            
        }

        public InvalidURIException(String message) {
            super(message, HttpStatus.SC_BAD_REQUEST, ErrorCode.INVALIED_URL);
            
        }
    }

    /**
     * JSON格式不合法
     * httpStatus: 400
     */
    public static class MalformedJSONException extends XCloudException {
        public MalformedJSONException() {
            super(I18nMessageUtils.message("MalformedJSONException"),
                    HttpStatus.SC_BAD_REQUEST,
                    ErrorCode.MALFORMED_JSON);
            
        }
    }

    /**
     * URI的版本号不合法
     * httpStatus: 404
     */
    public static class InvalidVersionException extends XCloudException {
        public InvalidVersionException() {
            super(I18nMessageUtils.message("InvalidVersionException"),
                    HttpStatus.SC_NOT_FOUND,
                    ErrorCode.INVALID_VERSION);
            
        }
    }

    /**
     * 没有开通对应的服务
     * httpStatus: 403
     */
    public static class OptInRequiredException extends XCloudException {
        public OptInRequiredException() {
            super(I18nMessageUtils.message("OptInRequiredException"), HttpStatus.SC_FORBIDDEN,
                    ErrorCode.OPT_IN_REQUIRED);
            
        }

        public OptInRequiredException(String message) {
            super(message, HttpStatus.SC_FORBIDDEN, ErrorCode.OPT_IN_REQUIRED);
            
        }
    }

    /**
     * 详见[ETag的使用](#etag)：为了保证读取到再提交中间没有其他更新被提交，B在提交时将x-bce-if-match头域设为之前读到的ETag值。
     * 服务会比较是否和当前的ETag值一致，只有在一致时才允许更新，否则返回PreconditionFailed。
     * 当B收到PreconditionFailed时，应重新读取最新配置，合并之后再提交。
     * httpStatus: 412
     */
    public static class PreconditionFailedException extends XCloudException {
        public PreconditionFailedException() {
            super(I18nMessageUtils.message("PreconditionFailedException"), HttpStatus.SC_PRECONDITION_FAILED,
                    ErrorCode.PRE_CONDITION_FAILED);
            
        }
    }

    /**
     * 请求超时。XXX要改成[x-bce-date](#req_header_x-bce-date)的值。如果请求中只有Date，则需要将Date转换为[本规范指定的格式](#datetime)。
     * 所有请求都必须使用头域Date或者x-bce-date指定时间戳。当服务器收到请求时，需要比较时间戳与当前服务器时间。当两者相差超过30分钟时，返回RequestExpired错误。这样可以在一定程度上防止重放。
     * httpStatus: 400
     */
    public static class RequestExpiredException extends XCloudException {
        public RequestExpiredException(String datetime) {
            super(I18nMessageUtils.message("RequestExpiredException", datetime), HttpStatus.SC_BAD_REQUEST,
                    ErrorCode.REQUEST_EXPIRED);
            
        }
    }

    /**
     * clientToken对应的API参数不一样。
     * httpStatus: 403
     */
    public static class IdempotentParameterMismatchException extends XCloudException {
        public IdempotentParameterMismatchException() {
            super(I18nMessageUtils.message("IdempotentParameterMismatchException"), HttpStatus.SC_FORBIDDEN,
                    ErrorCode.IDEMPOTENT_PARAMETER_MISMATCH);
            
        }
    }

    /**
     * Authorization头域中附带的签名和服务端验证不一致。
     * httpStatus: 400
     */
    public static class SignatureDoesNotMatchException extends XCloudException {
        public SignatureDoesNotMatchException() {
            super(I18nMessageUtils.message("SignatureDoesNotMatchException"), HttpStatus.SC_BAD_REQUEST,
                    ErrorCode.SIGNATURE_DOES_NOT_MATCH);
            
        }
    }



    /**
     * 内部（未知）服务器错误，handler会专门截获并记录error级别错误日志（包括causeException）。
     * 所有未定义的其他错误。在有明确对应的其他类型的错误时（包括通用的和服务自定义的）不应该使用。
     * httpStatus: 500
     */
    public static class ServerInternalException extends XCloudException {
        public ServerInternalException(Exception causeException) {
            super(I18nMessageUtils.message("ServerInternalException"), HttpStatus.SC_INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR);
            super.initCause(causeException);
            
        }
    }

    /**
     * 系统内没有找到该角色
     * httpStatus: 400
     */
    public static class RoleNotFoundException extends XCloudException{

        public RoleNotFoundException(String message) {
            super(I18nMessageUtils.message("RoleNotFoundException", message), HttpStatus.SC_BAD_REQUEST,
                    ErrorCode.ROLE_NOT_FOUND);
            
        }
    }
}
