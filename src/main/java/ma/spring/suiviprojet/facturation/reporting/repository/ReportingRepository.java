package ma.spring.suiviprojet.facturation.reporting.repository;


import ma.spring.suiviprojet.facturation.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportingRepository extends JpaRepository<Facture, Long> {

    // Calcul du chiffre d'affaires total (somme des montants)
    @Query("SELECT SUM(f.montant) FROM Facture f")
    Double getTotalChiffreAffaire();


    // Calcul du chiffre d'affaires par projet
    @Query("""
        SELECT p.nom, SUM(f.montant)
        FROM Facture f
        JOIN f.phase ph
        JOIN ph.projet p
        GROUP BY p.nom
    """)
    List<Object[]> getChiffreAffaireParProjet();


    // Nombre de phases terminées
    @Query("""
        SELECT COUNT(ph)
        FROM Phase ph
        WHERE ph.etatRealisation = true
    """)
    Long countPhasesTerminees();
}