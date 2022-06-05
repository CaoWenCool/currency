package com.currency.qrcode.currency.logging.dataplat;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

/**
 * Created by zhangmengqi on 21/3/15.
 */
@Configuration
@ConditionalOnExpression("${xcloud.platdata.log:false}")
public class DataPlatLogExceptionConfiguration  implements ApplicationContextAware, WebMvcConfigurer {

    @Autowired
    private ContentNegotiationManager contentNegotiationManager;

    private ApplicationContext applicationContext;

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new ExceptionResultExceptionResolver());
        addDefaultHandlerExceptionResolvers(exceptionResolvers);
    }

    protected final void addDefaultHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
        exceptionHandlerExceptionResolver.setApplicationContext(this.applicationContext);
        exceptionHandlerExceptionResolver.setContentNegotiationManager(contentNegotiationManager);
        List<HttpMessageConverter<?>> messageConverters = new HttpMessageConverters().getConverters();
        exceptionHandlerExceptionResolver.setMessageConverters(messageConverters);
        exceptionHandlerExceptionResolver.afterPropertiesSet();

        exceptionResolvers.add(exceptionHandlerExceptionResolver);
        exceptionResolvers.add(new ResponseStatusExceptionResolver());
        exceptionResolvers.add(new DefaultHandlerExceptionResolver());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
