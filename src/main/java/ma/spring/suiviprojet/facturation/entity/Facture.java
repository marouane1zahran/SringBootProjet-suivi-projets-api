package ma.spring.suiviprojet.facturation.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.spring.suiviprojet.projet.entity.Phase;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private LocalDate dateFacture;

    @ManyToOne
    @JoinColumn(name = "phase_id")
    private Phase phase;
}