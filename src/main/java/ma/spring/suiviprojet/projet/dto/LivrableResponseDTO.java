package ma.spring.suiviprojet.projet.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LivrableResponseDTO {
    private Long id;
    private String libelle;
    private String description;
    private LocalDate dateDepot;
    private String status;
    private Long phaseId;
    private String phaseLibelle;
}