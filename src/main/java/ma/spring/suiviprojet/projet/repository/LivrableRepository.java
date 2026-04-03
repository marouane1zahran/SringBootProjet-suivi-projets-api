package ma.spring.suiviprojet.projet.repository;

import ma.spring.suiviprojet.projet.entity.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivrableRepository extends JpaRepository<Livrable, Integer> {

    // Pour lister tous les livrables d'une phase spécifique
    List<Livrable> findByPhaseId(Integer phaseId);
}