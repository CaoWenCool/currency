/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.exception.handling;

import com.currency.qrcode.currency.exception.TokenException;
import com.currency.qrcode.currency.exception.XCloudException;
import com.currency.qrcode.currency.exception.XCloudValidationException;
import com.currency.qrcode.currency.i18n.I18nMessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service系列错误截处理器，返回标准错误格式数据.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/5 下午5:12
 */
@ControllerAdvice
public class ServiceExceptionHandler {
    Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MyBatisSystemException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request,
                                                           HttpServletResponse response, MyBatisSystemException ex) {
        log.debug("[exception:MyBatisSystemException] handled: {}", ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(500);
        body.setMsg(I18nMessageUtils.message("MyBatisSystemException"));
        body.setTime(new Date());
        log.info("[exception] [code:{}] {} {}", body.getCode(), request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.valueOf(500));
    }

    @ExceptionHandler(XCloudException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request,
                                                           HttpServletResponse response, XCloudException ex) {
        log.debug("[exception:XCloudException] handled: " + ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(ex.getHttpStatus());
        body.setMsg(ex.getMessage());
        body.setTime(new Date());
        log.info("[exception] [status:{},code:{}] {} {}", ex.getHttpStatus(), body.getCode(),
            request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.valueOf(ex.getHttpStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request,
                                                           HttpServletResponse response, Exception ex) {
        log.error("[exception:UnknownException] " + request.getMethod() + " " + request.getRequestURI(), ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.setTime(new Date());
        body.setMsg(I18nMessageUtils.message("ServerInternalException"));
        log.info("[exception:{}] {} {}", body.getCode(), request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           MethodArgumentTypeMismatchException ex) {
        log.debug("[exception:MethodArgumentTypeMismatchException] handled: " + ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        Integer code = HttpStatus.BAD_REQUEST.value();
        body.setCode(code);
        body.setTime(new Date());
        body.setMsg(I18nMessageUtils.message("RequestParamConverterException", ex.getName(), ex.getValue()));
        log.info("[exception] [status:{},code:{}] {} {}", code, body.getCode(),
                request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(XCloudValidationException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request, HttpServletResponse response,
                                                           XCloudValidationException ex) {
        log.debug("[exception:XCloudValidationException] handled: " + ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(ex.getHttpStatus());
        body.setTime(new Date());
        body.setMsg(convertFiledMessageMapToString(ex));
        log.info("[exception:{}] {} {}", body.getCode(), request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.valueOf(ex.getHttpStatus()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request, HttpServletResponse response,
                                                           ConstraintViolationException ex) {
        log.debug("[exception:XCloudValidationException] handled: " + ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(HttpStatus.BAD_REQUEST.value());
        body.setTime(new Date());
        body.setMsg(ex.getConstraintViolations().stream().map((cv) -> {
            String[] paths = cv.getPropertyPath().toString().split("\\.");
            return cv == null ? "null" : paths[paths.length - 1] + ": " + cv.getMessage();
        }).collect(Collectors.joining(", ")));
        log.info("[exception:{}] {} {}", body.getCode(), request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request, HttpServletResponse response,
                                                           MethodArgumentNotValidException ex) {
        return handle(request, response, new XCloudValidationException(ex.getBindingResult()));
    }

    /**
     * HttpMessageNotReadableException Handler
     * Handle Exceptions like Json deserialization exception `com.fasterxml.jackson.databind.exc.InvalidFormatException`
     * and some other bad request Exception
     * Error message only return some general message, like `Could not read document`, `Could not read JSON`,
     * in case of some sensitive information disclosure
     *
     * @param request 请求实体
     * @param response 响应实体
     * @param ex 异常实体
     * @return 过滤后响应
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request, HttpServletResponse response,
                                                           HttpMessageNotReadableException ex) {
        log.warn("[exception:HttpMessageNotReadableException] " + request.getMethod() + " "
                     + request.getRequestURI(), ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(HttpStatus.BAD_REQUEST.value());
        body.setTime(new Date());
        body.setMsg("Http Message Not Readable: " + ex.getMessage().split(";", 2)[0]);
        log.info("[exception:{}] {} {}", body.getCode(), request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request,
                                                           HttpServletResponse response, MultipartException ex) {
        log.debug("[exception:MultipartException] handled: " + ex);
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(HttpStatus.BAD_REQUEST.value());
        body.setMsg(ex.getMessage());
        body.setTime(new Date());
        log.info("[exception:{}] {} {}", body.getCode(), request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request,
                                                           HttpServletResponse response, TokenException ex) {
        log.debug("[exception:TokenException] handled: " + ex.getMessage());
        ServiceExceptionResponse body = new ServiceExceptionResponse();
        body.setCode(ex.getCode());
        body.setMsg(ex.getMessage());
        body.setTime(new Date());
        log.info("[exception:{}] {} {}", body.getCode(), request.getMethod(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    private String convertFiledMessageMapToString(XCloudValidationException ex) {
        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, String> fieldMessage : ex.getFieldMessageMap().entrySet()) {
            if (StringUtils.isNotEmpty(fieldMessage.getKey())) {
                message.append(fieldMessage.getKey()).append(":");
            }
            message.append(fieldMessage.getValue()).append('\n');
        }
        return message.toString();
    }
}
