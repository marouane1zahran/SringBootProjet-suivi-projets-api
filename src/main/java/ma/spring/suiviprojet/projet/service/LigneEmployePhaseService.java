package ma.spring.suiviprojet.projet.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import ma.spring.suiviprojet.projet.dto.LigneEmployePhaseRequestDTO;
import ma.spring.suiviprojet.projet.dto.LigneEmployePhaseResponseDTO;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhase;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhaseId;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.mapper.LigneEmployePhaseMapper;
import ma.spring.suiviprojet.projet.repository.LigneEmployePhaseRepository;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LigneEmployePhaseService {

    private final LigneEmployePhaseRepository affectationRepository;
    private final PhaseRepository phaseRepository;
    private final EmployeRepository employeRepository; // On interroge le module du Dev 2
    private final LigneEmployePhaseMapper mapper;

    // --- CRÉER UNE AFFECTATION ---
    public LigneEmployePhaseResponseDTO affecterEmploye(Integer phaseId, LigneEmployePhaseRequestDTO requestDTO) {

        // 1. Vérifier l'existence de la phase et de l'employé
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new RegleMetierException("La phase spécifiée est introuvable."));

        Employe employe = employeRepository.findById(requestDTO.getEmployeId())
                .orElseThrow(() -> new RegleMetierException("L'employé spécifié est introuvable."));

        // 2. RÈGLE : Cohérence des dates envoyées
        if (requestDTO.getDateDebut().isAfter(requestDTO.getDateFin())) {
            throw new RegleMetierException("La date de début d'affectation doit être antérieure à la date de fin.");
        }

        // 3. RÈGLE : L'affectation ne peut pas déborder de la phase
        if (requestDTO.getDateDebut().isBefore(phase.getDateDebut()) || requestDTO.getDateFin().isAfter(phase.getDateFin())) {
            throw new RegleMetierException("Les dates d'affectation doivent être strictement comprises dans la période de la phase (du "
                    + phase.getDateDebut() + " au " + phase.getDateFin() + ").");
        }

        // 4. RÈGLE : Vérifier la disponibilité de l'employé (Le Chevauchement / Overlap)
        List<LigneEmployePhase> affectationsExistantes = affectationRepository.findByEmployeId(employe.getId());

        for (LigneEmployePhase existante : affectationsExistantes) {
            // Formule magique du chevauchement : (Debut A <= Fin B) ET (Fin A >= Debut B)
            boolean isOverlapping = !requestDTO.getDateDebut().isAfter(existante.getDateFin())
                    && !requestDTO.getDateFin().isBefore(existante.getDateDebut());

            if (isOverlapping) {
                throw new RegleMetierException("Impossible : L'employé " + employe.getNom()
                        + " est déjà affecté à une autre tâche sur la période du "
                        + existante.getDateDebut() + " au " + existante.getDateFin() + ".");
            }
        }

        // 5. Instanciation Manuelle (À cause de la clé composée @EmbeddedId)
        LigneEmployePhase nouvelleAffectation = mapper.toEntity(requestDTO);

        // On crée la clé composée à la main !
        LigneEmployePhaseId cleComposee = new LigneEmployePhaseId(employe.getId(), phase.getId());
        nouvelleAffectation.setId(cleComposee);

        // On lie les objets manuellement pour éviter les bugs d'Hibernate
        nouvelleAffectation.setEmploye(employe);
        nouvelleAffectation.setPhase(phase);

        // 6. Sauvegarde et réponse
        nouvelleAffectation = affectationRepository.save(nouvelleAffectation);
        return mapper.toResponseDTO(nouvelleAffectation);
    }

    // --- CONSULTER L'ÉQUIPE D'UNE PHASE ---
    @Transactional(readOnly = true)
    public List<LigneEmployePhaseResponseDTO> getAffectationsByPhase(Integer phaseId) {
        if (!phaseRepository.existsById(phaseId)) {
            throw new RegleMetierException("La phase spécifiée est introuvable.");
        }

        return affectationRepository.findByPhaseId(phaseId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- DÉSAFFECTER UN EMPLOYÉ (DELETE) ---
    public void desaffecterEmploye(Integer phaseId, Integer employeId) {
        // On recrée la clé composée pour chercher l'affectation exacte
        LigneEmployePhaseId idCompose = new LigneEmployePhaseId(employeId, phaseId);

        LigneEmployePhase affectation = affectationRepository.findById(idCompose)
                .orElseThrow(() -> new RegleMetierException("Cette affectation n'existe pas."));

        affectationRepository.delete(affectation);
    }

    // --- MODIFIER LES DATES D'UNE AFFECTATION (PUT) ---
    public LigneEmployePhaseResponseDTO modifierAffectation(Integer phaseId, Integer employeId, LigneEmployePhaseRequestDTO requestDTO) {

        LigneEmployePhaseId idCompose = new LigneEmployePhaseId(employeId, phaseId);

        LigneEmployePhase affectationExistante = affectationRepository.findById(idCompose)
                .orElseThrow(() -> new RegleMetierException("Cette affectation n'existe pas."));

        Phase phase = affectationExistante.getPhase();

        // 1. RÈGLE : Cohérence des dates
        if (requestDTO.getDateDebut().isAfter(requestDTO.getDateFin())) {
            throw new RegleMetierException("La date de début d'affectation doit être antérieure à la date de fin.");
        }

        // 2. RÈGLE : Inclus dans la phase
        if (requestDTO.getDateDebut().isBefore(phase.getDateDebut()) || requestDTO.getDateFin().isAfter(phase.getDateFin())) {
            throw new RegleMetierException("Les dates d'affectation doivent être strictement comprises dans la période de la phase.");
        }

        // 3. RÈGLE : Vérifier le chevauchement (en excluant l'affectation qu'on est en train de modifier !)
        List<LigneEmployePhase> affectationsExistantes = affectationRepository.findByEmployeId(employeId);

        for (LigneEmployePhase existante : affectationsExistantes) {
            // Astuce : On ignore la ligne actuelle pour éviter qu'elle ne rentre en conflit avec elle-même
            if (existante.getId().equals(idCompose)) {
                continue;
            }

            boolean isOverlapping = !requestDTO.getDateDebut().isAfter(existante.getDateFin())
                    && !requestDTO.getDateFin().isBefore(existante.getDateDebut());

            if (isOverlapping) {
                throw new RegleMetierException("Impossible : L'employé est déjà affecté à une autre tâche sur cette période.");
            }
        }

        // 4. Mise à jour des dates uniquement (On ne touche surtout pas aux IDs !)
        affectationExistante.setDateDebut(requestDTO.getDateDebut());
        affectationExistante.setDateFin(requestDTO.getDateFin());

        affectationExistante = affectationRepository.save(affectationExistante);
        return mapper.toResponseDTO(affectationExistante);
    }
}