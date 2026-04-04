package ma.spring.suiviprojet.organisation.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException; // ⚠️ Changement ici pour l'uniformité
import ma.spring.suiviprojet.organisation.dto.OrganismeRequestDTO;
import ma.spring.suiviprojet.organisation.dto.OrganismeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Organisme;
import ma.spring.suiviprojet.organisation.mapper.OrganismeMapper;
import ma.spring.suiviprojet.organisation.repository.OrganismeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ⚠️ Ajout crucial

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrganismeService {

    private final OrganismeRepository organismeRepository;
    private final OrganismeMapper organismeMapper;

    public OrganismeResponseDTO ajouterOrganisme(OrganismeRequestDTO dto) {
        Organisme organisme = organismeMapper.toEntity(dto);
        return organismeMapper.toResponseDTO(organismeRepository.save(organisme));
    }

    @Transactional(readOnly = true) // Optimise les requêtes de lecture
    public List<OrganismeResponseDTO> obtenirTous() {
        return organismeRepository.findAll()
                .stream()
                .map(organismeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrganismeResponseDTO obtenirParId(Integer id) { // int -> Integer
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Organisme introuvable avec l'ID : " + id));
        return organismeMapper.toResponseDTO(organisme);
    }

    public OrganismeResponseDTO modifierOrganisme(Integer id, OrganismeRequestDTO dto) {
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Organisme introuvable avec l'ID : " + id));

        organismeMapper.updateEntityFromDto(dto, organisme);

        return organismeMapper.toResponseDTO(organismeRepository.save(organisme));
    }

    public void supprimerOrganisme(Integer id) {
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Organisme introuvable avec l'ID : " + id));
        organismeRepository.delete(organisme);
    }

    @Transactional(readOnly = true)
    public List<OrganismeResponseDTO> rechercherParNom(String nom) {
        return organismeRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(organismeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}