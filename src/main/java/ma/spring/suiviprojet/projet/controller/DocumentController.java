package ma.spring.suiviprojet.projet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.DocumentRequestDTO;
import ma.spring.suiviprojet.projet.dto.DocumentResponseDTO;
import ma.spring.suiviprojet.projet.entity.Document;
import ma.spring.suiviprojet.projet.repository.DocumentRepository;
import ma.spring.suiviprojet.projet.service.DocumentService;
import ma.spring.suiviprojet.projet.utils.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api") // On met /api en racine globale ici
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // On injecte ces deux là exceptionnellement pour gérer le Download directement au niveau Web
    private final FileStorageService fileStorageService;
    private final DocumentRepository documentRepository;

    // --- POST : Ajouter un document (Déjà fait) ---
    @PostMapping("/projets/{projetId}/documents")
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @PathVariable Integer projetId,
            @Valid @ModelAttribute DocumentRequestDTO requestDTO,
            @RequestParam("fichier") MultipartFile fichier) {
        DocumentResponseDTO nouveauDocument = documentService.ajouterDocument(projetId, requestDTO, fichier);
        return new ResponseEntity<>(nouveauDocument, HttpStatus.CREATED);
    }

    // --- GET : Lister les documents d'un projet ---
    @GetMapping("/projets/{projetId}/documents")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByProjet(@PathVariable Integer projetId) {
        return ResponseEntity.ok(documentService.getDocumentsByProjet(projetId));
    }

    // --- GET : Consulter un seul document ---
    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable Integer id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    // --- PUT : Modifier un document ---
    @PutMapping("/documents/{id}")
    public ResponseEntity<DocumentResponseDTO> modifierDocument(
            @PathVariable Integer id,
            @Valid @ModelAttribute DocumentRequestDTO requestDTO,
            // required = false car le client peut vouloir modifier juste le nom sans changer le fichier !
            @RequestParam(value = "fichier", required = false) MultipartFile fichier) {

        DocumentResponseDTO documentMaj = documentService.modifierDocument(id, requestDTO, fichier);
        return ResponseEntity.ok(documentMaj);
    }

    // --- DELETE : Supprimer un document ---
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> supprimerDocument(@PathVariable Integer id) {
        documentService.supprimerDocument(id);
        return ResponseEntity.noContent().build();
    }

    // --- GET : TÉLÉCHARGER LE FICHIER PHYSIQUE (DOWNLOAD) ---
    @GetMapping("/documents/{id}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Integer id) {
        // 1. Récupérer l'entité pour avoir le nom généré
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        // 2. Récupérer le fichier physique sous forme de ressource web
        Resource resource = fileStorageService.loadFileAsResource(document.getChemin());

        // 3. Renvoyer avec les bons headers pour forcer le téléchargement dans le navigateur
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getChemin() + "\"")
                .body(resource);
    }
}