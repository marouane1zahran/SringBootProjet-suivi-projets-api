package ma.spring.suiviprojet.projet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.LivrableRequestDTO;
import ma.spring.suiviprojet.projet.dto.LivrableResponseDTO;
import ma.spring.suiviprojet.projet.entity.Livrable;
import ma.spring.suiviprojet.projet.repository.LivrableRepository;
import ma.spring.suiviprojet.projet.service.LivrableService;
import ma.spring.suiviprojet.projet.utils.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LivrableController {

    private final LivrableService livrableService;
    private final FileStorageService fileStorageService;
    private final LivrableRepository livrableRepository;

    @PostMapping("/phases/{phaseId}/livrables")
    public ResponseEntity<LivrableResponseDTO> uploadLivrable(
            @PathVariable Integer phaseId,
            @Valid @ModelAttribute LivrableRequestDTO requestDTO,
            @RequestParam("fichier") MultipartFile fichier) {
        LivrableResponseDTO nouveauLivrable = livrableService.ajouterLivrable(phaseId, requestDTO, fichier);
        return new ResponseEntity<>(nouveauLivrable, HttpStatus.CREATED);
    }

    @GetMapping("/phases/{phaseId}/livrables")
    public ResponseEntity<List<LivrableResponseDTO>> getLivrablesByPhase(@PathVariable Integer phaseId) {
        return ResponseEntity.ok(livrableService.getLivrablesByPhase(phaseId));
    }

    @GetMapping("/livrables/{id}")
    public ResponseEntity<LivrableResponseDTO> getLivrableById(@PathVariable Integer id) {
        return ResponseEntity.ok(livrableService.getLivrableById(id));
    }

    @PutMapping("/livrables/{id}")
    public ResponseEntity<LivrableResponseDTO> modifierLivrable(
            @PathVariable Integer id,
            @Valid @ModelAttribute LivrableRequestDTO requestDTO,
            @RequestParam(value = "fichier", required = false) MultipartFile fichier) {
        return ResponseEntity.ok(livrableService.modifierLivrable(id, requestDTO, fichier));
    }

    @DeleteMapping("/livrables/{id}")
    public ResponseEntity<Void> supprimerLivrable(@PathVariable Integer id) {
        livrableService.supprimerLivrable(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/livrables/{id}/download")
    public ResponseEntity<Resource> downloadLivrable(@PathVariable Integer id) {
        Livrable livrable = livrableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livrable introuvable"));

        Resource resource = fileStorageService.loadFileAsResource(livrable.getChemin());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + livrable.getChemin() + "\"")
                .body(resource);
    }
}