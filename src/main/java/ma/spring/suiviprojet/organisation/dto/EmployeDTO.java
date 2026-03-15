package ma.spring.suiviprojet.organisation.dto;

import lombok.Data;

@Data
public class EmployeDTO {
    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String profilLibelle; // We just send the name of the profile
}