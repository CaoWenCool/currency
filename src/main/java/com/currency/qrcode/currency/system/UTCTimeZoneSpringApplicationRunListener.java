/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.system;

import com.currency.qrcode.currency.logging.logback.LogbackContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.TimeZone;

/**
 * 设置系统时钟为UTC时间，保证Date序列化、反序列化时间正确, after LoggingApplicationListener default logback.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午2:53
 */
public class UTCTimeZoneSpringApplicationRunListener implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String xcloud_web_framework_is_utc_timezone = "xcloud_web_framework.is_utc_timezone";

    private void setUTCTime(ConfigurableApplicationContext applicationContext) {
        Boolean property = applicationContext.getEnvironment().getProperty(xcloud_web_framework_is_utc_timezone,
            Boolean.class, true);
        if (Boolean.FALSE.equals(property)) {
            return;
        }

        LogbackContext.initCustomLogbackValve(applicationContext);
        log.info("Set timezone to UTC.");
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        setUTCTime(applicationContext);
    }
}
