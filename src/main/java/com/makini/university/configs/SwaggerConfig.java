package com.makini.university.configs;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Makini University Payment Integration API")
                        .description("REST API for integrating Cellulant Payment Gateway with Makini University student system.")
                        .version("v1.0")
                        .termsOfService("https://cellulant.com")
                );
    }
}
