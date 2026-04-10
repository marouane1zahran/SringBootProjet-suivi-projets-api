package ma.spring.suiviprojet.facturation.controller;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.facturation.dto.FactureCreateDTO;
import ma.spring.suiviprojet.facturation.dto.FactureResponseDTO;
import ma.spring.suiviprojet.facturation.service.FactureService;
import ma.spring.suiviprojet.projet.dto.PhaseResponseDTO;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.mapper.PhaseMapper;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;
    private final PhaseRepository phaseRepository ;
    private final PhaseMapper phaseMapper ;

    @PostMapping
    public FactureResponseDTO facturer(@RequestBody FactureCreateDTO dto) {
        return factureService.facturer(dto);
    }

    @GetMapping
    public List<FactureResponseDTO> getAll() {
        return factureService.getAll();
    }

    @GetMapping("/{id}")
    public FactureResponseDTO getById(@PathVariable Long id) {
        return factureService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        factureService.delete(id);
    }

    @GetMapping("/facturables")
    public ResponseEntity<List<PhaseResponseDTO>> getPhasesFacturables() {
        List<Phase> phases = phaseRepository.findByEtatRealisationTrueAndEtatFacturationFalse();
        return ResponseEntity.ok(phases.stream().map(phaseMapper::toResponseDTO).collect(Collectors.toList()));
    }
    @PutMapping("/{id}/payer")
    public ResponseEntity<Void> payer(@PathVariable Long id) {
        factureService.encaisserPaiement(id);
        return ResponseEntity.ok().build();
    }
}