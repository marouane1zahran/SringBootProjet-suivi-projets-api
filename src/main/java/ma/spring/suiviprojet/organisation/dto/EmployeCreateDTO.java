package ma.spring.suiviprojet.organisation.dto;

import lombok.Data;

@Data
public class EmployeCreateDTO {

    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String login;
    private String password;
    private Long profilId;

}