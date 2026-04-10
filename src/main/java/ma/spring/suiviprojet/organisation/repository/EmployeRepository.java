package ma.spring.suiviprojet.organisation.repository;

import ma.spring.suiviprojet.organisation.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Integer> {

    // Recherche un employé par son matricule unique
    Optional<Employe> findByMatricule(String matricule);

    // Recherche un employé par son login (très utile pour la future sécurité JWT)
    Optional<Employe> findByLogin(String login);

    // Recherche un employé par son email
    Optional<Employe> findByEmail(String email);

}