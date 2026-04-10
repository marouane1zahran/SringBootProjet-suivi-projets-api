package ma.spring.suiviprojet.projet.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.entity.Organisme;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import ma.spring.suiviprojet.organisation.repository.OrganismeRepository;
import ma.spring.suiviprojet.projet.dto.ProjetRequestDTO;
import ma.spring.suiviprojet.projet.dto.ProjetResponseDTO;
import ma.spring.suiviprojet.projet.entity.Projet;
import ma.spring.suiviprojet.projet.mapper.ProjetMapper;
import ma.spring.suiviprojet.projet.repository.ProjetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjetService {

    private final ProjetRepository projetRepository;
    private final ProjetMapper projetMapper;


    private final OrganismeRepository organismeRepository;
    private final EmployeRepository employeRepository;

    public ProjetResponseDTO creerProjet(ProjetRequestDTO requestDTO) {


        if (requestDTO.getDateDebut().isAfter(requestDTO.getDateFin())) {
            throw new RegleMetierException("La date de début doit être antérieure ou égale à la date de fin.");
        }

        // RÈGLE 2 : code projet unique
        if (projetRepository.findByCode(requestDTO.getCode()).isPresent()) {
            throw new RegleMetierException("Un projet avec le code '" + requestDTO.getCode() + "' existe déjà.");
        }

        // RÈGLE 3 : organisme existant
        organismeRepository.findById(requestDTO.getOrganismeId())
                .orElseThrow(() -> new RegleMetierException("L'organisme client spécifié n'existe pas."));

        // RÈGLE 4 : chef de projet existant
        employeRepository.findById(requestDTO.getChefProjetId())
                .orElseThrow(() -> new RegleMetierException("Le chef de projet spécifié n'existe pas."));


        Projet projet = projetMapper.toEntity(requestDTO);

        projet = projetRepository.save(projet);

        return projetMapper.toResponseDTO(projet);
    }

    // --- Méthode pour récupérer un projet ---
    @Transactional(readOnly = true)
    public ProjetResponseDTO getProjetById(Integer id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Projet introuvable avec l'ID : " + id));
        return projetMapper.toResponseDTO(projet);
    }

    // --- Méthode pour récupérer tous les projets ---
    @Transactional(readOnly = true)
    public List<ProjetResponseDTO> getAllProjets() {
        return projetRepository.findAll().stream()
                .map(projetMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- Méthode pour MODIFIER un projet (PUT) ---
    public ProjetResponseDTO modifierProjet(Integer id,  ProjetRequestDTO requestDTO) {

        // 1. On vérifie que le projet existe
        Projet projetExistant = projetRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Projet introuvable avec l'ID : " + id));

        // 2. RÈGLE : dateDebut <= dateFin
        if (requestDTO.getDateDebut().isAfter(requestDTO.getDateFin())) {
            throw new RegleMetierException("La date de début doit être antérieure ou égale à la date de fin.");
        }

        // 3. RÈGLE : Code projet unique
        projetRepository.findByCode(requestDTO.getCode())
                .ifPresent(p -> {
                    if (!p.getId().equals(id)) {
                        throw new RegleMetierException("Un autre projet utilise déjà le code '" + requestDTO.getCode() + "'.");
                    }
                });

        // 👇 4. LA CORRECTION COMMENCE ICI 👇
        // On récupère et ON CONSERVE les vraies entités depuis la base
        Organisme organismeClient = organismeRepository.findById(requestDTO.getOrganismeId())
                .orElseThrow(() -> new RegleMetierException("L'organisme client spécifié n'existe pas."));

        Employe chefProjet = employeRepository.findById(requestDTO.getChefProjetId())
                .orElseThrow(() -> new RegleMetierException("Le chef de projet spécifié n'existe pas."));

        // Astuce Anti-Crash : On détache temporairement les objets pour empêcher le Mapper
        // de modifier leurs clés primaires par erreur
        projetExistant.setOrganisme(null);
        projetExistant.setChefProjet(null);

        // Le mapper met à jour les champs texte (code, nom, description, dates, montant...)
        projetMapper.updateEntityFromDto(requestDTO, projetExistant);

        // On rattache les VRAIES entités que l'on vient de récupérer
        projetExistant.setOrganisme(organismeClient);
        projetExistant.setChefProjet(chefProjet);

        // 5. On sauvegarde
        projetExistant = projetRepository.save(projetExistant);

        return projetMapper.toResponseDTO(projetExistant);
    }
    // --- Méthode pour SUPPRIMER un projet (DELETE sous conditions) ---
    public void supprimerProjet(Integer id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Projet introuvable avec l'ID : " + id));

        // On ne supprime pas un projet qui a déjà des phases
        if (projet.getPhases() != null && !projet.getPhases().isEmpty()) {
            throw new RegleMetierException("Impossible de supprimer ce projet : il contient déjà des phases en cours.");
        }

        projetRepository.delete(projet);
    }
}