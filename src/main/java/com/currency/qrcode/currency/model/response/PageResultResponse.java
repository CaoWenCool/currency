/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.model.response;

import com.currency.qrcode.currency.model.request.ListRequest;
import com.currency.qrcode.currency.model.request.OrderModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.List;

/**
 * 分页结果返回格式的数据定义.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/6 下午5:26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResultResponse<T> extends ResultResponse<T> {
    /**
     * 多重排序
     */
    @ApiModelProperty(value = "排序信息", position = 6)
    private List<OrderModel> orders;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", example = "1", position = 3)
    private int pageNo = 1;

    /**
     * 每页数据项数目
     */
    @ApiModelProperty(value = "每页数据项数目", example = "10", position = 4)
    private int pageSize = 0;

    /**
     * 总数
     */
    @ApiModelProperty(value = "数据项总数", example = "100", position = 5)
    private Integer totalCount = null;

    public PageResultResponse() {
    }

    /**
     * 对于 页面 端的调用，建议使用此构造方法
     *
     * @param listRequest list请求实体
     * @see ListRequest
     */
    public PageResultResponse(ListRequest listRequest) {
        if (listRequest != null) {
            this.setOrders(listRequest.getOrders());
            this.setPageNo(listRequest.getPageNo());
            this.setPageSize(listRequest.getPageSize());
        }
    }

    /**
     * 对于 页面 端的调用，建议使用此构造方法, 避免单独赋值
     *
     * @param listRequest list请求实体
     * @param result 查询到的响应实体
     * @see ListRequest
     */
    public PageResultResponse(ListRequest listRequest, Collection<T> result) {
        this.setResult(result);

        if (listRequest != null) {
            this.setOrders(listRequest.getOrders());
            this.setPageNo(listRequest.getPageNo());
            this.setPageSize(listRequest.getPageSize());
        }

        if (result instanceof List) {
            PageInfo page = new PageInfo((List) result);
            totalCount = (int) page.getTotal();
        }

    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "PageResultResponse{" + "orders=" + orders + ", pageNo=" + pageNo + ", pageSize=" + pageSize +
                   ", totalCount=" + totalCount + ", result=" + result + '}';
    }
}
