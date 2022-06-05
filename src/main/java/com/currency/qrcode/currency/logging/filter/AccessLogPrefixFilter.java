/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging.filter;

import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.apache.commons.lang3.StringUtils;

/**
 * access日志过滤
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class AccessLogPrefixFilter extends Filter<IAccessEvent> {

    String separator = ",";

    String uriPrefix = "";

    public void setUriPrefix(String uriPrefix) {
        this.uriPrefix = uriPrefix;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public FilterReply decide(IAccessEvent event) {
        for (String prefix : uriPrefix.split(separator)) {
            if (StringUtils.isNotBlank(prefix) && event.getRequestURI().startsWith(prefix.trim())) {
                return FilterReply.ACCEPT;
            }
        }
        return FilterReply.DENY;
    }
}