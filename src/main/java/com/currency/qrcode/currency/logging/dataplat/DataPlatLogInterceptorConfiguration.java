package com.currency.qrcode.currency.logging.dataplat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhangmengqi on 21/3/15.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnExpression("${xcloud.platdata.log:false}")
public class DataPlatLogInterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Value("${xcloud.platdata.log.include.path:/**}")
    private String xcloudPlatDataLogIncludePath;

    @Value("${xcloud.platdata.log.exclude.path:}")
    private String xcloudPlatDataLogExcludePath;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataPlatLogInterceptor())
                .addPathPatterns(xcloudPlatDataLogIncludePath.split(";"))
                .excludePathPatterns(xcloudPlatDataLogExcludePath.split(";"));
    }
}
