package ma.spring.suiviprojet.projet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; // Import this for the date

@Entity
@Table(name = "livrables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livrable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    private String description;

    @Column(nullable = false)
    private String chemin;
    private LocalDate dateDepot;

    private String status;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;
}