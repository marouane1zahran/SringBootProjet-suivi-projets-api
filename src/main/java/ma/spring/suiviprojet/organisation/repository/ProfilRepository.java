package ma.spring.suiviprojet.organisation.repository;

import ma.spring.suiviprojet.organisation.entity.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Integer> {

    // Trouve un profil par son code exact
    Optional<Profil> findByCode(String code);
}