package ma.spring.suiviprojet.facturation.reporting.controller;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.facturation.reporting.dto.ChiffreAffaireDTO;
import ma.spring.suiviprojet.facturation.reporting.dto.FactureParProjetDTO;
import ma.spring.suiviprojet.facturation.reporting.service.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reporting")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService service;

    @GetMapping("/chiffre-affaire")
    public ChiffreAffaireDTO getTotal() {
        return service.getTotal();
    }

    @GetMapping("/par-projet")
    public List<FactureParProjetDTO> getParProjet() {
        return service.getParProjet();
    }

    @GetMapping("/phases-terminees")
    public Long getPhasesTerminees() {
        return service.countPhasesTerminees();
    }

    // --- NOUVEAUX ENDPOINTS POUR LE DASHBOARD ---

    // GET http://localhost:8080/api/reporting/total-projets
    // 1. On change "/total-projets" par "/projets-en-cours"
    @GetMapping("/projets-en-cours")
    public Long getTotalProjets() {
        return service.countTotalProjets();
    }

    // 2. On change "/phases-a-facturer" par "/a-facturer"
    @GetMapping("/a-facturer")
    public Long getPhasesAFacturer() {
        return service.countPhasesTermineesNonFacturees();
    }
}