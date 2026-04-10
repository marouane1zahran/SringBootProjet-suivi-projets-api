package ma.spring.suiviprojet.security;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeRepository employeRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println("🔍 [DEBUG SECURITY] - Recherche du login : '" + login + "'");

        Employe employe = employeRepository.findByLogin(login)
                .orElseThrow(() -> {
                    System.out.println("❌ [DEBUG SECURITY] - ERREUR : Le login '" + login + "' n'existe pas en BDD !");
                    return new UsernameNotFoundException("Utilisateur introuvable");
                });

        System.out.println("✅ [DEBUG SECURITY] - Utilisateur trouvé !");
        System.out.println("🔍 [DEBUG SECURITY] - Mot de passe en BDD : '" + employe.getPassword() + "'");
        System.out.println("🔍 [DEBUG SECURITY] - Longueur du mot de passe en BDD : " + employe.getPassword().length() + " caractères");

        String codeProfil = employe.getProfil().getCode().toUpperCase();
        if (!codeProfil.startsWith("ROLE_")) {
            codeProfil = "ROLE_" + codeProfil;
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(codeProfil);

        return new User(employe.getLogin(), employe.getPassword(), Collections.singletonList(authority));
    }
}