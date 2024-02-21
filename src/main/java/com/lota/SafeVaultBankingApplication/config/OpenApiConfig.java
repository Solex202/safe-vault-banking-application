package com.lota.SafeVaultBankingApplication.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Safe Vault Banking Application Documentation",
                description = "Documentation for Safe Vault Banking Application",
                version = "1.0"
        ),
        servers = {
                @Server(
                     description = "local",
                     url = "http://localhost:9639"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
public class OpenApiConfig {
}
