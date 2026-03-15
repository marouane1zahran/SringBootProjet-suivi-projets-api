package ma.spring.suiviprojet.organisation.mapper;

import ma.spring.suiviprojet.organisation.dto.EmployeCreateDTO;
import ma.spring.suiviprojet.organisation.dto.EmployeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Employe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    Employe toEntity(EmployeCreateDTO dto);

    EmployeResponseDTO toDTO(Employe employe);

}