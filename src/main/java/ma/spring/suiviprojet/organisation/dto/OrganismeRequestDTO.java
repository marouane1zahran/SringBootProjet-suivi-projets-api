package ma.spring.suiviprojet.organisation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OrganismeRequestDTO(
        @NotBlank(message = "Le code de l'organisme est obligatoire")
        String code,

        @NotBlank(message = "Le nom de l'organisme est obligatoire")
        String nom,

        String adresse,
        String telephone,
        String nomContact,

        @Email(message = "Le format de l'email n'est pas valide")
        String emailContact,

        String siteWeb
) {}