package ma.spring.suiviprojet.projet.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AffectationResponseDTO {

    private Long employeId;

    private Long phaseId;

    private LocalDate dateDebut;

    private LocalDate dateFin;
}