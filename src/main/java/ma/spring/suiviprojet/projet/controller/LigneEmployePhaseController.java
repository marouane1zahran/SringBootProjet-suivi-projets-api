package ma.spring.suiviprojet.projet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.LigneEmployePhaseRequestDTO;
import ma.spring.suiviprojet.projet.dto.LigneEmployePhaseResponseDTO;
import ma.spring.suiviprojet.projet.service.LigneEmployePhaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phases") // Remarquez bien l'URL de base !
@RequiredArgsConstructor
public class LigneEmployePhaseController {

    private final LigneEmployePhaseService affectationService;

    // --- AFFECTER UN EMPLOYÉ : POST /api/phases/{phaseId}/affectations ---
    @PostMapping("/{phaseId}/affectations")
    public ResponseEntity<LigneEmployePhaseResponseDTO> affecterEmploye(
            @PathVariable Integer phaseId,
            @Valid @RequestBody LigneEmployePhaseRequestDTO requestDTO) {

        // Le service s'occupe de vérifier les dates, le chevauchement et l'existence de l'employé
        LigneEmployePhaseResponseDTO nouvelleAffectation = affectationService.affecterEmploye(phaseId, requestDTO);

        return new ResponseEntity<>(nouvelleAffectation, HttpStatus.CREATED);
    }

    // --- VOIR L'ÉQUIPE D'UNE PHASE : GET /api/phases/{phaseId}/affectations ---
    @GetMapping("/{phaseId}/affectations")
    public ResponseEntity<List<LigneEmployePhaseResponseDTO>> getAffectationsByPhase(
            @PathVariable Integer phaseId) {

        List<LigneEmployePhaseResponseDTO> affectations = affectationService.getAffectationsByPhase(phaseId);

        return ResponseEntity.ok(affectations);
    }
    @DeleteMapping("/{phaseId}/affectations/{employeId}")
    public ResponseEntity<Void> desaffecterEmploye(@PathVariable Integer phaseId, @PathVariable Integer employeId) {
        // Appelez votre service ici pour supprimer via new LigneEmployePhaseId(employeId, phaseId)
        affectationService.desaffecterEmploye(phaseId, employeId);
        return ResponseEntity.noContent().build();
    }
    // --- MODIFIER UNE AFFECTATION (PUT) ---
    @PutMapping("/{phaseId}/affectations/{employeId}")
    public ResponseEntity<LigneEmployePhaseResponseDTO> modifierAffectation(
            @PathVariable Integer phaseId,
            @PathVariable Integer employeId,
            @Valid @RequestBody LigneEmployePhaseRequestDTO requestDTO) {

        LigneEmployePhaseResponseDTO reponse = affectationService.modifierAffectation(phaseId, employeId, requestDTO);
        return ResponseEntity.ok(reponse);
    }
}