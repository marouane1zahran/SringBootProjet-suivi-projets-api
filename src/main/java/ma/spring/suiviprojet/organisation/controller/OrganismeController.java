package ma.spring.suiviprojet.organisation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.dto.OrganismeRequestDTO;
import ma.spring.suiviprojet.organisation.dto.OrganismeResponseDTO;
import ma.spring.suiviprojet.organisation.service.OrganismeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organismes")
@RequiredArgsConstructor
public class OrganismeController {

    private final OrganismeService organismeService;

    // --- CRÉER : POST /api/organismes ---
    @PostMapping
    public ResponseEntity<OrganismeResponseDTO> ajouterOrganisme(@Valid @RequestBody OrganismeRequestDTO dto) {
        OrganismeResponseDTO nouvelOrganisme = organismeService.ajouterOrganisme(dto);
        return new ResponseEntity<>(nouvelOrganisme, HttpStatus.CREATED);
    }

    // --- LIRE (Tous) : GET /api/organismes ---
    @GetMapping
    public ResponseEntity<List<OrganismeResponseDTO>> obtenirTous() {
        return ResponseEntity.ok(organismeService.obtenirTous());
    }

    // --- LIRE (Un seul) : GET /api/organismes/{id} ---
    @GetMapping("/{id}")
    public ResponseEntity<OrganismeResponseDTO> obtenirParId(@PathVariable Integer id) {
        return ResponseEntity.ok(organismeService.obtenirParId(id));
    }

    // --- RECHERCHER (Par nom) : GET /api/organismes/recherche?nom=Valeur ---
    @GetMapping("/recherche")
    public ResponseEntity<List<OrganismeResponseDTO>> rechercherParNom(@RequestParam String nom) {
        return ResponseEntity.ok(organismeService.rechercherParNom(nom));
    }

    // --- MODIFIER : PUT /api/organismes/{id} ---
    @PutMapping("/{id}")
    public ResponseEntity<OrganismeResponseDTO> modifierOrganisme(
            @PathVariable Integer id,
            @Valid @RequestBody OrganismeRequestDTO dto) {
        return ResponseEntity.ok(organismeService.modifierOrganisme(id, dto));
    }

    // --- SUPPRIMER : DELETE /api/organismes/{id} ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerOrganisme(@PathVariable Integer id) {
        organismeService.supprimerOrganisme(id);
        return ResponseEntity.noContent().build(); // Renvoie le fameux code 204 (Succès, pas de contenu)
    }
}