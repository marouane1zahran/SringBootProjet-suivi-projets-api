package ma.spring.suiviprojet.projet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LigneEmployePhaseRequestDTO {

    @NotNull(message = "L'ID de l'employé est obligatoire")
    private Integer employeId;

    @NotNull(message = "La date de début d'affectation est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin d'affectation est obligatoire")
    private LocalDate dateFin;
}