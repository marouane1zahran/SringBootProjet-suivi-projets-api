package ma.spring.suiviprojet.organisation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "profils")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "profil", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Employe> employes ;
}