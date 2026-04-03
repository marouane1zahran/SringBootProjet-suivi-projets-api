package ma.spring.suiviprojet.projet.dto;

import lombok.Data;

@Data
public class DocumentResponseDTO {
    private Integer id;
    private String code;
    private String libelle;
    private String description;
    private String chemin; // Le nom du fichier généré (ex: 4a7b9c...pdf)
    private String nomProjet; // Utile pour l'affichage Front-End !
}