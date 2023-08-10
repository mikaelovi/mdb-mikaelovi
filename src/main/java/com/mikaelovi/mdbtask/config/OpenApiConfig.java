package com.mikaelovi.mdbtask.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        final var securityScheme = new SecurityScheme();
        securityScheme.name("Bearer Authentication");
        securityScheme.bearerFormat("JWT");
        securityScheme.type(SecurityScheme.Type.HTTP);
        securityScheme.scheme("bearer");

        return new OpenAPI()
                .info(new Info()
                        .title("MDB Task API documentation")
                        .description("Documentation for Marks API")
                        .version("0.0.1")).addSecurityItem(new SecurityRequirement())
                .components(new Components().addSecuritySchemes("mdb-auth", securityScheme));
    }
}
