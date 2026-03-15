package ma.spring.suiviprojet.facturation.controller;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.facturation.dto.FactureCreateDTO;
import ma.spring.suiviprojet.facturation.dto.FactureResponseDTO;
import ma.spring.suiviprojet.facturation.entity.Facture;
import ma.spring.suiviprojet.facturation.mapper.FactureMapper;
import ma.spring.suiviprojet.facturation.repository.FactureRepository;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureRepository factureRepository;
    private final PhaseRepository phaseRepository;
    private final FactureMapper factureMapper;

    // CREATE
    @PostMapping
    public ResponseEntity<FactureResponseDTO> create(@RequestBody FactureCreateDTO dto) {

        Phase phase = phaseRepository.findById(dto.getPhaseId())
                .orElseThrow(() -> new RuntimeException("Phase not found"));

        Facture facture = new Facture();
        facture.setCode(dto.getCode());
        facture.setDateFacture(dto.getDateFacture());
        facture.setPhase(phase);

        Facture saved = factureRepository.save(facture);

        return ResponseEntity.ok(factureMapper.toDto(saved));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<FactureResponseDTO>> getAll() {

        List<FactureResponseDTO> list = factureRepository.findAll()
                .stream()
                .map(factureMapper::toDto)
                .toList();

        return ResponseEntity.ok(list);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<FactureResponseDTO> getById(@PathVariable Long id) {

        return factureRepository.findById(id)
                .map(factureMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!factureRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        factureRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}