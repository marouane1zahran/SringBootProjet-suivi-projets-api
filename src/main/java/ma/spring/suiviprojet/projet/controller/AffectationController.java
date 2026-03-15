package ma.spring.suiviprojet.projet.controller;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.AffectationRequestDTO;
import ma.spring.suiviprojet.projet.dto.AffectationResponseDTO;
import ma.spring.suiviprojet.projet.service.AffectationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//test
@RestController
@RequestMapping("/api/affectations")
@RequiredArgsConstructor
public class AffectationController {

    private final AffectationService service;

    @PostMapping
    public ResponseEntity<AffectationResponseDTO> affecter(
            @RequestBody AffectationRequestDTO dto
    ) {

        return ResponseEntity.ok(service.affecterEmploye(dto));
    }
}