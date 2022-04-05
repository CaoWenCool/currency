package com.currency.qrcode.currency.model;

public enum ApiCode {
    SUCCESS(200, "操作成功"),

    UNAUTHORIZED(401, "非法访问"),

    NOT_PERMISSION(403, "没有权限"),

    NOT_FOUND(404, "你请求的资源不存在"),

    FAIL(500, "操作失败"),


    LOGIN_EXCEPTION(4000,"登陆失败"),


    SYSTEM_EXCEPTION(5000,"系统异常!"),

    PARAMETER_EXCEPTION(5001,"请求参数校验异常"),

    PARAMETER_PARSE_EXCEPTION(5002,"请求参数解析异常"),

    HTTP_MEDIA_TYPE_EXCEPTION(5003,"HTTP Media 类型异常"),


    IS_EXIST(6001,"已经存在，无法添加"),

    NO_EXIST(7001,"不存在"),
    MOORE_EXIST(7002,"存在多个");

    private final int code;
    private final String msg;

    ApiCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] ecs = ApiCode.values();
        for (ApiCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
