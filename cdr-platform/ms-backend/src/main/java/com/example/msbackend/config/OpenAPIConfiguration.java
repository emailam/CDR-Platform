package com.example.msbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        final String securitySchemeName = "oauth2";

        Server devServer = new Server()
                .url("http://localhost:8081")
                .description("Development Environment");

        Contact contact = new Contact()
                .name("Mohamed Khaled")
                .email("mohamedkhaledfcai@gmail.com")
                .url("https://linkedin.com/in/mohamedkhaledomran/");

        Info info = new Info()
                .title("CDR Platform API")
                .version("1.0")
                .description("""
                        ### Call Detail Records Management System
                        This API provides endpoints for:
                        - Processing and storing telecommunications CDRs
                        - Managing VOICE, SMS, and DATA service records
                        """)
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }

}