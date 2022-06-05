package com.currency.qrcode.currency.exception;

public class TokenException extends RuntimeException {
    private static final int EXPIRED_CODE = 400011;
    private static final int NOT_LOGIN_CODE = 400011;
    private int code;

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, int code) {
        this(message);
        this.code = code;
    }

    public static TokenException expired() {
        return new TokenException("登录过期", EXPIRED_CODE);
    }

    public static TokenException notLogin() {
        return new TokenException("未登录", NOT_LOGIN_CODE);
    }

    public Integer getCode() {
        return code;
    }
}
