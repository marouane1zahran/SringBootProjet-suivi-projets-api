package ma.spring.suiviprojet.organisation.dto;

import lombok.Data;

@Data
public class OrganismeDTO {
    private Long id;
    private String code;
    private String nom;
    private String adresse;
    private String telephone;
}