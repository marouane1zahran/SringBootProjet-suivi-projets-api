package ma.spring.suiviprojet.projet.dto;

import lombok.Data;

@Data
public class LivrableResponseDTO {
    private Integer id;
    private String code;
    private String libelle;
    private String description;
    private String chemin;
    private String libellePhase; // Pour savoir à quelle phase il appartient
}