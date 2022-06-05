package com.currency.qrcode.currency.logging;

import ch.qos.logback.access.servlet.TeeFilter;
import com.currency.qrcode.currency.logging.logback.LogbackContext;
import com.currency.qrcode.currency.logging.patch.CustomLogbackValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangmengqi on 21/3/15.
 */
@Configuration
@ConditionalOnExpression("${logging.has_access_log:true}")
class AccessLogConfiguration {

    Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    @ConditionalOnExpression("${logging.access_debug_log_full_content:true}")
    public FilterRegistrationBean teeFilter() {
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        // shiziye: TeeFilter used to log requestContent & responseContent
        filterRegBean.setFilter(new TeeFilter());
        return filterRegBean;
    }

    @Bean
    WebServerFactoryCustomizer embeddedServletContainerCustomizerAccessLog() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                CustomLogbackValve customLogbackValve = LogbackContext.getCustomLogbackValve();
                if (customLogbackValve != null && factory instanceof TomcatServletWebServerFactory) {
                    TomcatServletWebServerFactory tomcatFactory =
                            (TomcatServletWebServerFactory) factory;
                    tomcatFactory.addContextValves(customLogbackValve);
                }
            }
        };
    }
}