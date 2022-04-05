package com.currency.qrcode.currency.document;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = {"springfox.documentation.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@ConfigurationProperties("springfox.documentation")
public class SwaggerProperties {

    @Value("${springfox.documentation.application.name}")
    private String applicationName = "current-qr-code";

    @Value("${springfox.documentation.application.version}")
    private String applicationVersion = "1.0.0";

    @Value("${springfox.documentation.application.description}")
    private String applicationDescription = "加密数字货币二维码生成";

    @Value("${springfox.documentation.application.basepackage}")
    private String basePackage = "com.currency.qrcode.currency";

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
