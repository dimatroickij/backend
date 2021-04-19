package ru.bmstu.appointment.sendnotificationsservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Value("${server.port}")
    private String port;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(Arrays.asList(new Server().url("http://localhost:" + port).description("Стенд разработки"),
                        new Server().url("http://130.193.44.252/staging/backend/send-notification-service")
                                .description("Production")))
                .info(new Info().title("Send-notification-service API").version("1.0"));
    }
}
