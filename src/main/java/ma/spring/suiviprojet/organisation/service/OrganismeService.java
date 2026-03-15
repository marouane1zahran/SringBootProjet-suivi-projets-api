package ma.spring.suiviprojet.organisation.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.ResourceNotFoundException;
import ma.spring.suiviprojet.organisation.dto.OrganismeRequestDTO;
import ma.spring.suiviprojet.organisation.dto.OrganismeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Organisme;
import ma.spring.suiviprojet.organisation.mapper.OrganismeMapper;
import ma.spring.suiviprojet.organisation.repository.OrganismeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganismeService {

    private final OrganismeRepository organismeRepository;
    private final OrganismeMapper organismeMapper;

    public OrganismeResponseDTO ajouterOrganisme(OrganismeRequestDTO dto) {
        Organisme organisme = organismeMapper.toEntity(dto);
        return organismeMapper.toResponseDTO(organismeRepository.save(organisme));
    }

    public List<OrganismeResponseDTO> obtenirTous() {
        return organismeRepository.findAll()
                .stream()
                .map(organismeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public OrganismeResponseDTO obtenirParId(int id) {
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organisme non trouvé avec l'ID : " + id));
        return organismeMapper.toResponseDTO(organisme);
    }

    public OrganismeResponseDTO modifierOrganisme(int id, OrganismeRequestDTO dto) {
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organisme non trouvé avec l'ID : " + id));


        organismeMapper.updateEntityFromDto(dto, organisme);

        return organismeMapper.toResponseDTO(organismeRepository.save(organisme));
    }

    public void supprimerOrganisme(int id) {
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organisme non trouvé avec l'ID : " + id));
        organismeRepository.delete(organisme);
    }

    public List<OrganismeResponseDTO> rechercherParNom(String nom) {
        return organismeRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(organismeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}