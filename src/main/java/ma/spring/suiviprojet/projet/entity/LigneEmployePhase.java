package ma.spring.suiviprojet.projet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.spring.suiviprojet.organisation.entity.Employe;
import java.time.LocalDate;

@Entity
@Table(name = "ligne_employe_phase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneEmployePhase {

    @EmbeddedId
    private LigneEmployePhaseId id = new LigneEmployePhaseId();

    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("phaseId")
    @JoinColumn(name = "phase_id")
    private Phase phase;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;
}