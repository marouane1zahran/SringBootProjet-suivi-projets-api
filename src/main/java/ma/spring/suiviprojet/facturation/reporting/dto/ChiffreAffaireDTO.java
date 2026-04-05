package ma.spring.suiviprojet.facturation.reporting.dto;




// DTO utilisé pour retourner le chiffre d'affaires total
public class ChiffreAffaireDTO {

    // montant total des factures
    private Double total;

    public ChiffreAffaireDTO(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }
}