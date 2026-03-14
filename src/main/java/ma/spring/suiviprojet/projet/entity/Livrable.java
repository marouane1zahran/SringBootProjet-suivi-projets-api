package ma.spring.suiviprojet.projet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livrables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livrable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    private String description;

    @Column(nullable = false)
    private String chemin;




    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;
}