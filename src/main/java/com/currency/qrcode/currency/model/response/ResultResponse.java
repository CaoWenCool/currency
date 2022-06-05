/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 正常结果返回格式的数据定义，一般作为分页实体.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/6 下午5:23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse<T> {
    /**
     * 响应结果， 列表形式
     */
    @ApiModelProperty(value = "数据列表，多个数据结果时使用", position = 1)
    protected Collection<T> result = null;

    /**
     * 响应结果，对象形式
     */
    @ApiModelProperty(value = "数据项，单数据结果时使用", position = 2)
    protected T data = null;

    public ResultResponse() {

    }

    public ResultResponse(Collection<T> result) {
        this.result = result;
    }

    public ResultResponse(T data) {
        this.result = null;
        this.data = data;
    }

    public void setResult(Collection<T> result) {
        if (result == null) {
            this.result = new LinkedList<>();
        } else {
            this.result = result;
        }
        this.data = null;
    }

    public void setData(T data) {
        this.result = null;
        this.data = data;
    }

    public Collection<T> getResult() {
        return result;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResultResponse{" + "result=" + result + ", data=" + data + '}';
    }
}
