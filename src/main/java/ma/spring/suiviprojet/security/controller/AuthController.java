package ma.spring.suiviprojet.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import ma.spring.suiviprojet.organisation.dto.EmployeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.mapper.EmployeMapper;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import ma.spring.suiviprojet.security.JwtUtils;
import ma.spring.suiviprojet.security.dto.ChangePasswordDTO;
import ma.spring.suiviprojet.security.dto.LoginRequestDTO;
import ma.spring.suiviprojet.security.dto.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;
    private final PasswordEncoder passwordEncoder;

    // --- 1. CONNEXION (Générer le Token) ---
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        System.out.println("📥 [DEBUG AUTH] - Postman envoie le login : '" + request.getLogin() + "'");
        System.out.println("📥 [DEBUG AUTH] - Postman envoie le MDP   : '" + request.getPassword() + "'");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
            );

            System.out.println("✅ [DEBUG AUTH] - Mot de passe validé par Spring !");

            // 2. Si c'est bon, on récupère l'utilisateur
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. On fabrique le Token
            String jwt = jwtUtils.generateToken(userDetails);

            // 4. On récupère les infos supplémentaires pour le Front-End
            Employe employe = employeRepository.findByLogin(request.getLogin()).orElseThrow();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String nomComplet = employe.getPrenom() + " " + employe.getNom();

            // 5. On renvoie le tout !
            return ResponseEntity.ok(new LoginResponseDTO(jwt, role, nomComplet));

        } catch (BadCredentialsException e) {
            throw new RegleMetierException("Login ou mot de passe incorrect.");
        }
    }

    // --- 2. RÉCUPÉRER LE PROFIL CONNECTÉ ---
    @GetMapping("/me")
    public ResponseEntity<EmployeResponseDTO> getCurrentUser() {
        // On lit le nom de l'utilisateur à partir de son Token valide
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();

        Employe employe = employeRepository.findByLogin(currentLogin)
                .orElseThrow(() -> new RegleMetierException("Utilisateur introuvable"));

        return ResponseEntity.ok(employeMapper.toDTO(employe));
    }

    // --- 3. CHANGER DE MOT DE PASSE ---
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();

        Employe employe = employeRepository.findByLogin(currentLogin).orElseThrow();

        // On vérifie que l'ancien mot de passe tapé correspond bien à celui en base
        if (!passwordEncoder.matches(request.getOldPassword(), employe.getPassword())) {
            throw new RegleMetierException("L'ancien mot de passe est incorrect.");
        }

        // On hache le nouveau mot de passe avant de le sauvegarder !
        employe.setPassword(passwordEncoder.encode(request.getNewPassword()));
        employeRepository.save(employe);

        return ResponseEntity.ok("Mot de passe modifié avec succès.");
    }
}