/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging.patch;

import ch.qos.logback.access.spi.ServerAdapter;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 定制tomcat日志适配
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class CustomTomcatServerAdapter implements ServerAdapter {

    Request request;
    Response response;

    public CustomTomcatServerAdapter(Request tomcatRequest, Response tomcatResponse) {
        this.request = tomcatRequest;
        this.response = tomcatResponse;
    }

    @Override
    public long getContentLength() {
        return response.getContentLength();
    }

    @Override
    public int getStatusCode() {
        return response.getStatus();
    }

    @Override
    public long getRequestTimestamp() {
        return request.getCoyoteRequest().getStartTime();
    }

    @Override
    public Map<String, String> buildResponseHeaderMap() {
        Map<String, String> responseHeaderMap = new IdentityHashMap<>();
        Set<String> headerNames = new HashSet<>();
        for (String key : response.getHeaderNames()) {
            headerNames.add(key);
        }
        for (String key : headerNames) {
            Collection<String> values = response.getHeaders(key);
            if (CollectionUtils.isEmpty(values)) {
                continue;
            }
            for (String value : values) {
                responseHeaderMap.put(new String(key), value);
            }
        }
        return responseHeaderMap;
    }
}
