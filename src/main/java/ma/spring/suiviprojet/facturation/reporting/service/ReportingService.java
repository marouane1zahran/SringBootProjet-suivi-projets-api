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

    // Retourne le chiffre d'affaires total
    public ChiffreAffaireDTO getTotal() {

        // appel de la requête SQL
        Double total = repository.getTotalChiffreAffaire();

        // transformation en DTO
        return new ChiffreAffaireDTO(total);
    }


    // Retourne le chiffre d'affaires par projet
    public List<FactureParProjetDTO> getParProjet() {

        // résultat brut de la requête
        List<Object[]> result = repository.getChiffreAffaireParProjet();

        // transformation en DTO
        return result.stream()
                .map(obj -> new FactureParProjetDTO(
                        (String) obj[0],
                        (Double) obj[1]
                ))
                .toList();
    }


    // Retourne le nombre de phases terminées
    public Long countPhasesTerminees() {
        return repository.countPhasesTerminees();
    }
}