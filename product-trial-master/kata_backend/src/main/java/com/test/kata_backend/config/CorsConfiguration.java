package  com.test.kata_backend.config;
import io.micrometer.common.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry  registry) {
                registry.addMapping("/**")  // autorise CORS sur toutes les routes
                        .allowedOrigins("http://localhost:4200")  // origine autorisée (ici backend ?)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // méthodes HTTP autorisées
                        .allowedHeaders("*")  // tous les headers sont autorisés
                        .allowCredentials(true)  // autorise l’envoi de cookies, token d’auth, etc.
                        .maxAge(3600);  // durée pendant laquelle le navigateur peut mettre en cache cette autorisation (en secondes)
            }
        };
    }
}

