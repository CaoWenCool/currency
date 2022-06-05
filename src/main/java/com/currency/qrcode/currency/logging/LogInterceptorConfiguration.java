package com.currency.qrcode.currency.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录访问起止时间日志
 * 由于采用interceptor实现，在filter中生成的request-id及user-id会登记在MDC中
 * 使得整条日志有request-id、user-id、url、time，方便日志查看
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnExpression("${logging.has_log_interceptor:true}")
public class LogInterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }

    /**
     * 记录访问起止时间日志
     */
    public static class LogInterceptor implements HandlerInterceptor {

        Logger log = LoggerFactory.getLogger(getClass());

        // 记录访问时间，用于性能调试
        ThreadLocal<Long> beginTime = new ThreadLocal<>();

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            beginTime.set(System.currentTimeMillis());
            log.info("[begin] {} {}", request.getMethod(), request.getRequestURI());
            return true;
        }

        @Override
        public void afterCompletion(
                HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                throws Exception {
            long timeUsed = System.currentTimeMillis() - beginTime.get();
            log.info("[status:{},time:{}ms] {} {}", response.getStatus(), timeUsed,
                    request.getMethod(), request.getRequestURI());
        }
    }
}

