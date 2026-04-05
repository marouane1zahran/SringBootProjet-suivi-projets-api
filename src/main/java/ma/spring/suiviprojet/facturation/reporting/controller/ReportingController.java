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


    // GET http://localhost:8080/api/reporting/chiffre-affaire

    @GetMapping("/chiffre-affaire")
    public ChiffreAffaireDTO getTotal() {
        return service.getTotal();
    }


    // GET http://localhost:8080/api/reporting/par-projet

    @GetMapping("/par-projet")
    public List<FactureParProjetDTO> getParProjet() {
        return service.getParProjet();
    }


    // GET http://localhost:8080/api/reporting/phases-terminees

    @GetMapping("/phases-terminees")
    public Long getPhasesTerminees() {
        return service.countPhasesTerminees();
    }
}