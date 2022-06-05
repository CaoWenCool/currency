/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.model.request;

import com.currency.qrcode.currency.util.JsonConvertUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.*;

/**
 * 通用数据列表请求，具体业务可继承此类添加特定的字段
 * <p>
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/2/2 下午8:39
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListRequest {
    /**
     * api的版本号
     */
    protected String version;

    /**
     * 关键字类型
     */
    protected String keywordType;

    /**
     * 关键字，为了简化按照单个字段过滤时的复杂度而存在
     * 若多字段过滤，建议直接使用 filterMap
     */
    protected String keyword;

    /**
     * 页码
     */
    protected int pageNo = 1;

    /**
     * 每页数据项数目，与 pagehelper 一致，开启 page-size-zero=true，当 pageSize=0 表示查询全量
     */
    protected int pageSize = 0;

    /**
     * 多重排序
     */
    protected List<OrderModel> orders;

    /**
     * 是一个系统生成的字符串，用来标记查询的起始位置
     */
    protected String marker = "0";

    /**
     * 是否后面还有数据
     * true表示后面还有数据，false表示已经是最后一页
     */
    protected Boolean isTruncated = false;

    /**
     * 获取下一页所需要传递的marker值
     * 当isTruncated为false时，该域不出现
     */
    protected String nextMarker;

    /**
     * 每页包含的最大数量
     * 最大数量通常不超过1000，缺省值为1000
     */
    protected Integer maxKeys = 1000;

    /**
     * 按照哪些字段过滤
     */
    private Map<String, Object> filterMap;

    /**
     * 逻辑zone名称*
     */
    private String logicZone;

    public ListRequest(int pageNo, int pageSize, List<OrderModel> orders, Map<String, Object> filterMap) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orders = orders;
        this.filterMap = filterMap;
    }

    public ListRequest(String marker, Integer maxKeys, Map<String, Object> filterMap) {
        this.marker = marker;
        this.maxKeys = maxKeys;
        this.filterMap = filterMap;
    }

    public ListRequest(String keywordType, Object keyword, String marker, Integer maxKeys,
                       Map<String, Object> filterMap) {
        this(marker, maxKeys, filterMap);

        if (ObjectUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(keywordType)) {
            if (this.filterMap == null) {
                this.filterMap = new HashMap<String, Object>();
            }
            this.filterMap.put(keywordType, keyword);
        }
    }

    public ListRequest(String keywordType, Object keyword, String marker, Integer maxKeys, String filterMapStr) {
        this(keywordType, keyword, marker, maxKeys, new HashMap<>());
        if (StringUtils.isNotEmpty(filterMapStr)) {
            filterMapStr = StringEscapeUtils.unescapeHtml4(filterMapStr).replaceAll(
                "\\\\", "\\\\\\\\");
            Map filterMap = JsonConvertUtil.fromJSON(filterMapStr, Map.class);
            this.filterMap.putAll(filterMap);
        }
    }

    public ListRequest(String keywordType, Object keyword, String order, String orderBy,
                       int pageNo, int pageSize, List<OrderModel> orders, Map<String, Object> filterMap) {
        this(pageNo, pageSize, orders, filterMap);

        if (ObjectUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(keywordType)) {
            if (this.filterMap == null) {
                this.filterMap = new HashMap<String, Object>();
            }
            this.filterMap.put(keywordType, keyword);
        }

        if (StringUtils.isNotEmpty(order) && StringUtils.isNotEmpty(orderBy)) {
            if (this.orders == null) {
                this.orders = new ArrayList<>();
            }
            OrderModel orderModel = new OrderModel();
            orderModel.setOrder(order);
            orderModel.setOrderBy(orderBy);
            orderModel.setOrderByOrigin(orderBy);
            this.orders.add(orderModel);
        }
    }

    public ListRequest(String keywordType, Object keyword, String order, String orderBy,
                       int pageNo, int pageSize, List<OrderModel> orders, String filterMapStr) {
        this(keywordType, keyword, order, orderBy, pageNo, pageSize, orders, new HashMap<>());
        if (StringUtils.isNotEmpty(filterMapStr)) {
            filterMapStr = StringEscapeUtils.unescapeHtml4(filterMapStr).replaceAll(
                "\\\\", "\\\\\\\\");
            Map filterMap = JsonConvertUtil.fromJSON(filterMapStr, Map.class);
            this.filterMap.putAll(filterMap);
        }
    }

    public ListRequest(String keywordType, Object keyword, String order, String orderBy,
                       int pageNo, int pageSize) {
        this(keywordType, keyword, order, orderBy, pageNo, pageSize, null, new HashMap<>());
    }

    public ListRequest(String order, String orderBy, int pageNo, int pageSize) {
        this("", "", order, orderBy, pageNo, pageSize);
    }

    public ListRequest withFilter(String keywordType, Object keyword) {
        if (filterMap == null) {
            filterMap = new HashMap<String, Object>();
        }
        filterMap.put(keywordType, keyword);
        return this;
    }

    public ListRequest withFilterIfNotEmpty(String keywordType, Object keyword) {
        if (ObjectUtils.isNotEmpty(keyword)) {
            withFilter(keywordType, keyword);
        }
        return this;
    }

    public ListRequest withLike(String keywordType, Object keyword) {
        if (filterMap == null) {
            filterMap = new HashMap<String, Object>();
        }
        filterMap.put(keywordType, "%" + keyword + "%");
        return this;
    }

    public ListRequest withLikeIfNotEmpty(String keywordType, Object keyword) {
        if (ObjectUtils.isNotEmpty(keyword)) {
            withLike(keywordType, keyword);
        }
        return this;
    }

    public ListRequest withPreLike(String keywordType, Object keyword) {
        if (filterMap == null) {
            filterMap = new HashMap<String, Object>();
        }
        filterMap.put(keywordType, keyword + "%");
        return this;
    }

    public ListRequest withPreLikeIfNotEmpty(String keywordType, Object keyword) {
        if (ObjectUtils.isNotEmpty(keyword)) {
            withPreLike(keywordType, keyword);
        }
        return this;
    }

    public ListRequest withSuffixLike(String keywordType, Object keyword) {
        if (filterMap == null) {
            filterMap = new HashMap<String, Object>();
        }
        filterMap.put(keywordType, "%" + keyword);
        return this;
    }

    public ListRequest withSuffixLikeIfNotEmpty(String keywordType, Object keyword) {
        if (ObjectUtils.isNotEmpty(keyword)) {
            withSuffixLike(keywordType, keyword);
        }
        return this;
    }

    public ListRequest withIn(String keywordType, Collection<Object> keyword) {
        if (filterMap == null) {
            filterMap = new HashMap<String, Object>();
        }
        filterMap.put(keywordType, keyword);
        return this;
    }

    public ListRequest withInIfNotEmpty(String keywordType, Collection<Object> keyword) {
        if (ObjectUtils.isNotEmpty(keyword)) {
            withIn(keywordType, keyword);
        }
        return this;
    }

    public String getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(String keywordType) {
        this.keywordType = keywordType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    public Map<String, Object> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, Object> filterMap) {
        this.filterMap = filterMap;
    }

    public String getLogicZone() {
        return logicZone;
    }

    public void setLogicZone(String logicZone) {
        this.logicZone = logicZone;
    }

    @Override
    public String toString() {
        return "ListRequest{" + "version='" + version + '\'' + ", keywordType='" + keywordType + '\'' + ", keyword='" +
                   keyword + '\'' + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", orders=" + orders +
                   ", marker='" + marker + '\'' + ", isTruncated=" + isTruncated + ", nextMarker='" + nextMarker +
                   '\'' + ", maxKeys=" + maxKeys + ", filterMap=" + filterMap + ", logicZone='" + logicZone + '\'' +
                   '}';
    }
}
