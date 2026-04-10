package ma.spring.suiviprojet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. Autoriser l'envoi du Token JWT (Credentials)
        config.setAllowCredentials(true);

        // 2. Autoriser VOTRE frontend React (les deux écritures possibles)
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173",
                "http://127.0.0.1:5173",
                "http://localhost:5177",  // <--- VOTRE VRAI PORT
                "http://127.0.0.1:5177"));

        // 3. Autoriser TOUS les en-têtes et TOUTES les méthodes (y compris OPTIONS !)
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*")); // Remplace GET, POST, OPTIONS, etc.

        // 4. Appliquer sur toutes les URL de l'API
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}