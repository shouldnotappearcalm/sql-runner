package com.calm.sqlrunner.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

/**
 * swagger 相关的配置累.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
@Configuration
@EnableSwagger2WebFlux
@Import(BeanValidatorPluginsConfiguration.class)
@ConditionalOnProperty(prefix = "sqlrunner.swagger", name = "open", havingValue = "true")
public class SwaggerConfig {

    /**
     * 创建RestAPI的实例Bean.
     *
     * @return Docket实例
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.calm.sqlrunner"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构造 ApiInfo 实例的方法.
     *
     * @return ApiInfo 实例
     */
    private ApiInfo swaggerApiInfo() {
        return new ApiInfoBuilder()
                .title("sql 执行服务")
                .description("一个支持多数据源的 sql 执行服务")
                .termsOfServiceUrl("http://github.com/shouldnotappearcalm/sql-runner")
                .contact(new Contact("calm", "http://github.com/shouldnotappearcalm",
                        "losergzr@gmail.com"))
                .version("1.0.0")
                .build();
    }

}
