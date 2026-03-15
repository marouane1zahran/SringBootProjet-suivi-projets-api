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



    @PostMapping
    public ResponseEntity<OrganismeResponseDTO> ajouterOrganisme(@Valid @RequestBody OrganismeRequestDTO dto) {
        OrganismeResponseDTO response = organismeService.ajouterOrganisme(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<OrganismeResponseDTO>> obtenirTous() {
        return ResponseEntity.ok(organismeService.obtenirTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganismeResponseDTO> obtenirParId(@PathVariable int id) {
        return ResponseEntity.ok(organismeService.obtenirParId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganismeResponseDTO> modifierOrganisme(
            @PathVariable int id,
            @Valid @RequestBody OrganismeRequestDTO dto) {
        return ResponseEntity.ok(organismeService.modifierOrganisme(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerOrganisme(@PathVariable int id) {
        organismeService.supprimerOrganisme(id);
        return ResponseEntity.noContent().build();
    }

    // Exemple d'appel : GET /api/organismes/recherche?nom=Tech
    @GetMapping("/recherche")
    public ResponseEntity<List<OrganismeResponseDTO>> rechercherParNom(@RequestParam String nom) {
        return ResponseEntity.ok(organismeService.rechercherParNom(nom));
    }
}