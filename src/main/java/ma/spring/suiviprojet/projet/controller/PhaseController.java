package ma.spring.suiviprojet.projet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.PhaseRequestDTO;
import ma.spring.suiviprojet.projet.dto.PhaseResponseDTO;
import ma.spring.suiviprojet.projet.service.PhaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PhaseController {

    private final PhaseService phaseService;

    // --- CRÉATION : POST /api/projets/{projetId}/phases ---
    @PostMapping("/projets/{projetId}/phases")
    public ResponseEntity<PhaseResponseDTO> creerPhase(
            @PathVariable Integer projetId,
            @Valid @RequestBody PhaseRequestDTO requestDTO) {

        PhaseResponseDTO nouvellePhase = phaseService.creerPhase(projetId, requestDTO);
        return new ResponseEntity<>(nouvellePhase, HttpStatus.CREATED);
    }

    // --- LECTURE : GET /api/projets/{projetId}/phases ---
    @GetMapping("/projets/{projetId}/phases")
    public ResponseEntity<List<PhaseResponseDTO>> getPhasesByProjet(@PathVariable Integer projetId) {

        List<PhaseResponseDTO> phases = phaseService.getPhasesByProjet(projetId);
        return ResponseEntity.ok(phases);
    }

    // --- MISE À JOUR D'ÉTAT : PATCH /api/phases/{id}/realisation ---
    @PatchMapping("/phases/{id}/realisation")
    public ResponseEntity<PhaseResponseDTO> marquerRealisee(@PathVariable Integer id) {

        PhaseResponseDTO phaseMaj = phaseService.marquerRealisee(id);
        return ResponseEntity.ok(phaseMaj);
    }

    @PutMapping("/phases/{id}")
    public ResponseEntity<PhaseResponseDTO> modifierPhase(@PathVariable Integer id, @Valid @RequestBody PhaseRequestDTO requestDTO) {
        PhaseResponseDTO response = phaseService.modifierPhase(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/phases/{id}")
    public ResponseEntity<Void> supprimerPhase(@PathVariable Integer id) {
        phaseService.supprimerPhase(id);
        return ResponseEntity.noContent().build();
    }
    // --- PUT : TERMINER UNE PHASE ---
    // On ajoute bien "/phases/" pour correspondre à ce qu'attend React
    @PutMapping("/phases/{id}/terminer")
    public ResponseEntity<PhaseResponseDTO> marquerTerminee(@PathVariable Integer id) {
        return ResponseEntity.ok(phaseService.marquerCommeTerminee(id));
    }
}