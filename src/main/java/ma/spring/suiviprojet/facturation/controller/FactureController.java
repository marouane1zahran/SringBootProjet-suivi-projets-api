package ma.spring.suiviprojet.facturation.controller;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.facturation.dto.FactureCreateDTO;
import ma.spring.suiviprojet.facturation.dto.FactureResponseDTO;
import ma.spring.suiviprojet.facturation.service.FactureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

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
}