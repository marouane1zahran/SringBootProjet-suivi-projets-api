package ma.spring.suiviprojet.projet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.entity.Organisme;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String nom;

    private String description;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    private Double montant;


    @ManyToOne
    @JoinColumn(name = "organisme_id", nullable = false)
    private Organisme organisme;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Phase> phases;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Document> documents;

    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefProjet;
}