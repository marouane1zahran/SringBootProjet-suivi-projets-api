package ma.spring.suiviprojet.projet.mapper;

import ma.spring.suiviprojet.projet.dto.LigneEmployePhaseRequestDTO;
import ma.spring.suiviprojet.projet.dto.LigneEmployePhaseResponseDTO;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LigneEmployePhaseMapper {

    // --- Vers la Réponse ---
    // On extrait les IDs depuis l'objet "id" (la clé composée LigneEmployePhaseId)
    @Mapping(target = "employeId", source = "id.employeId")
    @Mapping(target = "phaseId", source = "id.phaseId")
    @Mapping(target = "nomEmploye", source = "employe.nom")
    @Mapping(target = "matriculeEmploye", source = "employe.matricule")
    LigneEmployePhaseResponseDTO toResponseDTO(LigneEmployePhase entity);

    // --- Vers l'Entité ---
    // ASTUCE DE PRO : Avec une clé composée, c'est trop risqué de laisser MapStruct
    // lier les entités automatiquement. On ignore l'ID, la phase et l'employé ici.
    // Nous allons les lier "à la main" dans le Service, c'est beaucoup plus sûr !
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employe", ignore = true)
    @Mapping(target = "phase", ignore = true)
    LigneEmployePhase toEntity(LigneEmployePhaseRequestDTO requestDTO);
}