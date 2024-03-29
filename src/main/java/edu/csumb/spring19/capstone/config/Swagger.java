package edu.csumb.spring19.capstone.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;

@Configuration
public class Swagger {
    @Value("${ENABLE_SWAGGER:false}")
    private Boolean docsEnabled;

    @Bean
    public GroupedOpenApi api() {
        if (docsEnabled) {
            return GroupedOpenApi.builder()
                    .group("1.Public")
                    .pathsToExclude("/error.*")
                    .addOpenApiCustomiser(publicApiCustomiser())
                    .build();

        } else {
            return GroupedOpenApi.builder()
                    .group("2.?")
                    .pathsToExclude("*")
                    .build();
        }
    }

    @Bean
    public OpenApiCustomiser publicApiCustomiser() {
        return openApi -> openApi.addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .in(SecurityScheme.In.HEADER)
                                .type(SecurityScheme.Type.APIKEY)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                )
                .info(new Info().title("Public REST API"));
    }
}

