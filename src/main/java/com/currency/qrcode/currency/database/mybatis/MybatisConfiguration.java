package com.currency.qrcode.currency.database.mybatis;

import com.currency.qrcode.currency.database.SqliPrevention;
import org.apache.ibatis.ognl.OgnlRuntime;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto configuration for mybatis.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/14 下午7:15
 */
@Configuration
@ConditionalOnExpression("${xcloud_web_framework.mybatis.enable:false}")
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class MybatisConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Value("${xcloud_web_framework.mybatis.language:velocity}")
    private String language;

    @Value("${xcloud_web_framework.mybatis.injection_filter_regex:" + SqliPrevention.IDENTIFIER_REGEX + "}")
    private String injectionFilterRegex;

    static {
        try {
            log.info("xcloud webframework mybatis use MybatisConfiguration::::");
            // Initialize DynamicContext class first, so that it will set property accessor before we do.
            Class.forName(DynamicContext.class.getName());
            // ContextMap is a package private class, we cannot directly reference it here.
            Class<?> contextMapClass = Class.forName(DynamicContext.class.getName() + "$ContextMap");

            OgnlRuntime.setPropertyAccessor(contextMapClass, new ContextMapPropertyAccessor());
        } catch (Exception e) {
            log.error("Failed to override PropertyAccessor for ContextMap", e);
        }
    }

    @Bean
    @ConditionalOnMissingBean(ConfigurationCustomizer.class)
    public ConfigurationCustomizer configurationCustomizer() {
        return new DefaultConfigurationCustomizer(language, injectionFilterRegex);
    }

    // TODO: 集成 百度安全编码规范及安全SDK new SafeBoundSqlInterceptor()
    // TODO: Plugin for recording latencies with bvar
    @Bean
    @ConditionalOnExpression("${xcloud_web_framework.mybatis.remove_whitespaces:true}")
    public WhitespaceRemoverPlugin whitespaceRemoverPlugin() {
        return WhitespaceRemoverPlugin.INSTANCE;
    }
}
