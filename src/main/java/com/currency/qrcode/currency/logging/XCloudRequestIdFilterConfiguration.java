/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;

/**
 * requestId 过滤器注册.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:18
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnExpression("${xcloud_web_framework.logging.has_request-id:true}")
public class XCloudRequestIdFilterConfiguration {
    @Value("${xcloud_web_framework.logging.requestId_urlPattern:/*}")
    private String urlPattern;

    @Bean
    public FilterRegistrationBean bceRequestIdFilterRegistrationBean() {
        XCloudRequestIdFilter filter = new XCloudRequestIdFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        List<String> urlPatterns = Arrays.asList(urlPattern.split(";"));
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
