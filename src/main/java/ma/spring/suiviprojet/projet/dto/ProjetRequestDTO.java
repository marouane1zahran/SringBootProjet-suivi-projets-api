package ma.spring.suiviprojet.projet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjetRequestDTO {

    @NotBlank(message = "Le code du projet est obligatoire")
    private String code;

    @NotBlank(message = "Le nom du projet est obligatoire")
    private String nom;

    private String description;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @Positive(message = "Le montant doit être positif")
    private Double montant;

    @NotNull(message = "L'ID de l'organisme client est obligatoire")
    private Integer organismeId;

    @NotNull(message = "L'ID du chef de projet est obligatoire")
    private Integer chefProjetId;
}