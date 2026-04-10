package ma.spring.suiviprojet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 1. LA PORTE PUBLIQUE (Ouverte à tous)
                        .requestMatchers(
                                "/api/auth/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // =======================================================
                        // 2. DROITS DE LECTURE (GET) - Pour les menus déroulants
                        // =======================================================
                        // Tout le personnel encadrant doit pouvoir lister les employés et profils
                        .requestMatchers(HttpMethod.GET, "/api/employes/**", "/api/profils/**")
                        .hasAnyAuthority("ROLE_ADMINISTRATEUR", "ROLE_CHEF_DE_PROJET", "ROLE_SECRETAIRE", "ROLE_DIRECTEUR")

                        // Tout le personnel projet doit pouvoir lister les organismes
                        .requestMatchers(HttpMethod.GET, "/api/organismes/**")
                        .hasAnyAuthority("ROLE_SECRETAIRE", "ROLE_CHEF_DE_PROJET", "ROLE_DIRECTEUR", "ROLE_ADMINISTRATEUR")

                        // Tout le personnel projet doit pouvoir lister les projets, phases et livrables
                        .requestMatchers(HttpMethod.GET, "/api/projets/**", "/api/phases/**", "/api/livrables/**")
                        .hasAnyAuthority("ROLE_SECRETAIRE", "ROLE_DIRECTEUR", "ROLE_CHEF_DE_PROJET","ROLE_ADMINISTRATEUR")


                        // =======================================================
                        // 3. DROITS D'ÉCRITURE (POST, PUT, DELETE) - Règles strictes
                        // =======================================================
                        // L'Administrateur gère les RH
                        .requestMatchers("/api/employes/**", "/api/profils/**").hasAuthority("ROLE_ADMINISTRATEUR")

                        // La Secrétaire gère les organismes clients
                        .requestMatchers("/api/organismes/**").hasAnyAuthority("ROLE_SECRETAIRE", "ROLE_ADMINISTRATEUR")

                        // La Secrétaire, le Directeur et le Chef de projet gèrent le "Projet" global
                        .requestMatchers("/api/projets/**").hasAnyAuthority("ROLE_SECRETAIRE", "ROLE_DIRECTEUR", "ROLE_CHEF_DE_PROJET", "ROLE_ADMINISTRATEUR")

                        // Le Chef de projet décompose et gère le détail (Phases, Affectations, Livrables)
                        .requestMatchers("/api/phases/**", "/api/livrables/**", "/api/documents/**").hasAnyAuthority("ROLE_CHEF_DE_PROJET", "ROLE_ADMINISTRATEUR")

                        // Le Comptable gère la facturation
                        .requestMatchers("/api/factures/**").hasAnyAuthority("ROLE_COMPTABLE", "ROLE_ADMINISTRATEUR")

                        // Reporting / Tableau de bord
                        .requestMatchers("/api/reporting/**").hasAnyAuthority("ROLE_DIRECTEUR", "ROLE_COMPTABLE", "ROLE_ADMINISTRATEUR")

                        // 4. LE RESTE DU SITE (Par exemple les simples Employés)
                        // Ils doivent être connectés, mais s'ils tapent sur une API ci-dessus, ils auront un 403.
                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}