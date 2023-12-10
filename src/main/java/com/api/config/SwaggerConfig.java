package com.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 *
 * @author Miguel Castro
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Panksys | Finance",
                version = "1.1.0",
                description = "The System Panksys | Finance is an application developed to help users control and organize their finances.",
                contact = @Contact(
                        name = "Miguel Castro",
                        url = "https://github.com/MiguelCastro9"
                )
        ),
        servers = {
            @Server(
                    description = "DEV",
                    url = "http://localhost:8080"
            )
        }
)
@SecurityScheme(
        name = "BearerAuthentication",
        description = "JWT token authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
