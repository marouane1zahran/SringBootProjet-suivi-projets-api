package ma.spring.suiviprojet.projet.mapper;

import ma.spring.suiviprojet.projet.dto.PhaseRequestDTO;
import ma.spring.suiviprojet.projet.dto.PhaseResponseDTO;
import ma.spring.suiviprojet.projet.entity.Phase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PhaseMapper {

    PhaseResponseDTO toResponseDTO(Phase phase);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projet", ignore = true)
    @Mapping(target = "livrables", ignore = true)
    @Mapping(target = "employesAssignes", ignore = true)
    @Mapping(target = "etatRealisation", constant = "false")
    @Mapping(target = "etatFacturation", constant = "false")
    @Mapping(target = "etatPaiement", constant = "false")
    Phase toEntity(PhaseRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projet", ignore = true)
    @Mapping(target = "livrables", ignore = true)
    @Mapping(target = "employesAssignes", ignore = true)
    @Mapping(target = "etatRealisation", ignore = true)
    @Mapping(target = "etatFacturation", ignore = true)
    @Mapping(target = "etatPaiement", ignore = true)
    void updateEntityFromDto(PhaseRequestDTO requestDTO, @MappingTarget Phase entity);
}