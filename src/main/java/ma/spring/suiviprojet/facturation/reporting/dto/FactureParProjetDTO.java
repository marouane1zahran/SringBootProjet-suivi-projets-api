package ma.spring.suiviprojet.facturation.reporting.dto;

// DTO pour afficher le total des factures par projet
public class FactureParProjetDTO {

    // nom du projet
    private String projetNom;

    // somme des factures de ce projet
    private Double total;

    public FactureParProjetDTO(String projetNom, Double total) {
        this.projetNom = projetNom;
        this.total = total;
    }

    public String getProjetNom() {
        return projetNom;
    }

    public Double getTotal() {
        return total;
    }
}