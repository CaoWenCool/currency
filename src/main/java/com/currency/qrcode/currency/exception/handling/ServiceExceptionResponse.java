package com.currency.qrcode.currency.exception.handling;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 服务层异常返回数据模型，用于jackson序列化.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/5 下午5:11
 */
public class ServiceExceptionResponse {
    private Integer code;
    private String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ServiceExceptionResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                '}';
    }
}
