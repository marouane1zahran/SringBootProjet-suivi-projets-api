package ma.spring.suiviprojet.projet.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import ma.spring.suiviprojet.projet.dto.PhaseRequestDTO;
import ma.spring.suiviprojet.projet.dto.PhaseResponseDTO;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.entity.Projet;
import ma.spring.suiviprojet.projet.mapper.PhaseMapper;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;
import ma.spring.suiviprojet.projet.repository.ProjetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final ProjetRepository projetRepository;
    private final PhaseMapper phaseMapper;

    // --- CRÉATION D'UNE PHASE ---
    public PhaseResponseDTO creerPhase(Integer projetId, PhaseRequestDTO requestDTO) {

        // 1. On récupère le projet parent
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RegleMetierException("Projet introuvable avec l'ID : " + projetId));

        // 2. RÈGLE : Vérification des dates internes de la phase
        if (requestDTO.getDateDebut().isAfter(requestDTO.getDateFin())) {
            throw new RegleMetierException("La date de début de la phase doit être antérieure à sa date de fin.");
        }

        // 3. RÈGLE : Les dates de la phase doivent être incluses dans le projet
        if (requestDTO.getDateDebut().isBefore(projet.getDateDebut()) || requestDTO.getDateFin().isAfter(projet.getDateFin())) {
            throw new RegleMetierException("Les dates de la phase doivent être comprises entre le "
                    + projet.getDateDebut() + " et le " + projet.getDateFin());
        }

        // 4. RÈGLE : Le budget ne doit pas déborder
        
        List<Phase> phasesExistantes = phaseRepository.findByProjetId(projetId);
        double sommeActuelle = phasesExistantes.stream()
                .mapToDouble(Phase::getMontant)
                .sum();

        if ((sommeActuelle + requestDTO.getMontant()) > projet.getMontant()) {
            throw new RegleMetierException("Budget dépassé ! Le montant restant pour ce projet est de : "
                    + (projet.getMontant() - sommeActuelle));
        }


        Phase phase = phaseMapper.toEntity(requestDTO);
        phase.setProjet(projet);

        phase = phaseRepository.save(phase);
        return phaseMapper.toResponseDTO(phase);
    }

    // --- RÉCUPÉRER LES PHASES D'UN PROJET ---
    @Transactional(readOnly = true)
    public List<PhaseResponseDTO> getPhasesByProjet(Integer projetId) {
        // On vérifie d'abord que le projet existe
        if (!projetRepository.existsById(projetId)) {
            throw new RegleMetierException("Projet introuvable.");
        }

        return phaseRepository.findByProjetId(projetId).stream()
                .map(phaseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- CHANGEMENT D'ÉTAT (Pour le Comptable ou le Chef de projet) ---
    public PhaseResponseDTO marquerRealisee(Integer phaseId) {
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new RegleMetierException("Phase introuvable."));
        phase.setEtatRealisation(true);
        return phaseMapper.toResponseDTO(phaseRepository.save(phase));
    }
}