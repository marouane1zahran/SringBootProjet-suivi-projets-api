package ma.spring.suiviprojet.organisation.dto;

public record OrganismeResponseDTO(
        int id,
        String code,
        String nom,
        String adresse,
        String telephone,
        String nomContact,
        String emailContact,
        String siteWeb
) {}