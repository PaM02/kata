package com.test.kata_backend.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KATA API")
                        .version("1.0")
                        .description("""
            API réalisée pour un test technique fullstack. Authentification via JWT obligatoire pour les routes protégées.
            💡 Étapes recommandées :
            1. Authentification
           - POST /account → Créer un utilisateur
           - POST /token → Récupérer le token JWT
           - Cliquer sur "Authorize" (en haut à droite) et coller le token
           """));
    }
}