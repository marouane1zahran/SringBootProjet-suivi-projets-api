package ma.spring.suiviprojet.projet.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import ma.spring.suiviprojet.projet.dto.LivrableRequestDTO;
import ma.spring.suiviprojet.projet.dto.LivrableResponseDTO;
import ma.spring.suiviprojet.projet.entity.Livrable;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.mapper.LivrableMapper;
import ma.spring.suiviprojet.projet.repository.LivrableRepository;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;
import ma.spring.suiviprojet.projet.utils.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LivrableService {

    private final LivrableRepository livrableRepository;
    private final PhaseRepository phaseRepository;
    private final FileStorageService fileStorageService;
    private final LivrableMapper livrableMapper;

    // --- CRÉATION ---
    public LivrableResponseDTO ajouterLivrable(Integer phaseId, LivrableRequestDTO requestDTO, MultipartFile fichier) {
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new RegleMetierException("Phase introuvable avec l'ID : " + phaseId));

        if (fichier == null || fichier.isEmpty()) {
            throw new RegleMetierException("Le fichier du livrable est vide ou absent.");
        }

        String nomFichierUnique = fileStorageService.sauvegarderFichier(fichier);

        Livrable livrable = livrableMapper.toEntity(requestDTO);
        livrable.setChemin(nomFichierUnique);
        livrable.setPhase(phase);

        return livrableMapper.toResponseDTO(livrableRepository.save(livrable));
    }

    // --- LECTURE : Tous les livrables d'une phase ---
    @Transactional(readOnly = true)
    public List<LivrableResponseDTO> getLivrablesByPhase(Integer phaseId) {
        if (!phaseRepository.existsById(phaseId)) {
            throw new RegleMetierException("Phase introuvable.");
        }
        return livrableRepository.findByPhaseId(phaseId).stream()
                .map(livrableMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- LECTURE : Un seul livrable ---
    @Transactional(readOnly = true)
    public LivrableResponseDTO getLivrableById(Integer id) {
        Livrable livrable = livrableRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Livrable introuvable avec l'ID : " + id));
        return livrableMapper.toResponseDTO(livrable);
    }

    // --- MODIFICATION ---
    public LivrableResponseDTO modifierLivrable(Integer id, LivrableRequestDTO requestDTO, MultipartFile fichier) {
        Livrable livrableExistant = livrableRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Livrable introuvable avec l'ID : " + id));

        livrableMapper.updateEntityFromDto(requestDTO, livrableExistant);

        // Remplacement du fichier si un nouveau est fourni
        if (fichier != null && !fichier.isEmpty()) {
            fileStorageService.supprimerFichier(livrableExistant.getChemin());
            String nouveauNom = fileStorageService.sauvegarderFichier(fichier);
            livrableExistant.setChemin(nouveauNom);
        }

        return livrableMapper.toResponseDTO(livrableRepository.save(livrableExistant));
    }

    // --- SUPPRESSION ---
    public void supprimerLivrable(Integer id) {
        Livrable livrable = livrableRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Livrable introuvable avec l'ID : " + id));

        fileStorageService.supprimerFichier(livrable.getChemin()); // Suppression physique
        livrableRepository.delete(livrable); // Suppression en base
    }
}