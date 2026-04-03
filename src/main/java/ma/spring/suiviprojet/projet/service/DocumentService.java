package ma.spring.suiviprojet.projet.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import ma.spring.suiviprojet.projet.dto.DocumentRequestDTO;
import ma.spring.suiviprojet.projet.dto.DocumentResponseDTO;
import ma.spring.suiviprojet.projet.entity.Document;
import ma.spring.suiviprojet.projet.entity.Projet;
import ma.spring.suiviprojet.projet.mapper.DocumentMapper;
import ma.spring.suiviprojet.projet.repository.DocumentRepository;
import ma.spring.suiviprojet.projet.repository.ProjetRepository;
import ma.spring.suiviprojet.projet.utils.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ProjetRepository projetRepository;
    private final FileStorageService fileStorageService;
    private final DocumentMapper documentMapper;



    public DocumentResponseDTO ajouterDocument(Integer projetId, DocumentRequestDTO requestDTO, MultipartFile fichier) {

        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RegleMetierException("Projet introuvable avec l'ID : " + projetId));

        if (fichier.isEmpty()) {
            throw new RegleMetierException("Le fichier est vide ou absent.");
        }

        // 1. Sauvegarde physique
        String nomFichierUnique = fileStorageService.sauvegarderFichier(fichier);

        // 2. Conversion DTO -> Entité
        Document document = documentMapper.toEntity(requestDTO);

        // 3. Ajout des données manquantes
        document.setChemin(nomFichierUnique);
        document.setProjet(projet);

        // 4. Sauvegarde en base et retour propre
        document = documentRepository.save(document);
        return documentMapper.toResponseDTO(document);
    }

    // --- LECTURE : Tous les documents d'un projet ---
    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> getDocumentsByProjet(Integer projetId) {
        if (!projetRepository.existsById(projetId)) {
            throw new RegleMetierException("Projet introuvable.");
        }
        return documentRepository.findByProjetId(projetId).stream()
                .map(documentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- LECTURE : Un seul document ---
    @Transactional(readOnly = true)
    public DocumentResponseDTO getDocumentById(Integer id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Document introuvable avec l'ID : " + id));
        return documentMapper.toResponseDTO(document);
    }

    // --- MODIFICATION (PUT) ---
    public DocumentResponseDTO modifierDocument(Integer id, DocumentRequestDTO requestDTO, MultipartFile fichier) {
        Document documentExistant = documentRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Document introuvable avec l'ID : " + id));

        // 1. On met à jour les données texte
        documentMapper.updateEntityFromDto(requestDTO, documentExistant);

        // 2. Si un NOUVEAU fichier est envoyé, on supprime l'ancien et on sauvegarde le nouveau
        if (fichier != null && !fichier.isEmpty()) {
            fileStorageService.supprimerFichier(documentExistant.getChemin()); // Adieu l'ancien
            String nouveauNom = fileStorageService.sauvegarderFichier(fichier); // Bonjour le nouveau
            documentExistant.setChemin(nouveauNom);
        }

        documentExistant = documentRepository.save(documentExistant);
        return documentMapper.toResponseDTO(documentExistant);
    }

    // --- SUPPRESSION (DELETE) ---
    public void supprimerDocument(Integer id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Document introuvable avec l'ID : " + id));

        // 1. On supprime le fichier physique du disque dur
        fileStorageService.supprimerFichier(document.getChemin());

        // 2. On supprime l'enregistrement de la base de données
        documentRepository.delete(document);
    }
}