package ma.spring.suiviprojet.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. On regarde le fameux en-tête HTTP "Authorization"
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userLogin;

        // 2. Si le visiteur n'a pas de badge ou si le badge n'a pas la bonne forme ("Bearer ...")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // On le laisse passer au contrôle suivant (qui va le rejeter)
            return;
        }

        // 3. On extrait le code secret du badge (en enlevant le mot "Bearer ")
        jwt = authHeader.substring(7);
        userLogin = jwtUtils.extractUsername(jwt); // On lit le nom de l'utilisateur écrit sur le badge

        // 4. Si on a trouvé un nom et que l'utilisateur n'est pas déjà enregistré comme connecté
        if (userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // On demande à notre traducteur d'aller chercher cet utilisateur en Base de Données
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userLogin);

            // 5. On vérifie mathématiquement que le badge n'est pas un faux et n'est pas expiré
            if (jwtUtils.validateToken(jwt, userDetails)) {

                // 6. Le badge est valide ! On crée le laissez-passer officiel pour Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities() // C'est ici qu'on donne le rôle (ADMIN, CP, SECRETAIRE...)
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. On valide l'entrée de l'utilisateur dans le système !
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // La vérification est terminée, on laisse la requête aller vers le Controller
        filterChain.doFilter(request, response);
    }
}