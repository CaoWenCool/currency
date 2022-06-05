package com.currency.qrcode.currency.exception;

import java.util.Map;
import java.util.Objects;

public class XCloudException extends RuntimeException {
    private int httpStatus = 400;
    private String code;
    private String requestId;
    private Map<String, String> detail;

    public XCloudException(String message) {
        super(message);
        String name = this.getClass().getSimpleName();
        if (this.getClass().getName().contains("$")) {
            String[] parts = getClass().getCanonicalName().split("\\.");
            name = parts[parts.length - 2] + "." + parts[parts.length - 1];
        }
        this.code = name;
    }

    public XCloudException(String message, int httpStatus) {
        this(message);
        this.httpStatus = httpStatus;
    }

    public XCloudException(String message, String code) {
        super(message);
        this.code = code;
    }

    public XCloudException(String message, int httpStatus, String code) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public XCloudException(String message, Map<String, String> detail) {
        super(message);
        this.detail = detail;
    }

    public XCloudException(String message, int httpStatus, String code, Map<String, String> detail) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
        this.detail = detail;
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

    public Map<String, String> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, String> detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        XCloudException that = (XCloudException) o;
        return httpStatus == that.httpStatus && Objects.equals(code, that.code) &&
                   Objects.equals(requestId, that.requestId) && Objects.equals(detail, that.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatus, code, requestId, detail);
    }

    @Override
    public String toString() {
        return "XCloudException{" + "httpStatus=" + httpStatus + ", code='" + code + '\'' + ", requestId='" +
                   requestId + '\'' + ", detail=" + detail + '}';
    }
}
