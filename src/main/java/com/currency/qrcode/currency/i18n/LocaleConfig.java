/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 设置默认的locale.
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/31 5:06 下午
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty("spring.messages.locale.language")
public class LocaleConfig {

    @Value("${spring.messages.locale.language:}")
    private String language;

    @Value("${spring.messages.locale.country:}")
    private String country;

    @Value("${spring.messages.locale.variant:}")
    private String variant;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        if (StringUtils.isEmpty(language)) {
            localeResolver.setDefaultLocale(Locale.getDefault());
        } else {
            localeResolver.setDefaultLocale(new Locale(language, country, variant));
        }
        return localeResolver;
    }
}
