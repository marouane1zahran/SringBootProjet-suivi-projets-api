package ma.spring.suiviprojet.projet.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LigneEmployePhaseResponseDTO {
    // On renvoie les deux parties de la clé composée
    private Integer employeId;
    private Integer phaseId;

    // Informations utiles pour l'affichage
    private String nomEmploye;
    private String matriculeEmploye;

    private LocalDate dateDebut;
    private LocalDate dateFin;
}