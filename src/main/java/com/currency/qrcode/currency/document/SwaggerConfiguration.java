package com.currency.qrcode.currency.document;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.web.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty(
        value = {"springfox.documentation.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class SwaggerConfiguration {


    private final SwaggerProperties swaggerProperties;

    public SwaggerConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enableUrlTemplating(false)
                .apiInfo(getApiInfo())
                .groupName("currency")
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("二维码生成器：" + swaggerProperties.getApplicationName())
                .description(swaggerProperties.getApplicationDescription())
                .version(swaggerProperties.getApplicationVersion())
                .contact(new Contact("CURRENCY", "http://11.com", "124.com"))
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(-1)
                .defaultModelExpandDepth(0)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    @Bean
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
    public ApiListingBuilderPlugin getApiPathEnrichPlugin() {
        return new ApiListingBuilderPlugin() {
            @Override
            public void apply(ApiListingContext apiListingContext) {
                List<ApiDescription> apis = apiListingContext.apiListingBuilder().build().getApis();
                ImmutableList.Builder builder = ImmutableList.builder();
                if (apis != null) {
                    apis.forEach(api -> {
                        String newPath = newPath(api);
                        builder.add(new ApiDescription(
                                api.getGroupName().get(),
                                newPath,
                                api.getSummary(),
                                api.getDescription(),
                                api.getOperations(),
                                api.isHidden()
                        ));

                    });
                }
            }

            private String newPath(ApiDescription api) {
                List<String> queryParams = new ArrayList<>();
                for (Operation operation : api.getOperations()) {
                    if (StringUtils.isEmpty(operation.getNotes())) {
                        for (String note : operation.getNotes().split("\\;")) {
                            if (note.startsWith("__query.")) {
                                String queryParam = note.split("\\.", 2)[1];
                                if (StringUtils.isNotEmpty(queryParam) && queryParams.contains(queryParam)) {
                                    queryParams.add(queryParam);
                                }
                            }
                        }
                    }
                }
                String queryStr = String.join("&", queryParams);
                if (StringUtils.isNotEmpty(queryStr)) {
                    return api.getPath() + "?" + queryStr;
                } else {
                    return api.getPath();
                }
            }

            @Override
            public boolean supports(DocumentationType documentationType) {
                return true;
            }
        };
    }

}
