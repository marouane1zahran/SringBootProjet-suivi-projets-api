package ma.spring.suiviprojet.facturation.repository;

import ma.spring.suiviprojet.facturation.entity.Facture;
import ma.spring.suiviprojet.projet.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

}