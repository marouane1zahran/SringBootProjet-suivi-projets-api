package ma.spring.suiviprojet.projet.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.LivrableRequestDTO;
import ma.spring.suiviprojet.projet.dto.LivrableResponseDTO;
import ma.spring.suiviprojet.projet.entity.Livrable;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.repository.LivrableRepository;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LivrableService {

    private final LivrableRepository repository;
    private final PhaseRepository phaseRepository;

    public LivrableResponseDTO saveLivrable(LivrableRequestDTO dto) {
        Phase phase = phaseRepository.findById(dto.getPhaseId())
                .orElseThrow(() -> new RuntimeException("Phase non trouvée avec l'ID: " + dto.getPhaseId()));

        Livrable livrable = new Livrable();
        livrable.setLibelle(dto.getLibelle());
        livrable.setDescription(dto.getDescription());
        livrable.setChemin(dto.getChemin());
        livrable.setCode(dto.getCode());
        livrable.setPhase(phase);
        livrable.setDateDepot(LocalDate.now());
        livrable.setStatus("A_VALIDER");
        Livrable saved = repository.save(livrable);

        //Map Entity to Response DTO (To avoid NULLs in Postman)
        LivrableResponseDTO response = new LivrableResponseDTO();
        response.setId(saved.getId());
        response.setLibelle(saved.getLibelle());
        response.setDescription(saved.getDescription());
        response.setDateDepot(saved.getDateDepot());
        response.setStatus(saved.getStatus());
        response.setPhaseId(phase.getId());
        response.setPhaseLibelle(phase.getLibelle());

        return response;
    }
}