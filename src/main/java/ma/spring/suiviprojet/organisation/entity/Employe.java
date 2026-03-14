package ma.spring.suiviprojet.organisation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ma.spring.suiviprojet.projet.entity.LigneEmployePhase;
import ma.spring.suiviprojet.projet.entity.Projet;

import java.util.List;

@Entity
@Table(name = "employes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String matricule;

    private String nom;
    private String prenom;
    private String telephone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    // La relation inverse vers l'association
    @OneToMany(mappedBy = "employe")
    @ToString.Exclude // Évite la boucle infinie
    private List<LigneEmployePhase> phasesAssignees;

    @OneToMany(mappedBy = "chefProjet")
    @ToString.Exclude
    private List<Projet> projets;



    // TODO pour le Dev 2 : Ajouter la relation @ManyToOne vers Profil
}