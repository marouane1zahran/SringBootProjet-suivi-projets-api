package ma.spring.suiviprojet.projet.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjetResponseDTO {
    private Integer id;
    private String code;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double montant;
    private String nomOrganisme;
    private String nomChefProjet;
}