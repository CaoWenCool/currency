/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录访问ip
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
@Configuration
@ConditionalOnExpression("${clientIp.interceptor.enabled:true}")
public class ClientIpInterceptorConfiguration implements WebMvcConfigurer {

    // 请求转发处信息
    @Value("${iam.console.username:console}")
    private String via;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ClientIpInterceptor(via)).addPathPatterns("/**");
    }

    /**
     * 记录访问ip
     */
    public static class ClientIpInterceptor implements HandlerInterceptor {
        private static final Logger LOGGER = LoggerFactory.getLogger(ClientIpInterceptor.class);
        private String via;

        public ClientIpInterceptor(String via) {
            if (via != null && via.length() > 0) {
                this.via = via.replaceFirst(via.substring(0, 1), via.substring(0, 1).toUpperCase());
            }
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String clientIp = request.getHeader("ClientIp");
            if (clientIp == null) {
                clientIp = request.getRemoteAddr();
            }

            LOGGER.debug("client ip is : [{}]", clientIp);
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                Exception ex) throws Exception {
        }
    }

}
