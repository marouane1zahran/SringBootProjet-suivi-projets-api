package ma.spring.suiviprojet.projet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.ProjetRequestDTO;
import ma.spring.suiviprojet.projet.dto.ProjetResponseDTO;
import ma.spring.suiviprojet.projet.service.ProjetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@RequiredArgsConstructor
public class ProjetController {

    private final ProjetService projetService;

    // Correspond à : POST /api/projets
    @PostMapping
    public ResponseEntity<ProjetResponseDTO> creerProjet(@Valid @RequestBody ProjetRequestDTO requestDTO) {

        ProjetResponseDTO nouveauProjet = projetService.creerProjet(requestDTO);

        return new ResponseEntity<>(nouveauProjet, HttpStatus.CREATED);
    }

    // Correspond à : GET /api/projets/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProjetResponseDTO> getProjetById(@PathVariable Integer id) {
        ProjetResponseDTO projet = projetService.getProjetById(id);
        return ResponseEntity.ok(projet);
    }

    // Correspond à : GET /api/projets
    @GetMapping
    public ResponseEntity<List<ProjetResponseDTO>> getAllProjets() {
        List<ProjetResponseDTO> projets = projetService.getAllProjets();
        return ResponseEntity.ok(projets);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProjet(@PathVariable Integer id) {
        projetService.supprimerProjet(id);
        return ResponseEntity.noContent().build();    }


    @PutMapping("/{id}")
    public ResponseEntity<ProjetResponseDTO> modifierProjet(
            @PathVariable Integer id,
            @Valid @RequestBody ProjetRequestDTO requestDTO) {

        ProjetResponseDTO projetMaj = projetService.modifierProjet(id, requestDTO);
        return ResponseEntity.ok(projetMaj);
    }
}

