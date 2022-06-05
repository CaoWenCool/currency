/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 *  RequestId 过滤器.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class XCloudRequestIdFilter implements Filter {
    Logger log = LoggerFactory.getLogger(getClass());

    // 记录访问时间，用于性能调试
    ThreadLocal<Long> beginTime = new ThreadLocal<>();

    // 本地requestId存储，用于校验MDC及response中requestId是否被错误更改
    ThreadLocal<String> threadLocalRequestId = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        preHandle((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
        afterCompletion((HttpServletRequest) request, (HttpServletResponse) response);
    }

    public void preHandle(HttpServletRequest request, HttpServletResponse response) {

        // get or create requestId
        String requestId = request.getHeader(XCloudConstant.X_IDG_REQUEST_ID);
        if (requestId == null) {
            requestId = response.getHeader(XCloudConstant.X_IDG_REQUEST_ID);
            if (requestId != null) {
                log.debug("X_IDG_REQUEST_ID found in HttpServletResponse");
            }
        }
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
            MDC.put(XCloudConstant.X_IDG_REQUEST_ID, requestId);
            log.info("X_IDG_REQUEST_ID not found in header, generate requestId:{} ", requestId);
        }

        // 记录标准requestId到本地变量
        threadLocalRequestId.set(requestId);

        // log中requestId变量设置
        MDC.put(XCloudConstant.X_IDG_REQUEST_ID, requestId);

        response.addHeader(XCloudConstant.X_IDG_REQUEST_ID, requestId);

        // 记录访问时间
        beginTime.set(System.currentTimeMillis());
        log.info("[begin] {} {}", request.getMethod(), request.getRequestURI());
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response) {

        // 从本地变量中获取标准requestId
        String requestId = threadLocalRequestId.get();

        // 校验response中的requestId是否被错误填写
        String responseRequestId = response.getHeader(XCloudConstant.X_IDG_REQUEST_ID);
        if (!requestId.equals(responseRequestId)) {
            log.error("response requestId changed. requestId={}, responseRequestId={}", requestId, responseRequestId);
        }
        // 确保response中的requestId正确设置
        response.setHeader(XCloudConstant.X_IDG_REQUEST_ID, requestId);

        // 记录访问时间
        long timeUsed = System.currentTimeMillis() - beginTime.get();
        log.info("[status:{},time:{}ms] {} {}", response.getStatus(), timeUsed,
            request.getMethod(), request.getRequestURI());

        // 检验并移除MDC中的requestId环境变量是否被错误填写
        String mdcRequestId = MDC.get(XCloudConstant.X_IDG_REQUEST_ID);
        if (!requestId.equals(mdcRequestId)) {
            log.error("MDC requestId changed. requestId={},  MDC[requestId]={}", requestId, mdcRequestId);
        }
        MDC.remove(XCloudConstant.X_IDG_REQUEST_ID);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
