package com.frankit.product_api.infrastructure.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("상품 및 옵션 관리 API")
                        .version("1.0.0")
                        .description("프랜킷 백엔드 개발자 사전과제: 상품 및 옵션 관리 RESTful API"));
    }
}
