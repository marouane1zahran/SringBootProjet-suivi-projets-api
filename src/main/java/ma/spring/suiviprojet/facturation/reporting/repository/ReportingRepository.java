package ma.spring.suiviprojet.facturation.reporting.repository;


import ma.spring.suiviprojet.facturation.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportingRepository extends JpaRepository<Facture, Long> {

    // On ne somme que les montants des factures "Payées"
    @Query("SELECT COALESCE(SUM(f.montant), 0.0) FROM Facture f WHERE f.phase.etatPaiement = true")
    Double getTotalChiffreAffaire();

    // On met aussi à jour le graphique pour ne montrer que l'argent encaissé par projet
    @Query("""
        SELECT p.nom, COALESCE(SUM(f.montant), 0.0)
        FROM Facture f
        JOIN f.phase ph
        JOIN ph.projet p
        WHERE ph.etatPaiement = true
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
    // Nombre total de projets (pour la première carte du dashboard)
    @Query("SELECT COUNT(p) FROM Projet p")
    Long countTotalProjets();

    // Phases terminées mais NON facturées (besoin critique du comptable)
    @Query("SELECT COUNT(ph) FROM Phase ph WHERE ph.etatRealisation = true AND ph.etatFacturation = false")
    Long countPhasesTermineesNonFacturees();
}