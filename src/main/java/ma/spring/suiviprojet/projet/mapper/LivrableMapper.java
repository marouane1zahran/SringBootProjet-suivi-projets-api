package ma.spring.suiviprojet.projet.mapper;

import ma.spring.suiviprojet.projet.dto.LivrableRequestDTO;
import ma.spring.suiviprojet.projet.dto.LivrableResponseDTO;
import ma.spring.suiviprojet.projet.entity.Livrable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LivrableMapper {

    @Mapping(target = "libellePhase", source = "phase.libelle")
    LivrableResponseDTO toResponseDTO(Livrable entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phase", ignore = true)
    @Mapping(target = "chemin", ignore = true)
    Livrable toEntity(LivrableRequestDTO requestDTO);

    // L'astuce pour la modification (PUT)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phase", ignore = true)
    @Mapping(target = "chemin", ignore = true)
    void updateEntityFromDto(LivrableRequestDTO requestDTO, @MappingTarget Livrable entity);
}