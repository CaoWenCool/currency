package com.currency.qrcode.currency.database.mybatis;

import com.currency.qrcode.currency.database.mybatis.xml.InjectionFilteringXmlLanguageDriver;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.scripting.velocity.VelocityLanguageDriver;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Default implementation of {@link ConfigurationCustomizer}.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/15 下午12:13
 */
public class DefaultConfigurationCustomizer implements ConfigurationCustomizer {
    private static final Logger log = LoggerFactory.getLogger(DefaultConfigurationCustomizer.class);

    private final String language;

    private final String injectionFilterRegex;

    @Autowired
    private ApplicationContext applicationContext;

    public DefaultConfigurationCustomizer(String language, String injectionFilterRegex) {
        this.language = language;
        this.injectionFilterRegex = injectionFilterRegex;
    }

    @Override
    public void customize(org.apache.ibatis.session.Configuration configuration) {
        // Registering the driver instance must before setting default scripting language.
        configuration.getLanguageRegistry().register(
            new InjectionFilteringXmlLanguageDriver(getInjectionFilterPattern()));
        configuration.getLanguageRegistry().register(VelocityLanguageDriver.class);
        configuration.getTypeAliasRegistry().registerAlias("velocity", VelocityLanguageDriver.class);
        // The alias "xml" is occupied by XMLLanguageDriver.
        configuration.getTypeAliasRegistry().registerAlias("_xml", InjectionFilteringXmlLanguageDriver.class);

        log.info("xcloud webframework mybatis use DefaultConfigurationCustomizer::::");
        switch (language) {
            case "velocity":
                configuration.setDefaultScriptingLanguage(VelocityLanguageDriver.class);
                break;
            case "xml":
                configuration.setDefaultScriptingLanguage(InjectionFilteringXmlLanguageDriver.class);
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        configuration.setMapUnderscoreToCamelCase(true);

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MybatisVariable.class);
        if (MapUtils.isNotEmpty(beans)) {
            configuration.setVariables(createVariableProperties(beans));
        }
    }

    private Properties createVariableProperties(Map<String, Object> beans) {
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            String name = entry.getKey();
            Object bean = entry.getValue();
            MybatisVariable mybatisVariable = AnnotationUtils.findAnnotation(bean.getClass(), MybatisVariable.class);
            if (isNotBlank(mybatisVariable.value())) {
                name = mybatisVariable.value();
            }
            properties.put(name, bean);
        }
        return properties;
    }

    protected Pattern getInjectionFilterPattern() {
        if (StringUtils.isEmpty(injectionFilterRegex)) {
            return null;
        }
        return Pattern.compile(injectionFilterRegex);
    }
}
