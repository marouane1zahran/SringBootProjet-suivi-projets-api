package ma.spring.suiviprojet.organisation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ma.spring.suiviprojet.projet.entity.Projet;

import java.util.List;

@Entity
@Table(name = "organismes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String nom;

    private String adresse;
    private String telephone;
    private String nomContact;
    private String emailContact;
    private String siteWeb;

    @OneToMany(mappedBy = "organisme", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Projet> projets;
}