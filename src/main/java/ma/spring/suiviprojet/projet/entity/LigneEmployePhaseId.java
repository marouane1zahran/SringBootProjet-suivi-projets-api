package ma.spring.suiviprojet.projet.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneEmployePhaseId implements Serializable {

    private Integer employeId;
    private Integer phaseId;
}