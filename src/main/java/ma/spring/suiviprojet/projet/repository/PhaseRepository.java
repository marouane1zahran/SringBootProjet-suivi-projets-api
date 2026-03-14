package ma.spring.suiviprojet.projet.repository;

import ma.spring.suiviprojet.projet.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

    // Cherche toutes les phases liées à un ID de projet
    List<Phase> findByProjetId(Integer projetId);
}