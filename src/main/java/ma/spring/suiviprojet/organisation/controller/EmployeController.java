package ma.spring.suiviprojet.organisation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.dto.EmployeCreateDTO;
import ma.spring.suiviprojet.organisation.dto.EmployeResponseDTO;
import ma.spring.suiviprojet.organisation.service.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    // POST /api/employes
    @PostMapping
    public ResponseEntity<EmployeResponseDTO> createEmploye(@Valid @RequestBody EmployeCreateDTO dto) {
        EmployeResponseDTO nouvelEmploye = employeService.create(dto);
        return new ResponseEntity<>(nouvelEmploye, HttpStatus.CREATED);
    }

    // GET /api/employes
    @GetMapping
    public ResponseEntity<List<EmployeResponseDTO>> getAllEmployes() {
        return ResponseEntity.ok(employeService.getAll());
    }

    // GET /api/employes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EmployeResponseDTO> getEmployeById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeService.getById(id));
    }

    // PUT /api/employes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EmployeResponseDTO> updateEmploye(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeCreateDTO dto) {
        return ResponseEntity.ok(employeService.update(id, dto));
    }

    // DELETE /api/employes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Integer id) {
        employeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}