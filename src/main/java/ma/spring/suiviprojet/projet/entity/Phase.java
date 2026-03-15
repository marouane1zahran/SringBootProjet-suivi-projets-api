package ma.spring.suiviprojet.projet.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.spring.suiviprojet.facturation.entity.Facture;

import java.time.LocalDate;
import java.util.List;



@Entity
@Table(name = "phases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    private String description;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    private Double montant;

    //d'initialiser les états à 'false' par défaut lors de la création d'une phase
    @Column(name = "etat_realisation")
    private Boolean etatRealisation = false;

    @Column(name = "etat_facturation")
    private Boolean etatFacturation = false;

    @Column(name = "etat_paiement")
    private Boolean etatPaiement = false;





    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;


    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Livrable> livrables;


    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<LigneEmployePhase> employesAssignes;

    //relation avec facture

    @OneToMany(mappedBy = "phase")
    private List<Facture> factures;

}