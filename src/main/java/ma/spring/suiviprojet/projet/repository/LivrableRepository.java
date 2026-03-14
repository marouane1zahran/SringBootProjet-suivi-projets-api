package ma.spring.suiviprojet.projet.repository;

import ma.spring.suiviprojet.projet.entity.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivrableRepository extends JpaRepository<Livrable, Integer> {
}