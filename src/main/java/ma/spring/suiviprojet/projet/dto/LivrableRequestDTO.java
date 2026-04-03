package ma.spring.suiviprojet.projet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LivrableRequestDTO {
    @NotBlank(message = "Le code du livrable est obligatoire")
    private String code;

    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;

    private String description;
}