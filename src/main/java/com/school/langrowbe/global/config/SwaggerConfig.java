/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "💳 Langrow API 명세서",
            description =
                ("""
                <p>함께 성장하는 언어 교환일기, <strong>Langrow</strong><br>
                """),
            contact =
                @Contact(
                    name = "Langrow",
                    url = "https://naooung.store",
                    email = "1030n@naver.com")),
    security = @SecurityRequirement(name = "Authorization"),
    servers = {
      @Server(url = "http://localhost:8080", description = "로컬 서버"),
      @Server(url = "https://api.naooung.store", description = "운영 서버")
    })
@SecurityScheme(
    name = "Authorization",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT")
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("Swagger API")
        .pathsToMatch("/api/**", "/swagger-ui/**", "/v3/api-docs/**")
        .build();
  }
}
