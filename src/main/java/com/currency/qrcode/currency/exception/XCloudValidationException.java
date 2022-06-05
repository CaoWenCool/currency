/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 校验异常.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/5 下午5:19
 */
public class XCloudValidationException extends RuntimeException {
    private int httpStatus = 400;
    private String code = "XCloudValidationException";
    private String requestId;
    private Map<String, String> fieldMessageMap;

    public XCloudValidationException() {
        fieldMessageMap = new HashMap<String, String>();
    }

    public XCloudValidationException(BindingResult bindingResult) {
        fieldMessageMap = new HashMap<String, String>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            fieldMessageMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    public XCloudValidationException withFieldMessage(String field, String message) {
        fieldMessageMap.put(field, message);
        return this;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Map<String, String> getFieldMessageMap() {
        return fieldMessageMap;
    }

    public void setFieldMessageMap(Map<String, String> fieldMessageMap) {
        this.fieldMessageMap = fieldMessageMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        XCloudValidationException that = (XCloudValidationException) o;
        return httpStatus == that.httpStatus && Objects.equals(code, that.code) &&
                   Objects.equals(requestId, that.requestId) && Objects.equals(fieldMessageMap, that.fieldMessageMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatus, code, requestId, fieldMessageMap);
    }

    @Override
    public String toString() {
        return "XCloudValidationException{" + "httpStatus=" + httpStatus + ", code='" + code + '\'' + ", requestId='" +
                   requestId + '\'' + ", fieldMessageMap=" + fieldMessageMap + '}';
    }
}
