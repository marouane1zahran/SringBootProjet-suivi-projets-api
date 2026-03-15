package ma.spring.suiviprojet.projet.repository;

import ma.spring.suiviprojet.projet.entity.LigneEmployePhase;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface LigneEmployePhaseRepository extends JpaRepository<LigneEmployePhase, LigneEmployePhaseId> {

    // Trouver toutes les phases sur lesquelles travaille un employé spécifique
    List<LigneEmployePhase> findByEmployeId(Integer employeId);

    // Trouver tous les employés qui travaillent sur une phase spécifique
    List<LigneEmployePhase> findByPhaseId(Integer phaseId);


    // Vérifie si un employé est déjà affecté à une autre phase
    // pendant la même période afin d'éviter un conflit d'affectation
    @Query("""
SELECT l FROM LigneEmployePhase l
WHERE l.employe.id = :employeId
AND (
    (:dateDebut BETWEEN l.dateDebut AND l.dateFin)
    OR
    (:dateFin BETWEEN l.dateDebut AND l.dateFin)
)
""")
    List<LigneEmployePhase> findConflictingAffectations(
            Integer employeId,
            LocalDate dateDebut,
            LocalDate dateFin
    );
}