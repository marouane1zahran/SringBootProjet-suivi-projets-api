package ma.spring.suiviprojet.facturation.reporting.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.facturation.reporting.dto.ChiffreAffaireDTO;
import ma.spring.suiviprojet.facturation.reporting.dto.FactureParProjetDTO;
import ma.spring.suiviprojet.facturation.reporting.repository.ReportingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final ReportingRepository repository;

    public ChiffreAffaireDTO getTotal() {
        Double total = repository.getTotalChiffreAffaire();
        // Si total est null, on renvoie 0.0 pour le Dashboard
        return new ChiffreAffaireDTO(total != null ? total : 0.0);
    }

    public List<FactureParProjetDTO> getParProjet() {
        List<Object[]> result = repository.getChiffreAffaireParProjet();
        return result.stream()
                .map(obj -> new FactureParProjetDTO(
                        (String) obj[0],
                        (Double) obj[1]
                ))
                .toList();
    }

    public Long countPhasesTerminees() {
        return repository.countPhasesTerminees();
    }

    // --- NOUVELLES STATISTIQUES ---

    // Retourne le nombre total de projets dans le système
    public Long countTotalProjets() {
        return repository.countTotalProjets();
    }

    // Retourne le nombre de phases terminées qui attendent d'être facturées
    public Long countPhasesTermineesNonFacturees() {
        return repository.countPhasesTermineesNonFacturees();
    }
}