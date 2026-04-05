package ma.spring.suiviprojet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // On applique cette règle à TOUTES nos routes API
                registry.addMapping("/api/**")
                        // On autorise spécifiquement les ports classiques de développement Front-End
                        .allowedOrigins("http://localhost:3000", "http://localhost:4200", "http://localhost:5173")
                        // On autorise toutes les méthodes HTTP dont on a besoin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        // Indispensable si on veut envoyer des Tokens ou des Cookies plus tard
                        .allowCredentials(true);
            }
        };
    }
}