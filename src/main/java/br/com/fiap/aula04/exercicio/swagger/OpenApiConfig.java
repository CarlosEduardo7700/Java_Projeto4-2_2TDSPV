package br.com.fiap.aula04.exercicio.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Carlos Eduardo", email = "rm552164@fiap.com.br", url =
                "http://wwww.fiap.com.br"),
                description = "Especificação da API do sistema de Blog",
                title = "FIAP Blog API",
                version = "1.0",
                license = @License(name = ""),
                termsOfService = "Termos"
        ),
        servers = {
                @Server(description = "Dev Env", url = "http://localhost:8080"),
                @Server(description = "Prod Env", url = "http://fiapblog.com.br")
        },
        security = @SecurityRequirement(name = "bearedJWT")
)

@SecurityScheme(
        name="bearedJWT",
        description = "Autenticação básica JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {
}
