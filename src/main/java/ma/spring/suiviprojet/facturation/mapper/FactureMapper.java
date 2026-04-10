package ma.spring.suiviprojet.facturation.mapper;

import ma.spring.suiviprojet.facturation.dto.FactureResponseDTO;
import ma.spring.suiviprojet.facturation.entity.Facture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FactureMapper {

    @Mapping(source = "phase.id", target = "phaseId")
    @Mapping(source = "phase.libelle", target = "libellePhase")
    FactureResponseDTO toDto(Facture facture);
}