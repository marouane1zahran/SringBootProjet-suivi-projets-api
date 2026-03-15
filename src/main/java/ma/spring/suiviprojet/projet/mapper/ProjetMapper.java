package ma.spring.suiviprojet.projet.mapper;

import ma.spring.suiviprojet.projet.dto.ProjetRequestDTO;
import ma.spring.suiviprojet.projet.dto.ProjetResponseDTO;
import ma.spring.suiviprojet.projet.entity.Projet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProjetMapper {


    @Mapping(target = "nomOrganisme", source = "organisme.nom")
    @Mapping(target = "nomChefProjet", source = "chefProjet.nom")
    ProjetResponseDTO toResponseDTO(Projet projet);

    @Mapping(target = "organisme.id", source = "organismeId")
    @Mapping(target = "chefProjet.id", source = "chefProjetId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phases", ignore = true)
    @Mapping(target = "documents", ignore = true)
    Projet toEntity(ProjetRequestDTO requestDTO);



    @Mapping(target = "organisme.id", source = "organismeId")
    @Mapping(target = "chefProjet.id", source = "chefProjetId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phases", ignore = true)
    @Mapping(target = "documents", ignore = true)
    void updateEntityFromDto(ProjetRequestDTO requestDTO, @MappingTarget Projet entity);
}