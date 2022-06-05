/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.i18n;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 拦截器来对请求的语言参数进行获取，采用默认的LocaleChangeInterceptor作为拦截器来指定切换国际化语言的参数名。
 * 比如当请求的url中包含?lang=zh_CN表示读取国际化文件messages_zh_CN.properties。
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/31 5:55 下午
 * @since 1.0.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor);
    }
}
