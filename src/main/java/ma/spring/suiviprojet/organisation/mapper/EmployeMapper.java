package ma.spring.suiviprojet.organisation.mapper;

import ma.spring.suiviprojet.organisation.dto.EmployeCreateDTO;
import ma.spring.suiviprojet.organisation.dto.EmployeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Employe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    Employe toEntity(EmployeCreateDTO dto);

    @Mapping(source = "profil.id", target = "profilId")
    EmployeResponseDTO toDTO(Employe employe);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profil", ignore = true)
    void updateEntityFromDto(EmployeCreateDTO dto, @MappingTarget Employe entity);

}