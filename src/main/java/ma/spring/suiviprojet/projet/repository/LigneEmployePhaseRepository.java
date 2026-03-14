package ma.spring.suiviprojet.projet.repository;

import ma.spring.suiviprojet.projet.entity.LigneEmployePhase;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface LigneEmployePhaseRepository extends JpaRepository<LigneEmployePhase, LigneEmployePhaseId> {

    // Trouver toutes les phases sur lesquelles travaille un employé spécifique
    List<LigneEmployePhase> findByEmployeId(Integer employeId);

    // Trouver tous les employés qui travaillent sur une phase spécifique
    List<LigneEmployePhase> findByPhaseId(Integer phaseId);
}