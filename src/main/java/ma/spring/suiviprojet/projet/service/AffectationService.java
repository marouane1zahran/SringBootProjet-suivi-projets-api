package ma.spring.suiviprojet.projet.service;


import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import ma.spring.suiviprojet.projet.dto.AffectationRequestDTO;
import ma.spring.suiviprojet.projet.dto.AffectationResponseDTO;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhase;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.mapper.AffectationMapper;
import ma.spring.suiviprojet.projet.repository.LigneEmployePhaseRepository;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AffectationService {

    private final LigneEmployePhaseRepository repository;
    private final EmployeRepository employeRepository;
    private final PhaseRepository phaseRepository;
    private final AffectationMapper mapper;

    public AffectationResponseDTO affecterEmploye(AffectationRequestDTO dto) {

        Employe employe = employeRepository.findById(dto.getEmployeId().intValue())
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        Phase phase = phaseRepository.findById(dto.getPhaseId().intValue())
                .orElseThrow(() -> new RuntimeException("Phase introuvable"));

        if (dto.getDateDebut().isAfter(dto.getDateFin())) {
            throw new RuntimeException("Date début invalide");
        }

        List<LigneEmployePhase> conflits =
                repository.findConflictingAffectations(
                        dto.getEmployeId().intValue(),
                        dto.getDateDebut(),
                        dto.getDateFin()
                );

        if (!conflits.isEmpty()) {
            throw new RuntimeException("Employé déjà occupé pendant cette période");
        }

        LigneEmployePhase affectation = new LigneEmployePhase();

        affectation.setEmploye(employe);
        affectation.setPhase(phase);
        affectation.setDateDebut(dto.getDateDebut());
        affectation.setDateFin(dto.getDateFin());

        LigneEmployePhase saved = repository.save(affectation);

        return mapper.toDTO(saved);
    }
}