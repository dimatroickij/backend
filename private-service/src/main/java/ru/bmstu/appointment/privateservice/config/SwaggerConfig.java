package ru.bmstu.appointment.privateservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class SwaggerConfig {

    @Value("${server.port}")
    private String port;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(Arrays.asList(new Server().url("http://localhost:" + port).description("Стенд разработки"),
                        new Server().url("http://130.193.44.252/staging/backend/private-service")
                                .description("Production")))
                .components(new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                ))
                .info(new Info().title("Private-service API").version("1.0"))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")));
    }
}
