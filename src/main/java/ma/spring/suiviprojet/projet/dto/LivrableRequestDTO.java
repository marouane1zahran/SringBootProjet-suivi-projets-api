package ma.spring.suiviprojet.projet.dto;

import lombok.Data;

@Data
public class LivrableRequestDTO {
    private String libelle;
    private String description;
    private Long phaseId;
    private String code;
    private String chemin;
}