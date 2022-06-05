/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * 排序模型.
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/2/2 下午8:36
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderModel {
    /*
     * 升序或降序
     */
    @ApiModelProperty(value = "排序方式", allowableValues = "asc,desc", position = 1)
    private String order;
    /*
     * 排序字段, 原始是传递过来的 bean 属性，转换后变为数据库列名
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String orderBy;

    /*
     * 排序字段, 原始传递过来的 bean 属性
     */
    @JsonProperty(value = "orderBy", access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(value = "排序字段", position = 2)
    private String orderByOrigin;

    public String getOrderByOrigin() {
        return orderByOrigin;
    }

    public void setOrderByOrigin(String orderByOrigin) {
        this.orderByOrigin = orderByOrigin;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "OrderModel{"
                   + "order='" + order + '\''
                   + ", orderBy='" + orderBy + '\''
                   + '}';
    }
}
