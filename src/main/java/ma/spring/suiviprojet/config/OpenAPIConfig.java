package ma.spring.suiviprojet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Suivi de Projets")
                        .version("1.0.0")
                        .description("Documentation interactive de l'API REST pour la gestion et le suivi des projets, phases, et affectations.")
                        .contact(new Contact()
                                .name("Équipe Backend (Dev 1, 2, 3)")
                                .email("contact@suiviprojet.ma")));
    }
}