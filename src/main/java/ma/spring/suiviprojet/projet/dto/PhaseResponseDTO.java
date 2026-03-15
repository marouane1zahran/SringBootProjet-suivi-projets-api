package ma.spring.suiviprojet.projet.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PhaseResponseDTO {
    private Integer id;
    private String code;
    private String libelle;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double montant;


    private Boolean etatRealisation;
    private Boolean etatFacturation;
    private Boolean etatPaiement;
}