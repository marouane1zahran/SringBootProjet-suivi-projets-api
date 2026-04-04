package ma.spring.suiviprojet.organisation.repository;

import ma.spring.suiviprojet.organisation.entity.Organisme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganismeRepository extends JpaRepository<Organisme, Integer> {

    // Recherche par code (doit être unique)
    Optional<Organisme> findByCode(String code);

    // Recherche par mot-clé dans le nom (ex: trouver tous les organismes contenant "Tech")
    List<Organisme> findByNomContainingIgnoreCase(String nom);

    // Recherche par nom de contact
    List<Organisme> findByNomContactContainingIgnoreCase(String nomContact);

}