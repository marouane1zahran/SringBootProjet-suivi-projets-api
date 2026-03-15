package ma.spring.suiviprojet.projet.mapper;


import ma.spring.suiviprojet.projet.dto.AffectationRequestDTO;
import ma.spring.suiviprojet.projet.dto.AffectationResponseDTO;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AffectationMapper {

    AffectationResponseDTO toDTO(LigneEmployePhase entity);

    LigneEmployePhase toEntity(AffectationRequestDTO dto);
}