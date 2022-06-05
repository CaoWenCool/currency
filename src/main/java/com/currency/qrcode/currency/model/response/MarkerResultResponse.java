/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.model.response;

import com.currency.qrcode.currency.model.request.ListRequest;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;

/**
 * marker 形式分页结果返回格式的数据定义.
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/2/2 下午8:43
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarkerResultResponse<T>  extends ResultResponse<T> {
    /**
     * 是一个系统生成的字符串，用来标记查询的起始位置
     */
    private String marker;

    /**
     * 是否后面还有数据
     * true表示后面还有数据，false表示已经是最后一页
     */
    private Boolean isTruncated = false;

    /**
     * 获取下一页所需要传递的marker值
     * 当isTruncated为false时，该域不出现
     */
    private String nextMarker;

    /**
     * 每页包含的最大数量
     * 最大数量通常不超过1000
     */
    private Integer maxKeys;

    public MarkerResultResponse() {

    }

    public MarkerResultResponse(ListRequest listRequest) {
        this.setMarker(listRequest.getMarker());
        this.setMaxKeys(listRequest.getMaxKeys());
    }

    public MarkerResultResponse(ListRequest listRequest, Collection<T> result) {
        this.setMarker(listRequest.getMarker());
        this.setMaxKeys(listRequest.getMaxKeys());
        this.setResult(result);
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    @Override
    public String toString() {
        return "LogicPageResultResponse{"
                   + ", result=" + result
                   + ", marker='" + marker + '\''
                   + ", isTruncated=" + isTruncated
                   + ", nextMarker='" + nextMarker + '\''
                   + ", maxKeys='" + maxKeys + '\''
                   + '}';
    }
}
