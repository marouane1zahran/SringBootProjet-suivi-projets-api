package ma.spring.suiviprojet.projet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PhaseRequestDTO {

    @NotBlank(message = "Le code de la phase est obligatoire")
    private String code;

    @NotBlank(message = "Le libellé de la phase est obligatoire")
    private String libelle;

    private String description;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @Positive(message = "Le montant de la phase doit être positif")
    private Double montant;
}