package com.giuliosmtech.products.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Giuliosmtech Products API")
                        .description("OpenAPI documentation for the Giuliosmtech Products platform.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Giuliosmtech Team")
                                .email("support@giuliosmtech.com")
                                .url("https://www.giuliosmtech.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/giuliosmtech/springboot-graphql-api"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development server"),
                        new Server()
                                .url("https://api.giuliosmtech.com")
                                .description("Production server")
                ));
    }
}