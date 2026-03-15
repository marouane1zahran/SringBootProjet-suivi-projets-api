package ma.spring.suiviprojet.organisation.controller;


import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.dto.EmployeCreateDTO;

import ma.spring.suiviprojet.organisation.dto.EmployeResponseDTO;
import ma.spring.suiviprojet.organisation.service.EmployeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeResponseDTO> create(@RequestBody EmployeCreateDTO dto){
        return ResponseEntity.ok(employeService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmployeResponseDTO>> getAll(){
        return ResponseEntity.ok(employeService.getAll());
    }
}