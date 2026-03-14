package ma.spring.suiviprojet.facturation.repository;

import ma.spring.suiviprojet.facturation.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

}