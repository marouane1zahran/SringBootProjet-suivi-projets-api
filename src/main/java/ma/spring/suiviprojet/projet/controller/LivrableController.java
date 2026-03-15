package ma.spring.suiviprojet.projet.controller;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.projet.dto.LivrableRequestDTO;
import ma.spring.suiviprojet.projet.dto.LivrableResponseDTO;
import ma.spring.suiviprojet.projet.service.LivrableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/livrables")
@RequiredArgsConstructor
public class LivrableController {

    private final LivrableService service;

    @PostMapping
    public ResponseEntity<LivrableResponseDTO> create(@RequestBody LivrableRequestDTO dto) {
        return ResponseEntity.ok(service.saveLivrable(dto));
    }
}