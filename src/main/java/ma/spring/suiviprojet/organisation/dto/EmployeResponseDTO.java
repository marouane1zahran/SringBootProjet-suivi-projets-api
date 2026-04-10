package ma.spring.suiviprojet.organisation.dto;

import lombok.Data;

@Data
public class EmployeResponseDTO {

    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Long profilId;

}