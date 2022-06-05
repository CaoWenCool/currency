/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * 获取i18n资源消息.
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/3/31 3:31 下午
 * @since 1.0.0
 */
@Component
public class I18nMessageUtils {

    @Autowired
    private MessageSource messageSource;

    private static MessageSource staticMessageSource;

    @PostConstruct
    public void init() {
        I18nMessageUtils.staticMessageSource = messageSource;
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化消息
     */
    public static String message(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return staticMessageSource.getMessage(code, args, locale);
    }
}
