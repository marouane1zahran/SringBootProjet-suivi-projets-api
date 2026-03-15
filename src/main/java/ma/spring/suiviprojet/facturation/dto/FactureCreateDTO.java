package ma.spring.suiviprojet.facturation.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FactureCreateDTO {

    private String code;
    private LocalDate dateFacture;
    private Long phaseId;

}