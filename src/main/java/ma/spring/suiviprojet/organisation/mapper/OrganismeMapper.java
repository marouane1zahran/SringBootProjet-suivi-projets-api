package ma.spring.suiviprojet.organisation.mapper;

import ma.spring.suiviprojet.organisation.dto.OrganismeRequestDTO;
import ma.spring.suiviprojet.organisation.dto.OrganismeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Organisme;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrganismeMapper {


    OrganismeResponseDTO toResponseDTO(Organisme organisme);


    Organisme toEntity(OrganismeRequestDTO dto);


    void updateEntityFromDto(OrganismeRequestDTO dto, @MappingTarget Organisme organisme);
}