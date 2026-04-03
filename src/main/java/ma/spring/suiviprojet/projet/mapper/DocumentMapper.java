package ma.spring.suiviprojet.projet.mapper;

import ma.spring.suiviprojet.projet.dto.DocumentRequestDTO;
import ma.spring.suiviprojet.projet.dto.DocumentResponseDTO;
import ma.spring.suiviprojet.projet.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "nomProjet", source = "projet.nom")
    DocumentResponseDTO toResponseDTO(Document entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projet", ignore = true)
    @Mapping(target = "chemin", ignore = true) // Le chemin sera géré par notre FileStorageService
    Document toEntity(DocumentRequestDTO requestDTO);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projet", ignore = true)
    @Mapping(target = "chemin", ignore = true)
    void updateEntityFromDto(DocumentRequestDTO requestDTO, @MappingTarget Document entity);
}